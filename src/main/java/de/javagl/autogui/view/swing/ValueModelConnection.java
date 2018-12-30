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

import java.util.logging.Level;
import java.util.logging.Logger;

import de.javagl.autogui.model.ValueListener;
import de.javagl.autogui.model.ValueModel;

/**
 * A connection between two {@link ValueModel} instances
 *
 * @param <T> The value type
 */
class ValueModelConnection<T> 
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ValueModelConnection.class.getName());
    
    /**
     * The default log level
     */
    private static final Level level = Level.FINER;
    
    /**
     * The first {@link ValueModel}
     */
    private ValueModel<T> valueModelA;

    /**
     * The second {@link ValueModel}
     */
    private ValueModel<T> valueModelB;
    
    /**
     * The {@link ValueListener} that will forward values from the first
     * to the second {@link ValueModel}
     */
    private final ValueListener<T> listenerAtoB;

    /**
     * The {@link ValueListener} that will forward values from the second
     * to the first {@link ValueModel}
     */
    private final ValueListener<T> listenerBtoA;

    /**
     * Default constructor
     */
    ValueModelConnection()
    {
        // Implementation note: These listeners COULD cause an endless
        // chain of mutual notifications. But ValueListeners are only
        // supposed to be notified when the value actually changed,
        // so these notifications should settle and stop after one cycle.
        // Nevertheless, there are still these log outputs...
        listenerAtoB = (oldValue, newValue) -> 
        {
            logger.log(level, "Forwarding  " + newValue + " from "
                + valueModelA + " to " + valueModelB + "...");
            valueModelB.setValue(newValue);
            logger.log(level, "Forwarding  " + newValue + " from "
                + valueModelA + " to " + valueModelB + " DONE");
        };
        listenerBtoA = (T oldValue, T newValue) ->
        {
            logger.log(level, "Backwarding " + newValue + " from "
                + valueModelB + " to " + valueModelA + "...");
            valueModelA.setValue(newValue);
            logger.log(level, "Backwarding " + newValue + " from "
                + valueModelB + " to " + valueModelA + " DONE");
        };
    }
    
    /**
     * Detach the listeners that this instance has added to the
     * {@link ValueModel} instances
     */
    void detach()
    {
        if (valueModelA != null)
        {
            valueModelA.removeValueListener(listenerAtoB);
            valueModelA = null;
        }
        if (valueModelB != null)
        {
            valueModelB.removeValueListener(listenerBtoA);
            valueModelB = null;
        }
    }
    
    /**
     * Attach the listeners that connect the given {@link ValueModel} 
     * instances. If <b>both</code> {@link ValueModel} instances are
     * <code>null</code>, then this is equivalent to calling 
     * {@link #detach()}.
     * 
     * @param newValueModelA The first {@link ValueModel} 
     * @param newValueModelB The second {@link ValueModel}
     * @throws IllegalArgumentException If exactly one of the given models
     * is <code>null</code>
     */
    public void attach(
        ValueModel<T> newValueModelA, 
        ValueModel<T> newValueModelB)
    {
        if ((newValueModelA == null) != (newValueModelB == null))
        {
            throw new IllegalArgumentException(
                "Either none or both value models must be null, "
                + "but received " + newValueModelA + " and " + newValueModelB);
        }
        detach();
        valueModelA = newValueModelA;
        valueModelB = newValueModelB;
        if (valueModelA != null && valueModelB != null)
        {
            valueModelA.addValueListener(listenerAtoB);
            valueModelB.addValueListener(listenerBtoA);
        }
    }
}
