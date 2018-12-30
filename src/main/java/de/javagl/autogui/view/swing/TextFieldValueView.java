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

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a Swing JTextField to
 * represent a String value
 */
final class TextFieldValueView 
    extends AbstractSwingValueView<String, JTextField>
{
    /**
     * The text field that represents the value
     */
    private final JTextField textField;
    
    /**
     * Whether the text field is currently updated, and changes in the
     * value should be ignored and NOT cause an update of the component
     */
    private boolean updating = false;
    
    /**
     * Creates a new string text field value component
     * 
     * @param valueModel The {@link ValueModel}
     */
    TextFieldValueView(ValueModel<String> valueModel)
    {
        super(valueModel);
        textField = new JTextField();
        String value = valueModel.getValue();
        if (value != null)
        {
            textField.setText(valueModel.getValue());
        }
        textField.getDocument().addDocumentListener(createDocumentListener());
    }
    
    /**
     * Creates the DocumentListener that will be attached to the
     * document of the text field and update the {@link #getSwingValueModel()
     * swing value model}
     * 
     * @return The document listener
     */
    private DocumentListener createDocumentListener()
    {
        return new DocumentListener()
        {
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                update();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                update();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                update();
            }

            private void update()
            {
                updating = true;
                String valueFromComponent = textField.getText();
                getSwingValueModel().setValue(valueFromComponent);
                updating = false;
            }
        };
    }
    
    @Override
    public void setValueInComponent(String valueForComponent)
    {
        if (updating)
        {
            return;
        }
        if (valueForComponent == null)
        {
            textField.setText("");
        }
        else
        {
            textField.setText(valueForComponent);
        }
    }

    @Override
    public String getValueFromComponent()
    {
        return textField.getText();
    }
    
    @Override
    public final JTextField getComponent()
    {
        return textField;
    }


}
