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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default implementation of an {@link ArrayValueModel}. It is backed by
 * an internal {@link ValueModel} that provides the actual array, and is
 * used for the implementation of the {@link ValueModel} methods.<br>
 * <br>
 * The methods that are specific for the {@link ArrayValueModel} interface
 * are implemented based on generic array access methods that operate on
 * the value of the internal {@link ValueModel}.
 *
 * @param <A> The array type
 * @param <E> The element/component type
 */
class DefaultArrayValueModel<A, E>
    implements ArrayValueModel<A, E>, Detachable
{
    /**
     * The list of {@link ArrayListener} instances that will be informed 
     * in {@link #fireArrayElementChanged}
     */
    private final List<ArrayListener<A, E>> arrayListeners;
    
    /**
     * The internal {@link ValueModel}
     */
    private final ValueModel<A> internalValueModel;
    
    /**
     * The listener that will be attached to the internal {@link ValueModel}
     * and has to be detached when this instance is no longer used
     */
    private final ValueListener<A> internalValueModelListener;
    
    /**
     * One {@link ValueModel} for each array element
     */
    private final List<ValueModel<E>> elementValueModels;
    
    /**
     * Creates a new value model
     * 
     * @param internalValueModel The internal {@link ValueModel} that provides
     * the array
     * @throws NullPointerException If the internalValueModel is 
     * <code>null</code>
     * @throws IllegalArgumentException If the {@link ValueModel#getValueType()
     * value type of the internal value model} is not an array type
     */
    DefaultArrayValueModel(ValueModel<A> internalValueModel)
    {
        this.internalValueModel = Objects.requireNonNull(internalValueModel,
            "The internalValueModel may not be null");
        
        Class<?> valueType = internalValueModel.getValueType();
        if (!valueType.isArray())
        {
            throw new IllegalArgumentException(
                "The type " + valueType + " is not an array type");
        }
        this.arrayListeners =
            new CopyOnWriteArrayList<ArrayListener<A, E>>();
        this.elementValueModels = new ArrayList<ValueModel<E>>();
        updateElementValueModels(getValue());
        
        internalValueModelListener = new ValueListener<A>()
        {
            @Override
            public void valueChanged(A oldValue, A newValue)
            {
                updateElementValueModels(newValue);
            }
        };
        
        internalValueModel.addValueListener(internalValueModelListener);
    }
    
    @Override
    public void detach()
    {
        internalValueModel.removeValueListener(internalValueModelListener);
    }

    /**
     * Update the {@link #elementValueModels} so that there is exactly
     * one {@link ValueModel} for each element of the given array value
     * 
     * @param newValue The array
     */
    private void updateElementValueModels(A newValue)
    {
        if (newValue == null)
        {
            elementValueModels.clear();
            return;
        }
        int length = getArrayLength(newValue);
        while (elementValueModels.size() > length)
        {
            int lastIndex = elementValueModels.size()-1;
            ValueModel<E> lastElement = elementValueModels.get(lastIndex);
            if (lastElement instanceof Detachable)
            {
                Detachable lastDetachableElement = (Detachable)lastElement;
                lastDetachableElement.detach();
            }
            elementValueModels.remove(lastIndex);
        }
        while (elementValueModels.size() < length)
        {
            int index = elementValueModels.size();
            elementValueModels.add(createElementValueModel(index));
        }
    }
    
    /**
     * Create a {@link ValueModel} for the specified array element
     * 
     * @param index The index
     * @return The {@link ValueModel}
     */
    private ValueModel<E> createElementValueModel(int index)
    {
        Class<?> elementValueType = getElementValueType();
        ArrayElementValueModel<A, E> arrayElementValueModel = 
            new ArrayElementValueModel<A, E>(this, index);
        if (elementValueType.isArray())
        {
            return ValueModelsInternal.createArrayValueModel(
                arrayElementValueModel);
        }
        return arrayElementValueModel;
    }
    
    @Override
    public ValueModel<E> getElementValueModel(int index)
    {
        if (elementValueModels.size() != getArrayLength())
        {
            updateElementValueModels(getValue());
        }
        return elementValueModels.get(index);
    }
    
    @Override
    public final Class<?> getElementValueType()
    {
        return getValueType().getComponentType();
    }
    
    @Override
    public final int getArrayLength()
    {
        return getArrayLength(getValue());
    }
    
    /**
     * Return the length of the given array object, or -1 if the given
     * object is <code>null</code>
     * 
     * @param array The array
     * @return The array length
     * @throws IllegalArgumentException If the object is not an array
     */
    protected final int getArrayLength(A array)
    {
        if (array == null)
        {
            return -1;
        }
        return Array.getLength(array);
    }
    
    @Override
    public final E getElementValue(int index)
    {
        return getElementValue(getValue(), index);
    }
    
    /**
     * Returns the value of the element at the given index in the given array
     * object, or <code>null</code> if the given array is <code>null</code>
     * 
     * @param array The array
     * @param index The index
     * @return The array element
     * @throws IllegalArgumentException If the object is not an array
     * @throws IndexOutOfBoundsException If the index is invalid
     */
    protected final E getElementValue(A array, int index)
    {
        if (array == null)
        {
            return null;
        }
        validate(array, index);
        Object elementValue = Array.get(array, index);
        @SuppressWarnings("unchecked")
        E result = (E)elementValue;
        return result;
    }
    
    @Override
    public final void setElementValue(int index, E newValue)
    {
        A array = getValue();
        E oldValue = getElementValue(index);
        validate(array, index);
        Array.set(array, index, newValue);
        if (!Objects.equals(oldValue, newValue))
        {
            fireArrayElementChanged(index, oldValue, newValue);
        }
    }

    /**
     * Make sure that the given index is valid for the given array object.
     * 
     * @param array The array object
     * @param index The index
     * @throws IllegalStateException If the array is <code>null</code>
     * @throws IllegalStateException If the given index is not smaller than
     * the length of the array
     */
    private static void validate(Object array, int index)
    {
        if (array == null)
        {
            throw new IllegalStateException(
                "The array that this ValueModel refers to is null");
        }
        int length = Array.getLength(array);
        if (index >= length)
        {
            throw new IllegalStateException(
                "The array that this ValueModel refers to has a length of " + 
                    length +", but the index is " + index);
        }
    }
    
    /**
     * Will be called when the value of an element of the array in this
     * {@link ArrayValueModel} changed to the given value, and the 
     * {@link ArrayListener} instances should be informed.
     * 
     * @param index The index
     * @param oldValue The old value
     * @param newValue The new value
     */
    protected final void fireArrayElementChanged(
        int index, E oldValue, E newValue)
    {
        for (ArrayListener<A, E> arrayValueListener : arrayListeners)
        {
            arrayValueListener.arrayElementChanged(index, oldValue, newValue);
        }
    }
    
    @Override
    public final void addArrayListener(
        ArrayListener<A, E> arrayListener)
    {
        arrayListeners.add(arrayListener);
    }

    @Override
    public final void removeArrayListener(
        ArrayListener<A, E> arrayListener)
    {
        arrayListeners.remove(arrayListener);
    }

    @Override
    public A getValue()
    {
        return internalValueModel.getValue();
    }

    @Override
    public void setValue(A newValue)
    {
        A oldValue = getValue();
        
        internalValueModel.setValue(newValue);
        
        int oldLength = getArrayLength(oldValue);
        int newLength = getArrayLength(newValue);
        for (int i=0; i<newLength; i++)
        {
            E oldElementValue = null;
            if (i < oldLength)
            {
                oldElementValue = getElementValue(oldValue, i);
            }
            E newElementValue = getElementValue(newValue, i);
            
            if (!Objects.equals(oldElementValue, newElementValue))
            {
                fireArrayElementChanged(i, oldElementValue, newElementValue);
            }
        }
    }

    @Override
    public Class<?> getValueType()
    {
        return internalValueModel.getValueType();
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
        return "DefaultArrayValueModel[" 
            + "internalValueModel=" + internalValueModel + "]";
    }
}