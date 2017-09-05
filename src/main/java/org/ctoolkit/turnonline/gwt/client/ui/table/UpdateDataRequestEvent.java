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

package org.ctoolkit.turnonline.gwt.client.ui.table;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Root of all list based table data update request.
 *
 * @param <R> the concrete type of the list item resource
 * @param <H> the associated event handler
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class UpdateDataRequestEvent<R, H extends EventHandler>
        extends GwtEvent<H>
{
    private final Table<R> table;

    protected UpdateDataRequestEvent( Table<R> table )
    {
        this.table = checkNotNull( table );
    }

    /**
     * Returns the requested start index of the data (paging from).
     */
    public abstract int start();

    /**
     * Callback that will update table's rows.
     *
     * @param response the list of items to update table
     */
    public void successCallback( List<R> response )
    {
        table.dataProvider.updateRowData( start(), response );
    }

    /**
     * Callback that will reset table's rows.
     */
    public void failureCallback()
    {
        table.reset();
    }
}
