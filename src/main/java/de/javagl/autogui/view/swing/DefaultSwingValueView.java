/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.autogui.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.javagl.autogui.model.StructuredValueModel;
import de.javagl.autogui.model.ValueListener;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ComponentOwner;
import de.javagl.autogui.view.MutableValueView;
import de.javagl.autogui.view.ValueView;

/**
 * Swing-based implementation of a {@link MutableValueView}
 * 
 * @param <T> The value type
 */
public final class DefaultSwingValueView<T>
    implements MutableValueView<T, JComponent>
{
    /**
     * The main component
     */
    private final JComponent component;
    
    /**
     * The container for the sub-components
     */
    private final JComponent container;

    /**
     * Internal row counter for the layout
     */
    private int rowCounter = 0;

    /**
     * The {@link StructuredValueModel}
     */
    private final StructuredValueModel<T> structuredValueModel;
    
    /**
     * The list of child {@link ValueView} instances 
     */
    private final List<ValueView<?, ?>> children;
    
    /**
     * Default constructor
     * 
     * @param structuredValueModel The {@link StructuredValueModel}
     */
    DefaultSwingValueView(StructuredValueModel<T> structuredValueModel)
    {
        this.structuredValueModel = structuredValueModel;
        this.component = new JPanel(new BorderLayout());
        this.container = new JPanel(new GridBagLayout());
        this.component.add(container, BorderLayout.NORTH);
        this.children = new ArrayList<ValueView<?,?>>();
    }
    
    @Override
    public StructuredValueModel<T> getValueModel()
    {
        return structuredValueModel;
    }
    
    @Override
    public void addChild( 
        Supplier<String> labelTextSupplier,
        ValueView<?, ? extends JComponent> child)
    {
        JLabel label = new JLabel(labelTextSupplier.get());
        ValueListener<Object> listener = (oldValue, newValue) -> 
        {
            label.setText(labelTextSupplier.get());
        };
        @SuppressWarnings("unchecked")
        ValueModel<Object> model = (ValueModel<Object>)child.getValueModel();
        model.addValueListener(listener);
        add(label, child);
        
        children.add(child);
    }
    
    /**
     * Add the given label and the component from the given
     * {@link ComponentOwner} to the main container
     * 
     * @param label The label
     * @param componentOwner The component owner
     */
    private void add(JLabel label, 
        ComponentOwner<? extends JComponent> componentOwner)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.weighty = 1.0;
        constraints.gridy = rowCounter;
        constraints.gridx = 0;
        constraints.weightx = 0.0;

        label.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0,2,0,2),
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        container.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        if (componentOwner != null)
        {
            container.add(componentOwner.getComponent(), constraints);
        }
        else
        {
            container.add(new JLabel("NONE"), constraints);
        }
        
        rowCounter++;
        
    }

    @Override
    public JComponent getComponent()
    {
        return component;
    }
    
    @Override
    public void updateView()
    {
        for (ValueView<?, ?> child : children)
        {
            child.updateView();
        }
    }
    
}