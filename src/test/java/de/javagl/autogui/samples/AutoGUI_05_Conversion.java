/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import static de.javagl.autogui.view.swing.SwingValueViewFactories.slider;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.javagl.autogui.Converters;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViews;
import de.javagl.common.beans.PropertyChangeListeners;

/**
 * A sample showing the basic usage of the AutoGUI library
 */
public class AutoGUI_05_Conversion
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
        
        // Create a ValueView for a Person. Configure the ValueViewBuilder
        // to use a slider for the "weight" property of the Pets of the 
        // Person, converting the slider values (0...100) to appropriate
        // weights (2.0f ... 20.0f)
        ValueView<Person, ? extends JComponent> valueView = ValueViews
            .createSwing()
            .using(".pets.weight", slider(0, 100), 
                Converters.numberMapping(2.0f, 20.0f, 0, 100))
            .createValueView(Person.class);
        
        // Assign a Person to the ValueModel of the ValueView
        Person person = ExampleBeans.createPerson();
        valueView.getValueModel().setValue(person);
        
        PropertyChangeListeners.addDeepConsoleLogger(person);
        
        f.getContentPane().add(valueView.getComponent());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        // That's it.
    }
}

