package org.ctoolkit.turnonline.gwt.client.ui;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
        this( null, null, false );
    }

    /**
     * Constructs a new {@link FieldSetRow} with label and component.
     *
     * @param labelText the HTML text content as label for widget
     * @param widget    any widget that implements {@link com.google.gwt.user.client.ui.IsWidget} interface
     */
    public FieldSetRow( String labelText, IsWidget widget )
    {
        this( labelText, widget, false );
    }

    /**
     * Constructs a new {@link FieldSetRow} with label and component.
     * ComponentFirst attribute determine the position of label/component.
     *
     * @param labelText      the HTML text content as label for widget
     * @param widget         any widget that implements {@link com.google.gwt.user.client.ui.IsWidget} interface
     * @param componentFirst the boolean value to determine if component will be rendered before label.
     *                       If is set to <code>true</code> component will be rendered first,
     *                       if is set to <code>false</code> component will be rendered after label
     */
    public FieldSetRow( String labelText, IsWidget widget, boolean componentFirst )
    {
        this.componentFirst = componentFirst;
        initWidget( formRow );
        formRow.setStyleName( "form-row" );

        if ( labelText != null )
        {
            setLabelText( labelText );
        }
        if ( widget != null )
        {
            setWidget( widget );
        }
        component.setStyleName( "component" );

        label.setStyleName( "label" );

        if ( componentFirst )
        {
            addStyleName( "component-first" );
            add( component );
            add( label );
        }
        else
        {
            add( label );
            add( component );
        }
    }

    /**
     * Sets the widget that will be wrapped as fieldset row.
     *
     * @param widget the widget to be set
     */
    public void setWidget( IsWidget widget )
    {
        component.add( widget );
    }

    /**
     * Adds a widget to this row.
     *
     * @param widget the widget to be added
     * @return instance of {@link FieldSetRow}
     */
    public FieldSetRow add( IsWidget widget )
    {
        formRow.add( widget );

        return this;
    }

    /**
     * Sets the HTML text content as label for widget.
     *
     * @param text the HTML text content
     * @return instance of {@link FieldSetRow}
     */
    public FieldSetRow setLabelText( String text )
    {
        if ( text == null )
        {
            label.setHTML( "" );
        }
        else
        {
            label.setHTML( text + ( componentFirst ? "" : LABEL_SEPARATOR ) );
        }

        return this;
    }

    /**
     * Sets row note.
     *
     * @param text the row note text
     * @return instance of {@link FieldSetRow}
     */
    public FieldSetRow setNote( String text )
    {
        if ( note == null )
        {
            note = new HTML( text );
            note.setStyleName( "note" );
            add( note );
        }

        note.setText( text );

        return this;
    }

    /**
     * Displays label and component as block elements, instead of inline-block.
     *
     * @return instance of {@link FieldSetRow}
     */
    public FieldSetRow displayAsBlock()
    {
        FlowPanel clearer = new FlowPanel();
        clearer.getElement().setAttribute( "style", "clear:both;" );

        label.getParent().getElement().insertAfter( clearer.getElement(), label.getElement() );

        return this;
    }

    /**
     * Sets note for fieldset row and appends it at the end of the fieldset row.
     *
     * @param text the text to be set as a note
     * @return instance of {@link FieldSetRow}
     */
    public FieldSetRow setTooltip( String text )
    {
        HTML tooltip = new HTML();
        tooltip.setStyleName( "tooltip" );
        formRow.add( tooltip );

        this.text = text;

        tooltip.addMouseOverHandler( new MouseOverHandler()
        {
            @Override
            public void onMouseOver( MouseOverEvent mouseOverEvent )
            {
                createTooltipText();
                showTooltipText( true );
            }
        } );

        tooltip.addMouseOutHandler( new MouseOutHandler()
        {
            @Override
            public void onMouseOut( MouseOutEvent mouseOutEvent )
            {
                showTooltipText( false );
            }
        } );

        return this;
    }

    private void createTooltipText()
    {
        if ( tooltipText == null )
        {
            tooltipText = new TooltipPanel();
            tooltipText.setVisible( false );
            tooltipText.setStyleName( "tooltip-text" );
            tooltipText.add( new HTML( text ) );

            tooltipText.addMouseOverHandler( new MouseOverHandler()
            {
                @Override
                public void onMouseOver( MouseOverEvent mouseOverEvent )
                {
                    showTooltipText( true );
                }
            } );

            tooltipText.addMouseOutHandler( new MouseOutHandler()
            {
                @Override
                public void onMouseOut( MouseOutEvent mouseOutEvent )
                {
                    showTooltipText( false );
                }
            } );

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