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

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The common implementation of the GWT request factory exception handler.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class CommonExceptionHandler
        implements ExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger( CommonExceptionHandler.class );

    public CommonExceptionHandler()
    {
    }

    @Override
    public ServerFailure createServerFailure( Throwable throwable )
    {
        logger.error( "Server failure", throwable );

        String message = throwable == null ? null : throwable.getMessage();
        return new ServerFailure( "Server Error: " + message, null, null, true );
    }
}
