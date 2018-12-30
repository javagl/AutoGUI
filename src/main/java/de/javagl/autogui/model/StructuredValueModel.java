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
package de.javagl.autogui.model;

import java.util.Map;

import de.javagl.autogui.model.properties.PropertyExtractor;

/**
 * A {@link ValueModel} that has additional structural information. Namely,
 * information about its parent and children, and a name that identifies
 * this model in the {@link #getChildren() children} of its parent. 
 *
 * @param <T> The value type
 */
public interface StructuredValueModel<T> extends ValueModel<T>
{
    /**
     * Returns the {@link StructuredValueModel} that this model is part of
     * 
     * @return The {@link StructuredValueModel}
     */
    StructuredValueModel<?> getParent();
    
    /**
     * Returns the name that identifies this model in its parent
     * 
     * @return The name of this model 
     */
    String getName();
    
    /**
     * Returns the name path that identifies this model relative to the root
     * 
     * @return The name path 
     */
    String getNamePath();
    
    /**
     * Returns an unmodifiable view on the mapping from names to children
     * 
     * @return The children
     */
    Map<String, StructuredValueModel<?>> getChildren();
    
    /**
     * Returns the child with the given name, or <code>null</code> if no such 
     * child exists
     * 
     * @param name The name
     * @return The child
     */
    StructuredValueModel<?> getChild(String name);
    
    /**
     * Returns the property extractor. Only used internally.
     * 
     * @return The property extractor
     */
    PropertyExtractor getPropertyExtractor();
    
}
