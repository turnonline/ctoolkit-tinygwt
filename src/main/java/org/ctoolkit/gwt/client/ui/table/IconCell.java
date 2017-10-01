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

package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class IconCell<C>
        extends AbstractCell<C>
{
    private final String className;

    private final String classNameDisabled;

    private final Delegate<C> delegate;

    private final String buttonClassName;

    /**
     * Construct a new {@link IconCell}.
     *
     * @param className         name of specified icon class
     * @param classNameDisabled name of specified disabled icon class
     * @param buttonClassName   the style name to style the button
     * @param delegate          the delegate that will handle events
     */
    public IconCell( String className, String classNameDisabled, String buttonClassName, Delegate<C> delegate )
    {
        super( "click", "keydown" );
        this.delegate = delegate;

        this.className = className;
        this.classNameDisabled = classNameDisabled;
        this.buttonClassName = buttonClassName;
    }

    @Override
    public void onBrowserEvent( Context context, Element parent, C value,
                                NativeEvent event, ValueUpdater<C> valueUpdater )
    {
        super.onBrowserEvent( context, parent, value, event, valueUpdater );
        if ( "click".equals( event.getType() ) && delegate.isExecutable( value ) )
        {
            EventTarget eventTarget = event.getEventTarget();
            if ( !Element.is( eventTarget ) )
            {
                return;
            }
            if ( parent.getFirstChildElement().isOrHasChild( Element.as( eventTarget ) ) )
            {
                // Ignore clicks that occur outside of the main element.
                onEnterKeyDown( context, parent, value, event, valueUpdater );
            }
        }
    }

    @Override
    public void render( Context context, C value, SafeHtmlBuilder sb )
    {
        if ( delegate.isExecutable( value ) )
        {
            sb.append( new SafeHtmlBuilder().appendHtmlConstant(
                    "<button type=\"button\" tabindex=\"-1\" class=\"" + buttonClassName + " " + className + "\">" )
                    .appendHtmlConstant( "</button>" ).toSafeHtml() );
        }
        else
        {
            sb.append( new SafeHtmlBuilder().appendHtmlConstant(
                    "<button type=\"button\" tabindex=\"-1\" class=\"" + buttonClassName + " " + classNameDisabled + "\">" )
                    .appendHtmlConstant( "</button>" ).toSafeHtml() );
        }
    }

    @Override
    protected void onEnterKeyDown( Context context, Element parent, C value,
                                   NativeEvent event, ValueUpdater<C> valueUpdater )
    {
        if ( delegate.isExecutable( value ) )
        {
            delegate.execute( value );
        }
    }

    /**
     * The delegate that will handle events from the cell.
     *
     * @param <T> the type that this delegate acts on
     */
    public interface Delegate<T>
    {
        /**
         * Perform the desired action on the given object.
         *
         * @param object the object to be acted upon
         */
        void execute( T object );

        /**
         * Return <code>true</code> if delegate is executable (icon will be rendered) or <code>false</code>
         * if delegate cannot be executable (no icon will be rendered)
         *
         * @param object the object to be acted upon
         * @return <code>true</code> if delegate is executable, <code>false</code> otherwise
         */
        boolean isExecutable( T object );
    }
}
