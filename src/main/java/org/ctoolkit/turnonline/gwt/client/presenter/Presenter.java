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

package org.ctoolkit.turnonline.gwt.client.presenter;

import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.gwt.client.event.CachedEventBus;
import org.ctoolkit.turnonline.gwt.client.view.IView;

/**
 * Specific presenter injecting customized {@link CachedEventBus}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class Presenter<V extends IView>
        extends BinderyPresenter<V>
{
    public Presenter( CachedEventBus eventBus, V view, PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    protected final CachedEventBus bus()
    {
        return ( CachedEventBus ) super.bus();
    }

    @Override
    protected final V view()
    {
        return super.view();
    }

    @Override
    protected final PlaceController controller()
    {
        return super.controller();
    }
}
