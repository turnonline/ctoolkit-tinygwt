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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The JSON response wrapper of the BLOB (binary large object) upload operation.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public class UploadResponse
        extends JavaScriptObject
{
    protected UploadResponse()
    {
    }

    /**
     * Returns the storage name as full storage path of the uploaded file.
     *
     * @return the storage name
     */
    public final native String getStorageName() /*-{
        return this.storageName;
    }-*/;

    /**
     * Returns the CDN image static URL if uploaded BLOB represents an image.
     * Otherwise returns {@code null}.
     *
     * @return the image static URL
     */
    public final native String getServingUrl() /*-{
        return this.servingUrl;
    }-*/;

    /**
     * Returns the BLOB key as an identification of the uploaded file.
     *
     * @return the BLOB key
     */
    public final native String getBlobKey() /*-{
        return this.blobKey;
    }-*/;
}
