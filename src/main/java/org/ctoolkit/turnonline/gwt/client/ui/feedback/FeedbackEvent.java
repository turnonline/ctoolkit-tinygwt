package org.ctoolkit.turnonline.gwt.client.ui.feedback;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FeedbackEvent
        extends GwtEvent<FeedbackEventHandler>
{

    public static Type<FeedbackEventHandler> TYPE = new Type<>();

    private FeedbackType feedbackType;

    private String feedbackMessage;

    public FeedbackEvent( String feedbackMessage, FeedbackType feedbackType )
    {
        this.feedbackMessage = feedbackMessage;
        this.feedbackType = feedbackType;
    }

    public FeedbackType getFeedbackType()
    {
        return feedbackType;
    }

    public String getFeedbackMessage()
    {
        return feedbackMessage;
    }

    @Override
    public Type<FeedbackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch( FeedbackEventHandler handler )
    {
        handler.onMessage( this );
    }
}
