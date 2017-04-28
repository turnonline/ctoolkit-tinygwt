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

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class FilterAction<R extends FilterResponse>
        extends IAction<R>
{

    private static final long serialVersionUID = 1L;

    private int start;

    private int length;

    private boolean ascending;

    private String sortBy;

    private String query;

    public FilterAction()
    {
    }

    public FilterAction( String query )
    {
        this.query = query;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart( int start )
    {
        this.start = start;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength( int length )
    {
        this.length = length;
    }

    public boolean isAscending()
    {
        return ascending;
    }

    public void setAscending( boolean ascending )
    {
        this.ascending = ascending;
    }

    public String getSortBy()
    {
        return sortBy;
    }

    public void setSortBy( String sortBy )
    {
        this.sortBy = sortBy;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return "FilterAction{" +
                "start=" + start +
                ", length=" + length +
                ", ascending=" + ascending +
                ", sortBy='" + sortBy + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}
