/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import javax.swing.JComponent;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.swing.AbstractSwingValueView;

/**
 * A {@link ValueView} for the {@link CustomType} sample
 */
@SuppressWarnings("javadoc")
class CustomTypeValueView 
    extends AbstractSwingValueView<CustomType, JComponent>
{
    private final CustomTypePanel panel;

    CustomTypeValueView(ValueModel<CustomType> valueModel)
    {
        super(valueModel);
        this.panel = new CustomTypePanel();
        setValueInComponent(valueModel.getValue());
    }

    @Override
    public JComponent getComponent()
    {
        return panel;
    }

    @Override
    public void setValueInComponent(CustomType newValue)
    {
        panel.setCustomType(newValue);
    }

    @Override
    public CustomType getValueFromComponent()
    {
        return panel.getCustomType();
    }
}
