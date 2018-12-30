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

import java.util.Objects;
import java.util.function.Function;

/**
 * Utility methods to create {@link Converter} instances
 */
public class Converters
{
    /**
     * Creates a generic {@link Converter} for converting between the
     * given number types, rounding to the respective type if
     * necessary.
     * 
     * @param <S> The source type
     * @param <T> The target type
     * @param sourceType The source type
     * @param targetType The target type
     * @return The {@link Converter}
     */
    public static <S extends Number, T extends Number> Converter<S, T> 
        forNumbers(Class<S> sourceType, Class<T> targetType)
    {
        Function<S, T> forward = n -> 
            Numbers.convertNumberRoundingTo(n, targetType);
        Function<T, S> backward = n -> 
            Numbers.convertNumberRoundingTo(n, sourceType);
        return fromFunctions(sourceType, targetType, forward, backward);
    }
    
    
    /**
     * Creates a {@link Converter} from two {@link Function} objects that
     * perform the forward- and backward conversion. None of these
     * functions may be <code>null</code>.
     * 
     * @param <S> The first type
     * @param <T> The second type
     * @param sourceType The source type
     * @param targetType The target type
     * @param forward The forward conversion {@link Function}
     * @param backward The backward conversion {@link Function}
     * @return The {@link Converter}
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static <S, T> Converter<S, T> fromFunctions(
        Class<S> sourceType, Class<T> targetType,
        Function<S, ? extends T> forward, 
        Function<T, ? extends S> backward)
    {
        Objects.requireNonNull(sourceType, 
            "The sourceType is null");
        Objects.requireNonNull(targetType, 
            "The targetType is null");
        Objects.requireNonNull(forward, 
            "The forward function is null");
        Objects.requireNonNull(backward, 
            "The backward function is null");
        return new Converter<S, T>()
        {
            @Override
            public Class<S> getSourceType()
            {
                return sourceType;
            }

            @Override
            public Class<T> getTargetType()
            {
                return targetType;
            }
            
            @Override
            public Function<S, ? extends T> forward()
            {
                return forward;
            }

            @Override
            public Function<T, ? extends S> backward()
            {
                return backward;
            }
        };
    }
    
//    /**
//     * Creates an identity {@link Converter}
//     * 
//     * @param <T> The type
//     * @return The {@link Converter}
//     */
//    public static <T> Converter<T, T> identity()
//    {
//        return fromFunctions(
//            Function.identity(), 
//            Function.identity());
//    }
    
    
    /**
     * Creates a {@link Converter} that converts from one number
     * interval to another. None of the given parameters may 
     * be <code>null</code>.
     * 
     * @param <S> The source interval type
     * @param <T> The target interval type
     * 
     * @param minS The minimum value of the source interval
     * @param maxS The maximum value of the source interval
     * @param minT The minimum value of the target interval
     * @param maxT The maximum value of the target interval
     * @return The {@link Converter}
     * @throws NullPointerException If any argument is <code>null</code>
     */
    public static <S extends Number, T extends Number> Converter<S, T> 
        numberMapping(
            S minS, S maxS, 
            T minT, T maxT)
    {
        Objects.requireNonNull(minS, 
            "The minimum source value is null");
        Objects.requireNonNull(maxS, 
            "The maximum source value is null");
        Objects.requireNonNull(minT, 
            "The minimum target value is null");
        Objects.requireNonNull(maxT, 
            "The maximum target value is null");
                
        Function<S, T> forward = 
            Functions.mapping(minS, maxS, minT, maxT);
        
        Function<T, S> backward = 
            Functions.mapping(minT, maxT, minS, maxS);
        
        @SuppressWarnings("unchecked")
        Class<S> sourceType = (Class<S>) minS.getClass();
        @SuppressWarnings("unchecked")
        Class<T> targetType = (Class<T>) minT.getClass();
        return fromFunctions(sourceType, targetType, forward, backward);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Converters()
    {
        // Private constructor to prevent instantiation
    }
    
}
