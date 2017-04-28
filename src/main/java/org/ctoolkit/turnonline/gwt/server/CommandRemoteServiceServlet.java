/*
 * Comvai GWT, Comvai specific GWT components
 * Copyright (C) 2012 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.turnonline.gwt.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ctoolkit.turnonline.gwt.client.command.Command;
import org.ctoolkit.turnonline.gwt.client.command.CommandException;
import org.ctoolkit.turnonline.gwt.client.command.CommandRemoteService;
import org.ctoolkit.turnonline.gwt.client.command.IAction;
import org.ctoolkit.turnonline.gwt.client.command.IResponse;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The implementation of a remote service to interact with other back-end components. It plays the role of the adapter
 * to bind together back-end facades and its counterpart {@link IAction} implementations.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see CommandRemoteService
 * @see org.ctoolkit.turnonline.gwt.client.command.CommandRemoteServiceAsync
 */
@Singleton
public class CommandRemoteServiceServlet
        extends RemoteServiceServlet
        implements CommandRemoteService
{

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger( CommandRemoteServiceServlet.class.getName() );

    private List<Object> facades;

    public CommandRemoteServiceServlet()
    {
    }

    @SuppressWarnings( value = "unchecked" )
    public CommandRemoteServiceServlet( Object... facades )
    {
        if ( facades == null )
        {
            throw new NullPointerException( "Facades cannot be null. You probably did not inject objects properly." );
        }

        this.facades = Arrays.asList( facades );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <R extends IResponse> R execute( IAction<R> action, String fullUrl )
            throws CommandException
    {
        return ( R ) invokeMethod( action );
    }

    private Object invokeMethod( Object action ) throws CommandException
    {
        if ( action == null )
        {
            throw new NullPointerException( "Action cannot be null." );
        }

        Object response = null;

        try
        {
            // get command annotation from action class
            Command command = action.getClass().getAnnotation( Command.class );
            if ( command != null )
            {
                // get facade
                Object facade = getFacade( command.facadeClass() );

                if ( facade == null )
                {
                    throw new NullPointerException( "Facade not found for class: " + command.facadeClass().getName() );
                }

                // get method by action method name and class
                Method method;
                try
                {
                    // try to find method with action parameter and http request parameter
                    method = facade.getClass().getMethod( command.methodName(), action.getClass(), HttpServletRequest.class );

                    if ( log.isLoggable( Level.INFO ) )
                    {
                        log.info( "Executing action [" + action.getClass() + "] on " +
                                "[" + command.facadeClass().getName() + "#" + command.methodName() + "(" + action + ")]" );
                    }

                    // invoke method specified by action
                    response = method.invoke( facade, action, getThreadLocalRequest() );
                }
                catch ( NoSuchMethodException e )
                {
                    throw new NoSuchMethodException( "Method for " + command.facadeClass().getName() + "#" + command.methodName() +
                            " does not exists. Ensure that you defined that method and check if first parameter is child of " + action.getClass().getName() +
                            " and second " + HttpServletRequest.class.getName() );
                }
            }
            else
            {
                throw new IllegalArgumentException( "Action '" + action.getClass().getName() + "' must be annotated with '" + Command.class.getName() + "' annotation." );
            }
        }
        catch ( Exception e )
        {
            handleException( e, action.getClass() );
        }

        return response;
    }

    private void handleException( Exception e, Class action ) throws CommandException
    {
        if ( log.isLoggable( Level.SEVERE ) )
        {
            log.log( Level.SEVERE, "Error occurred during invoking method for action '" + action.getName() + "'", e );
        }

        throw new CommandException( e.getCause() != null ? e.getCause() : e );
    }

    /**
     * Return facade for specified class or <code>null</code> if no facade for class was found.
     *
     * @param facade class of service
     * @return service for specified class
     */
    private Object getFacade( Class facade )
    {
        for ( Object s : facades )
        {
            if ( facade.isInstance( s ) )
            {
                return s;
            }
        }

        return null;
    }

}