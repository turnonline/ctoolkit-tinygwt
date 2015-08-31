package org.ctoolkit.turnonline.gwt.server.requestfactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

/**
 * The GWT request factory servlet guice wrapper.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Singleton
public class GuiceRequestFactoryServlet
        extends RequestFactoryServlet
{
    private static final long serialVersionUID = 7038323133496393310L;

    @Inject
    public GuiceRequestFactoryServlet( ExceptionHandler exceptionHandler, Set<ServiceLayerDecorator> decorators )
    {
        super( exceptionHandler, decorators.toArray( new ServiceLayerDecorator[decorators.size()] ) );
    }
}
