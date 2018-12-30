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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.view.ValueView;

/**
 * A {@link ValueView} that uses a panel to show a color, and allows
 * selecting the color with a color chooser
 */
class ColorChooserValueView 
    extends AbstractSwingValueView<Color, JComponent>
{
    /**
     * A panel that simply paints a color and its RGB string
     */
    private static class ColorPanel extends JPanel
    {
        /**
         * Serial UID
         */
        private static final long serialVersionUID = 2936232584005588081L;
        
        /**
         * The current color shown in this panel
         */
        private Color color = null;
        
        /**
         * Set the given color to be shown in this panel
         * 
         * @param color The color to be shown
         */
        void setColor(Color color)
        {
            this.color = color;
            repaint();
        }
        
        /**
         * Returns the current color shown in this panel
         * 
         * @return The current color
         */
        Color getColor()
        {
            return color;
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            int ascend = g.getFontMetrics().getAscent();
            
            if (color == null)
            {
                g.setColor(Color.WHITE);
                g.fillRect(0,0,getWidth(),getHeight());
                g.setColor(Color.BLACK);
                g.drawString("<null>", 5, ascend);
                return;
            }
            
            g.setColor(color);
            g.fillRect(0,0,getWidth(),getHeight());
            
            int cr = color.getRed();
            int cg = color.getGreen();
            int cb = color.getBlue();
            
            Color textColor = textColorFor(color);
            g.setColor(textColor);

            String colorString = "("+cr+","+cg+","+cb+")";
            g.drawString(colorString, 5, ascend);
        }
    }

    /**
     * Utility method that returns a color that is appropriate for 
     * painting (readable) text over the given color
     * 
     * @param color The input color
     * @return An appropriate text color
     */
    private static Color textColorFor(Color color)
    {
        int cr = color.getRed();
        int cg = color.getGreen();
        int cb = color.getBlue();
        float r = cr / 255.0f;
        float g = cg / 255.0f;
        float b = cb / 255.0f;
        double d = Math.sqrt(r * r + g * g + b * b);
        if (d > Math.sqrt(3) / 2)
        {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
    
    /**
     * The panel that shows the color and its RGB string
     */
    private final ColorPanel colorPanel;
    
    /**
     * The color chooser that is used to select the color
     */
    private final JColorChooser colorChooser;

    /**
     * Create the new color chooser value component
     * 
     * @param valueModel The {@link ValueModel}
     */
    ColorChooserValueView(ValueModel<Color> valueModel)
    {
        super(valueModel);
        colorPanel = new ColorPanel();
        colorChooser = new JColorChooser();
        
        final ActionListener okListener = e -> 
        {
            Color newColor = colorChooser.getColor();
            colorPanel.setColor(newColor);
            getSwingValueModel().setValue(newColor);
        };
        
        colorPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JDialog dialog = JColorChooser.createDialog(
                    colorPanel, "Color", true, colorChooser, okListener, null);
                dialog.setVisible(true);
            }
        });
    }

    
    @Override
    public JComponent getComponent()
    {
        return colorPanel;
    }

    @Override
    public void setValueInComponent(Color newValue)
    {
        colorPanel.setColor(newValue);
    }

    @Override
    public Color getValueFromComponent()
    {
        return colorPanel.getColor();
    }
}
