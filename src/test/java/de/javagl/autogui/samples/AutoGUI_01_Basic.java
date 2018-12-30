/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViews;
import de.javagl.common.beans.PropertyChangeListeners;

/**
 * A sample showing the basic usage of the AutoGUI library
 */
public class AutoGUI_01_Basic
{
    /**
     * The entry point of this sample
     * 
     * @param args Not used
     */
    public static void main(String[] args) 
    {
        LoggerUtil.initLogging();
        SwingUtilities.invokeLater(
            () -> createAndShowGUI());
    }
    
    /**
     * Create and show the GUI, to be called on the Event Dispatch Thread
     */
    private static void createAndShowGUI()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a ValueView for a Pet
        ValueView<Pet, ? extends JComponent> valueView = 
            ValueViews.createDefaultSwing(Pet.class);
        
        // Assign a Pet to the ValueModel of the ValueView
        Pet pet = ExampleBeans.createPetA();
        valueView.getValueModel().setValue(pet);
        
        PropertyChangeListeners.addDeepConsoleLogger(pet);
        
        f.getContentPane().add(valueView.getComponent());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        // That's it.
    }
}

