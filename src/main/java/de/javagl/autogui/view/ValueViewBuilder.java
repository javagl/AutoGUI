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

import de.javagl.autogui.Converter;
import de.javagl.autogui.model.ValueModel;


/**
 * Interface for classes that can be used to build {@link ValueView} instances.
 * <br>
 * <br>
 * <a name="namePath">Definition of the <i>name path</i></a>:
 * <br>
 * <br>
 * The <i>name path</i> is a string describing the path to one value in
 * a structured object. It describes the path from a "root object" to one 
 * particular value. For example, given classes like<br>
 * <pre><code>
 * class Person {
 *     String getName() { ... }
 *     Address getAddress() { ... }
 *     Address getBirthPlace() { ... }
 *     ...
 * }
 * class Address {
 *     City getCity() { ... }
 *     String getStreet() { ... }
 * }
 * class City {
 *     String getName() { ... }
 * }
 * </code></pre>
 * a name path may for example be <br>
 * <code>.name</code> or <br>
 * <code>.address.city</code> or <br>
 * <code>.address.city.name</code> or <br>
 * <code>.birthPlace.city.name</code><br>
 * Thus, the name path uniquely identifies one particular
 * value in a complex object.<br> 
 * <br>
 * Throughout this class, name paths are case-<b>in</b>sensitive.
 * Thus, for example, <br>
 * <code>.birth<b>P</b>lace.<b>C</b>ity</code> and <br>
 * <code>.birth<b>p</b>lace.<b>c</b>ity</code> <br>
 * are treated equally. 
 * 
 * @param <C> The component type
 */
public interface ValueViewBuilder<C>
{
    /**
     * Instruct this builder to create the sub-components of the 
     * {@link ValueView} according to the order specified via the given 
     * <a href="#namePath">name paths</a>. 
     *  
     * @param namePaths The name paths in the desired order
     * @return This instance 
     */
    ValueViewBuilder<C> sorting(String... namePaths);

    /**
     * Instruct this builder to use a {@link ValueView} that is created
     * using the given {@link ValueViewFactory} for values with the
     * given <a href="#namePath">name path</a>.
     * 
     * @param <V> The type of the value in the component
     *  
     * @param namePath The name path
     * @param valueViewFactory The {@link ValueViewFactory}
     * @param converter The {@link Converter}. If this is 
     * <code>null</code>, a default (identity) value converter will be used
     * @return This instance
     */
    <V> ValueViewBuilder<C> using(String namePath,
        ValueViewFactory<V, ? extends C> valueViewFactory,
        Converter<?, ? extends V> converter);

    /**
     * Instruct this builder to use a {@link ValueView} that is created
     * using the given {@link ValueViewFactory} for values with the
     * given <a href="#namePath">name path</a>.
     *  
     * @param namePath The name path
     * @param valueViewFactory The {@link ValueViewFactory}
     * @return This instance
     */
    ValueViewBuilder<C> using(String namePath,
        ValueViewFactory<?, ? extends C> valueViewFactory);

    /**
     * Instruct this builder to use a {@link ValueView} that is created
     * using the given {@link ValueViewFactory} for values with the
     * given type.
     * 
     * @param valueType The value type
     * @param valueViewFactory The {@link ValueViewFactory}
     * @return This instance
     */
    ValueViewBuilder<C> using(Class<?> valueType,
        ValueViewFactory<?, ? extends C> valueViewFactory);

    /**
     * Set the {@link LabelProvider} that should be used for creating
     * the label of the component that is associated with the value
     * that has the given <a href="#namePath">name path</a>.
     * 
     * @param namePath The name path
     * @param labelProvider The label provider. If this is <code>null</code>,
     * a label provider will be used that creates a default label based on
     * the name of the value.
     * @return This instance
     */
    ValueViewBuilder<C> label(String namePath,
        LabelProvider labelProvider);

    /**
     * Set the the label of the component that is associated with the value 
     * that has the given <a href="#namePath">name path</a>.
     * 
     * @param namePath The name path
     * @param label The label
     * @return This instance
     */
    ValueViewBuilder<C> label(String namePath, String label);

    /**
     * Build the {@link ValueView} for the given value type, based
     * on the current configuration of this builder. This assumes
     * that the given type is a "structured", like a Java Bean or a 
     * POJO with multiple properties. For single values (like
     * an <code>int</code> or <code>String</code>, the 
     * {@link #createSingleValueView(Class)} method may be used.
     * 
     * @param <T> The type of the value
     * @param valueType The value type
     * @return The {@link ValueView}
     */
    <T> ValueView<T, ? extends C> createValueView(Class<T> valueType);
    
    /**
     * Build the {@link ValueView} for the given value type, based
     * on the current configuration of this builder. 
     * 
     * @param <T> The type of the value
     * @param valueType The value type
     * @return The {@link ValueView}
     */
    <T> ValueView<T, ? extends C> createSingleValueView(Class<T> valueType);
    
    /**
     * Create the {@link ValueView} for the given {@link ValueModel}, based on
     * the current configuration of this builder.
     *  
     * @param <T> The value type
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    <T> ValueView<T, ? extends C> createValueView(ValueModel<T> valueModel);
}
