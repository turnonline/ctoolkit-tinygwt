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

package org.ctoolkit.gwt.client.facade;

import javax.annotation.Nullable;

/**
 * The Firebase token callback that provides asynchronously a JSON Web Token (JWT)
 * as response from the remote call.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
@FunctionalInterface
public interface TokenCallback
{
    /**
     * Notification method will be called once the Firebase has responded.
     *
     * @param token the firebase JWT token
     * @param key   the key, the client has provided ahead
     */
    void then( @Nullable String token, @Nullable String key );
}
