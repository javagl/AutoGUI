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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.javagl.autogui.model.properties.PropertyAccessor;
import de.javagl.autogui.model.properties.PropertyExtractor;

/**
 * Default implementation of a {@link StructuredValueModel}. The implementation
 * of the methods of the {@link ValueModel} interface are forwarding to an 
 * internal {@link ValueModel}. This class only adds the structural information
 * for the {@link StructuredValueModel} implementation.
 *
 * @param <T> The value type
 */
class DefaultStructuredValueModel<T> 
    implements StructuredValueModel<T>
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
     * The internal {@link ValueModel}
     */
    private ValueModel<T> internalValueModel;

    /**
     * The map from names to the corresponding children
     */
    private final Map<String, StructuredValueModel<Object>> children;
    
    /**
     * The {@link PropertyExtractor}
     */
    private final PropertyExtractor propertyExtractor;
    
    /**
     * Creates a structured value model instance
     * 
     * @param parent The parent
     * @param name The name. If this is <code>null</code>, then the name
     * of the parent will be used. 
     * @param internalValueModel The internal {@link ValueModel} 
     * @param propertyExtractor The {@link PropertyExtractor} to use
     * @throws NullPointerException If the internalValueModel or
     * the propertyExtractor is <code>null</code>
     */
    DefaultStructuredValueModel(StructuredValueModel<?> parent, 
        String name, ValueModel<T> internalValueModel,
        PropertyExtractor propertyExtractor)
    {
        this.parent = parent;
        this.name = name;
        this.internalValueModel = Objects.requireNonNull(internalValueModel,
            "The internalValueModel may not be null");
        this.children = 
            new LinkedHashMap<String, StructuredValueModel<Object>>();
        this.propertyExtractor = Objects.requireNonNull(
            propertyExtractor, "The propertyExtractor may not be null");
        createChildren();
    }
    
    /**
     * Create the children. For each property of the 
     * {@link #getValueType() value type}, one {@link StructuredValueModel}
     * will be created. 
     */
    private void createChildren()
    {
        Class<?> valueType = getValueType();
        
        List<PropertyAccessor> propertyAccessors = 
            propertyExtractor.getPropertyAccessors(valueType);
        
        for (PropertyAccessor propertyAccessor : propertyAccessors)
        {
            String propertyName = propertyAccessor.getName();
            Class<?> propertyType = propertyAccessor.getType();            
            
            PropertyValueModel<Object> propertyValueModel = 
                new PropertyValueModel<Object>(
                    this, propertyAccessor);
            
            StructuredValueModel<Object> child = null;
            if (propertyType.isArray())
            {
                ArrayValueModel<Object, Object> arrayValueModel =
                    new DefaultArrayValueModel<Object, Object>(
                        propertyValueModel);
                child = new DefaultStructuredArrayValueModel<Object, Object>(
                    this, propertyName, arrayValueModel, propertyExtractor);
            }
            else
            {
                child = new DefaultStructuredValueModel<Object>(
                    this, propertyName, propertyValueModel,
                    propertyExtractor);
            }
            children.put(propertyName, child);
        }
    }
    

    @Override
    public StructuredValueModel<?> getParent()
    {
        return parent;
    }
    
    @Override
    public StructuredValueModel<?> getChild(String name)
    {
        return children.get(name);
    }
    
    @Override
    public Map<String, StructuredValueModel<?>> getChildren()
    {
        return Collections.unmodifiableMap(children);
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
    public T getValue()
    {
        return internalValueModel.getValue();
    }

    @Override
    public void setValue(T newValue)
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
    public void addValueListener(ValueListener<T> valueListener)
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
        return "DefaultStructuredValueModel[" 
            + "name=" + name + ","
            + "namePath=" + getNamePath() + "," 
            + "internalValueModel=" + internalValueModel + "]";
    }
    
}