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
public class UploadItem
        extends JavaScriptObject
{
    protected UploadItem()
    {
    }

    /**
     * Returns the file name of the uploaded file.
     *
     * @return the file name
     */
    public final native String getFileName() /*-{
        return this.fileName;
    }-*/;

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
     * Returns ID that represents an identification of the concrete object
     * that's being associated with the uploaded BLOB.
     *
     * @return the ID of the associated object or {@code null} if none
     */
    public final native String getAssociatedId() /*-{
        return this.associatedId;
    }-*/;
}
