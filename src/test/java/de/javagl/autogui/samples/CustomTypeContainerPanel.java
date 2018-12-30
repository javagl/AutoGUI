/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * A panel for showing a {@link CustomTypeContainer} in the samples
 */
@SuppressWarnings({ "javadoc", "serial" })
class CustomTypeContainerPanel extends JPanel
{
    private CustomTypeContainer customTypeContainer;

    public CustomTypeContainerPanel()
    {
        super(new GridLayout(0, 1));
    }

    public void setCustomTypeContainer(CustomTypeContainer customTypeContainer)
    {
        removeAll();
        this.customTypeContainer = customTypeContainer;
        if (customTypeContainer != null)
        {
            CustomType element = customTypeContainer.getElement();
            if (element != null)
            {
                CustomTypePanel customTypePanel = 
                    new CustomTypePanel();
                customTypePanel.setCustomType(element);
                add(customTypePanel);
            }
            CustomType others[] = customTypeContainer.getOthers();
            if (others != null)
            {
                for (CustomType other : others)
                {
                    CustomTypePanel customTypePanel = 
                        new CustomTypePanel();
                    customTypePanel.setCustomType(other);
                    add(customTypePanel);
                }
            }
        }
    }

    public CustomTypeContainer getCustomTypeContainer()
    {
        return customTypeContainer;
    }

}