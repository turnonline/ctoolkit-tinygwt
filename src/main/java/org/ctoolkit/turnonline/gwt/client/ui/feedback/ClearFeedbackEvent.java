package org.ctoolkit.turnonline.gwt.client.ui.feedback;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class ClearFeedbackEvent
        extends GwtEvent<ClearFeedbackEventHandler>
{

    public static Type<ClearFeedbackEventHandler> TYPE = new Type<>();

    @Override
    public Type<ClearFeedbackEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch( ClearFeedbackEventHandler handler )
    {
        handler.onClear( this );
    }
}
