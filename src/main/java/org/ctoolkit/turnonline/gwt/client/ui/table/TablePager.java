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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

/**
 * A pager for controlling a {@link HasRows} that only supports simple page
 * navigation.
 * <p/>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.cellview.SimplePagerExample}
 * </p>
 */
public class TablePager
        extends AbstractPager
{
    private static int DEFAULT_FAST_FORWARD_ROWS = 0;

    private final ImageButton fastForward;

    private final int fastForwardRows;

    private final ImageButton firstPage;

    /**
     * We use an {@link HTML} so we can embed the loading image.
     */
    private final HTML label = new HTML();

    private final ImageButton lastPage;

    private final ImageButton nextPage;

    private final ImageButton prevPage;

    /**
     * Construct a {@link TablePager} with the default text location.
     */
    public TablePager()
    {
        this( TextLocation.CENTER );
    }

    /**
     * Construct a {@link TablePager} with the specified text location.
     *
     * @param location the location of the text relative to the buttons
     */
    @UiConstructor
    // Hack for Google I/O demo
    public TablePager( TextLocation location )
    {
        this( location,
                false,
                DEFAULT_FAST_FORWARD_ROWS,
                false );
    }

    /**
     * Construct a {@link TablePager} with the specified resources.
     *
     * @param location              the location of the text relative to the buttons
     * @param showFastForwardButton if true, show a fast-forward button that
     *                              advances by a larger increment than a single page
     * @param fastForwardRows       the number of rows to jump when fast forwarding
     * @param showLastPageButton    if true, show a button to go the the last page
     */
    public TablePager( TextLocation location,
                       boolean showFastForwardButton,
                       final int fastForwardRows,
                       boolean showLastPageButton )
    {
        this.fastForwardRows = fastForwardRows;

        label.setStyleName( "pager-label" );

        // Create the buttons.
        firstPage = new ImageButton( "pager-first-page" );
        firstPage.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                firstPage();
            }
        } );
        nextPage = new ImageButton( "pager-next-page" );
        nextPage.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                nextPage();
            }
        } );
        prevPage = new ImageButton( "pager-prev-page" );
        prevPage.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                previousPage();
            }
        } );
        if ( showLastPageButton )
        {
            lastPage = new ImageButton( "pager-last-page" );
            lastPage.addClickHandler( new ClickHandler()
            {
                public void onClick( ClickEvent event )
                {
                    lastPage();
                }
            } );
        }
        else
        {
            lastPage = null;
        }
        if ( showFastForwardButton )
        {
            fastForward = new ImageButton( "pager-fast-forward-page" );
            fastForward.addClickHandler( new ClickHandler()
            {
                public void onClick( ClickEvent event )
                {
                    setPage( getPage() + getFastForwardPages() );
                }
            } );
        }
        else
        {
            fastForward = null;
        }

        // Construct the widget.
        FlowPanel layout = new FlowPanel();
        layout.setStyleName( "table-pager" );
        initWidget( layout );

        if ( location == TextLocation.LEFT )
        {
            layout.add( label );
        }

        layout.add( firstPage );
        layout.add( prevPage );

        if ( location == TextLocation.CENTER )
        {
            layout.add( label );
        }

        layout.add( nextPage );

        if ( showFastForwardButton )
        {
            layout.add( fastForward );
        }
        if ( showLastPageButton )
        {
            layout.add( lastPage );
        }
        if ( location == TextLocation.RIGHT )
        {
            layout.add( label );
        }

        // Disable the buttons by default.
        setDisplay( null );

        setRangeLimited( false );


    }

    @Override
    public void setDisplay( HasRows display )
    {
        // Enable or disable all buttons.
        boolean disableButtons = ( display == null );
        setFastForwardDisabled( disableButtons );
        setNextPageButtonsDisabled( disableButtons );
        setPrevPageButtonsDisabled( disableButtons );
        super.setDisplay( display );
    }

    /**
     * Let the page know that the table is loading. Call this method to clear all
     * data from the table and hide the current range when new data is being
     * loaded into the table.
     */
    public void startLoading()
    {
        getDisplay().setRowCount( 0, true );
        label.setHTML( "" );
    }

    /**
     * Get the text to display in the pager that reflects the state of the pager.
     *
     * @return the text
     */
    protected String createText()
    {
        // Default text is 1 based.
        NumberFormat formatter = NumberFormat.getFormat( "#,###" );
        HasRows display = getDisplay();
        Range range = display.getVisibleRange();
        int pageStart = range.getStart() + 1;
        int pageSize = range.getLength();
        int dataSize = display.getRowCount();
        int endIndex = Math.min( dataSize, pageStart + pageSize - 1 );
        endIndex = Math.max( pageStart, endIndex );

        return formatter.format( pageStart ) + " - " + formatter.format( endIndex );
    }

    @Override
    protected void onRangeOrRowCountChanged()
    {
        label.setText( createText() );

        // Update the prev and first buttons.
        setPrevPageButtonsDisabled( !hasPreviousPage() );

        // Update the next and last buttons.
        setNextPageButtonsDisabled( !hasNextPage() );
        setFastForwardDisabled( !hasNextPages( getFastForwardPages() ) );
    }

    /**
     * Check if the next button is disabled. Visible for testing.
     */
    boolean isNextButtonDisabled()
    {
        return !nextPage.isEnabled();
    }

    /**
     * Check if the previous button is disabled. Visible for testing.
     */
    boolean isPreviousButtonDisabled()
    {
        return !prevPage.isEnabled();
    }

    /**
     * Get the number of pages to fast forward based on the current page size.
     *
     * @return the number of pages to fast forward
     */
    private int getFastForwardPages()
    {
        int pageSize = getPageSize();
        return pageSize > 0 ? fastForwardRows / pageSize : 0;
    }

    /**
     * Enable or disable the fast forward button.
     *
     * @param disabled true to disable, false to enable
     */
    private void setFastForwardDisabled( boolean disabled )
    {
        if ( fastForward == null )
        {
            return;
        }

        fastForward.setEnabled( !disabled );
    }

    /**
     * Enable or disable the next page buttons.
     *
     * @param disabled true to disable, false to enable
     */
    private void setNextPageButtonsDisabled( boolean disabled )
    {
        nextPage.setEnabled( !disabled );
        if ( lastPage != null )
        {
            lastPage.setEnabled( !disabled );
        }
    }

    /**
     * Enable or disable the previous page buttons.
     *
     * @param disabled true to disable, false to enable
     */
    private void setPrevPageButtonsDisabled( boolean disabled )
    {
        firstPage.setEnabled( !disabled );
        prevPage.setEnabled( !disabled );
    }

    /**
     * The location of the text relative to the paging buttons.
     */
    public static enum TextLocation
    {
        CENTER, LEFT, RIGHT
    }

    /**
     * An {@link com.google.gwt.user.client.ui.Image} that acts as a button.
     */
    private static class ImageButton
            extends Button
    {
        public ImageButton( String style )
        {
            setStyleName( "pager-button" );
            addStyleName( style );
        }

        @Override
        public void onBrowserEvent( Event event )
        {
            // Ignore events if disabled.
            if ( !isEnabled() )
            {
                return;
            }

            super.onBrowserEvent( event );
        }
    }
}
