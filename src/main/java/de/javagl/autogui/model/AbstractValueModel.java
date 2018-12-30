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

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abstract implementation of a {@link ValueModel}, only maintaining the
 * {@link ValueListener} instances and the {@link #getValueType() value type}
 *
 * @param <T> The type of the value
 */
public abstract class AbstractValueModel<T> implements ValueModel<T>
{
    /**
     * The value type
     */
    private final Class<?> valueType;
    
    /**
     * The list of {@link ValueListener} instances that will be informed 
     * in {@link #fireValueChanged}
     */
    private final List<ValueListener<T>> valueListeners;

    /**
     * Creates a new value model
     * 
     * @param valueType The value type
     * @throws NullPointerException If the valueType is <code>null</code>
     */
    protected AbstractValueModel(Class<?> valueType)
    {
        this.valueType = Objects.requireNonNull(valueType,
            "The valueType may not be null");
        this.valueListeners = new CopyOnWriteArrayList<ValueListener<T>>();
    }
    
    @Override
    public final Class<?> getValueType()
    {
        return valueType;
    }
    
    /**
     * Will be called when the value of this {@link ValueModel} changed
     * to the given value, and the {@link ValueListener} instances should be
     * informed.
     * 
     * @param oldValue The old value
     * @param newValue The new value
     */
    protected final void fireValueChanged(T oldValue, T newValue)
    {
        for (ValueListener<T> valueListener : valueListeners)
        {
            valueListener.valueChanged(oldValue, newValue);
        }
    }
    
    @Override
    public final void addValueListener(ValueListener<T> valueListener)
    {
        valueListeners.add(valueListener);
    }

    @Override
    public final void removeValueListener(ValueListener<?> valueListener)
    {
        valueListeners.remove(valueListener);
    }

    
}