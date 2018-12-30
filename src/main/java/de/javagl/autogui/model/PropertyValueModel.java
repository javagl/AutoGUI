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

import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.javagl.autogui.model.properties.PropertyAccessor;
import de.javagl.common.beans.PropertyChangeUtils;

/**
 * Implementation of a {@link ValueModel} that is backed by the property
 * of a bean (which is, in turn, also contained in a {@link ValueModel})
 *
 * @param <T> The value type
 */
class PropertyValueModel<T> extends AbstractValueModel<T> 
    implements ValueModel<T>, Detachable
{
    /**
     * The listener that will be attached to the bean and translate
     * PropertyChangeEvents into a call to the {@link ValueListener} instances
     */
    private final PropertyChangeListener propertyChangeListener;
    
    /**
     * The owner, whose value is the current bean
     */
    private final ValueModel<?> owner;
    
    /**
     * The current bean that offers the value of this model. This is
     * maintained by the {@link #beanListener} that is attached to 
     * the {@link #owner}.
     */
    private Object bean;
    
    /**
     * The listener that will be attached to the {@link #owner} and set the
     * current {@link #bean} that backs this value model
     */
    private ValueListener<?> beanListener;
    
    /**
     * The name of the property
     */
    private final String name;

    /**
     * The {@link PropertyAccessor}
     */
    private final PropertyAccessor propertyAccessor;
    
    /**
     * Creates a new instance
     * 
     * @param owner The owner
     * @param propertyAccessor The {@link PropertyAccessor}
     * @throws NullPointerException If any argument is <code>null</code>
     */
    PropertyValueModel(ValueModel<?> owner, PropertyAccessor propertyAccessor)
    {
        super(propertyAccessor.getType());
        this.owner = Objects.requireNonNull(owner, "The owner may not be null");
        this.name = propertyAccessor.getName();
        this.propertyAccessor = propertyAccessor;
        this.propertyChangeListener = event -> 
        {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            @SuppressWarnings("unchecked")
            T typedOldValue = (T) oldValue;
            @SuppressWarnings("unchecked")
            T typedNewValue = (T) newValue;
            fireValueChanged(typedOldValue, typedNewValue);
        };
        attachListenerTo(owner);
    }
    
    /**
     * Attach a listener to the owner that will set the value of the owner
     * as the current {@link #bean} of this model.
     * 
     * @param <U> The owner value type
     * @param owner The owner
     */
    private <U> void attachListenerTo(ValueModel<U> owner)
    {
        ValueListener<U> listener = (oldValue, newValue) -> 
        {
            setCurrentBean(newValue);
        };
        owner.addValueListener(listener);
        setCurrentBean(owner.getValue());
        beanListener = listener;
    }
    
    @Override
    public void detach()
    {
        owner.removeValueListener(beanListener);
    }

    /**
     * Set the bean object that is currently backing this property model
     * 
     * @param newBean The new bean
     */
    private void setCurrentBean(Object newBean)
    {
        T oldValue = getValue();

        if (bean != null)
        {
            PropertyChangeUtils.tryRemoveNamedPropertyChangeListenerUnchecked(
                bean, name, propertyChangeListener);
        }
        bean = newBean;
        if (bean != null)
        {
            PropertyChangeUtils.tryAddNamedPropertyChangeListenerUnchecked(
                bean, name, propertyChangeListener);
        }
        T newValue = getValue();
        if (!Objects.equals(oldValue, newValue))
        {
            fireValueChanged(oldValue, newValue);
        }
    }
    
    
    @Override
    public T getValue()
    {
        if (bean == null)
        {
            return null;
        }
        Function<Object, Object> readMethod = propertyAccessor.getReadMethod();
        Object valueFromBean = readMethod.apply(bean);

        @SuppressWarnings("unchecked")
        T result = (T) valueFromBean; 
        
        return result;
    }
    
    
    @Override
    public void setValue(T valueForBean)
    {
        if (bean != null)
        {
            BiConsumer<Object, Object> writeMethod = 
                propertyAccessor.getWriteMethod();
            writeMethod.accept(bean, valueForBean);
        }
    }
    
    
    @Override
    public String toString()
    {
        return "PropertyValueModel["+name+"]";
    }
}