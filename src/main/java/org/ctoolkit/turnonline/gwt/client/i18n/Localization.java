/*
 * Comvai GWT, Comvai specific GWT components
 * Copyright (C) 2012 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.turnonline.gwt.client.i18n;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import org.ctoolkit.turnonline.gwt.client.ui.BooleanListBox;

import java.util.MissingResourceException;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class Localization
{
    private static ConstantsWithLookup constants;

    public static String localize( String value, String... params )
    {
        try
        {
            if ( constants == null )
            {
                throw new NullPointerException( "Constants must not be null. You probably forgot to call " + Localization.class.getName() + "#init method" );
            }

            String val = constants.getString( value );


            if ( params != null )
            {
                for ( int i = 0; i < params.length; i++ )
                {
                    val = val.replaceAll( "\\{" + i + "\\}", params[i] );
                }
            }

            return val;
        }
        catch ( MissingResourceException e )
        {
            return "##" + value + "##";
        }
    }

    public static String localizeEnum( Enum localizedEnum, String... params )
    {
        String[] parts = localizedEnum.getDeclaringClass().getName().split( "\\." );
        String prefix = parts[parts.length - 1];
        return localize( prefix + "__" + localizedEnum.name(), params );
    }

    public static String localizeBoolean( String prefix, BooleanListBox.BooleanEnum booleanEnum )
    {
        return localize( prefix + "__" + booleanEnum.name() );
    }

    public static void init( ConstantsWithLookup constantsWithLookup )
    {
        constants = constantsWithLookup;
    }
}