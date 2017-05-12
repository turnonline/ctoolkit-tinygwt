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

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * The <code>IAction</code> is own implementation of the GoF Command pattern inspired by Google I/O 2009
 * - Best Practices for Architecting GWT App.
 * <p/>
 * The Command pattern lets client make requests by turning the request itself into an object <code>IAction</code>.
 * This object can be stored and passed around like any other objects. Actions - Commands may be stored in a history
 * list to support undo and redo functionality achieved by traversing this list.
 * <p/>
 * Thus command pattern helps with:
 * <ul>
 * <li>Caching the repeated user requests</li>
 * <li>Batching</li>
 * <li>Support redo and  undo actions</li>
 * <li>Useful for offline mode of the HTML5 DB</li>
 * </ul>
 * <b>Tips:</b> It's an optimization to use ArrayList instead of List interface to disallow the serializer to choose
 * the concrete implementation of the List interface. Although it's generally not recommended.
 * <p/>
 * The server side actions should be stateless, all stateful operations should be handled by AJAX at client side.
 * <p/>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see IResponse
 */
public abstract class IAction<R extends IResponse>
        implements Serializable, IsSerializable
{
    private static final long serialVersionUID = 1L;

    /**
     * If you want to cache this action override hashCode to value other then -1.
     * {@link org.ctoolkit.turnonline.gwt.client.event.CachedEventBus}
     * than will cache this action and use its hashCode as a key.
     *
     * @return hash code
     */
    @Override
    public int hashCode()
    {
        return -1;
    }
}
