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

import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * Abstract base implementation of a {@link ValueView} that uses a Swing 
 * component. <br>
 * <br>
 * Its {@link ValueModel} will be connected to an internal {@link ValueModel} 
 * that is implemented based on the abstract {@link #getValueFromComponent()} 
 * and {@link #setValueInComponent(Object)} methods, which will only be 
 * called on the Event Dispatch Thread.<br>
 * <br>
 * Implementors of this class should use this {@link #getSwingValueModel()
 * internal value model} to set the value in the {@link ValueModel} on
 * the Event Dispatch Thread. For example:
 * <pre><code>
 * implementationComponent.addActionListener(e -> {
 *     getSwingValueModel().setValue(getValueFromImplementationComponent());
 * }
 * </code></pre>
 *
 * @param <T> The value type
 * @param <C> The GUI component type
 */
public abstract class AbstractSwingValueView<T, C extends JComponent>
    implements SwingValueView<T, C>
{
    /**
     * The {@link ValueModel} of this {@link ValueView}
     */
    private final ValueModel<T> valueModel;
    
    /**
     * The {@link ValueModel} that is backed by the Swing component
     */
    private final SwingValueModel<T> swingValueModel;
    
    /**
     * Creates a component with the given value type
     * 
     * @param valueModel The {@link ValueModel}
     */
    protected AbstractSwingValueView(
        ValueModel<T> valueModel)
    {
        this.valueModel = Objects.requireNonNull(valueModel,
            "The valueModel may not be null");

        swingValueModel = new SwingValueModel<T>(
            valueModel.getValueType(), this);
        ValueModelConnection<T> connection = 
            new ValueModelConnection<T>();
        connection.attach(valueModel, swingValueModel);
    }
    
    /**
     * Returns the {@link ValueModel} that is backed by the Swing component
     * 
     * @return The {@link ValueModel}
     */
    protected final ValueModel<T> getSwingValueModel()
    {
        return swingValueModel;
    }
    
    @Override
    public final ValueModel<T> getValueModel()
    {
        return valueModel;
    }
    
    @Override
    public void updateView()
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            setValueInComponent(getValueModel().getValue());
        }
        else
        {
            SwingUtilities.invokeLater(() -> 
            setValueInComponent(getValueModel().getValue()));
        }
        
    }
    
}
