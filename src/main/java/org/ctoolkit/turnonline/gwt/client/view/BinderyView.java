package org.ctoolkit.turnonline.gwt.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;

import java.util.ArrayList;
import java.util.List;

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

    private List<Decorator> decorators;

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

    /**
     * Associate given decorator instance with this view.
     *
     * @param decorator the decorator to be added into the list of decorators
     */
    public void add( Decorator decorator )
    {
        if ( decorator == null )
        {
            throw new NullPointerException();
        }

        if ( decorators == null )
        {
            decorators = new ArrayList<>();
        }

        decorators.add( decorator );
    }

    /**
     * Clear all associated decorators.
     */
    public void clearDecorators()
    {
        decorators.clear();
    }

    @Override
    public void decorate()
    {
        if ( decorators != null )
        {
            for ( Decorator decorator : decorators )
            {
                decorator.decorate();
            }
        }
    }
}
