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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.javagl.common.beans.BeanUtils;
import de.javagl.reflection.Methods;

/**
 * Implementation of a {@link PropertyExtractor} that extracts Java Bean
 * properties.
 */
class BeanPropertyExtractor implements PropertyExtractor
{
    @Override
    public List<PropertyAccessor> getPropertyAccessors(Class<?> type)
    {
        List<PropertyAccessor> propertyAccessors = 
            new ArrayList<PropertyAccessor>();
        
        List<String> propertyNames = 
            BeanUtils.getMutablePropertyNamesOptional(type);
        for (String propertyName : propertyNames)
        {
            Class<?> propertyType = BeanUtils.getPropertyTypeOptional(
                type, propertyName);
            if (propertyType == null)
            {
                continue;
            }
            Method readMethodInternal = 
                BeanUtils.getReadMethodOptional(type, propertyName);
            if (readMethodInternal == null)
            {
                continue;
            }
            Method writeMethodInternal = 
                BeanUtils.getWriteMethodOptional(type, propertyName);
            if (writeMethodInternal == null)
            {
                continue;
            }
            
            Function<Object, Object> readMethod = (bean) -> 
            {
                return Methods.invokeOptional(readMethodInternal, bean);
            };
            BiConsumer<Object, Object> writeMethod = (bean, propertyValue) -> 
            {
                Methods.invokeOptional(
                    writeMethodInternal, bean, propertyValue);
            };
            
            PropertyAccessor propertyAccessor = 
                new DefaultPropertyAccessor(
                    propertyName, propertyType, readMethod, writeMethod);
            propertyAccessors.add(propertyAccessor);
        }
        return propertyAccessors;
    }
}