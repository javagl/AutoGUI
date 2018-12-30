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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.javagl.autogui.model.ArrayValueModel;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ArrayValueView;
import de.javagl.autogui.view.ValueView;
import de.javagl.autogui.view.ValueViewBuilder;

/**
 * A {@link ValueView} that uses a other {@link ValueView} instances for
 * the elements of a array.
 * 
 * @param <A> The array type 
 * @param <E> The element/component type
 */
public final class SwingArrayValueView<A, E> 
    implements ArrayValueView<A, E, JComponent>
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(SwingArrayValueView.class.getName());
    
    /**
     * The {@link ArrayValueModel}
     */
    private final ArrayValueModel<A, E> arrayValueModel;
    
    /**
     * The panel that contains the controls and other components
     */
    private final JPanel mainPanel;
    
    /**
     * The panel that contains the rows, one for each array element
     */
    private final JPanel rowsPanel;
    
    /**
     * The {@link ValueViewBuilder} for the array elements
     */
    private final ValueViewBuilder<? extends JComponent> valueViewBuilder;
    
    /**
     * The current list of {@link ValueView} instances for the array elements
     */
    private final List<ValueView<E, ? extends JComponent>> elementValueViews;
    
    /**
     * Creates a new array value view
     * 
     * @param arrayValueModel The {@link ArrayValueModel}
     * @param valueViewBuilder The {@link ValueViewBuilder} for the 
     * array elements
     */
    public SwingArrayValueView(
        ArrayValueModel<A, E> arrayValueModel, 
        ValueViewBuilder<? extends JComponent> valueViewBuilder)
    {
        this.arrayValueModel = arrayValueModel;
        this.valueViewBuilder = valueViewBuilder;
        
        this.elementValueViews = 
            new ArrayList<ValueView<E, ? extends JComponent>>();
        
        mainPanel = new JPanel(new BorderLayout());

        rowsPanel = new JPanel(new GridLayout(0,1));
        mainPanel.add(rowsPanel, BorderLayout.CENTER);
        
        JButton addButton = new JButton(" + ");
        addButton.setMargin(new Insets(0,0,0,0));
        addButton.setFont(new Font("Monospaced", Font.BOLD, 12));
        addButton.addActionListener(ae -> addRow());
        JPanel addButtonPanel = new JPanel(new BorderLayout());
        addButtonPanel.add(addButton, BorderLayout.WEST);
        mainPanel.add(addButtonPanel, BorderLayout.SOUTH);
        
        int arrayLength = arrayValueModel.getArrayLength();
        for (int i=0; i<arrayLength; i++)
        {
            addRowPanel(i);
        }
        
        arrayValueModel.addValueListener((oldArray, newArray) -> 
        {
            if (SwingUtilities.isEventDispatchThread())
            {
                updateView(newArray);
            }
            else
            {
                SwingUtilities.invokeLater(() -> updateView(newArray));
            }
        });
    }
    
    /**
     * Add a row in the UI, creating a new array that is passed as the
     * new value to the {@link ArrayValueModel}
     */
    private void addRow()
    {
        int oldLength = 0;
        A oldArray = getValueModel().getValue();
        if (oldArray != null)
        {
            oldLength = Array.getLength(oldArray);
        }
        int newLength = oldLength + 1;
        Class<?> elementType = arrayValueModel.getElementValueType();
        @SuppressWarnings("unchecked")
        A newArray = (A) Array.newInstance(elementType, newLength);
        
        if (oldArray != null)
        {
            System.arraycopy(oldArray, 0, newArray, 0, oldLength);
        }
        
        if (!elementType.isPrimitive())
        {
            try
            {
                Object newValue = elementType.newInstance();
                Array.set(newArray, newLength - 1, newValue);
            } 
            catch (InstantiationException | IllegalAccessException e)
            {
                logger.warning("Could not instantiate: " + e.getMessage());
            }
        }
        
        arrayValueModel.setValue(newArray);
        
        // This update is needed for properties that are
        // not observed with PropertyChangeListeners
        updateView(newArray);
    }
    
    /**
     * Remove the row with the specified index from the UI, passing the
     * resulting new array to the {@link ArrayValueModel}
     * 
     * @param index The index
     */
    private void removeRow(int index)
    {
        A oldArray = getValueModel().getValue();
        int oldLength = Array.getLength(oldArray);

        int newLength = oldLength - 1;
        Class<?> elementType = arrayValueModel.getElementValueType();
        @SuppressWarnings("unchecked")
        A newArray = (A) Array.newInstance(elementType, newLength);
        
        System.arraycopy(oldArray, 0, newArray, 0, index);
        System.arraycopy(
            oldArray, index + 1, newArray, index, newLength - index);
        
        arrayValueModel.setValue(newArray);
        
        // This update is needed for properties that are
        // not observed with PropertyChangeListeners
        updateView(newArray);
    }
    
    /**
     * Update the view for the given new (array) value
     * 
     * @param newValue The new value
     */
    private void updateView(A newValue)
    {
        SwingUtils.validateEventDispathThread();
        
        rowsPanel.removeAll();
        elementValueViews.clear();
        
        if (newValue != null)
        {
            int length = Array.getLength(newValue);
            for (int i=0; i<length; i++)
            {
                addRowPanel(i);
            }
        }
        rowsPanel.revalidate();
    }
    
    /**
     * Add a new row containing the {@link ValueView} for the array element
     * with the given index
     * 
     * @param index The index
     */
    private void addRowPanel(int index)
    {
        ValueModel<E> elementValueModel = 
            arrayValueModel.getElementValueModel(index);
        
        ValueView<E, ? extends JComponent> elementValueView = 
            valueViewBuilder.createValueView(elementValueModel);
        
        elementValueViews.add(elementValueView);
        
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.add(elementValueView.getComponent(), BorderLayout.CENTER);
        
        JButton removeButton = new JButton(" - ");
        removeButton.setMargin(new Insets(0,0,0,0));
        removeButton.setFont(new Font("Monospaced", Font.BOLD, 12));
        JPanel removeButtonPanel = new JPanel(new BorderLayout());
        removeButtonPanel.add(removeButton, BorderLayout.NORTH);
        rowPanel.add(removeButtonPanel, BorderLayout.WEST);

        removeButton.addActionListener(re -> removeRow(index));
        
        rowsPanel.add(rowPanel);
        rowsPanel.revalidate();
    }
    
    @Override
    public final JPanel getComponent()
    {
        return mainPanel;
    }

    @Override
    public ArrayValueModel<A, E> getValueModel()
    {
        return arrayValueModel;
    }
    
    @Override
    public void updateView()
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            updateView(arrayValueModel.getValue());
        }
        else
        {
            SwingUtilities.invokeLater(() -> 
                updateView(arrayValueModel.getValue()));
        }
    }

    @Override
    public int getArrayLength()
    {
        return arrayValueModel.getArrayLength();
    }

    @Override
    public ValueView<E, ? extends JComponent> getElementValueView(int index)
    {
        return elementValueViews.get(index);
    }
}
