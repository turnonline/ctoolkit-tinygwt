package org.ctoolkit.turnonline.gwt.client.view;

/**
 * The view decorator gives encapsulated possibility to slightly modify/decorate content of the associated view.
 * For example show or hide some buttons based on the specific conditions encapsulated in decorator implementation etc.
 * The {@link #decorate()} method will be called right before showing a view to the user once data for view are ready.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface Decorator
{
    /**
     * Decorate/modify associated view.
     */
    void decorate();
}
