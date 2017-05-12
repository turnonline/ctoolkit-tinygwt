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

package org.ctoolkit.turnonline.gwt.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * The bindery {@link IView} implementation as a composite widget.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class BinderyView
        extends Composite
        implements IView
{
    private final EventBus eventBus;

    private List<Decorator> decorators;

    public BinderyView( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    /**
     * Returns the singleton event bus.
     *
     * @return the singleton event bus
     */
    protected EventBus bus()
    {
        return eventBus;
    }

    /**
     * Associate given decorator instance with this view.
     *
     * @param decorator the decorator to be added into the list of decorators
     */
    public void add( Decorator decorator )
    {
        if ( decorator == null )
        {
            throw new NullPointerException();
        }

        if ( decorators == null )
        {
            decorators = new ArrayList<>();
        }

        decorators.add( decorator );
    }

    /**
     * Clear all associated decorators.
     */
    public void clearDecorators()
    {
        decorators.clear();
    }

    @Override
    public void decorate()
    {
        if ( decorators != null )
        {
            for ( Decorator decorator : decorators )
            {
                decorator.decorate();
            }
        }
    }
}
