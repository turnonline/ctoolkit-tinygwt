package org.ctoolkit.turnonline.gwt.client.presenter;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import org.ctoolkit.turnonline.gwt.client.view.IView;

import static com.google.gwt.thirdparty.guava.common.base.Preconditions.checkNotNull;

/**
 * The bindery activity presenter implementation. Presenter acts upon the model and the view.
 * It retrieves data from the model and prepares it to be displayed in the view.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class BinderyPresenter<V extends IView>
        extends BaseActivity
{
    private static IView currentView;

    private final EventBus eventBus;

    private final V view;

    private final PlaceController placeController;

    private AcceptsOneWidget root;

    public BinderyPresenter( EventBus eventBus, V view, PlaceController placeController )
    {
        this.eventBus = checkNotNull( eventBus );
        this.view = checkNotNull( view );
        this.placeController = checkNotNull( placeController );

        bind();
    }

    /**
     * See {@link com.google.gwt.activity.shared.ActivityManager#onPlaceChange(PlaceChangeEvent)}
     * -> currentActivity.equals(nextActivity).
     * It always returns false to make sure that presenter will reload its data for same place with different values.
     *
     * @param obj the reference object to compare
     * @return always false
     */
    @Override
    public boolean equals( Object obj )
    {
        return false;
    }

    /**
     * Use this method to add handlers. It's called right after root presenter has been constructed.
     */
    protected abstract void bind();

    @Override
    public void start( AcceptsOneWidget panel, EventBus eventBus )
    {
        root = panel;

        // call to hide content
        onBeforeBackingObject();

        // add view to the root of the page (NOTE: panel is root of the page)
        root.setWidget( currentView == null ? view : currentView );

        // call backing object method
        onBackingObject();
    }

    @Override
    public void onStop()
    {
        currentView = view;
    }

    /**
     * This method is being called right before {@link #onBackingObject()} method.
     * It hides current view, until data are completely loaded from backend and prepared to be shown.
     */
    protected void onBeforeBackingObject()
    {
        view.hide();
    }

    /**
     * Override if needed to prepare data. For example for backend data initialization etc.
     * <p>
     * NOTE: Do not put any handlers in to this method otherwise handler will be added every time view is being rendered.
     */
    protected void onBackingObject()
    {
    }

    /**
     * Call this method once backing object is fully loaded from backend and its data are ready to be shown.
     * You need to call this method on your own because of asynchronous behavior of getting backing object.
     * It will show view with prepared data and view components.
     */
    protected void onAfterBackingObject()
    {
        view.show();
        this.root.setWidget( view );
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
     * Returns the related view instance.
     *
     * @return the related view instance
     */
    protected V view()
    {
        return view;
    }

    /**
     * Returns the place controller.
     *
     * @return the place controller
     */
    protected PlaceController controller()
    {
        return placeController;
    }
}
