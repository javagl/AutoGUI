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

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;

import de.javagl.autogui.Numbers;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;
import de.javagl.common.ui.JSpinners;

import javax.swing.SpinnerNumberModel;

/**
 * A {@link ValueView} that uses a Swing JSpinner to
 * represent the numeric value
 * 
 * @param <T> The number type
 */
public final class DoubleSpinnerValueView<T extends Number>
    extends AbstractSwingValueView<T, JSpinner>
{
    /**
     * The spinner that represents the numeric value
     */
    private final JSpinner spinner;
    
    /**
     * The default value for the spinner
     */
    private final double defaultValue;
    
    /**
     * Creates a new spinner
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param stepSize The step size for the spinner
     */
    DoubleSpinnerValueView(ValueModel<T> valueModel, 
        double minimum, double maximum, double stepSize)
    {
        super(valueModel);
        SpinnerNumberModel spinnerNumberModel = 
            new SpinnerNumberModel(0.0, 0.0, 0.0, 0.0);
        spinnerNumberModel.setMinimum(minimum);
        spinnerNumberModel.setMaximum(maximum);
        spinnerNumberModel.setStepSize(stepSize);

        Number value = valueModel.getValue();
        defaultValue = value == null ? 0.0 : value.doubleValue();
        spinnerNumberModel.setValue(defaultValue);

        spinner = new JSpinner(spinnerNumberModel);
        spinner.addChangeListener(e ->
            getSwingValueModel().setValue(getValueFromComponent()));
        JSpinners.setSpinnerDraggingEnabled(spinner, true);
        
        DefaultEditor editor = (DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        textField.setColumns(10);        
    }
    
    /**
     * Convert the given value from the component to the value for the model
     * 
     * @param valueFromComponent The value from the component
     * @return The value for the model
     */
    private T convertToModel(Number valueFromComponent)
    {
        @SuppressWarnings("unchecked")
        T valueForModel = (T)Numbers.convertNumberRoundingTo(
            valueFromComponent, getValueModel().getValueType());
        return valueForModel;
    }
    
    @Override
    public void setValueInComponent(T valueForComponent)
    {
        if (valueForComponent == null)
        {
            spinner.setValue(defaultValue);
        }
        else
        {
            spinner.setValue(valueForComponent.doubleValue());
        }
    }
    
    @Override
    public T getValueFromComponent()
    {
        return convertToModel((Number)spinner.getValue());
    }

    @Override
    public final JSpinner getComponent()
    {
        return spinner;
    }


    
}
