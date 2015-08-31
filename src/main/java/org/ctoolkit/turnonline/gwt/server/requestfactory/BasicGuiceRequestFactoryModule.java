package org.ctoolkit.turnonline.gwt.server.requestfactory;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

import javax.inject.Singleton;

/**
 * The basic GWT request factory guice module.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BasicGuiceRequestFactoryModule
        extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind( ExceptionHandler.class ).to( CommonExceptionHandler.class );
        bind( BasicGuiceServiceLocator.class ).in( Singleton.class );

        Multibinder<ServiceLayerDecorator> decorators = Multibinder.newSetBinder( binder(), ServiceLayerDecorator.class );
        decorators.addBinding().to( BasicGuiceServiceLayerDecorator.class );
    }
}
