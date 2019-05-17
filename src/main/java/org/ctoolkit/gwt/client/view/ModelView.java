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

import com.google.web.bindery.event.shared.EventBus;

/**
 * The bindery {@link IView} implementation as a composite widget with associated model.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class ModelView<T>
        extends BinderyView
{
    private T model;

    public ModelView( EventBus eventBus )
    {
        super( eventBus );
    }

    /**
     * Returns the view's model, no {@link #bind()} notification will be fired.
     *
     * @return the view's model
     */
    protected T getRawModel()
    {
        return model;
    }

    /**
     * Returns the view's model.
     *
     * @return the view's model
     */
    public T getModel()
    {
        bind();
        return model;
    }

    /**
     * Sets the model to be associated with this view.
     *
     * @param model the model to be set
     */
    public void setModel( T model )
    {
        this.model = model;
        fill();
    }

    /**
     * Override to get a notification right before model is going to be retrieved.
     */
    protected void bind()
    {
    }

    /**
     * Override to get a notification right after model has been set.
     */
    protected void fill()
    {
    }
}
