package org.ctoolkit.gwt.client.ui.table;

import java.util.List;

/**
 * Table's data update handler interface.
 *
 * @param <T> the concrete type of the list item resource
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface DataUpdateHandler<T>
{
    /**
     * Callback that will update table's rows.
     *
     * @param start  the start index of the list values (paging from).
     * @param values the list of items as an input to update table
     */
    void updateRowData( int start, List<T> values );

    /**
     * Clears table data.
     */
    void clear();
}
