/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.model.ValueModels;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViews;

/**
 * A sample showing the basic usage of the AutoGUI library for objects that
 * are not beans with a PropertyChangeSupport, but only POJOs with plain
 * fields and without setters and getters
 */
public class AutoGUI_07_Pojo
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
        
        // Create a value model from ALL properties of the given class
        // (even the non-public fields)
        ValueModel<House> valueModel = 
            ValueModels.createStructured(House.class, true);
        ValueView<House, ? extends JComponent> valueView = 
            ValueViews.createSwing().createValueView(valueModel);
        
        House house = new House();
        valueView.getValueModel().setValue(house);
        
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(valueView.getComponent(), BorderLayout.CENTER);
        
        JPanel p = new JPanel();
        
        JButton changeValueButton = new JButton("Change value");
        changeValueButton.addActionListener(e -> house.height = 42);
        p.add(changeValueButton);

        JButton updateViewButton = new JButton("Update view");
        updateViewButton.addActionListener(e -> valueView.updateView());
        p.add(updateViewButton);
        
        f.getContentPane().add(p, BorderLayout.SOUTH);
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        // That's it.
    }
}

