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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import de.javagl.reflection.Fields;
import de.javagl.reflection.Members;

/**
 * Implementation of a {@link PropertyExtractor} that can extract the
 * properties of a POJO (Plain Old Java Object). The properties are
 * here the fields of the class.
 */
final class PojoPropertyExtractor implements PropertyExtractor
{
    /**
     * The enumeration of the possible access levels that may be covered
     */
    enum AccessLevel
    {
        /**
         * Access public fields
         */
        PUBLIC,
        
        /**
         * Access protected fields
         */
        PROTECTED,
        
        /**
         * Access default (package-private) fields
         */
        DEFAULT,
        
        /**
         * Access private fields
         */
        PRIVATE
    }
    
    /**
     * The predicate that will check whether the {@link PropertyAccessor}
     * for the given field should be created
     */
    private final Predicate<Field> inclusionPredicate;
    
    /**
     * Default constructor, covering all field access levels
     */
    PojoPropertyExtractor()
    {
        this(AccessLevel.values());
    }
    
    /**
     * Create an instance that accesses fields with the given access levels
     * 
     * @param accessLevels The access levels
     */
    PojoPropertyExtractor(AccessLevel ... accessLevels)
    {
        Predicate<Field> p = field -> false;
        Set<AccessLevel> set = EnumSet.copyOf(Arrays.asList(accessLevels));
        if (set.contains(AccessLevel.PUBLIC))
        {
            p = p.or(Members::isPublic);
        }
        if (set.contains(AccessLevel.PROTECTED))
        {
            p = p.or(Members::isProtected);
        }
        if (set.contains(AccessLevel.DEFAULT))
        {
            p = p.or(Members::isPackageAccessible);
        }
        if (set.contains(AccessLevel.PRIVATE))
        {
            p = p.or(Members::isPrivate);
        }
        inclusionPredicate = p;
    }
    
    @Override
    public List<PropertyAccessor> getPropertyAccessors(Class<?> type)
    {
        List<PropertyAccessor> propertyAccessors = 
            new ArrayList<PropertyAccessor>();
        
        List<Field> allOwnInstanceFields = Fields.getAllOptional(
            type, Members::isNotStatic, inclusionPredicate, 
            f -> f.getDeclaringClass().equals(type));
        for (Field field : allOwnInstanceFields)
        {
            String propertyName= field.getName();
            Class<?> propertyType = field.getType();
            
            Function<Object, Object> readMethod = (bean) -> 
            {
                return Fields.getNonAccessibleOptional(field, bean);
            };
            BiConsumer<Object, Object> writeMethod = (bean, propertyValue) -> 
            {
                Fields.setNonAccessibleOptional(field, bean, propertyValue);
            };
            
            PropertyAccessor propertyAccessor = 
                new DefaultPropertyAccessor(
                    propertyName, propertyType, readMethod, writeMethod);
            propertyAccessors.add(propertyAccessor);
        }
        return propertyAccessors;
    }
}