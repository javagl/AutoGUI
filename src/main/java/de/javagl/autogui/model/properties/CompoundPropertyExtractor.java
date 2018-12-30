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
package de.javagl.autogui.model.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of a {@link PropertyExtractor} that combines the results
 * of several delegates. When multiple delegates return  
 * {@link PropertyAccessor} instances with the same name, then only
 * the <i>first</i> {@link PropertyAccessor} that was returned will
 * be kept (according to the order in which the delegates have been
 * given in the constructor)
 */
final class CompoundPropertyExtractor implements PropertyExtractor
{
    /**
     * The delegates
     */
    private final List<PropertyExtractor> delegates;
    
    /**
     * Creates a new instance
     * 
     * @param delegates The delegates
     */
    CompoundPropertyExtractor(PropertyExtractor ... delegates)
    {
        this.delegates = 
            new ArrayList<PropertyExtractor>(Arrays.asList(delegates));
    }

    @Override
    public List<PropertyAccessor> getPropertyAccessors(Class<?> type)
    {
        List<PropertyAccessor> result = new ArrayList<PropertyAccessor>();
        Set<String> knownNames = new LinkedHashSet<String>();
        for (PropertyExtractor delegate : delegates)
        {
            List<PropertyAccessor> delegateResult = 
                delegate.getPropertyAccessors(type);
            for (PropertyAccessor propertyAccessor : delegateResult)
            {
                String name = propertyAccessor.getName();
                if (!knownNames.contains(name))
                {
                    result.add(propertyAccessor);
                }
                knownNames.add(name);
            }
        }
        return result;
    }
    
    
}
