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

import java.util.function.Function;

/**
 * Utility methods to create {@link Function} instances
 */
class Functions
{
    /**
     * Creates a {@link Function} that maps one interval to another,
     * rounding the resulting values to the target types if necessary.
     * None of the given parameters may be <code>null</code>.
     * 
     * @param <S> The source interval type 
     * @param <T> The target interval type
     * 
     * @param minS The minimum value of the source interval
     * @param maxS The maximum value of the source interval
     * @param minT The minimum value of the target interval
     * @param maxT The maximum value of the target interval
     * @return The {@link Function}
     */
    static <S extends Number, T extends Number> Function<S, T> mapping(
        S minS, S maxS, 
        T minT, T maxT)
    {
        return new Function<S, T>()
        {
            @Override
            public T apply(S s)
            {
                if (s == null)
                {
                    return null;
                }
                double alpha = Numbers.computeAlpha(minS, maxS, s);
                return Numbers.interpolate(minT, maxT, alpha);
            }
        };
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Functions()
    {
        // Private constructor to prevent instantiation
    }
    
    
    

}
