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

import java.awt.Color;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * Methods to create {@link ValueView} instances 
 */
class SwingValueViews
{
    /**
     * Create a {@link ValueView} that uses a JTextField to
     * represent a String value
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    static ValueView<String, JTextField> createTextField(
        ValueModel<String> valueModel)
    {
        return new TextFieldValueView(valueModel);
    }
    
    /**
     * Create a {@link ValueView} that uses a JComboBox to
     * represent an Object value
     * 
     * @param valueModel The {@link ValueModel}
     * @param entries The entries of the combo box
     * @return The {@link ValueView}
     */
    @SuppressWarnings("rawtypes")
    static ValueView<Object, JComboBox>  createComboBox(
        ValueModel<Object> valueModel, Object ... entries)
    {
        return new ComboBoxValueView(valueModel, entries);
    }
    
    /**
     * Create a {@link ValueView} that uses a JCheckBox to
     * represent a Boolean value
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    static ValueView<Boolean, JCheckBox> createCheckBox(
        ValueModel<Boolean> valueModel)
    {
        return new CheckBoxValueView(valueModel);
    }

    /**
     * Create a {@link ValueView} that uses a JSlider to
     * represent a Number value
     * 
     * @param <T> The Number type
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the slider
     * @param maximum The maximum value of the slider
     * @param steps The number of steps
     * @return The {@link ValueView}
     */
    static <T extends Number> ValueView<T, JSlider> createSlider(
        ValueModel<T> valueModel, T minimum, T maximum, int steps)
    {
        return new SliderValueView<T>(
            valueModel, minimum, maximum, steps);
    }
    
    /**
     * Create a {@link ValueView} that uses a JSpinner to
     * represent a Number value
     * 
     * @param <T> The Number type
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param stepSize The step size for the spinner
     * @return The {@link ValueView}
     */
    static <T extends Number> ValueView<T, JSpinner> createSpinner(
        ValueModel<T> valueModel, double minimum, 
        double maximum, double stepSize)
    {
        return new DoubleSpinnerValueView<T>(
            valueModel, minimum, maximum, stepSize);
    }
    
    /**
     * Create a {@link ValueView} that uses a JSpinner to
     * represent a Number value
     * 
     * @param <T> The Number type
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param stepSize The step size for the spinner
     * @return The {@link ValueView}
     */
    static <T extends Number> ValueView<T, JSpinner> createSpinner(
        ValueModel<T> valueModel, int minimum, 
        int maximum, int stepSize)
    {
        return new IntegerSpinnerValueView<T>(
            valueModel, minimum, maximum, stepSize);
    }
    

    /**
     * Create a {@link ValueView} that uses a JSpinner to
     * represent a Date value
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param calendarField The calendar field
     * @return The {@link ValueView}
     */
    static ValueView<Date, JSpinner> createSpinner(ValueModel<Date> valueModel, 
        Date minimum, Date maximum, int calendarField)
    {
        return new DateSpinnerValueView(
            valueModel, minimum, maximum, calendarField);
    }
    

    /**
     * Create a {@link ValueView} that uses a custom component
     * to show a color and allows changing the color via a 
     * color chooser
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    static ValueView<Color, JComponent> createColorChooserPanel(
        ValueModel<Color> valueModel)
    {
        return new ColorChooserValueView(valueModel);
    }
    

    
    
    /**
     * Private constructor to prevent instantiation
     */
    private SwingValueViews()
    {
        // Private constructor to prevent instantiation
    }
    
    
    
}
