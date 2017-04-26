package org.ctoolkit.turnonline.gwt.client.common.view;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * The view interface to be used with {@link org.ctoolkit.turnonline.gwt.client.common.presenter.Presenter}.
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
}
