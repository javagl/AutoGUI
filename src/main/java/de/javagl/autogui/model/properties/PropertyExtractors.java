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

import de.javagl.autogui.model.properties.PojoPropertyExtractor.AccessLevel;

/**
 * Methods to create {@link PropertyExtractor} instances.<br>
 * <br>
 * This class should not be considered to be part of the public API!
 */
public class PropertyExtractors
{
    /**
     * Create a {@link PropertyExtractor} for Java Bean properties.
     * 
     * @return The {@link PropertyExtractor}
     */
    public static PropertyExtractor createForBeans()
    {
        return new BeanPropertyExtractor();
    }
    
    /**
     * Create a {@link PropertyExtractor} for POJOs
     * 
     * @return The {@link PropertyExtractor}
     */
    public static PropertyExtractor createForPojos()
    {
        return new PojoPropertyExtractor();
    }
    
    /**
     * Create a new {@link PropertyExtractor}
     * 
     * @return The {@link PropertyExtractor}
     */
    public static PropertyExtractor createDefault()
    {
        return new CompoundPropertyExtractor(
            new BeanPropertyExtractor(),
            new PojoPropertyExtractor(AccessLevel.PUBLIC));
    }

    /**
     * Create a new {@link PropertyExtractor}
     * 
     * @return The {@link PropertyExtractor}
     */
    public static PropertyExtractor createForAllProperties()
    {
        return new CompoundPropertyExtractor(
            new BeanPropertyExtractor(),
            new PojoPropertyExtractor());
    }
    
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private PropertyExtractors()
    {
        // Private constructor to prevent instantiation
    }
    
}
