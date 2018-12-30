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
package de.javagl.autogui.view;

import de.javagl.autogui.model.ArrayValueModel;
import de.javagl.autogui.model.ValueModel;

/**
 * Interface for classes that serve as the view for an {@link ArrayValueModel}.
 * This is a {@link ValueView} that additionally offers information about the
 * {@link #getArrayLength() length} of the array value, and allows obtaining
 * one {@link ValueView} for each array element.
 *
 * @param <A> The (array) value type
 * @param <E> The array element/component type
 * @param <C> The GUI component type
 */
public interface ArrayValueView<A, E, C> extends ValueView<A, C>
{
    /**
     * {@inheritDoc}
     * 
     * This is a specialization that returns an {@link ArrayValueModel}
     */
    @Override
    ArrayValueModel<A, E> getValueModel();
    
    /**
     * Returns the length of the array, or <code>-1</code> if the array
     * (that is the current {@link ValueModel#getValue() value of the 
     * ValueModel}) is <code>null</code>.
     *  
     * @return The length of the array
     */
    int getArrayLength();
    
    /**
     * Returns a {@link ValueView} for the element of the array with the
     * given index. <br>
     * 
     * TODO Specify validity
     * 
     * @param index The index
     * @return The {@link ValueView}
     */
    ValueView<E, ? extends C> getElementValueView(int index);
}
