package org.ctoolkit.turnonline.gwt.server.requestfactory;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The common implementation of the GWT request factory exception handler.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class CommonExceptionHandler
        implements ExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger( CommonExceptionHandler.class );

    public CommonExceptionHandler()
    {
    }

    @Override
    public ServerFailure createServerFailure( Throwable throwable )
    {
        logger.error( "Server failure", throwable );

        String message = throwable == null ? null : throwable.getMessage();
        return new ServerFailure( "Server Error: " + message, null, null, true );
    }
}
