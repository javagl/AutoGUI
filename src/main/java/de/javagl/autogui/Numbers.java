/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.autogui;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Utility methods related to Numbers
 */
public class Numbers
{
    /**
     * The set of all number types
     */
    private static final Set<Class<?>> NUMBER_TYPES;
    
    // Initialization of the NUMBER_TYPES set
    static
    {
        Set<Class<?>> numberTypes = new LinkedHashSet<Class<?>>();
        numberTypes.add(byte.class);
        numberTypes.add(Byte.class);
        numberTypes.add(short.class);
        numberTypes.add(Short.class);
        numberTypes.add(int.class);
        numberTypes.add(Integer.class);
        numberTypes.add(long.class);
        numberTypes.add(Long.class);
        numberTypes.add(float.class);
        numberTypes.add(Float.class);
        numberTypes.add(double.class);
        numberTypes.add(Double.class);
        NUMBER_TYPES = Collections.unmodifiableSet(numberTypes);
    }
    
    /**
     * Returns whether the given type is a number type. That is, whether 
     * it is <code>byte.class</code>, <code>short.class</code>, 
     * <code>int.class</code>, <code>long.class</code>, 
     * <code>float.class</code> or <code>double.class</code>, 
     * or the respective reference type.
     * 
     * @param t The type
     * @return Whether the given type is a number type
     */
    public static boolean isNumberType(Class<?> t)
    {
        return NUMBER_TYPES.contains(t);
    }

    /**
     * Calls {@link #convertNumberRoundingTo(Number, Class)} with the
     * class of the given target instance
     * 
     * @param <T> The target type
     * @param number The number
     * @param target The target instance
     * @return The number, converted (possibly rounded) into the target type
     * @throws ClassCastException If the target type is not one of the
     * basic Number types
     * @throws NullPointerException If the target is <code>null</code>
     */
    @SuppressWarnings("unchecked")
    static <T> T convertNumberRoundingToTarget(Number number, T target)
    {
        return convertNumberRoundingTo(number, (Class<T>)target.getClass());
    }
    
    /**
     * Convert the given number into the given target type, rounding if
     * necessary. E.g. if the given target type is <code>byte</code>, 
     * <code>short</code>, <code>int</code> or <code>long</code>,
     * or the respective reference type, then the double value
     * of the given number will be rounded to the closest integral
     * value, and converted into the given target type. Otherwise,
     * if the target type is <code>float</code> or <code>double</code>, 
     * or the respective reference type, then the corresponding value 
     * will be obtained from the given number and returned. Otherwise, 
     * an attempt is made to cast the given number to the expected type 
     * (which will most likely fail with a ClassCastException).
     * 
     * @param <T> The target type
     * @param number The number
     * @param targetType The target type
     * @return The number, converted (possibly rounded) into the target type
     * @throws ClassCastException If the target type is not one of the
     * basic Number types
     */
    public static <T> T convertNumberRoundingTo(
        Number number, Class<T> targetType)
    {
        if (number == null)
        {
            return null;
        }
        Number localNumber = number;
        Number result = localNumber;
        if (targetType.equals(byte.class) ||
            targetType.equals(Byte.class))
        {
            localNumber = Math.round(localNumber.doubleValue());
            result = localNumber.byteValue();
        }
        if (targetType.equals(short.class) ||
            targetType.equals(Short.class))
        {
            localNumber = Math.round(localNumber.doubleValue());
            result = localNumber.shortValue();
        }
        if (targetType.equals(int.class) ||
            targetType.equals(Integer.class))
        {
            localNumber = Math.round(localNumber.doubleValue());
            result = localNumber.intValue();
        }
        if (targetType.equals(long.class) ||
            targetType.equals(Long.class))
        {
            localNumber = Math.round(localNumber.doubleValue());
            result = localNumber.longValue();
        }
        if (targetType.equals(float.class) ||
            targetType.equals(Float.class))
        {
            result = localNumber.floatValue();
        }
        if (targetType.equals(double.class) ||
            targetType.equals(Double.class))
        {
            result = localNumber.doubleValue();
        }
        @SuppressWarnings("unchecked")
        T castedResult = (T)result;
        return castedResult;
    }
    
    
    /**
     * Compute the relative position of the given value between the given
     * minimum and maximum value.
     * None of the given parameters may be <code>null</code>.
     * 
     * @param <T> The number type
     * @param min The minimum
     * @param max The maximum
     * @param value The value
     * @return The alpha value
     */
    public static <T extends Number> double computeAlpha(T min, T max, T value)
    {
        double minD = min.doubleValue();
        double maxD = max.doubleValue();
        double valueD = value.doubleValue();
        double alpha = (valueD - minD) / (maxD - minD);
        return alpha;
    }
    
    /**
     * Interpolate between the given minimum and maximum value, based on
     * the given alpha value.
     * None of the given parameters may be <code>null</code>.
     * 
     * @param <T> The number type
     * @param min The minimum
     * @param max The maximum
     * @param alpha The alpha value
     * @return The interpolated value
     */
    public static <T extends Number> T interpolate(T min, T max, double alpha)
    {
        @SuppressWarnings("unchecked")
        Class<T> targetType = (Class<T>) min.getClass();
        double minD = min.doubleValue();
        double maxD = max.doubleValue();
        double interpolated = minD + (maxD - minD) * alpha;
        return convertNumberRoundingTo(interpolated, targetType);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Numbers()
    {
        // Private constructor to prevent instantiation
    }
}
