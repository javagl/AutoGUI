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

import javax.swing.JComponent;

import de.javagl.autogui.view.swing.SwingValueViewBuilder;

/**
 * Methods to create {@link ValueView} instances
 */
public class ValueViews
{
    /**
     * Create a default Swing-Based {@link ValueView} for the given 
     * value type
     * 
     * @param <T> The value type
     * @param valueType The value type
     * @return The {@link ValueView}
     */
    public static <T> ValueView<T, ? extends JComponent> createDefaultSwing(
        Class<T> valueType)
    {
        return createSwing().createValueView(valueType);
    }

    /**
     * Create a Swing-based {@link ValueViewBuilder}
     *  
     * @return The {@link ValueViewBuilder}
     */
    public static ValueViewBuilder<JComponent> createSwing()
    {
        return new SwingValueViewBuilder();
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private ValueViews()
    {
        // Private constructor to prevent instantiation
    }

}
