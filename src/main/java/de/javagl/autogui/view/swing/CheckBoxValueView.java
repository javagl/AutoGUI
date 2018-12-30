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

import javax.swing.JCheckBox;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a Swing JCheckBox to
 * represent a boolean value
 */
final class CheckBoxValueView 
    extends AbstractSwingValueView<Boolean, JCheckBox>
{
    /**
     * The check box that represents the boolean value
     */
    private final JCheckBox checkBox;
    
    /**
     * Creates a new boolean check box value component
     * 
     * @param valueModel The {@link ValueModel}
     */
    CheckBoxValueView(ValueModel<Boolean> valueModel)
    {
        super(valueModel);
        checkBox = new JCheckBox();
        checkBox.addActionListener(e -> 
        {
            getSwingValueModel().setValue(checkBox.isSelected());
        });
    }
    
    @Override
    public void setValueInComponent(Boolean valueForComponent)
    {
        if (valueForComponent == null)
        {
            checkBox.setSelected(false);
        }
        else
        {
            checkBox.setSelected(valueForComponent);
        }
    }

    @Override
    public Boolean getValueFromComponent()
    {
        return checkBox.isSelected();
    }

    @Override
    public JCheckBox getComponent()
    {
        return checkBox;
    }

}
