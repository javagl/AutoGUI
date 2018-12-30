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
package de.javagl.autogui.model;

import de.javagl.autogui.model.properties.PropertyExtractor;

/**
 * Methods to create specialized {@link ValueModel} instances that are used
 * internally. These methods should not be used by clients.
 */
public class ValueModelsInternal
{
    /**
     * Create a new {@link StructuredValueModel} from the given parameters
     * 
     * @param <T> The value type
     * @param parent The parent 
     * @param name The name
     * @param internalValueModel The internal value model 
     * @param propertyExtractor The {@link PropertyExtractor}
     * @return The {@link StructuredValueModel}
     */
    public static <T> StructuredValueModel<T> createStructuredValueModel(
        StructuredValueModel<?> parent, String name, 
        ValueModel<T> internalValueModel, PropertyExtractor propertyExtractor)
    {
        return new DefaultStructuredValueModel<T>(
            parent, name, internalValueModel, propertyExtractor);
    }
    
    /**
     * Creates an {@link ArrayValueModel} that is backed by the given
     * {@link ValueModel}
     * 
     * @param <A> The array type
     * @param <E> The element/component type
     * @param valueModel The {@link ValueModel} that provides the array
     * @return The {@link ArrayValueModel}
     * @throws NullPointerException If the valueModel is <code>null</code>
     * @throws IllegalArgumentException If the {@link ValueModel#getValueType()
     * value type of the value model} is not an array type
     */
    static <A, E> ArrayValueModel<A, E> createArrayValueModel(
        ValueModel<A> valueModel)
    {
        return new DefaultArrayValueModel<A, E>(valueModel);
    }
    
    /**
     * Create a {@link StructuredArrayValueModel} from the given parameters
     * 
     * @param <A> The array type
     * @param <E> The element/component type
     * @param parent The parent
     * @param name The name
     * @param arrayValueModel The backing {@link ArrayValueModel}
     * @param propertyExtractor The {@link PropertyExtractor}
     * @return The {@link StructuredArrayValueModel}
     * @throws NullPointerException If the name is <code>null</code>
     * @throws NullPointerException If the arrayValueModel is <code>null</code>
     */
    static <A, E> StructuredArrayValueModel<A, E> 
        createStructuredArrayValueModel(
            StructuredValueModel<?> parent, String name, 
            ArrayValueModel<A, E> arrayValueModel,
            PropertyExtractor propertyExtractor)
    {
        return new DefaultStructuredArrayValueModel<A, E>(
            parent, name, arrayValueModel, propertyExtractor);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ValueModelsInternal()
    {
        // Private constructor to prevent instantiation
    }
    
}
