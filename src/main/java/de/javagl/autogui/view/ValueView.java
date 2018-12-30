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
package de.javagl.autogui.view;

import de.javagl.autogui.model.ValueModel;

/**
 * Interface for classes that serve as the view for a {@link ValueModel}.
 * They provide a {@link #getComponent() component}, which will be a 
 * GUI component that depends on the implementation. 
 *
 * @param <T> The value type
 * @param <C> The GUI component type
 */
public interface ValueView<T, C> extends ComponentOwner<C> 
{
    /**
     * Returns the {@link ValueModel} that is represented by this 
     * {@link ValueView}
     * 
     * @return The {@link ValueModel}
     */
    ValueModel<T> getValueModel();
    
    /**
     * Trigger an update of the view with the latest value from the model.
     * Note that calling this method is <b>not</b> necessary for values
     * that represent Java Beans with bound properties. It is only
     * necessary for POJOs that do not allow observing their properties
     * with property change listeners.
     */
    void updateView();
}
