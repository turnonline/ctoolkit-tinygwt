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

package org.ctoolkit.turnonline.gwt.client.event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import org.ctoolkit.turnonline.gwt.client.command.CommandRemoteService;
import org.ctoolkit.turnonline.gwt.client.command.CommandRemoteServiceAsync;
import org.ctoolkit.turnonline.gwt.client.command.IAction;
import org.ctoolkit.turnonline.gwt.client.command.IResponse;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>CachedEventBus is extension to {@link SimpleEventBus} with additional methods:</p>
 * <ul>
 * <li><code>execute</code> - executes {@link IAction} implementation and return response as a implementation of {@link IResponse}</li>
 * </ul>
 * <p/>
 * <p>CachedEventBus also cache actions.</p>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see SimpleEventBus
 * @see IAction
 * @see IResponse
 * @see org.ctoolkit.turnonline.gwt.server.CommandRemoteServiceServlet
 */
@Singleton
public abstract class CachedEventBus<F extends RequestFactory>
        extends SimpleEventBus
{
    private static CommandRemoteServiceAsync service = GWT.create( CommandRemoteService.class );

    private final Map<Integer, IResponse> cache = new HashMap<>();

    private F factory;

    /**
     * Execute command for specified {@link IAction} action
     *
     * @param action   contains implementation of {@link IAction} action
     * @param <R>      specified implementation of {@link IResponse}
     * @param callback asynchronous callback for frontend
     */
    @SuppressWarnings( value = "unchecked" )
    public <R extends IResponse> void execute( IAction<R> action, AsyncCallback<R> callback )
    {
        int hashCode = action.hashCode();
        if ( hashCode != -1 && cache.containsKey( hashCode ) )
        {
            callback.onSuccess( ( R ) cache.get( hashCode ) );
        }
        else
        {
            String baseUrl = Window.Location.getProtocol() + "//" + Window.Location.getHost();
            service.execute( action, baseUrl, new IActionCachedCallBack<>( action, callback, this, cache ) );
        }
    }

    public F factory()
    {
        if ( factory == null )
        {
            factory = createFactory();
            factory.initialize( this, new DefaultRequestTransport()
            {
                @Override
                protected void configureRequestBuilder( RequestBuilder builder )
                {
                    super.configureRequestBuilder( builder );
                    builder.setHeader( "X-GWT-Language", LocaleInfo.getCurrentLocale().getLocaleName() );
                }
            } );
        }
        return factory;
    }

    protected abstract F createFactory();
}
