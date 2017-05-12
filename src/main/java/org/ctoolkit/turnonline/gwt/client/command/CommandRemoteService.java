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

package org.ctoolkit.turnonline.gwt.client.command;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The GWT Remote Procedure Call interface to declare methods for asynchronous calls to the server.
 * Generally it defines one <code>execute</code> method of the Command Pattern but feel free add more if you need any.
 * However it should be convenient to add in context of the pattern. For example <code>rollback</code>.
 * <p/>
 * At the server side resides the implementation of this interface, already registered with guice, and mostly will cover
 * the client requests to serve interaction with the back-end. But feel free add more remote services and its
 * implementations if you need.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see CommandRemoteServiceAsync
 * @see org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet
 */
@RemoteServiceRelativePath( "CommandRemoteService" )
public interface CommandRemoteService
        extends RemoteService
{
    /**
     * Execute command for specified {@link IAction} action
     *
     * @param action contains implementation of {@link IAction} action
     * @param <R>    specified implementation of {@link IResponse}
     * @return implementation of {@link IResponse}
     * @throws CommandException if action is not annotated with {@link Command} annotation,
     *                          or facade is not found for action, or method is not found for action
     */
    <R extends IResponse> R execute( IAction<R> action, String fullUrl )
            throws CommandException;
}
