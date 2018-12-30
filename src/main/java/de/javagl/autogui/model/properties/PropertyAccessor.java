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
package de.javagl.autogui.model.properties;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Interface for classes that can access a property. This property may,
 * for example, be a field of another class, or a property of a Java Bean.<br>
 * <br>
 * This interface should not be considered to be part of the public API!
 */
public interface PropertyAccessor
{
    /**
     * Returns the name of the property
     * 
     * @return The name
     */
    String getName();
    
    /**
     * Returns the type of the property
     * 
     * @return The type
     */
    Class<?> getType();
    
    /**
     * Returns the read method. This method may be used to extract the
     * value of this property for a given object.
     * 
     * @return The read method
     */
    Function<Object, Object> getReadMethod();
    
    /**
     * Returns the write method. This method may be used to set the 
     * value of this property for a given object to the given value:
     * The first argument will be the object, and the second argument
     * will be the new property value.
     * 
     * @return The write method
     */
    BiConsumer<Object, Object> getWriteMethod();
}

