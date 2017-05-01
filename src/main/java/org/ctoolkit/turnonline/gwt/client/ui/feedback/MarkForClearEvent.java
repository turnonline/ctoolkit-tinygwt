package org.ctoolkit.turnonline.gwt.client.ui.feedback;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class MarkForClearEvent
        extends GwtEvent<MarkForClearEventHandler>
{
    public static Type<MarkForClearEventHandler> TYPE = new Type<>();

    public Type<MarkForClearEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    protected void dispatch( MarkForClearEventHandler handler )
    {
        handler.onMark( this );
    }
}