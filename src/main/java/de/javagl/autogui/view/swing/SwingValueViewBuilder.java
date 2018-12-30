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
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;

import de.javagl.autogui.model.ArrayValueModel;
import de.javagl.autogui.model.StructuredValueModel;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.model.ValueModels;
import de.javagl.autogui.view.AbstractValueViewBuilder;
import de.javagl.autogui.view.ArrayValueView;
import de.javagl.autogui.view.MutableValueView;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViewBuilder;
import de.javagl.autogui.view.ValueViewFactory;

/**
 * Implementation of an {@link ValueViewBuilder} for Swing
 */
public final class SwingValueViewBuilder 
    extends AbstractValueViewBuilder<JComponent>
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(SwingValueViewBuilder.class.getName());
    
    /**
     * Default constructor
     */
    public SwingValueViewBuilder()
    {
        SwingValueViewBuilderInitializer initializer = 
            new SwingValueViewBuilderInitializer();
        initializer.initialize(this);
    }
    
    @Override
    protected ValueViewFactory<?, ? extends JComponent>
        getFactoryForEnumType(Class<?> valueType, Object[] enumConstants)
    {
        return SwingValueViewFactories.comboBox(enumConstants);
    }
    
    @Override
    protected final <T> MutableValueView<T, JComponent> 
        createMutableValueViewImpl(StructuredValueModel<T> structuredValueModel)
    {
        return new DefaultSwingValueView<T>(structuredValueModel);
    }
    
    @Override
    protected final <A, E> ArrayValueView<A, E, JComponent>
        createArrayValueViewImpl(ArrayValueModel<A, E> arrayValueModel)
    {
        SwingArrayValueView<A, E> valueView = 
            new SwingArrayValueView<A, E>(arrayValueModel, this);
        return valueView;
    }
    
    @Override
    protected final <T> ValueView<T, JComponent> createErrorValueViewImpl(
        ValueModel<T> valueModel)
    {
        logger.warning("createErrorValueView for " + valueModel);
        
        Class<?> valueType = Object.class;
        String valueTypeString = "null";
        ValueModel<T> resultValueModel = valueModel;
        if (resultValueModel != null)
        {
            valueType = valueModel.getValueType();
            valueTypeString = String.valueOf(valueType);
        }
        else
        {
            @SuppressWarnings("unchecked")
            ValueModel<T> typedValueModel = 
                (ValueModel<T>)ValueModels.create(valueType, null);
            resultValueModel = typedValueModel;
        }
        JComponent component = new JLabel(valueTypeString);
        component.setForeground(Color.GRAY);
        return new AbstractSwingValueView<T, JComponent>(resultValueModel)
        {
            @Override
            public JComponent getComponent()
            {
                return component;
            }

            @Override
            public void setValueInComponent(Object newValue)
            {
                // Nothing to do here
            }

            @Override
            public T getValueFromComponent()
            {
                return null;
            }
        };
    }
    
    
    

}
