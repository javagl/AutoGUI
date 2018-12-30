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

/**
 * Default implementation of a {@link ValueModel}
 *
 * @param <T> The value type
 */
class DefaultValueModel<T> extends AbstractValueModel<T> 
    implements ValueModel<T>
{
    /**
     * The value
     */
    private T value;
    
    /**
     * Default constructor
     * 
     * @param valueType The value type
     */
    DefaultValueModel(Class<?> valueType)
    {
        super(valueType);
    }

    @Override
    public T getValue()
    {
        return value;
    }

    @Override
    public void setValue(T newValue)
    {
        T oldValue = getValue();
        this.value = newValue;
        if (!Objects.equals(oldValue, newValue))
        {
            fireValueChanged(oldValue, newValue);
        }
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[" 
            + "valueType=" + getValueType().getSimpleName() + "]";
    }
    
}
