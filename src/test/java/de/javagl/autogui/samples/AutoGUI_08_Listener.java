/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViews;
import de.javagl.common.beans.PropertyChangeListeners;
import de.javagl.common.beans.PropertyChangeListeners.ObservedObject;

/**
 * A sample showing the basic usage of the AutoGUI library
 */
public class AutoGUI_08_Listener
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
        f.getContentPane().setLayout(new BorderLayout());
        
        // Create a ValueView for a Person
        ValueView<Person, ? extends JComponent> valueView = 
            ValueViews.createDefaultSwing(Person.class);
        
        // Assign a Person to the ValueModel of the ValueView
        Person person = ExampleBeans.createPerson();
        valueView.getValueModel().setValue(person);
        
        // Create a PropertyChangeListener, and attach it "deeply" to
        // the object, so that it will be informed about all changes
        // in the object and its sub-objects
        PropertyChangeListener p = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                System.out.println("Event: " + evt);
            }
        };
        ObservedObject observedObject =
            PropertyChangeListeners.addDeepPropertyChangeListener(person, p);
        
        // Add a button to detach the listeners
        JButton detachButton = new JButton("Detach PropertyChangeListeners");
        detachButton.addActionListener(e -> observedObject.detach()); 
        
        f.getContentPane().add(valueView.getComponent(), BorderLayout.CENTER);
        f.getContentPane().add(detachButton, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

