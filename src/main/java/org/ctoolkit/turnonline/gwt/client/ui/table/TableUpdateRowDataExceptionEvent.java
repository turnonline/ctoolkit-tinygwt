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

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class TableUpdateRowDataExceptionEvent
        extends GwtEvent<TableUpdateRowDataExceptionEventHandler>
{
    public static Type<TableUpdateRowDataExceptionEventHandler> TYPE = new Type<>();

    private Throwable throwable;

    private String message;

    public TableUpdateRowDataExceptionEvent()
    {
    }

    public TableUpdateRowDataExceptionEvent( Throwable throwable )
    {
        this.throwable = throwable;
        this.message = throwable.getMessage();
    }

    public Type<TableUpdateRowDataExceptionEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( TableUpdateRowDataExceptionEventHandler handler )
    {
        handler.onException( this );
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    public String getMessage()
    {
        return message;
    }
}
