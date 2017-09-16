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

package org.ctoolkit.turnonline.gwt.client.ui;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * The fieldset row widget implementation that represents a single line in the parent fieldset.
 * <p/>
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.form-row</dt>
 * <dd>the fieldset row style name</dd>
 * </dl>
 * <dl>
 * <dt>.component</dt>
 * <dd>the widget component style name</dd>
 * </dl>
 * <dl>
 * <dt>.label</dt>
 * <dd>the label row style name</dd>
 * </dl>
 * <dl>
 * <dt>.note</dt>
 * <dd>the note row style name</dd>
 * </dl>
 * <dl>
 * <dt>.tooltip</dt>
 * <dd>the tooltip row style name</dd>
 * </dl>
 * <dl>
 * <dt>.tooltip-text</dt>
 * <dd>the tooltip text row style name</dd>
 * </dl>
 * <dl>
 * <dt>.component-first</dt>
 * <dd>the component first row style name</dd>
 * </dl>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FieldSetRow
        extends Composite
{
    private static final String LABEL_SEPARATOR = ":";

    private FlowPanel formRow = new FlowPanel();

    private HTML label = new HTML();

    private FlowPanel component = new FlowPanel();

    private TooltipPanel tooltipText;

    private HTML note;

    private String text;

    private boolean componentFirst;

    /**
     * Default constructor.
     */
    public FieldSetRow()
    {
        this( null, false );
    }

    /**
     * Constructs a new {@link FieldSetRow} with label and component.
     *
     * @param labelText the HTML text content as label for widget
     */
    public FieldSetRow( String labelText )
    {
        this( labelText, false );
    }

    /**
     * Constructs a new {@link FieldSetRow} component.
     *
     * @param componentFirst the boolean value to determine if component will be rendered before label.
     *                       If is set to <code>true</code> component will be rendered first,
     *                       if is set to <code>false</code> component will be rendered after label
     */
    @UiConstructor
    public FieldSetRow( boolean componentFirst )
    {
        this( null, componentFirst );
    }

    /**
     * Constructs a new {@link FieldSetRow} with label and component.
     * ComponentFirst attribute determine the position of label/component.
     *
     * @param labelText      the HTML text content as label for widget
     * @param componentFirst the boolean value to determine if component will be rendered before label.
     *                       If is set to <code>true</code> component will be rendered first,
     *                       if is set to <code>false</code> component will be rendered after label
     */
    public FieldSetRow( String labelText, boolean componentFirst )
    {
        this.componentFirst = componentFirst;
        initWidget( formRow );
        formRow.setStyleName( "form-row" );

        if ( labelText != null )
        {
            setLabelText( labelText );
        }
        component.setStyleName( "component" );

        label.setStyleName( "label" );

        if ( componentFirst )
        {
            addStyleName( "component-first" );
            addRowWidget( component );
            addRowWidget( label );
        }
        else
        {
            addRowWidget( label );
            addRowWidget( component );
        }
    }

    /**
     * Sets the widget that will be wrapped as fieldset row.
     *
     * @param widget the widget to be set
     */
    @UiChild( tagname = "widget", limit = 1 )
    public void setWidget( IsWidget widget )
    {
        component.add( widget );
    }

    /**
     * Adds a widget to this row.
     *
     * @param widget the widget to be added
     */
    @UiChild( tagname = "rowWidget" )
    public void addRowWidget( IsWidget widget )
    {
        formRow.add( widget );
    }

    /**
     * Sets the HTML text content as label for widget.
     *
     * @param text the HTML text content
     */
    public void setLabelText( String text )
    {
        if ( text == null )
        {
            label.setHTML( "" );
        }
        else
        {
            label.setHTML( text + ( componentFirst ? "" : LABEL_SEPARATOR ) );
        }
    }

    /**
     * Sets row note.
     *
     * @param text the row note text
     */
    public void setNote( String text )
    {
        if ( note == null )
        {
            note = new HTML( text );
            note.setStyleName( "note" );
            addRowWidget( note );
        }

        note.setText( text );
    }

    /**
     * Displays label and component as block elements, instead of inline-block.
     */
    public void displayAsBlock()
    {
        FlowPanel clearer = new FlowPanel();
        clearer.getElement().setAttribute( "style", "clear:both;" );

        label.getParent().getElement().insertAfter( clearer.getElement(), label.getElement() );
    }

    /**
     * Sets note for fieldset row and appends it at the end of the fieldset row.
     *
     * @param text the text to be set as a note
     */
    public void setTooltip( String text )
    {
        HTML tooltip = new HTML();
        tooltip.setStyleName( "tooltip" );
        formRow.add( tooltip );

        this.text = text;

        tooltip.addMouseOverHandler( mouseOverEvent -> {
            createTooltipText();
            showTooltipText( true );
        } );

        tooltip.addMouseOutHandler( mouseOutEvent -> showTooltipText( false ) );
    }

    private void createTooltipText()
    {
        if ( tooltipText == null )
        {
            tooltipText = new TooltipPanel();
            tooltipText.setVisible( false );
            tooltipText.setStyleName( "tooltip-text" );
            tooltipText.add( new HTML( text ) );

            tooltipText.addMouseOverHandler( mouseOverEvent -> showTooltipText( true ) );

            tooltipText.addMouseOutHandler( mouseOutEvent -> showTooltipText( false ) );

            formRow.add( tooltipText );
        }
    }

    private void showTooltipText( boolean show )
    {
        tooltipText.setVisible( show );
    }

    private class TooltipPanel
            extends FlowPanel
            implements HasMouseOverHandlers, HasMouseOutHandlers
    {
        @Override
        public HandlerRegistration addMouseOverHandler( MouseOverHandler mouseOverHandler )
        {
            return addDomHandler( mouseOverHandler, MouseOverEvent.getType() );
        }

        @Override
        public HandlerRegistration addMouseOutHandler( MouseOutHandler mouseOutHandler )
        {
            return addDomHandler( mouseOutHandler, MouseOutEvent.getType() );
        }
    }
}