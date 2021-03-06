/*
 * Copyright (c) 2019 Comvai, s.r.o. All Rights Reserved.
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

package org.ctoolkit.gwt.client.facade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Value checker that checks relevant property values whether they are not {@code null}
 * for POST operation (sent to remote service).
 * <p>
 * The basic motivation behind is to avoid to send for example the following:
 * <pre>
 * {
 *     "firstName": "John",
 *     "address": {
 *         "street": null,
 *         "city": null
 * }
 * </pre>
 * or
 * <pre>
 * {
 *     "firstName": "John",
 *     "address": {}
 * }
 * </pre>
 * The proper JSON for above should look like:
 * <pre>
 * {
 *     "firstName": "John"
 * }
 * </pre>
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public interface RelevantNullChecker
{
    /**
     * Checks whether this resource has at least a single property with non {@code null}
     * value within its tree, among those that has been included to be checked.
     *
     * @return {@code true} if at least a single property of this resource has a non {@code null} value,
     * otherwise {@code false}
     */
    boolean allNull();

    /**
     * Sets value at consumer (setter) only if input value meets a condition.
     * Other words, include only those properties to be checked that will be processed by a remote service.
     *
     * @param setter the consumer to be used to set value only if {@link #allNull(Object...)} returns {@code true}
     * @param value  the value to be checked and set if meets condition
     * @param <T>    the type of the value
     * @return {@code true} if value from the input tree has at least single non {@code null} value within its tree,
     * otherwise {@code false}
     */
    default <T extends RelevantNullChecker> boolean setIfNotAllNull( @Nonnull Consumer<T> setter, @Nullable T value )
    {
        if ( allNull( value ) )
        {
            setter.accept( null );
            return false;
        }
        else
        {
            setter.accept( value );
            return true;
        }
    }

    /**
     * Checks whether the input values has at least single non {@code null} value within its tree.
     *
     * @param values those values that are relevant to be sent to remote service
     * @return {@code true} if at least a single value from the input a non {@code null} value,
     * otherwise {@code false}
     */
    default boolean allNull( @Nullable Object... values )
    {
        if ( values == null )
        {
            return true;
        }

        for ( Object value : values )
        {
            if ( value != null )
            {
                if ( value instanceof RelevantNullChecker )
                {
                    if ( !( ( RelevantNullChecker ) value ).allNull() )
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        return true;
    }
}
