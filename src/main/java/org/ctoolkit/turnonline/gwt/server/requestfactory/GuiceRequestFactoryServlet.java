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

package org.ctoolkit.turnonline.gwt.server.requestfactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

/**
 * The GWT request factory servlet guice wrapper.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Singleton
public class GuiceRequestFactoryServlet
        extends RequestFactoryServlet
{
    private static final long serialVersionUID = 7038323133496393310L;

    @Inject
    public GuiceRequestFactoryServlet( ExceptionHandler exceptionHandler, Set<ServiceLayerDecorator> decorators )
    {
        super( exceptionHandler, decorators.toArray( new ServiceLayerDecorator[decorators.size()] ) );
    }
}
