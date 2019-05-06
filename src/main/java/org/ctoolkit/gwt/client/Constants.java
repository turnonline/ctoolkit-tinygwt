package org.ctoolkit.gwt.client;

/**
 * Constants.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface Constants
{
    /**
     * ISO-8601 (RFC-3339) date format.
     */
    String REST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";

    /**
     * Servlet path that will handle target URL to upload file (blob).
     */
    String UPLOAD_PATH = "/storage-upload";
}
