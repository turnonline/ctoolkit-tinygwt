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

package org.ctoolkit.gwt.server.requestfactory;

import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * The GWT request factory basic service layer decorator implementation handled by guice.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BasicGuiceServiceLayerDecorator
        extends ServiceLayerDecorator
{
    private final Validator validator;

    private final Injector injector;

    @Inject
    public BasicGuiceServiceLayerDecorator( Injector injector, Validator validator )
    {
        this.injector = injector;
        this.validator = validator;
    }

    @Override
    public <T extends Locator<?, ?>> T createLocator( Class<T> clazz )
    {
        return injector.getInstance( clazz );
    }

    @Override
    public Object createServiceInstance( Class<? extends RequestContext> context )
    {
        Class<? extends ServiceLocator> serviceLocator;

        if ( ( serviceLocator = getTop().resolveServiceLocator( context ) ) != null )
        {
            Class<?> value = context.getAnnotation( Service.class ).value();
            return injector.getInstance( serviceLocator ).getInstance( value );
        }

        return null;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validate( T domainObject )
    {
        return validator.validate( domainObject );
    }
}
