package org.ctoolkit.turnonline.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * Blob uploader composite component.
 * <p/>
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.preview-image</dt>
 * <dd>the preview image style name</dd>
 * </dl>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class BlobUploader
        extends Composite
{
    public static final String PARAMETER_FILE_FIELD = "__name_field_marker";

    public static final String IMAGE_SIZE = "__image_size";

    private static final Image PREVIEW_IMAGE = new Image();

    private final String uploadUrl;

    private final FormPanel form = new FormPanel();

    private OutputFactory ofa = GWT.create( OutputFactory.class );

    private IconButton button;

    private String storageName;

    private String blobKey;

    private String imageServingUrl;

    private FileUpload fileUpload;

    private FlowPanel previewPanel;

    private Hidden imageSizeHidden;

    /**
     * Constructor
     *
     * @param uploadUrl the relative URL to handle data upload
     */
    public BlobUploader( String uploadUrl )
    {
        this( uploadUrl, null );
    }

    /**
     * @param uploadUrl the relative URL to handle data upload
     * @param label     the upload button label
     */
    public BlobUploader( String uploadUrl, String label )
    {
        this( uploadUrl, label, -1 );
    }

    /**
     * @param uploadUrl the relative URL to handle data upload
     * @param label     the upload button label
     * @param imageSize the requested size of the image returned as an image serving URL
     */
    public BlobUploader( String uploadUrl, String label, int imageSize )
    {
        this.uploadUrl = uploadUrl;
        // create form
        form.setAction( this.uploadUrl );
        form.setEncoding( FormPanel.ENCODING_MULTIPART );
        form.setMethod( FormPanel.METHOD_POST );

        // create form items panel
        FlowPanel formItemsWrapper = new FlowPanel();
        form.add( formItemsWrapper );

        imageSizeHidden = new Hidden( IMAGE_SIZE );
        formItemsWrapper.add( imageSizeHidden );
        setImageSize( imageSize );

        // add file upload to form
        fileUpload = new FileUpload();
        fileUpload.setName( PARAMETER_FILE_FIELD );
        fileUpload.getElement().setAttribute( "style", "position:absolute;opacity: 0;filter:alpha(opacity:0);z-index:2;" );
        fileUpload.addChangeHandler( new ChangeHandler()
        {
            @Override
            public void onChange( ChangeEvent changeEvent )
            {
                try
                {
                    // show loading image
                    setPreviewImage( PREVIEW_IMAGE );

                    // send ajax request
                    sendAjaxCreateBlobUrl( form );
                }
                catch ( RequestException e )
                {
                    GWT.log( "Error occurred during creating of sending request for creating blob url: " + e );
                }
            }
        } );
        formItemsWrapper.add( fileUpload );

        // Create nice button instead of fileUpload one
        button = new IconButton( IconButton.Type.UPLOAD, label );
        formItemsWrapper.add( button );

        // add form to root panel
        FlowPanel rootPanel = new FlowPanel();
        rootPanel.add( form );

        form.addSubmitCompleteHandler( new FormPanel.SubmitCompleteHandler()
        {
            @Override
            public void onSubmitComplete( FormPanel.SubmitCompleteEvent submitCompleteEvent )
            {
                String result = submitCompleteEvent.getResults().replaceAll( "\\<.*?\\>", "" );

                //TODO AutoBean: is worth to be replaced by com.google.gwt.core.client.JsonUtils?
                AutoBean<JsonOutput> bean = AutoBeanCodex.decode( ofa, JsonOutput.class, result );
                JsonOutput json = bean.as();

                setImageServingUrl( json.getServingUrl() );
                setBlobKey( json.getBlobKey() );
                setStorageName( json.getStorageName() );
            }
        } );

        PREVIEW_IMAGE.setStyleName( "preview-image" );

        initWidget( rootPanel );
    }

    /**
     * Returns the blob name in storage once upload has been processed successfully.
     * Otherwise returns <tt>null</tt>.
     *
     * @return the blob name in storage
     */
    public String getStorageName()
    {
        return storageName;
    }

    /**
     * Sets the blob name.
     *
     * @param name the blob name to be set
     */
    public void setStorageName( String name )
    {
        this.storageName = name;
    }

    /**
     * Returns the blob key once upload has been processed successfully.
     * Otherwise returns <tt>null</tt>.
     *
     * @return the blob key
     */
    public String getBlobKey()
    {
        return blobKey;
    }

    private void setBlobKey( String blobKey )
    {
        this.blobKey = blobKey;
    }

    /**
     * Returns either the image serving URL once upload has been processed successfully or origin value set by
     * {@link #setImageServingUrl(String)}.
     *
     * @return the serving URL
     */
    public String getImageServingUrl()
    {
        return imageServingUrl;
    }

    /**
     * Sets the image serving URL.
     *
     * @param imageServingUrl the image serving URL to be set
     */
    public void setImageServingUrl( String imageServingUrl )
    {
        this.imageServingUrl = imageServingUrl;

        Image image = new Image();
        if ( !isNullOrEmpty( imageServingUrl ) )
        {
            image.setUrl( imageServingUrl );
        }
        setPreviewImage( image );
    }

    /**
     * Sets the upload field name marker name that's included in to multiform form submit.
     * Default value is {@link #PARAMETER_FILE_FIELD}
     *
     * @param name the upload field name to be set
     */
    public void setUploadFieldNameMarkerName( String name )
    {
        fileUpload.setName( name );
    }

    /**
     * Sets the requested size of the image returned as an image serving URL.
     * The form hidden input name is {@link #IMAGE_SIZE}
     *
     * @param imageSize the image size to be included to multiform form submit
     */
    public void setImageSize( int imageSize )
    {
        imageSizeHidden.setValue( String.valueOf( imageSize ) );
    }

    /**
     * Sets the upload button label.
     *
     * @param text the upload button label to be set
     */
    public void setButtonLabel( String text )
    {
        button.setButtonLabel( text );
    }

    private void setPreviewImage( final Image image )
    {
        previewPanel.clear();
        previewPanel.add( image );

        Button deleteButton = new Button(  );
        deleteButton.addClickHandler( new ClickHandler()
        {
            @Override
            public void onClick( ClickEvent event )
            {
                // TODO: call handler to delete image
            }
        } );
        deleteButton.setStyleName( "preview-image-delete" );
        previewPanel.add( deleteButton );
    }

    private boolean isNullOrEmpty( String string )
    {
        return string == null || string.length() == 0;
    }

    private void sendAjaxCreateBlobUrl( final FormPanel form ) throws RequestException
    {
        XMLHttpRequest xmlHttpRequest = XMLHttpRequest.create();
        xmlHttpRequest.open( RequestBuilder.GET.toString(), uploadUrl );
        xmlHttpRequest.setOnReadyStateChange( new ReadyStateChangeHandler()
        {
            public void onReadyStateChange( XMLHttpRequest xhr )
            {
                if ( xhr.getReadyState() == XMLHttpRequest.DONE )
                {
                    xhr.clearOnReadyStateChange();
                    form.setAction( xhr.getResponseText() );
                    form.submit();
                }
            }
        } );

        try
        {
            xmlHttpRequest.send();
        }
        catch ( JavaScriptException e )
        {
            throw new RequestException( e.getMessage() );
        }
    }

    public void setPreviewPanel( FlowPanel previewPanel )
    {
        this.previewPanel = previewPanel;
    }

    interface JsonOutput
    {
        String getStorageName();

        void setStorageName( String name );

        String getServingUrl();

        void setServingUrl( String servingUrl );

        String getCustomName();

        void setCustomName( String uploadName );

        String getBlobKey();

        void setBlobKey( String blobKey );
    }

    interface OutputFactory
            extends AutoBeanFactory
    {
        AutoBean<JsonOutput> output();
    }
}