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
package de.javagl.autogui.view.swing;

import javax.swing.JComponent;

import de.javagl.autogui.view.ValueView;


/**
 * Interface for implementations of a {@link ValueView} for Swing
 *  
 * @param <T> The type of the value
 * @param <C> The component type
 */
public interface SwingValueView<T, C extends JComponent> 
    extends ValueView<T, C>
{
    /**
     * Set the given value in the Swing component. This method will only 
     * be called on the Event Dispatch Thread.
     * 
     * @param newValue The new value for the GUI component
     */
    void setValueInComponent(T newValue);

    /**
     * Returns the current value from the GUI component. This method will
     * only be called on the Event Dispatch Thread
     * 
     * @return The current value from the GUI component
     */
    T getValueFromComponent();

}