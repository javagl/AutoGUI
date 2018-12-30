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

import javax.swing.JComboBox;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a Swing JComboBox to
 * represent an Object value
 */
@SuppressWarnings({"rawtypes", "unchecked"})
final class ComboBoxValueView 
    extends AbstractSwingValueView<Object, JComboBox>
{
    /**
     * The combo box that contains the value
     */
    private final JComboBox comboBox;

    /**
     * Creates a new combo box value component with the given entries
     * 
     * @param valueModel The {@link ValueModel}
     * @param entries The entries
     */
    ComboBoxValueView(ValueModel<Object> valueModel, Object ... entries)
    {
        super(valueModel);
        comboBox = new JComboBox(entries);
        comboBox.addActionListener(e ->
        {
            getSwingValueModel().setValue(getValueFromComponent());
        });
    }
    
    @Override
    public void setValueInComponent(Object valueForComponent)
    {
        comboBox.setSelectedItem(valueForComponent);
    }

    @Override
    public Object getValueFromComponent()
    {
        return comboBox.getSelectedItem();
    }

    @Override
    public final JComboBox getComponent()
    {
        return comboBox;
    }


}
