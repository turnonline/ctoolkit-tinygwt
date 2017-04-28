package org.ctoolkit.turnonline.gwt.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The base {@link IView} implementation as composite widget.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class View
        extends Composite
        implements IView
{
    private final EventBus eventBus;

    public View( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    /**
     * Returns the singleton event bus.
     *
     * @return the singleton event bus
     */
    protected final EventBus bus()
    {
        return eventBus;
    }
}
