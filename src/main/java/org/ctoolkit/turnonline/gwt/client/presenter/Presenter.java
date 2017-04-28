package org.ctoolkit.turnonline.gwt.client.presenter;

import com.google.gwt.place.shared.PlaceController;
import org.ctoolkit.turnonline.gwt.client.event.CachedEventBus;
import org.ctoolkit.turnonline.gwt.client.view.IView;

/**
 * Specific presenter injecting customized {@link CachedEventBus}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class Presenter<V extends IView>
        extends BinderyPresenter<V>
{
    public Presenter( CachedEventBus eventBus, V view, PlaceController placeController )
    {
        super( eventBus, view, placeController );
    }

    @Override
    protected final CachedEventBus bus()
    {
        return ( CachedEventBus ) super.bus();
    }

    @Override
    protected final V view()
    {
        return super.view();
    }

    @Override
    protected final PlaceController controller()
    {
        return super.controller();
    }
}
