/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.ctoolkit.turnonline.gwt.client.ui.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A <code>CellTable</code> wrapper bundled together with {@link TablePager}.</p>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class Table<T>
        extends Composite
        implements HasData<T>, Focusable, HasKeyboardPagingPolicy
{
    public static final String EVENT_CLICK = "click";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private static final String DEFAULT_TABLE_WIDTH = "100%";

    TableDataProvider<T> dataProvider;

    private CellTable<T> table = new CellTable<T>( DEFAULT_PAGE_SIZE, new ResourceProxy() );

    private int iconColumns = 0;

    private boolean displayAdded = false;

    private Map<Column, String> sortableColumns = new HashMap<Column, String>();

    public Table()
    {
        super();

        FlowPanel panel = new FlowPanel();

        table.setWidth( DEFAULT_TABLE_WIDTH );

        TablePager pager = new TablePager( TablePager.TextLocation.LEFT );
        pager.setDisplay( table );

        FlowPanel headerPanel = new FlowPanel();
        headerPanel.setStyleName( "table-header-panel" );
        panel.add( headerPanel );

        headerPanel.add( pager );

        panel.add( table );

        initWidget( panel );

        dataProvider = new TableDataProvider<T>()
        {
            @Override
            protected void onRangeChanged( HasData<T> display )
            {
                TableQuery action = new TableQuery();

                action.setLength( getPageSize() );
                action.setStart( display.getVisibleRange().getStart() );

                if ( table.getColumnSortList().size() > 0 )
                {
                    ColumnSortList.ColumnSortInfo columnSortInfo = table.getColumnSortList().get( 0 );
                    action.setAscending( columnSortInfo.isAscending() );
                    action.setSortBy( sortableColumns.get( columnSortInfo.getColumn() ) );
                }

                fireUpdateRowData( action );
            }
        };

        table.addColumnSortHandler( new ColumnSortEvent.AsyncHandler( table ) );
    }

    public Table( int pageSize )
    {
        table.setPageSize( pageSize );
    }

    public void addSortableColumn( String name, Column column )
    {
        sortableColumns.put( column, name );
        column.setSortable( true );
    }

    public void setDefaultSortColumn( Column column, boolean ascending )
    {
        table.getColumnSortList().push( new ColumnSortList.ColumnSortInfo( column, ascending ) );
    }

    public void redraw()
    {
        if ( !displayAdded )
        {
            dataProvider.addDataDisplay( this );
            displayAdded = true;
        }
        getCellTable().setVisibleRangeAndClearData( new Range( table.getPageStart(), table.getPageSize() ), true );
    }

    public void reset()
    {
        getCellTable().setVisibleRangeAndClearData( new Range( 0, table.getPageSize() ), true );
    }

    protected abstract void fireUpdateRowData( TableQuery query );

    public CellTable<T> getCellTable()
    {
        return table;
    }

    public int getPageSize()
    {
        return table.getPageSize() + 1; // get one extra record to be available show/hide next button in TableDataProvider
    }

    /**
     * Adds a column to the end of the table.
     *
     * @param col the column to be added
     */
    public void addColumn( Column<T, ?> col )
    {
        table.addColumn( col );
    }

    /**
     * Adds a column to the end of the table with an associated header.
     *
     * @param col    the column to be added
     * @param header the associated {@link Header}
     */
    public void addColumn( Column<T, ?> col, Header<?> header )
    {
        table.addColumn( col, header );
    }

    /**
     * Adds a column to the end of the table with an associated header and footer.
     *
     * @param col    the column to be added
     * @param header the associated {@link Header}
     * @param footer the associated footer (as a {@link Header} object)
     */
    public void addColumn( Column<T, ?> col, Header<?> header, Header<?> footer )
    {
        table.addColumn( col, header, footer );
    }

    /**
     * Adds a column to the end of the table with an associated String header.
     *
     * @param col          the column to be added
     * @param headerString the associated header text, as a String
     */
    public void addColumn( Column<T, ?> col, String headerString )
    {
        table.addColumn( col, headerString );
    }

    /**
     * Adds a column to the end of the table with an associated {@link SafeHtml}
     * header.
     *
     * @param col        the column to be added
     * @param headerHtml the associated header text, as safe HTML
     */
    public void addColumn( Column<T, ?> col, SafeHtml headerHtml )
    {
        table.addColumn( col, headerHtml );
    }

    /**
     * Adds a column to the end of the table with an associated String header and
     * footer.
     *
     * @param col          the column to be added
     * @param headerString the associated header text, as a String
     * @param footerString the associated footer text, as a String
     */
    public void addColumn( Column<T, ?> col, String headerString, String footerString )
    {
        table.addColumn( col, headerString, footerString );
    }

    /**
     * Adds a column to the end of the table with an associated {@link SafeHtml}
     * header and footer.
     *
     * @param col        the column to be added
     * @param headerHtml the associated header text, as safe HTML
     * @param footerHtml the associated footer text, as safe HTML
     */
    public void addColumn( Column<T, ?> col, SafeHtml headerHtml, SafeHtml footerHtml )
    {
        table.addColumn( col, headerHtml, footerHtml );
    }

    /**
     * Add an action column to the end fo the table with associated header and {@link com.google.gwt.cell.client.ActionCell.Delegate}
     * action.
     *
     * @param className         name of specified icon class
     * @param classNameDisabled name of specified disabled icon class
     *                          String buttonClassName,
     * @param delegate          the delegate that will handle events from the cell.
     */
    public void addIconColumn( String className,
                               String classNameDisabled,
                               String buttonClassName,
                               IconCell.Delegate<T> delegate )
    {
        addIconColumn( className, classNameDisabled, buttonClassName, delegate, null );
    }

    /**
     * Add an action column to the end fo the table with associated header and {@link com.google.gwt.cell.client.ActionCell.Delegate}
     * action.
     *
     * @param className         name of specified icon class
     * @param classNameDisabled name of specified disabled icon class
     * @param buttonClassName   the style name to style the button
     * @param delegate          the delegate that will handle events from the cell.
     */
    public void addIconColumn( String className,
                               String classNameDisabled,
                               String buttonClassName,
                               IconCell.Delegate<T> delegate,
                               Header<T> header )
    {
        table.addColumn( new Column<T, T>( new IconCell<T>( className, classNameDisabled, buttonClassName, delegate ) )
        {
            @Override
            public T getValue( T object )
            {
                return object;
            }
        }, header );

        iconColumns++;
    }

    public void setRowCount( int size, boolean isExact )
    {
        table.setRowCount( size, isExact );
    }

    @Override
    public void setVisibleRange( int start, int length )
    {
        table.setVisibleRange( start, length );
    }

    @Override
    public HandlerRegistration addRangeChangeHandler( RangeChangeEvent.Handler handler )
    {
        return table.addRangeChangeHandler( handler );
    }

    @Override
    public HandlerRegistration addRowCountChangeHandler( RowCountChangeEvent.Handler handler )
    {
        return table.addRowCountChangeHandler( handler );
    }

    public int getRowCount()
    {
        return table.getRowCount();
    }

    public void setRowCount( int count )
    {
        table.setRowCount( count );
    }

    @Override
    public Range getVisibleRange()
    {
        return table.getVisibleRange();
    }

    @Override
    public void setVisibleRange( Range range )
    {
        table.setVisibleRange( range );
    }

    @Override
    public boolean isRowCountExact()
    {
        return table.isRowCountExact();
    }

    @Override
    public int getTabIndex()
    {
        return table.getTabIndex();
    }

    @Override
    public void setTabIndex( int index )
    {
        table.setTabIndex( index );
    }

    @Override
    public void setAccessKey( char key )
    {
        table.setAccessKey( key );
    }

    @Override
    public void setFocus( boolean focused )
    {
        table.setFocus( focused );
    }

    @Override
    public SelectionModel<? super T> getSelectionModel()
    {
        return table.getSelectionModel();
    }

    @Override
    public void setSelectionModel( SelectionModel<? super T> selectionModel )
    {
        table.setSelectionModel( selectionModel );
    }

    @Override
    public T getVisibleItem( int indexOnPage )
    {
        return table.getVisibleItem( indexOnPage );
    }

    @Override
    public int getVisibleItemCount()
    {
        return table.getVisibleItemCount();
    }

    @Override
    public Iterable<T> getVisibleItems()
    {
        return table.getVisibleItems();
    }

    @Override
    public void setRowData( int start, List<? extends T> values )
    {
        table.setRowData( start, values );
    }

    @Override
    public void setVisibleRangeAndClearData( Range range, boolean forceRangeChangeEvent )
    {
        table.setVisibleRangeAndClearData( range, forceRangeChangeEvent );
    }

    @Override
    public HandlerRegistration addCellPreviewHandler( CellPreviewEvent.Handler<T> tHandler )
    {
        return table.addCellPreviewHandler( tHandler );
    }

    @Override
    public KeyboardPagingPolicy getKeyboardPagingPolicy()
    {
        return table.getKeyboardPagingPolicy();
    }

    @Override
    public void setKeyboardPagingPolicy( KeyboardPagingPolicy policy )
    {
        table.setKeyboardPagingPolicy( policy );
    }

    @Override
    public KeyboardSelectionPolicy getKeyboardSelectionPolicy()
    {
        return table.getKeyboardSelectionPolicy();
    }

    @Override
    public void setKeyboardSelectionPolicy( KeyboardSelectionPolicy policy )
    {
        table.setKeyboardSelectionPolicy( policy );
    }

    public boolean isIconColumn( CellPreviewEvent<T> event )
    {
        if ( iconColumns == 0 )
        {
            return false;
        }

        return getCellTable().getColumnCount() - iconColumns == event.getColumn();
    }

    public boolean isRowClick( CellPreviewEvent<T> event )
    {
        return EVENT_CLICK.equals( event.getNativeEvent().getType() ) && !isIconColumn( event );
    }

    public String getSearchText()
    {
        return null;
    }

    public static class TableQuery
            implements Serializable, IsSerializable
    {
        private static final long serialVersionUID = 1L;

        private int start;

        private int length;

        private boolean ascending;

        private String sortBy;

        private String query;

        public TableQuery()
        {
        }

        public TableQuery( String query )
        {
            this.query = query;
        }

        public int getStart()
        {
            return start;
        }

        public void setStart( int start )
        {
            this.start = start;
        }

        public int getLength()
        {
            return length;
        }

        public void setLength( int length )
        {
            this.length = length;
        }

        public boolean isAscending()
        {
            return ascending;
        }

        public void setAscending( boolean ascending )
        {
            this.ascending = ascending;
        }

        public String getSortBy()
        {
            return sortBy;
        }

        public void setSortBy( String sortBy )
        {
            this.sortBy = sortBy;
        }

        public String getQuery()
        {
            return query;
        }

        public void setQuery( String query )
        {
            this.query = query;
        }

        @Override
        public String toString()
        {
            return "FilterAction{" +
                    "start=" + start +
                    ", length=" + length +
                    ", ascending=" + ascending +
                    ", sortBy='" + sortBy + '\'' +
                    ", query='" + query + '\'' +
                    '}';
        }
    }

    public static class ResourceProxy
            implements CellTable.Resources
    {

        private CellTable.Resources resources;

        public ResourceProxy()
        {
            this.resources = GWT.create( CellTable.Resources.class );
        }

        @Override
        public ImageResource cellTableFooterBackground()
        {
            return resources.cellTableFooterBackground();
        }

        @Override
        public ImageResource cellTableHeaderBackground()
        {
            return resources.cellTableHeaderBackground();
        }

        @Override
        public ImageResource cellTableLoading()
        {
            return resources.cellTableLoading();
        }

        @Override
        public ImageResource cellTableSelectedBackground()
        {
            return resources.cellTableSelectedBackground();
        }

        @Override
        public ImageResource cellTableSortAscending()
        {
            return resources.cellTableSortAscending();
        }

        @Override
        public ImageResource cellTableSortDescending()
        {
            return resources.cellTableSortDescending();
        }

        @Override
        public CellTable.Style cellTableStyle()
        {
            return new StyleProxy();
        }
    }

    public static class StyleProxy
            implements CellTable.Style
    {

        @Override
        public String cellTableCell()
        {
            return "cell";
        }

        @Override
        public String cellTableEvenRow()
        {
            return "event-row";
        }

        @Override
        public String cellTableEvenRowCell()
        {
            return "event-row-cell";
        }

        @Override
        public String cellTableFirstColumn()
        {
            return "first-column";
        }

        @Override
        public String cellTableFirstColumnFooter()
        {
            return "first-column-footer";
        }

        @Override
        public String cellTableFirstColumnHeader()
        {
            return "first-column-header";
        }

        @Override
        public String cellTableFooter()
        {
            return "footer";
        }

        @Override
        public String cellTableHeader()
        {
            return "header";
        }

        @Override
        public String cellTableHoveredRow()
        {
            return "hovered-row";
        }

        @Override
        public String cellTableHoveredRowCell()
        {
            return "hovered-row-cell";
        }

        @Override
        public String cellTableKeyboardSelectedCell()
        {
            return "keyboard-selected-cell";
        }

        @Override
        public String cellTableKeyboardSelectedRow()
        {
            return "keyboard-selected-row";
        }

        @Override
        public String cellTableKeyboardSelectedRowCell()
        {
            return "keyboard-selected-row-cell";
        }

        @Override
        public String cellTableLastColumn()
        {
            return "last-column";
        }

        @Override
        public String cellTableLastColumnFooter()
        {
            return "last-column-footer";
        }

        @Override
        public String cellTableLastColumnHeader()
        {
            return "last-column-header";
        }

        @Override
        public String cellTableLoading()
        {
            return "progress";
        }

        @Override
        public String cellTableOddRow()
        {
            return "odd-row";
        }

        @Override
        public String cellTableOddRowCell()
        {
            return "odd-row-cell";
        }

        @Override
        public String cellTableSelectedRow()
        {
            return "selected-row";
        }

        @Override
        public String cellTableSelectedRowCell()
        {
            return "selected-row-cell";
        }

        @Override
        public String cellTableSortableHeader()
        {
            return "sortable-header";
        }

        @Override
        public String cellTableSortedHeaderAscending()
        {
            return "sorted-header-ascending";
        }

        @Override
        public String cellTableSortedHeaderDescending()
        {
            return "sorted-header-descending";
        }

        @Override
        public String cellTableWidget()
        {
            return "table-widget";
        }

        @Override
        public boolean ensureInjected()
        {
            return false;
        }

        @Override
        public String getText()
        {
            return null;
        }

        @Override
        public String getName()
        {
            return null;
        }
    }
}
