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

package org.ctoolkit.gwt.client.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The view where {@link FlowPanel} is a root component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class FlowPanelView
        extends BinderyView
{
    /**
     * This panel represents a root of all widgets on the page.
     */
    private FlowPanel content;

    public FlowPanelView( EventBus eventBus )
    {
        super( eventBus );
        content = new FlowPanel();
    }

    @Override
    public void show()
    {
        content.setVisible( true );
    }

    @Override
    public void hide()
    {
        content.setVisible( false );
    }

    /**
     * Adds a child widget.
     *
     * @param widget the widget to be added
     */
    public void add( Widget widget )
    {
        content.add( widget );
    }

    /**
     * Removes all child widgets.
     */
    public void clear()
    {
        content.clear();
    }

    /**
     * Inserts a widget before the specified index.
     *
     * @param widget      the widget to be inserted
     * @param beforeIndex the index before which it will be inserted
     */
    public void insert( Widget widget, int beforeIndex )
    {
        content.insert( widget, beforeIndex );
    }

    /**
     * Returns the root {@link FlowPanel} component.
     *
     * @return the root panel
     */
    protected FlowPanel root()
    {
        return content;
    }
}