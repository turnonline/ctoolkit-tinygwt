package org.ctoolkit.turnonline.gwt.client.view;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * The view interface to be used with {@link org.ctoolkit.turnonline.gwt.client.presenter.Presenter}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface IView
        extends IsWidget
{
    /**
     * Hides this composite widget.
     */
    void hide();

    /**
     * Shows this composite widget.
     */
    void show();

    /**
     * Decorate/modify the content of this view (show or hide some buttons based on the specific conditions).
     * The method will be called right before showing a view to the user once data for view are ready.
     */
    void decorate();
}
