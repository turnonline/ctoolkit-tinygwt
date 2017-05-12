/*
 * Copyright (c) 2017 Comvai, s.r.o. All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.ctoolkit.turnonline.gwt.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import org.ctoolkit.turnonline.gwt.client.i18n.Localization;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class BooleanListBox
        extends ListBox
{
    public BooleanListBox( String prefix )
    {
        this( prefix, false );
    }

    public BooleanListBox( String prefix, boolean emptyItem )
    {
        if ( emptyItem )
        {
            addItem( Localization.localize( "NULL" ), "" );
        }

        addItem( Localization.localizeBoolean( prefix, BooleanEnum.TRUE ), BooleanEnum.TRUE.name() );
        addItem( Localization.localizeBoolean( prefix, BooleanEnum.FALSE ), BooleanEnum.FALSE.name() );
    }

    public Boolean getValue()
    {
        String value = getValue( getSelectedIndex() );
        if ( value != null && !"".equals( value.trim() ) )
        {
            return BooleanEnum.valueOf( value ).enumToBoolean();
        }

        return null;
    }

    public void setValue( Boolean value )
    {
        if ( value != null )
        {
            setSelectedIndex( BooleanEnum.booleanToEnum( value ).ordinal() );
        }
    }

    public enum BooleanEnum
    {
        NULL( null ),
        TRUE( Boolean.TRUE ),
        FALSE( Boolean.FALSE );

        private Boolean bool;

        BooleanEnum( Boolean bool )
        {
            this.bool = bool;
        }

        public static BooleanEnum booleanToEnum( Boolean value )
        {
            for ( BooleanEnum be : BooleanEnum.class.getEnumConstants() )
            {
                if ( be.getBool().equals( value ) )
                {
                    return be;
                }
            }

            return NULL;
        }

        private Boolean getBool()
        {
            return bool;
        }

        public Boolean enumToBoolean()
        {
            return getBool();
        }
    }
}
