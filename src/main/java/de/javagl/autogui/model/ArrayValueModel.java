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
 * Interface for a {@link ValueModel} where the value is an array. It offers
 * methods for setting and getting the array elements and maintains 
 * listeners that will be informed about changes in the array elements.
 * Each array element may be obtained as a new {@link ValueModel}.
 * 
 * @param <A> The array type
 * @param <E> The element/component type
 */
public interface ArrayValueModel<A, E> extends ValueModel<A> 
{
    /**
     * Returns the length of the array, or <code>-1</code> if the array
     * (that is the current {@link #getValue() value of this ValueModel})
     * is <code>null</code>.
     *  
     * @return The length of the array
     */
    int getArrayLength();

    /**
     * Returns the type of the elements/components of the array
     * 
     * @return The element/component type
     */
    Class<?> getElementValueType();
    
    /**
     * 
     * Returns a {@link ValueModel} for the element of the array with the
     * given index.<br>
     * <br>
     * Changes in the returned {@link ValueModel} will be propagated into
     * the array that is the current {@link #getValue() value} of <i>this</i>
     * model, and vice versa.
     * <br>
     * Note that the returned {@link ValueModel} will only be valid as
     * long as it refers to a valid array element. If the value of this
     * {@link ArrayValueModel} changes, then the returned {@link ValueModel}
     * may refer to an invalid array index.<br>
     * <br>
     * Users of this method may be notified about changes in the array
     * by attaching a {@link ValueListener} to <i>this</i> 
     * {@link ArrayValueModel} : When its {@link ValueListener#valueChanged}
     * method is called, then the current {@link #getArrayLength() array
     * length} will determine whether an element value model is still valid.
     * 
     * @param index The index
     * @return The {@link ValueModel} for the array element
     * @throws IndexOutOfBoundsException If the given index is negative or
     * not smaller than the {@link #getArrayLength() array length} 
     */
    ValueModel<E> getElementValueModel(int index);
    
    /**
     * Return the value for the array at the specified index.
     * 
     * @param index The index
     * @return The element value
     * @throws IndexOutOfBoundsException If the index is negative or not
     * smaller than the {@link #getArrayLength() array length}
     */
    E getElementValue(int index);

    /**
     * Set the value for the array at the specified index.
     * 
     * @param index The index
     * @param elementValue The new element value
     * @throws IndexOutOfBoundsException If the index is negative or not
     * smaller than the {@link #getArrayLength() array length}
     */
    void setElementValue(int index, E elementValue);

    /**
     * Add the given {@link ArrayListener} to be informed about changes
     * in this model
     * 
     * @param arrayListener The {@link ArrayListener} to add
     */
    void addArrayListener(ArrayListener<A, E> arrayListener);

    /**
     * Remove the given {@link ArrayListener}
     * 
     * @param arrayListener The {@link ArrayListener} to remove
     */
    void removeArrayListener(ArrayListener<A, E> arrayListener);
    
}


