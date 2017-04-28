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

package org.ctoolkit.turnonline.gwt.client.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Every {@link IAction} subclasses should be annotated with this annotation.
 * {@link org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet} determines by this annotation which facade will be used and which method
 * on this facade will be invoked.</p>
 * <p>If action subclass is not annotated with this annotation and {@link org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet#execute(IAction, String)}
 * method is called, {@link IllegalArgumentException} will be thrown.</p>
 * <p>Example:</p>
 * <code>@Command( methodName = "saveFoo", facadeClass = MyService.class )</code><br/>
 * <code>public class MyAction extends IAction<MyResponse></code><br/>
 * <code>{</code><br/>
 * <code>&nbsp;&nbsp;&nbsp;...</code><br/>
 * <code>}</code><br/>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Command
{
    /**
     * Return facade class, ie MyServiceFacade.class
     *
     * @return facade class which will be used in {@link org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet} to determine which facade will be used to invoke method
     */
    Class facadeClass();

    /**
     * Return method name from facadeClass which will be invoked
     *
     * @return method name
     */
    String methodName();
}
