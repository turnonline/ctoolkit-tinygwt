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

import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class TableUpdateRowDataEvent
        extends GwtEvent<TableUpdateRowDataEventHandler>
{
    public static Type<TableUpdateRowDataEventHandler> TYPE = new Type<>();

    private final List list;

    private final int start;

    public TableUpdateRowDataEvent( List list, int start )
    {
        this.list = list;
        this.start = start;
    }

    public Type<TableUpdateRowDataEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( TableUpdateRowDataEventHandler handler )
    {
        handler.onUpdateRowData( this );
    }

    @SuppressWarnings( "unchecked" )
    public <T> List<T> getList()
    {
        return list;
    }

    public int getStart()
    {
        return start;
    }
}
