package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

/**
 * Button column with icon and tooltip
 *
 * @author <a href="mailto:pohorelec@comvai.com">Jozef Pohorelec</a>
 */
public class IconButtonColumn<T>
        extends Column<T, String>
{
    public IconButtonColumn( String icon )
    {
        this( icon, null );
    }

    /**
     * Construct a new Column with a given {@link Cell}.
     */
    public IconButtonColumn( String icon, String tooltip )
    {
        super( new IconButtonCell( icon, tooltip ) );
    }

    @Override
    public String getValue( T object )
    {
        return null;
    }
}
