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

import static de.javagl.autogui.view.swing.SwingValueViewFactories.checkBox;
import static de.javagl.autogui.view.swing.SwingValueViewFactories.colorChooserPanel;
import static de.javagl.autogui.view.swing.SwingValueViewFactories.spinner;
import static de.javagl.autogui.view.swing.SwingValueViewFactories.textField;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;

import de.javagl.autogui.view.ValueViewBuilder;
import de.javagl.autogui.view.ValueViewFactory;

/**
 * A class for initializing a <code>JComponent</code>-based 
 * {@link ValueViewBuilder} to use the default {@link ValueViewFactory} 
 * instances for the common types 
 */
class SwingValueViewBuilderInitializer
{
    /**
     * Default constructor
     */
    SwingValueViewBuilderInitializer()
    {
        // Default constructor
    }
    
    /**
     * Initialize the given {@link ValueViewBuilder}
     * 
     * @param valueViewBuilder The {@link ValueViewBuilder}
     */
    void initialize(ValueViewBuilder<JComponent> valueViewBuilder)
    {
        valueViewBuilder.using(byte.class, spinner(
            Byte.MIN_VALUE, Byte.MAX_VALUE, 1));

        valueViewBuilder.using(Byte.class, spinner(
            Byte.MIN_VALUE, Byte.MAX_VALUE, 1));
        
        valueViewBuilder.using(short.class, spinner(
            Short.MIN_VALUE, Short.MAX_VALUE, 1));

        valueViewBuilder.using(Short.class, spinner(
            Short.MIN_VALUE, Short.MAX_VALUE, 1));
        
        valueViewBuilder.using(int.class, spinner(
            Integer.MIN_VALUE, Integer.MAX_VALUE, 1));

        valueViewBuilder.using(Integer.class, spinner(
            Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        
        valueViewBuilder.using(long.class, spinner(
            Integer.MIN_VALUE, Integer.MAX_VALUE, 1));

        valueViewBuilder.using(Long.class, spinner(
            Integer.MIN_VALUE, Integer.MAX_VALUE, 1));

        valueViewBuilder.using(float.class, spinner(
            -Float.MAX_VALUE, Float.MAX_VALUE, 0.01f));

        valueViewBuilder.using(Float.class, spinner(
            -Float.MAX_VALUE, Float.MAX_VALUE, 0.01f));

        valueViewBuilder.using(double.class, spinner(
            -Double.MAX_VALUE, Double.MAX_VALUE, 0.01));

        valueViewBuilder.using(Double.class, spinner(
            -Double.MAX_VALUE, Double.MAX_VALUE, 0.01));

        valueViewBuilder.using(boolean.class, checkBox());
        
        valueViewBuilder.using(Boolean.class, checkBox());

        valueViewBuilder.using(String.class, textField());
        
        valueViewBuilder.using(Date.class, spinner(
            createDate(Calendar.YEAR, -10000), 
            createDate(Calendar.YEAR, +10000),
            Calendar.DAY_OF_WEEK));        

        valueViewBuilder.using(Color.class, colorChooserPanel());        
    }
    
    /**
     * Returns the current Date, with the specified Calendar field
     * changed by the given amount
     * 
     * @param field The Calendar field
     * @param delta The delta for the field
     * @return The Date
     */
    private static Date createDate(int field, int delta)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, delta);
        return calendar.getTime();
    }
    
}
