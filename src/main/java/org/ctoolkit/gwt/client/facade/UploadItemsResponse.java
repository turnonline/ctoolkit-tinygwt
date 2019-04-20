package org.ctoolkit.gwt.client.facade;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * The JSON response wrapper of the {@link UploadItem} array upload operation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class UploadItemsResponse
        extends JavaScriptObject
{
    protected UploadItemsResponse()
    {
    }

    /**
     * Returns the {@link UploadItem} array.
     *
     * @return the uploaded items array
     */
    public final native JsArray<UploadItem> getItems() /*-{
        return this.items;
    }-*/;
}
