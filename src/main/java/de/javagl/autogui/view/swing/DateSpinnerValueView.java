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

import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a Swing JSpinner to
 * represent a Date value
 */
final class DateSpinnerValueView 
    extends AbstractSwingValueView<Date, JSpinner>
{
    /**
     * The spinner that represents the date value
     */
    private final JSpinner spinner;
    
    /**
     * The default value for the spinner
     */
    private final Date defaultValue;
    
    /**
     * Creates a new date spinner
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value of the spinner
     * @param maximum The maximum value of the spinner
     * @param calendarField The Calendar field that is affected
     */
    DateSpinnerValueView(ValueModel<Date> valueModel, 
        Date minimum, Date maximum, int calendarField)
    {
        super(valueModel);
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        spinnerDateModel.setStart(minimum);
        spinnerDateModel.setEnd(maximum);
        spinnerDateModel.setCalendarField(calendarField);
        
        Date value = valueModel.getValue();
        defaultValue = value == null ? new Date() : value;
        spinnerDateModel.setValue(defaultValue);
        
        spinner = new JSpinner(spinnerDateModel);
        spinner.addChangeListener(e -> 
        {
            Date valueFromComponent = (Date)spinner.getValue();
            getSwingValueModel().setValue(valueFromComponent);
        });
    }

    @Override
    public void setValueInComponent(Date valueForComponent)
    {
        if (valueForComponent == null)
        {
            spinner.setValue(defaultValue);
        }
        else
        {
            spinner.setValue(valueForComponent);
        }
    }
    
    @Override
    public Date getValueFromComponent()
    {
        return (Date) spinner.getValue();
    }

    @Override
    public final JSpinner getComponent()
    {
        return spinner;
    }


    
}

