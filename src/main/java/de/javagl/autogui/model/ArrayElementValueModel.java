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

/**
 * Implementation of a {@link ValueModel} that represents one element of
 * the array of an {@link ArrayValueModel}
 *
 * @param <A> The array type
 * @param <E> The element/component type
 */
class ArrayElementValueModel<A, E> 
    extends AbstractValueModel<E> 
    implements ValueModel<E>, Detachable
{
    /**
     * The {@link ArrayValueModel} that this model refers to
     */
    private final ArrayValueModel<A, E> arrayValueModel;
    
    /**
     * The index in the array that this element stands for
     */
    private final int index;
    
    /**
     * The {@link ArrayListener} that will be attached to the
     * {@link ArrayValueModel} and forward change notifications
     * from the {@link ArrayValueModel} to the listeners of
     * this model.
     */
    private final ArrayListener<A, E> arrayListener; 
    
    /**
     * Default constructor
     * 
     * @param arrayValueModel The {@link ArrayValueModel}
     * @param index The index
     * @throws NullPointerException If the arrayValueModel is null
     * @throws IllegalArgumentException If the index is negative
     */
    ArrayElementValueModel(
        ArrayValueModel<A, E> arrayValueModel, int index)
    {
        super(arrayValueModel.getValueType().getComponentType());
        this.arrayValueModel = arrayValueModel;
        if (index < 0)
        {
            throw new IllegalArgumentException(
                "The index may not be negative, but is " + index);
        }
        this.index = index;
        
        arrayListener = new ArrayListener<A, E>()
        {
            @Override
            public void arrayElementChanged(int i, E oldElement, E newElement)
            {
                if (i == index)
                {
                    fireValueChanged(oldElement, newElement);
                }
            }
        };
        arrayValueModel.addArrayListener(arrayListener);
    }
    
    @Override
    public void detach()
    {
        arrayValueModel.removeArrayListener(arrayListener);
    }
    

    @Override
    public E getValue()
    {
        return arrayValueModel.getElementValue(index);
    }

    @Override
    public void setValue(E newValue)
    {
        arrayValueModel.setElementValue(index, newValue);
    }

    @Override
    public String toString()
    {
        return "ArrayElementValueModel["
            + "index=" + index + ","
            + "arrayValueModel=" + arrayValueModel + "]";
    }

}

