package org.ctoolkit.gwt.client.ui.table;

import com.google.gwt.user.cellview.client.Column;

/**
 * Column with icon and tooltip
 *
 * @author <a href="mailto:pohorelec@comvai.com">Jozef Pohorelec</a>
 */
public class IconColumn<T>
        extends Column<T, T>
{
    public IconColumn( IconCell<T> cell )
    {
        super( cell );
    }

    public IconColumn( String className, IconCell.Delegate<T> delegate )
    {
        super( new IconCell<>( className, delegate ) );
    }

    public IconColumn( String className, String tooltip, IconCell.Delegate<T> delegate )
    {
        super( new IconCell<>( className, "", tooltip, delegate ) );
    }

    public IconColumn( String className, String classNameDisabled, String tooltip, IconCell.Delegate<T> delegate )
    {
        super( new IconCell<>( className, classNameDisabled, tooltip, delegate ) );
    }

    @Override
    public T getValue( T object )
    {
        return object;
    }
}
