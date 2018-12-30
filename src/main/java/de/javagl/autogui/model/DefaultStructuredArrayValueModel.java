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

import java.util.Collections;
import java.util.Map;

import de.javagl.autogui.model.properties.PropertyExtractor;

/**
 * Default implementation of a {@link StructuredArrayValueModel}. It only
 * wraps an {@link ArrayValueModel} and adds the structure information.
 * This includes wrapping the {@link #getElementValueModel(int) element value
 * models} into {@link StructuredValueModel} instances.
 *
 * @param <A> The array type
 * @param <E> The element/component type
 */
class DefaultStructuredArrayValueModel<A, E> 
    implements StructuredArrayValueModel<A, E>
{
    /**
     * The parent
     */
    private final StructuredValueModel<?> parent;
    
    /**
     * The name of this model inside its parent
     */
    private final String name;
    
    /**
     * The internal {@link ArrayValueModel}
     */
    private final ArrayValueModel<A, E> internalValueModel;
    
    /**
     * The {@link PropertyExtractor}
     */
    private final PropertyExtractor propertyExtractor;
    
    /**
     * Creates a new instance
     * 
     * @param parent The parent
     * @param name The name. If this is <code>null</code>, then the name
     * of the parent will be used. 
     * @param internalValueModel The internal {@link ArrayValueModel}
     * @param propertyExtractor The {@link PropertyExtractor}
     */
    DefaultStructuredArrayValueModel(StructuredValueModel<?> parent,
        String name, ArrayValueModel<A, E> internalValueModel,
        PropertyExtractor propertyExtractor)
    {
        this.parent = parent;
        this.name = name;
        this.internalValueModel = internalValueModel; 
        this.propertyExtractor = propertyExtractor;
            
    }

    @Override
    public int getArrayLength()
    {
        return internalValueModel.getArrayLength();
    }

    @Override
    public StructuredValueModel<E> getElementValueModel(int index)
    {
        ValueModel<E> elementValueModel =
            internalValueModel.getElementValueModel(index);
        if (elementValueModel instanceof ArrayValueModel<?, ?>)
        {
            @SuppressWarnings("unchecked")
            ArrayValueModel<E, Object> arrayValueModel =
                (ArrayValueModel<E, Object>)elementValueModel;
            return new DefaultStructuredArrayValueModel<E, Object>(
                this, null, arrayValueModel, propertyExtractor);
        }
        return new DefaultStructuredValueModel<E>(
            this, null, elementValueModel, propertyExtractor);
    }

    @Override
    public void setElementValue(int index, E elementValue)
    {
        internalValueModel.setElementValue(index, elementValue);
    }

    @Override
    public E getElementValue(int index)
    {
        return internalValueModel.getElementValue(index);
    }

    @Override
    public Class<?> getElementValueType()
    {
        return internalValueModel.getElementValueType();
    }

    @Override
    public void addArrayListener(
        ArrayListener<A, E> arrayListener)
    {
        internalValueModel.addArrayListener(arrayListener);
    }

    @Override
    public void removeArrayListener(
        ArrayListener<A, E> arrayListener)
    {
        internalValueModel.removeArrayListener(arrayListener);
    }

    @Override
    public StructuredValueModel<?> getParent()
    {
        return parent;
    }

    @Override
    public String getName()
    {
        if (name == null)
        {
            if (parent == null)
            {
                return null;
            }
            return parent.getName();
        }
        return name;
    }
    
    @Override
    public String getNamePath()
    {
        if (parent == null)
        {
            return getName();
        }
        if (name == null)
        {
            return parent.getNamePath();
        }
        return parent.getNamePath() + "." + getName();
    }

    @Override
    public Map<String, StructuredValueModel<?>> getChildren()
    {
        return Collections.emptyMap();
    }

    @Override
    public StructuredValueModel<?> getChild(String name)
    {
        return null;
    }

    @Override
    public A getValue()
    {
        return internalValueModel.getValue();
    }

    @Override
    public void setValue(A newValue)
    {
        internalValueModel.setValue(newValue);
    }

    @Override
    public Class<?> getValueType()
    {
        return internalValueModel.getValueType();
    }

    @Override
    public PropertyExtractor getPropertyExtractor()
    {
        return propertyExtractor;
    }
    
    @Override
    public void addValueListener(ValueListener<A> valueListener)
    {
        internalValueModel.addValueListener(valueListener);
    }

    @Override
    public void removeValueListener(ValueListener<?> valueListener)
    {
        internalValueModel.removeValueListener(valueListener);
    }

    @Override
    public String toString()
    {
        return "DefaultStructuredArrayValueModel[" 
            + "name=" + name + ","
            + "namePath=" + getNamePath() + "," 
            + "internalValueModel=" + internalValueModel + "]";
    }
    
}
