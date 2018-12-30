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

/**
 * A sample showing the basic usage of the AutoGUI library
 */
public class AutoGUI_06_Sorting
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
        
        // Create a ValueView for a Person, defining a sort order for
        // some of the properties.
        ValueView<Person, ? extends JComponent> valueView = ValueViews
            .createSwing()
            .sorting(
                ".name",
                ".age",
                ".weight",
                ".address",
                ".address.street",
                ".address.number",
                ".address.city.name",
                ".address.city.zipCode",
                ".pets",
                ".pets.name",
                ".pets.age",
                ".pets.weight")
            .createValueView(Person.class);
        
        // Assign a Person to the BeanModel of the BeanView
        Person person = ExampleBeans.createPerson();
        valueView.getValueModel().setValue(person);
        
        f.getContentPane().add(valueView.getComponent());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        // That's it.
    }
}

