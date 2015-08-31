package org.ctoolkit.turnonline.gwt.server.requestfactory;

import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Basic implementation of the GWT request factory service locator injecting requested services by guice.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BasicGuiceServiceLocator
        implements ServiceLocator
{
    private static final Logger log = LoggerFactory.getLogger( BasicGuiceServiceLocator.class );

    private final Injector injector;

    @Inject
    public BasicGuiceServiceLocator( Injector injector )
    {
        this.injector = injector;
    }

    @Override
    public Object getInstance( Class<?> clazz )
    {
        if ( log.isInfoEnabled() )
        {
            log.info( "Requesting service for " + clazz );
        }

        return injector.getInstance( clazz );
    }
}
