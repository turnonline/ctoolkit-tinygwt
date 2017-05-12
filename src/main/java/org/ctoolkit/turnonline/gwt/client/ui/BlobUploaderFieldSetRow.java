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

package org.ctoolkit.turnonline.gwt.client.ui;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * The Blob uploader fieldset row that wraps {@link BlobUploader}.
 * <p/>
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.form-row</dt>
 * <dd>the form row style name</dd>
 * </dl>
 * <dl>
 * <dt>.label</dt>
 * <dd>the row label style name</dd>
 * </dl>
 * <dl>
 * <dt>.component image-preview</dt>
 * <dd>the row component and image preview style name</dd>
 * </dl>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class BlobUploaderFieldSetRow
        extends Composite
{
    private static final String LABEL_SEPARATOR = ":";

    private Label label = new Label();

    private BlobUploader uploader;

    /**
     * Constructor
     *
     * @param uploadUrl the relative URL to handle data upload
     */
    @UiConstructor
    public BlobUploaderFieldSetRow( String uploadUrl )
    {
        FlowPanel formRow = new FlowPanel();
        initWidget( formRow );

        this.uploader = new BlobUploader( uploadUrl );

        formRow.setStyleName( "form-row" );
        FlowPanel labelWrapper = new FlowPanel();
        formRow.add( labelWrapper );
        formRow.add( clearer() );
        FlowPanel component = new FlowPanel();
        formRow.add( component );

        label.setStyleName( "label" );
        component.add( uploader );

        labelWrapper.add( label );
        FlowPanel imageWrapper = new FlowPanel();
        labelWrapper.add( imageWrapper );

        imageWrapper.setStyleName( "component image-preview" );
        uploader.setPreviewPanel( imageWrapper );
        uploader.setImageServingUrl( null );
    }

    /**
     * Sets the image size of the image returned as serving URL.
     *
     * @param imageSize the image size to be set
     */
    public void setImageSize( int imageSize )
    {
        this.uploader.setImageSize( imageSize );
    }

    /**
     * The upload button label.
     *
     * @param text upload button label to be set
     */
    public void setButtonLabel( String text )
    {
        this.uploader.setButtonLabel( text );
    }

    /**
     * Sets the label for the row widget.
     *
     * @param text the label to be set
     */
    public void setLabel( String text )
    {
        label.setText( text + LABEL_SEPARATOR );
    }

    /**
     * Returns the blob name in storage once upload by uploader component has been processed successfully.
     * The <tt>null</tt> value means there was not processed any upload successfully.
     *
     * @return the blob name in storage
     */
    public String getStorageName()
    {
        return uploader.getStorageName();
    }

    /**
     * Returns the blob key taken from the uploader component.
     * The <tt>null</tt> value means there was not processed any upload successfully.
     *
     * @return the blob key
     */
    public String getBlobKey()
    {
        return uploader.getBlobKey();
    }

    /**
     * Returns the image serving URL taken from the uploader component.
     *
     * @return the image serving URL
     */
    public String getImageServingUrl()
    {
        return uploader.getImageServingUrl();
    }

    /**
     * Sets the image serving URL.
     *
     * @param servingUrl the image serving URL to be set on uploader
     */
    public void setImageServingUrl( String servingUrl )
    {
        uploader.setImageServingUrl( servingUrl );
    }

    private FlowPanel clearer()
    {
        FlowPanel clearer = new FlowPanel();
        clearer.getElement().setAttribute( "style", "clear:both;" );

        return clearer;
    }
}
