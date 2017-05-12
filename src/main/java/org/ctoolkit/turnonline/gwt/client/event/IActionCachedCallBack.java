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

package org.ctoolkit.turnonline.gwt.client.event;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import org.ctoolkit.turnonline.gwt.client.command.IAction;
import org.ctoolkit.turnonline.gwt.client.command.IResponse;

import java.util.Map;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
class IActionCachedCallBack<R extends IResponse>
        implements AsyncCallback<R>
{
    private IAction<R> action;

    private AsyncCallback<R> callback;

    private Map<Integer, IResponse> cache;

    private EventBus eventBus;

    public IActionCachedCallBack( IAction<R> action,
                                  AsyncCallback<R> callback,
                                  EventBus eventBus,
                                  Map<Integer, IResponse> cache )
    {
        this.action = action;
        this.callback = callback;
        this.eventBus = eventBus;
        this.cache = cache;
    }

    @Override
    public void onFailure( Throwable caught )
    {
        callback.onFailure( caught );
    }

    @Override
    public void onSuccess( R result )
    {
        // cash result if hash code is other than -1 (IAction child did not override hash code)
        int hashCode = action.hashCode();
        if ( hashCode != -1 )
        {
            cache.put( action.hashCode(), result );
        }

        callback.onSuccess( result );
    }
}
