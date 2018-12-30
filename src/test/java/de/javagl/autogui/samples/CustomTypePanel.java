/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import de.javagl.common.ui.JSpinners;

/**
 * A panel for showing a {@link CustomType} in the samples
 */
@SuppressWarnings({ "javadoc", "serial" })
class CustomTypePanel extends JPanel
{
    private CustomType customType;

    public CustomTypePanel()
    {
        super(new GridLayout(0, 1));
    }

    public void setCustomType(CustomType customType)
    {
        removeAll();
        this.customType = customType;
        if (customType != null)
        {
            int n = customType.getNumberOfElements();
            for (int i = 0; i < n; i++)
            {
                int index = i;
                double value = customType.getValue(i);
                JSpinner spinner = new JSpinner(
                    new SpinnerNumberModel(value, -1000.0, 1000.0, 0.001));
                add(spinner);
                spinner.addChangeListener(e -> 
                {
                    double newValue = 
                        ((Number) spinner.getValue()).doubleValue();
                    customType.setValue(index, newValue);
                });
                JSpinners.setSpinnerDraggingEnabled(spinner, true);
            }
        }
    }

    public CustomType getCustomType()
    {
        return customType;
    }

}