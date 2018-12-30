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
 * Interface for classes that can convert values between different scales
 * or types
 * 
 * @param <S> The first type
 * @param <T> The second type
 */
public interface Converter<S, T>
{
    /**
     * Returns the source type
     * 
     * @return The source type
     */
    Class<S> getSourceType();

    /**
     * Returns the target type
     * 
     * @return The target type
     */
    Class<T> getTargetType();
    
    /**
     * Returns a function to convert a source value into a target value
     * 
     * @return The conversion function
     */
    Function<S, ? extends T> forward();

    /**
     * Returns a function to convert a target value into a source value
     * 
     * @return The conversion function
     */
    Function<T, ? extends S> backward();
    
}