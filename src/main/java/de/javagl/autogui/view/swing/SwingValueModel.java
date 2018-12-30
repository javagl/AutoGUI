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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.SwingUtilities;

import de.javagl.autogui.model.AbstractValueModel;
import de.javagl.autogui.model.ValueModel;

/**
 * Implementation of a {@link ValueModel} that is backed by a
 * {@link SwingValueView} and takes care of accessing the GUI component only 
 * on the Event Dispatch Thread. The {@link #setValue(Object)} and 
 * {@link #getValue()} methods are implemented by calling 
 * {@link SwingValueView#setValueInComponent(Object)} and 
 * {@link SwingValueView#getValueFromComponent()} of the 
 * {@link SwingValueView} instance that contains this model.<br>
 *
 * @param <T> The value type
 */
class SwingValueModel<T> extends AbstractValueModel<T> implements ValueModel<T>
{
    /**
     * The backing {@link SwingValueView}
     */
    private final SwingValueView<T, ?> swingValueView;
    
    /**
     * The current value
     */
    private T currentValue;
    
    /**
     * Creates a component with the given value type
     * 
     * @param valueType The type of the value
     * @param swingValueView The backing {@link SwingValueView}
     */
    SwingValueModel(
        Class<?> valueType, SwingValueView<T, ?> swingValueView)
    {
       super(valueType); 
       this.swingValueView = swingValueView;
       this.currentValue = null;
    }
    
    @Override
    public final void setValue(T newValue)
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            doSetValue(newValue);
        }
        else
        {
            SwingUtilities.invokeLater(
                () -> doSetValue(newValue));
        }
    }
    
    /**
     * Set the new value. Only to be called on the EDT
     * 
     * @param newValue The new value
     */
    private void doSetValue(T newValue)
    {
        SwingUtils.validateEventDispathThread();
        
        T oldValue = currentValue;
        swingValueView.setValueInComponent(newValue);
        currentValue = newValue;
        if (!Objects.equals(oldValue, newValue))
        {
            fireValueChanged(oldValue, newValue);
        }
    }

    @Override
    public final T getValue()
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            return swingValueView.getValueFromComponent();
        }

        List<T> result = Arrays.asList((T)null);
        try
        {
            SwingUtilities.invokeAndWait(
                () -> result.set(0, swingValueView.getValueFromComponent()));
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        return result.get(0);
    }
    
    
    @Override
    public String toString()
    {
        return "SwingValueModel["+swingValueView+"]";
    }
    
}