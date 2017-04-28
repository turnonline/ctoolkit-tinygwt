package org.ctoolkit.turnonline.gwt.client.presenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Helper class to fix the issue https://github.com/gwtproject/gwt/issues/6652
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class BaseActivity
        extends AbstractActivity
{

    public abstract void start( AcceptsOneWidget panel, EventBus eventBus );

    @Override
    public void start( AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus )
    {
        start( panel, ( EventBus ) eventBus );
    }
}
