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

import javax.swing.JSlider;

import de.javagl.autogui.Numbers;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a Swing JSlider to
 * represent a Number value
 * 
 * @param <T> The number type
 */
public final class SliderValueView<T extends Number>
    extends AbstractSwingValueView<T, JSlider>
{
    /**
     * The minimum
     */
    private final T minimum;
    
    /**
     * The maximum
     */
    private final T maximum;
    
    /**
     * The number of steps
     */
    private final int steps;
    
    /**
     * The slider that represents the value
     */
    private final JSlider slider;
    
    /**
     * Creates a new number slider value component
     * 
     * @param valueModel The {@link ValueModel}
     * @param minimum The minimum value for the slider
     * @param maximum The maximum value for the slider
     * @param steps The number of steps
     */
    SliderValueView(ValueModel<T> valueModel, 
        T minimum, T maximum, int steps)
    {
        super(valueModel);
        this.minimum = minimum;
        this.maximum = maximum;
        this.steps = steps;
        
        T value = valueModel.getValue();
        int sliderValue = computeSliderValue(value);
        slider = new JSlider(0, steps, sliderValue);
        slider.addChangeListener(e -> 
            getSwingValueModel().setValue(getValueFromComponent()));
    }
    
    /**
     * Compute the value that the slider must have for the given model value
     * 
     * @param value The model value
     * @return The slider value
     */
    private int computeSliderValue(T value)
    {
        if (value == null)
        {
            return 0;
        }
        double alpha = Numbers.computeAlpha(minimum, maximum, value);
        return Numbers.interpolate(0, steps, alpha);
    }

    @Override
    public void setValueInComponent(T valueForComponent)
    {
        slider.setValue(computeSliderValue(valueForComponent));
    }
    
    @Override
    public T getValueFromComponent()
    {
        double alpha = (double)slider.getValue() / steps;
        return Numbers.interpolate(minimum, maximum, alpha);
    }
    
    @Override
    public final JSlider getComponent()
    {
        return slider;
    }




    
}
