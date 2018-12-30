package de.javagl.autogui.samples;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViewBuilder;
import de.javagl.autogui.view.ValueViewFactory;
import de.javagl.autogui.view.ValueViews;

/**
 * A sample showing the basic usage of the AutoGUI library
 */
public class AutoGUI_10_CustomTypeContainer
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

        ValueViewBuilder<JComponent> b = ValueViews.createSwing(); 

        
        // Register the factory that should be used for all "CustomType"
        // instances
        b.using(CustomType.class, 
            new ValueViewFactory<CustomType, JComponent>()
        {
            @Override
            public ValueView<CustomType, JComponent> create(
                ValueModel<CustomType> valueModel)
            {
                return new CustomTypeValueView(valueModel);
            }
        });

        // Create a view for the CustomTypeContainer
        ValueView<CustomTypeContainer, ? extends JComponent> valueView = 
            b.createValueView(CustomTypeContainer.class);
        
        // Create the CustomTypeContainer, assign an initial
        // property, and show it in the view
        CustomTypeContainer customTypeContainer = new CustomTypeContainer();
        
        CustomType customType = new CustomType();
        customType.setValue(0, 1.0);
        customType.setValue(1, 2.0);
        customType.setValue(2, 4.0);
        customTypeContainer.setElement(customType);

        valueView.getValueModel().setValue(customTypeContainer);
        

        f.getContentPane().add(valueView.getComponent());
        
        f.setSize(400, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
    }

}
