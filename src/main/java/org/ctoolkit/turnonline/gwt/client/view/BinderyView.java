package org.ctoolkit.turnonline.gwt.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The bindery {@link IView} implementation as a composite widget.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class BinderyView
        extends Composite
        implements IView
{
    private final EventBus eventBus;

    public BinderyView( EventBus eventBus )
    {
        this.eventBus = eventBus;
    }

    /**
     * Returns the singleton event bus.
     *
     * @return the singleton event bus
     */
    protected EventBus bus()
    {
        return eventBus;
    }
}
