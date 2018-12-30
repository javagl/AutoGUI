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
 * A {@link ValueView} for the {@link CustomTypeContainer} sample
 */
@SuppressWarnings("javadoc")
class CustomTypeContainerValueView 
    extends AbstractSwingValueView<CustomTypeContainer, JComponent>
{
    private final CustomTypeContainerPanel panel;

    CustomTypeContainerValueView(ValueModel<CustomTypeContainer> valueModel)
    {
        super(valueModel);
        this.panel = new CustomTypeContainerPanel();
        setValueInComponent(valueModel.getValue());
    }

    @Override
    public JComponent getComponent()
    {
        return panel;
    }

    @Override
    public void setValueInComponent(CustomTypeContainer newValue)
    {
        panel.setCustomTypeContainer(newValue);
    }

    @Override
    public CustomTypeContainer getValueFromComponent()
    {
        return panel.getCustomTypeContainer();
    }
}
