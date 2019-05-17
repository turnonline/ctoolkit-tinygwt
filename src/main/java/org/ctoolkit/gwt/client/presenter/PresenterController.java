/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.gwt.client.presenter;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import java.util.HashMap;

/**
 * Presenter controller that keeps binding between presenters {@link Activity} and associated {@link Place}.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public abstract class PresenterController
        implements ActivityMapper
{
    private final HashMap<String, Activity> presenters;

    /**
     * Constructor.
     *
     * @param presenters that will act as a source of mapping Place -> Activity.
     */
    public PresenterController( BinderyPresenter... presenters )
    {
        this.presenters = new HashMap<>();
        for ( BinderyPresenter presenter : presenters )
        {
            Class place = checkNotNull( presenter.getPlace() );
            this.presenters.put( place.getName(), presenter );
        }
    }

    @Override
    public Activity getActivity( Place place )
    {
        Activity presenter = presenters.get( place.getClass().getName() );
        if ( presenter == null )
        {
            throw new IllegalArgumentException( "Specified place '" + place + "' has no presenter associated" );
        }

        return presenter;
    }

    private <T> T checkNotNull( T reference )
    {
        if ( reference == null )
        {
            throw new NullPointerException( "Presenters place cannot be null -> BinderyPresenter#setPlace(Place)" );
        }
        else
        {
            return reference;
        }
    }
}
