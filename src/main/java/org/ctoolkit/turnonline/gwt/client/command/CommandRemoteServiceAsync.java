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

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The GWT Remote Procedure Call interface's pair for use on the client side.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see CommandRemoteService
 * @see org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet
 */
public interface CommandRemoteServiceAsync
{
    /**
     * Execute command for specified {@link IAction} action
     *
     * @param action  contains implementation of {@link IAction} action
     * @param <R>     specified implementation of {@link IResponse}
     * @param context serlvet path
     * @param async   asynchronous callback for frontend
     */
    <R extends IResponse> void execute( IAction<R> action, String context, AsyncCallback<R> async );
}
