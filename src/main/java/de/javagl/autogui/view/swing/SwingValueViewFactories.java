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

import javax.swing.JComponent;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViewFactory;

/**
 * Methods that provide {@link ValueViewFactory} instances for Swing components
 */
public class SwingValueViewFactories
{
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JCheckBox
     * to represent a Boolean value 
     * 
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Boolean, JComponent> checkBox()
    {
        return new ValueViewFactory<Boolean, JComponent>()
        {
            @Override
            public ValueView<Boolean, ? extends JComponent> create(
                ValueModel<Boolean> valueModel)
            {
                return SwingValueViews.createCheckBox(valueModel);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[checkBox]";
            }
            
        };
    }
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JSpinner
     * to represent a Double value
     * 
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param stepSize The step size for the spinner
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Double, JComponent> spinner(
        double minimum, double maximum, double stepSize)
    {
        return new ValueViewFactory<Double, JComponent>()
        {
            @Override
            public ValueView<Double, ? extends JComponent> create(
                ValueModel<Double> valueModel)
            {
                return SwingValueViews.createSpinner(
                    valueModel, minimum, maximum, stepSize);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[spinner[Double]]";
            }
            
        };
    }

    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JSpinner
     * to represent an Integer value
     * 
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param stepSize The step size for the spinner
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Integer, JComponent> spinner(
        int minimum, int maximum, int stepSize)
    {
        return new ValueViewFactory<Integer, JComponent>()
        {
            @Override
            public ValueView<Integer, ? extends JComponent> create(
                ValueModel<Integer> valueModel)
            {
                return SwingValueViews.createSpinner(
                    valueModel, minimum, maximum, stepSize);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[spinner[Integer]]";
            }
            
        };
    }

    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JSlider
     * to represent an Integer value 
     * 
     * @param minimum The minimum value of the slider
     * @param maximum The maximum value of the slider
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Integer, JComponent> slider(
        int minimum, int maximum)
    {
        return slider(minimum, maximum, (maximum - minimum));
    }
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JSlider
     * to represent a Number value 
     * 
     * @param <T> The number type 
     * 
     * @param minimum The minimum value of the slider
     * @param maximum The maximum value of the slider
     * @param steps The number of steps
     * @return The {@link ValueViewFactory}
     */
    public static <T extends Number> ValueViewFactory<T, JComponent> slider(
        T minimum, T maximum, int steps)
    {
        return new ValueViewFactory<T, JComponent>()
        {
            @Override
            public ValueView<T, ? extends JComponent> create(
                ValueModel<T> valueModel)
            {
                return SwingValueViews.createSlider(
                    valueModel, minimum, maximum, steps);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[slider]";
            }
            
        };
    }
    
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JTextField
     * to represent a String value 
     * 
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<String, JComponent> textField()
    {
        return new ValueViewFactory<String, JComponent>()
        {
            @Override
            public ValueView<String, ? extends JComponent> create(
                ValueModel<String> valueModel)
            {
                return SwingValueViews.createTextField(valueModel);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[textField]";
            }
            
        };
    }
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JComboBox
     * to represent an Object value 
     * 
     * @param entries The entries for the combo box
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Object, JComponent> comboBox(
        Object ... entries)
    {
        return new ValueViewFactory<Object, JComponent>()
        {
            @Override
            public ValueView<Object, ? extends JComponent> create(
                ValueModel<Object> valueModel)
            {
                return SwingValueViews.createComboBox(valueModel, entries);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[comboBox]";
            }
            
        };
    }
    
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that use a JSpinner
     * to represent a Date value
     *  
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param calendarField The calendar field for the spinner
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Date, JComponent> spinner(
        Date minimum, Date maximum, int calendarField)
    {
        return new ValueViewFactory<Date, JComponent>()
        {
            @Override
            public ValueView<Date, ? extends JComponent> create(
                ValueModel<Date> valueModel)
            {
                return SwingValueViews.createSpinner(
                    valueModel, minimum, maximum, calendarField);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[spinner[Date]]";
            }
            
        };
    }
    
    /**
     * Provides a {@link ValueViewFactory} that creates
     * {@link ValueView} instances that show a color and
     * allow changing the color with a color chooser
     *  
     * @return The {@link ValueViewFactory}
     */
    public static ValueViewFactory<Color, JComponent> colorChooserPanel()
    {
        return new ValueViewFactory<Color, JComponent>()
        {
            @Override
            public ValueView<Color, ? extends JComponent> create(
                ValueModel<Color> valueModel)
            {
                return SwingValueViews.createColorChooserPanel(
                    valueModel);
            }
            
            @Override
            public String toString()
            {
                return "ValueViewFactory[colorChooserPanel]";
            }
        };
    }
    

    /**
     * Private constructor to prevent instantiation
     */
    private SwingValueViewFactories()
    {
        // Private constructor to prevent instantiation
    }

}
