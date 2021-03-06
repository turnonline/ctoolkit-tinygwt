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

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import org.ctoolkit.gwt.client.view.IView;

/**
 * The bindery activity presenter implementation. Presenter acts upon the model and the view.
 * It retrieves data from the model and prepares it to be displayed in the view.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public abstract class BinderyPresenter<V extends IView<?>>
        extends BaseActivity
{
    private final EventBus eventBus;

    private final V view;

    private final PlaceController placeController;

    private AcceptsOneWidget root;

    private Class<? extends Place> place;

    public BinderyPresenter( EventBus eventBus, V view, PlaceController placeController )
    {
        this.eventBus = checkNotNull( eventBus );
        this.view = checkNotNull( view );
        this.placeController = checkNotNull( placeController );

        bind();
    }

    private <T> T checkNotNull( T reference )
    {
        if ( reference == null )
        {
            throw new NullPointerException();
        }
        else
        {
            return reference;
        }
    }

    /**
     * Returns the place that's associated with this presenter.
     *
     * @return the associated place
     */
    public Class getPlace()
    {
        return place;
    }

    /**
     * Sets the place that will be associated with this presenter.
     *
     * @param place the place to be associated
     */
    public void setPlace( Class<? extends Place> place )
    {
        this.place = place;
    }

    /**
     * See {@link com.google.gwt.activity.shared.ActivityManager#onPlaceChange(PlaceChangeEvent)}
     * currentActivity.equals(nextActivity).
     * It always returns false to make sure that presenter will reload its data for same place with different values.
     *
     * @param obj the reference object to compare
     * @return always false
     */
    @Override
    public boolean equals( Object obj )
    {
        return false;
    }

    /**
     * Use this method to add handlers. It's called right after root presenter has been constructed.
     */
    protected abstract void bind();

    @Override
    public void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        root = panel;

        // call to hide content
        onBeforeBackingObject();

        // add view to the root of the page (NOTE: panel is root of the page)
        root.setWidget( view );

        // call backing object method
        onBackingObject();
    }

    /**
     * This method is being called right before {@link #onBackingObject()} method.
     * It hides current view, until data are completely loaded from backend and prepared to be shown.
     */
    protected void onBeforeBackingObject()
    {
        view.hide();
    }

    /**
     * Override if needed to prepare data. For example for backend data initialization etc.
     * <p>
     * NOTE: Do not put any handlers in to this method otherwise handler will be added every time view is being rendered.
     */
    protected void onBackingObject()
    {
    }

    /**
     * Call this method once backing object is fully loaded from backend and its data are ready to be shown.
     * You need to call this method on your own because of asynchronous behavior of getting backing object.
     * It will show associated view with prepared data and optionally decorate view components.
     */
    protected void onAfterBackingObject()
    {
        view.decorate();
        view.show();
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
     * Returns the related view instance.
     *
     * @return the related view instance
     */
    protected V view()
    {
        return view;
    }

    /**
     * Returns the place controller.
     *
     * @return the place controller
     */
    protected PlaceController controller()
    {
        return placeController;
    }
}
