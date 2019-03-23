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

package org.ctoolkit.gwt.client.facade;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;

import java.util.HashMap;
import java.util.Map;

/**
 * The request builder that populates header with Firebase id token.
 * Prerequisite is to have a Firebase correctly initialized with authenticated user.
 * {@code firebase.auth().currentUser;} must return a valid user. If returns null, call
 * is executed without authorization header
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class FirebaseAuthRequestBuilder
{
    private static Map<String, RequestBuilder> calls = new HashMap<>();

    private String token;

    protected native void fireTokenRetrieval( final FirebaseAuthRequestBuilder instance, String key ) /*-{
        var firebase = $wnd.firebase;

        // firebase is initialized
        if ( firebase )
        {
            var user = firebase.auth().currentUser;
            if ( user )
            {
                user.getIdToken().then( function ( currentToken ) {
                    if ( currentToken )
                    {
                        instance.@org.ctoolkit.gwt.client.facade.FirebaseAuthRequestBuilder::onTokenReceived(Ljava/lang/String;Ljava/lang/String;)( currentToken, key );
                    }
                } );
            }
            else
            {
                instance.@org.ctoolkit.gwt.client.facade.FirebaseAuthRequestBuilder::onTokenReceived(Ljava/lang/String;Ljava/lang/String;)( null, key );
            }
        }
        else
        {
            instance.@org.ctoolkit.gwt.client.facade.FirebaseAuthRequestBuilder::onTokenReceived(Ljava/lang/String;Ljava/lang/String;)( null, key );
        }
    }-*/;

    protected void onTokenReceived( final String idToken, final String key )
    {
        token = idToken;

        if ( key != null )
        {
            RequestBuilder builder = calls.get( key );
            if ( builder != null )
            {
                Scheduler.get().scheduleDeferred( () -> {
                    try
                    {
                        if ( token != null )
                        {
                            populateAuthorization( builder, token );
                        }
                        builder.send();
                        calls.remove( key );
                    }
                    catch ( RequestException e )
                    {
                        resetToken();
                    }
                } );
            }
        }
    }

    /**
     * Sends a HTTP request based on the current builder configuration
     * populated with Firebase id token if current user has been found.
     *
     * @param builder the request builder instance
     * @return the {@link Request} object that can be used to track the request
     */
    protected Request sendRequest( RequestBuilder builder ) throws RequestException
    {
        if ( token == null || token.isEmpty() )
        {
            String key = builder.getUrl();
            calls.put( key, builder );

            fireTokenRetrieval( this, key );
            return null;
        }
        else
        {
            populateAuthorization( builder, token );
            return builder.send();
        }
    }

    /**
     * Sets the current ID token to {@code null}.
     */
    protected void resetToken()
    {
        token = null;
    }

    protected void populateAuthorization( RequestBuilder builder, String token )
    {
        builder.setHeader( "Authorization", "Bearer " + token );
    }
}
