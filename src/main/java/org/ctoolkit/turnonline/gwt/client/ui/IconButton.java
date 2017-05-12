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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * A button decorated by icon based on its type.
 * <p/>
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.button</dt>
 * <dd>the button style name</dd>
 * </dl>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class IconButton
        extends FlowPanel
        implements HasClickHandlers
{
    private Label label;

    private FlowPanel icon;

    public IconButton( Type type )
    {
        this( type, null );
    }

    public IconButton( Type type, String label )
    {
        setStyleName( "button" );

        icon = new FlowPanel();
        addStyleName( type.getStyle() );
        add( icon );

        getElement().setAttribute( "style", "position:relative;top: 0px;left: 0px;" );

        if ( label != null )
        {
            setButtonLabel( label );
        }
    }

    public void setType( Type type )
    {
        icon.setStyleName( type.getStyle() );
    }

    public void setButtonLabel( String text )
    {
        if ( this.label == null )
        {
            this.label = new Label();
            this.label.getElement().setAttribute( "style", "float:left" );
            add( this.label );
        }
        this.label.setText( text );
    }

    @Override
    public HandlerRegistration addClickHandler( ClickHandler clickHandler )
    {
        return addDomHandler( clickHandler, ClickEvent.getType() );
    }

    public enum Type
    {
        SEARCH( "button-search" ),
        CLOSE( "button-close" ),
        UPLOAD( "button-upload" );

        private String style;

        Type( String style )
        {
            this.style = style;
        }

        public String getStyle()
        {
            return style;
        }
    }
}
