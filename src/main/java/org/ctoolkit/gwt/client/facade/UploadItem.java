package org.ctoolkit.gwt.client.facade;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The JSON response wrapper of the BLOB (binary large object) upload operation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
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
}
