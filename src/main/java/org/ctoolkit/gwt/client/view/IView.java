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

package org.ctoolkit.gwt.client.view;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * The view interface to be used with {@link org.ctoolkit.gwt.client.presenter.Presenter}.
 *
 * @param <T> the type of the model to be associated with this view
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface IView<T>
        extends IsWidget
{
    /**
     * Returns the view's model with updated values taken from the UI components.
     *
     * @return the view's model
     */
    T getModel();

    /**
     * Sets the model to this view and updates UI components with these values.
     *
     * @param model the model to be set
     */
    void setModel( T model );

    /**
     * Hides this composite widget.
     */
    void hide();

    /**
     * Shows this composite widget.
     */
    void show();

    /**
     * Decorate/modify the content of this view (show or hide some buttons based on the specific conditions).
     * The method will be called right before showing a view to the user once data for view are ready.
     */
    void decorate();
}
