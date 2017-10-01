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

package org.ctoolkit.gwt.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A fieldset as GWT element.
 * <p/>
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.title</dt>
 * <dd>the legend title style name</dd>
 * </dl>
 * <dl>
 * <dt>.collapsable</dt>
 * <dd>the collapsable style name</dd>
 * </dl>
 * <dl>
 * <dt>.collapsed</dt>
 * <dd>the collapsed style name</dd>
 * </dl>
 * <dl>
 * <dt>.fieldset-wrapper</dt>
 * <dd>the fieldset wrapper style name</dd>
 * </dl>
 * <dl>
 * <dt>.fieldset-content</dt>
 * <dd>the fieldset content style name</dd>
 * </dl>
 * <dl>
 * <dt>.fieldset-content-wrapper</dt>
 * <dd>the fieldset content wrapper style name</dd>
 * </dl>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FieldSet
        extends Composite
        implements HasEnabled, HasWidgets
{
    private FlowPanel wrapper = new FlowPanel();

    private FlowPanel content = new FlowPanel();

    private FlowPanel contentWrapper = new FlowPanel();

    private ClickableFlowPanel legend = new ClickableFlowPanel();

    private HTML legendComponent = new HTML();

    private boolean enabled = true;

    private boolean collapsed = false;

    private Map<IsWidget, FieldSetRow> rowsMap = new HashMap<>();

    /**
     * Constructor of collapsable fieldset.
     */
    public FieldSet()
    {
        this( null );
    }

    /**
     * Constructor of collapsable fieldset.
     *
     * @param legendText the legend's text HTML content
     */
    public FieldSet( String legendText )
    {
        this( legendText, true );
    }

    /**
     * Constructor of collapsable fieldset.
     *
     * @param collapsable the boolean value whether to set this fieldset as collapsable
     */
    @UiConstructor
    public FieldSet( boolean collapsable )
    {
        this( null, collapsable );
    }

    /**
     * Constructor of collapsable fieldset.
     *
     * @param legendText  the legend's text HTML content
     * @param collapsable the boolean value whether to set this fieldset as collapsable
     */
    public FieldSet( String legendText, boolean collapsable )
    {
        CaptionPanel captionPanel = new CaptionPanel();

        wrapper.setStyleName( "fieldset-wrapper" );
        captionPanel.add( wrapper );

        if ( legendText != null )
        {
            legendComponent.setHTML( legendText );
        }
        legend.add( legendComponent );
        legend.setStyleName( "title" );
        if ( collapsable )
        {
            legend.addStyleName( "collapsable" );

            legend.addClickHandler( event -> toggle() );
            // collapsed by default
            collapse();
        }
        wrapper.add( legend );

        contentWrapper.setStyleName( "fieldset-content-wrapper" );
        wrapper.add( contentWrapper );

        content.setStyleName( "fieldset-content" );
        contentWrapper.add( content );

        initWidget( captionPanel );
    }

    /**
     * Adds a child widget to this fieldset.
     *
     * @param widget the widget to be added
     */
    @UiChild( tagname = "widget" )
    public void add( Widget widget )
    {
        content.add( widget );
        setEnabledItem( widget );
    }

    /**
     * Adds a child widget wrapped as {@link FieldSetRow} to this fieldset.
     *
     * @param widget the widget to be added
     * @param label  the label for widget
     * @return the newly created instance of {@link FieldSetRow}
     */
    @UiChild( tagname = "row" )
    public FieldSetRow addRow( IsWidget widget, String label )
    {
        FieldSetRow fieldSetRow = new FieldSetRow( label );
        fieldSetRow.setWidget( widget );

        add( fieldSetRow );
        rowsMap.put( widget, fieldSetRow );
        setEnabledItem( widget );

        return fieldSetRow;
    }

    /**
     * Adds a child widget to this fieldset as row and displays the label
     * and component as a block elements, instead of inline-block.
     *
     * @param widget the widget to be added as a row
     * @param label  the label for widget
     */
    @UiChild( tagname = "block" )
    public void addRowAsBlock( IsWidget widget, String label )
    {
        addRow( widget, label ).displayAsBlock();
    }

    /**
     * Adds a child widget wrapped as {@link FieldSetRow} to this fieldset.
     *
     * @param widget  the widget to be added
     * @param label   the label for widget
     * @param tooltip the tooltip's note
     */
    public void addRow( IsWidget widget, String label, String tooltip )
    {
        FieldSetRow fieldSetRow = new FieldSetRow( label );
        fieldSetRow.setTooltip( tooltip );
        fieldSetRow.setWidget( widget );

        add( fieldSetRow );
        rowsMap.put( widget, fieldSetRow );
        setEnabledItem( widget );
    }

    /**
     * Adds a child widget wrapped as {@link FieldSetRow} to this fieldset
     * where component will be rendered first and label as second.
     *
     * @param widget the widget to be added
     * @param label  the label for widget
     */
    @UiChild( tagname = "rowWidgetFirst" )
    public void addRowComponentFirst( IsWidget widget, String label )
    {
        FieldSetRow fieldSetRow = new FieldSetRow( label, true );
        fieldSetRow.setWidget( widget );

        add( fieldSetRow );
        rowsMap.put( widget, fieldSetRow );
        setEnabledItem( widget );
    }

    /**
     * Returns associated {@link FieldSetRow} to the given widget.
     *
     * @param widget the widget
     * @return the associated row instance
     */
    public FieldSetRow findRow( IsWidget widget )
    {
        return rowsMap.get( widget );
    }

    /**
     * Returns the boolean value whether either this fieldset is being collapsed or not.
     *
     * @return true if collapsed
     */
    public boolean isCollapsed()
    {
        return collapsed;
    }

    /**
     * Sets this fieldset to be collapsed.
     *
     * @param collapsed true to be set as collapsed
     */
    public void setCollapsed( boolean collapsed )
    {
        if ( collapsed )
        {
            collapse();
        }
        else
        {
            expand();
        }
    }

    /**
     * Returns the legend panel.
     *
     * @return the legend panel
     */
    public ClickableFlowPanel getLegend()
    {
        return legend;
    }

    /**
     * Returns the content wrapper panel.
     *
     * @return the content wrapper panel
     */
    public FlowPanel getContentWrapper()
    {
        return contentWrapper;
    }

    /**
     * Returns the wrapper panel.
     *
     * @return the wrapper panel
     */
    public FlowPanel getWrapper()
    {
        return wrapper;
    }

    /**
     * Sets the legend's text HTML content.
     *
     * @param legend the legend's text HTML content
     */
    public void setLegendText( String legend )
    {
        legendComponent.setHTML( legend );
    }

    public void onComplete( boolean collapsed )
    {
    }

    /**
     * Expands this fieldset.
     */
    public void expand()
    {
        collapsed = false;
        contentWrapper.setVisible( true );
        onComplete( collapsed );
        legend.removeStyleName( "collapsed" );
    }

    /**
     * Collapses this fieldset.
     */
    public void collapse()
    {
        collapsed = true;
        contentWrapper.setVisible( false );
        onComplete( collapsed );
        legend.addStyleName( "collapsed" );
    }

    /**
     * Toggle expand/collapse this fieldset.
     */
    public void toggle()
    {
        if ( collapsed )
        {
            expand();
        }
        else
        {
            collapse();
        }
    }

    /**
     * Clears the content of this fieldset.
     */
    public void clear()
    {
        content.clear();
    }

    @Override
    public Iterator<Widget> iterator()
    {
        return content.iterator();
    }

    @Override
    public boolean remove( Widget widget )
    {
        return content.remove( widget );
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    /**
     * Set enabled on children
     *
     * @param isWidget widget
     */
    private void setEnabledItem( IsWidget isWidget )
    {
        if ( isWidget instanceof HasEnabled )
        {
            ( ( HasEnabled ) isWidget ).setEnabled( enabled );
        }
    }

    public class ClickableFlowPanel
            extends FlowPanel
            implements HasClickHandlers
    {

        @Override
        public HandlerRegistration addClickHandler( ClickHandler handler )
        {
            return addDomHandler( handler, ClickEvent.getType() );
        }
    }
}