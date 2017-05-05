/*
 * Comvai GWT, Comvai specific GWT components
 * Copyright (C) 2012 Comvai, s.r.o. All Rights Reserved.
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
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class ClosableCaption
        extends FlowPanel
        implements DialogBox.Caption
{
    private static final String CAPTION_STYLE = "caption";

    private static final String TITLE_STYLE = "title";

    private HTML title = new HTML();

    public ClosableCaption()
    {
        setStyleName( CAPTION_STYLE );
    }

    public void init( final DialogBox dialogBox )
    {
        add( title );

        IconButton button = new IconButton( IconButton.Type.CLOSE );
        button.addClickHandler( new ClickHandler()
        {
            @Override
            public void onClick( ClickEvent event )
            {
                dialogBox.hide();
            }
        } );

        add( button );

        title.setStyleName( TITLE_STYLE );
    }

    @Override
    public String getHTML()
    {
        return title.getHTML();
    }

    @Override
    public void setHTML( SafeHtml html )
    {
        title.setHTML( html );
    }

    @Override
    public void setHTML( String html )
    {
        title.setHTML( html );
    }

    @Override
    public HandlerRegistration addMouseDownHandler( MouseDownHandler handler )
    {
        return addDomHandler( handler, MouseDownEvent.getType() );
    }

    @Override
    public HandlerRegistration addMouseMoveHandler( MouseMoveHandler handler )
    {
        return addDomHandler( handler, MouseMoveEvent.getType() );
    }

    @Override
    public HandlerRegistration addMouseOutHandler( MouseOutHandler handler )
    {
        return addDomHandler( handler, MouseOutEvent.getType() );
    }

    @Override
    public HandlerRegistration addMouseOverHandler( MouseOverHandler handler )
    {
        return addDomHandler( handler, MouseOverEvent.getType() );
    }

    @Override
    public HandlerRegistration addMouseUpHandler( MouseUpHandler handler )
    {
        return addDomHandler( handler, MouseUpEvent.getType() );
    }

    @Override
    public HandlerRegistration addMouseWheelHandler( MouseWheelHandler handler )
    {
        return addDomHandler( handler, MouseWheelEvent.getType() );
    }

    @Override
    public String getText()
    {
        return title.getText();
    }

    @Override
    public void setText( String text )
    {
        title.setText( text );
    }
}