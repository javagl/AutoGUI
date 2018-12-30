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

import java.util.Objects;

import de.javagl.autogui.Converter;
import de.javagl.autogui.model.properties.PropertyExtractor;
import de.javagl.autogui.model.properties.PropertyExtractors;

/**
 * Methods related to {@link ValueModel} instances
 */
public class ValueModels
{
    /**
     * Creates a new {@link ValueModel} with the given initial value. The
     * initial value may not be <code>null</code>. If it has to be 
     * <code>null</code>, then the {@link #create(Class) class-based factory
     * method} has to be used.
     * 
     * @param <T> The value type
     * @param initialValue The initial value. 
     * @return The {@link ValueModel}
     * @throws NullPointerException If the initial value is <code>null</code>
     */
    public static <T> ValueModel<T> create(T initialValue)
    {
        Objects.requireNonNull(initialValue, 
            "The initialValue may not be null");
        Class<?> valueType = initialValue.getClass();
        ValueModel<T> valueModel = new DefaultValueModel<T>(valueType);
        valueModel.setValue(initialValue);
        return valueModel;
    }
    
    /**
     * Creates a new {@link ValueModel} with the given type
     * 
     * @param <T> The value type
     * @param valueType The value type
     * @return The {@link ValueModel}
     * @throws NullPointerException If the value type is <code>null</code>
     */
    public static <T> ValueModel<T> create(Class<T> valueType)
    {
        ValueModel<T> valueModel = new DefaultValueModel<T>(valueType);
        return valueModel;
    }

    /**
     * Create a new {@link StructuredValueModel} from the given class
     * 
     * @param <T> The value type
     * @param valueType The value type
     * @return The {@link StructuredValueModel}
     */
    public static <T> StructuredValueModel<T> createStructured(
        Class<T> valueType)
    {
        ValueModel<T> internalValueModel = ValueModels.create(valueType);
        return new DefaultStructuredValueModel<T>(null, "", internalValueModel,
            PropertyExtractors.createDefault());
    }
    
    
    /**
     * Create a new {@link StructuredValueModel} from the given class
     * 
     * @param <T> The value type
     * @param valueType The value type
     * @param allProperties Whether all properties should be considered
     * (including non-public fields)
     * @return The {@link StructuredValueModel}
     */
    public static <T> StructuredValueModel<T> createStructured(
        Class<T> valueType, boolean allProperties)
    {
        ValueModel<T> internalValueModel = ValueModels.create(valueType);
        PropertyExtractor propertyExtractor = allProperties ?
            PropertyExtractors.createForAllProperties() :
            PropertyExtractors.createDefault();
        return new DefaultStructuredValueModel<T>(null, "", internalValueModel,
            propertyExtractor);
    }
    
    
    /**
     * Creates a new {@link ValueModel} with the given type and initial value
     * 
     * @param <T> The value type
     * @param valueType The value type
     * @param initialValue The initial value
     * @return The {@link ValueModel}
     * @throws NullPointerException If the value type is <code>null</code>
     */
    public static <T> ValueModel<T> create(
        Class<T> valueType, T initialValue)
    {
        ValueModel<T> valueModel = new DefaultValueModel<T>(valueType);
        valueModel.setValue(initialValue);
        return valueModel;
    }
    
    /**
     * Create a {@link ValueModel} that is a converting view on another
     * {@link ValueModel}
     * 
     * @param <S> The source (delegate) type
     * @param <T> The target type
     * @param delegate The delegate
     * @param converter The {@link Converter}
     * @return The {@link ValueModel}
     * @throws NullPointerException If any parameter is <code>null</code>
     */
    public static <S, T> ValueModel<T> converting(
        ValueModel<S> delegate, Converter<S, T> converter)
    {
        return new ConvertingValueModel<S, T>(
            delegate, converter);
    }
    
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private ValueModels()
    {
        // Private constructor to prevent instantiation
    }
}
