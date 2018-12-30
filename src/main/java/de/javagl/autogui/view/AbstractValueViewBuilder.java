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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.javagl.autogui.Converter;
import de.javagl.autogui.model.ArrayValueModel;
import de.javagl.autogui.model.StructuredArrayValueModel;
import de.javagl.autogui.model.StructuredValueModel;
import de.javagl.autogui.model.ValueModel;
import de.javagl.autogui.model.ValueModels;
import de.javagl.autogui.model.ValueModelsInternal;
import de.javagl.common.beans.BeanUtils;

/**
 * Abstract base implementation of a {@link ValueViewBuilder}
 *
 * @param <C> The component type
 */
public abstract class AbstractValueViewBuilder<C> 
    implements ValueViewBuilder<C>
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(AbstractValueViewBuilder.class.getName());
    
    /**
     * The default log level
     */
    private final Level level = Level.FINE;
    
    /**
     * The map from name paths to {@link ValueViewFactory} instances
     */
    private final Map<String, ValueViewFactory<?, ? extends C>> 
        factoriesByNamePath;
    
    /**
     * The map from types to {@link ValueViewFactory} instances
     */
    private final Map<Class<?>, ValueViewFactory<?, ? extends C>> 
        factoriesByValueType;
    
    /**
     * The map from name paths to {@link Converter} instances
     */
    private final Map<String, Converter<?,?>> convertersByNamePath;

    /**
     * The map from name paths to {@link LabelProvider} instances
     */
    private final Map<String, LabelProvider> labelProviders;
    
    /**
     * The comparator that will be used for comparing the name paths
     * and determine the oder of the child components 
     */
    private Comparator<String> namePathComparator = 
        Comparators.createOrderIgnoreCase();

    /**
     * Protected default constructor
     */
    protected AbstractValueViewBuilder()
    {
        this.factoriesByNamePath =
            new LinkedHashMap<String, ValueViewFactory<?, ? extends C>>();
        this.factoriesByValueType = 
            new LinkedHashMap<Class<?>, ValueViewFactory<?, ? extends C>>();
        this.convertersByNamePath = 
            new LinkedHashMap<String, Converter<?, ?>>();
        this.labelProviders = new LinkedHashMap<String, LabelProvider>();
    }
    
    /**
     * Implemented by subclasses in order to create an instance of a 
     * {@link MutableValueView}
     *  
     * @param <T> The value type
     * 
     * @param structuredValueModel The {@link StructuredValueModel} for which 
     * the {@link MutableValueView} should be created
     * @return The {@link MutableValueView}
     */
    protected abstract <T> MutableValueView<T, C> 
        createMutableValueViewImpl(
            StructuredValueModel<T> structuredValueModel);
    
    /**
     * Implemented by subclasses in order to create an instance of an
     * {@link ArrayValueView}
     * 
     * @param <A> The array type
     * @param <E> The element/component type
     * @param arrayValueModel The {@link ArrayValueModel}
     * @return The {@link ArrayValueView}
     */
    protected abstract <A, E> ArrayValueView<A, E, C>
        createArrayValueViewImpl(ArrayValueModel<A, E> arrayValueModel);
    
    
    /**
     * Creates a {@link ValueView} that indicates an error, meaning that 
     * no proper {@link ValueView} could be created
     * 
     * @param <T> The value type
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    protected abstract <T> ValueView<T, C> createErrorValueViewImpl(
        ValueModel<T> valueModel);

    /**
     * Create an instance of a {@link MutableValueView}
     *  
     * @param <T> The value type
     * 
     * @param structuredValueModel The {@link StructuredValueModel} for which 
     * the {@link MutableValueView} should be created
     * @return The {@link MutableValueView}
     */
    private <T> MutableValueView<T, C> createMutableValueView(
            StructuredValueModel<T> structuredValueModel)
    {
        logger.log(level, "createMutableValueView    for " 
            + structuredValueModel.getNamePath()
            + " : " + structuredValueModel);
        return createMutableValueViewImpl(structuredValueModel);
    }
    
    /**
     * Create an instance of an {@link ArrayValueView}
     * 
     * @param <A> The array type
     * @param <E> The element/component type
     * @param arrayValueModel The {@link ArrayValueModel}
     * @return The {@link ArrayValueView}
     */
    private <A, E> ArrayValueView<A, E, C> createArrayValueView(
        ArrayValueModel<A, E> arrayValueModel)
    {
        logger.log(level, "createArrayValueView      for " + arrayValueModel);
        return createArrayValueViewImpl(arrayValueModel);
    }
    
    
    /**
     * Creates a {@link ValueView} that indicates an error, meaning that 
     * no proper {@link ValueView} could be created
     * 
     * @param <T> The value type
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueView}
     */
    private <T> ValueView<T, C> createErrorValueView(
        ValueModel<T> valueModel)
    {
        logger.log(level, "createErrorValueView      for " + valueModel);
        return createErrorValueViewImpl(valueModel);
    }

    @Override
    public final ValueViewBuilder<C> sorting(
        String ... namePaths)
    {
        if (Arrays.asList(namePaths).contains(null))
        {
            throw new NullPointerException(
                "The name paths contain 'null' elements");
        }
        this.namePathComparator = 
            Comparators.createOrderIgnoreCase(namePaths);
        return this;
    }
    
    @Override
    public final <T> ValueViewBuilder<C> using(
        String namePath, 
        ValueViewFactory<T, ? extends C> valueViewFactory, 
        Converter<?, ? extends T> converter)
    {
        Objects.requireNonNull(namePath, 
            "The namePath may not be null");
        String lowerCaseNamePath = namePath.toLowerCase();
        
        factoriesByNamePath.put(
            lowerCaseNamePath, valueViewFactory);
        if (converter == null)
        {
            convertersByNamePath.remove(lowerCaseNamePath);
        }
        else
        {
            convertersByNamePath.put(
                lowerCaseNamePath, converter);
        }
        return this;
    }
    
    @Override
    public final ValueViewBuilder<C> using(
        String namePath, 
        ValueViewFactory<?, ? extends C> valueViewFactory)
    {
        return using(namePath, valueViewFactory, null);
    }

    @Override
    public final ValueViewBuilder<C> using(
        Class<?> valueType, 
        ValueViewFactory<?, ? extends C> valueViewFactory)
    {
        Objects.requireNonNull(valueType, "The valueType may not be null");
        if (valueViewFactory == null)
        {
            factoriesByValueType.remove(valueType);
        }
        else
        {
            factoriesByValueType.put(valueType, valueViewFactory);
        }
        return this;
    }
    
    
    @Override
    public final ValueViewBuilder<C> label(
        String namePath, 
        LabelProvider labelProvider)
    {
        Objects.requireNonNull(namePath, 
            "The namePath may not be null");
        String lowerCaseNamePath = namePath.toLowerCase();
        if (labelProvider == null)
        {
            labelProviders.remove(lowerCaseNamePath);
        }
        else
        {
            labelProviders.put(lowerCaseNamePath, labelProvider);
        }
        return this;
    }
    
    @Override
    public final ValueViewBuilder<C> label(
        String namePath, String label)
    {
        return label(namePath, p -> label);
    }
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Create a list that contains the given names, sorted according
     * to the {@link #namePathComparator}.
     * 
     * @param namePath The current name path
     * @param names The list of names
     * @return A sorted list containing the names
     */
    private List<String> computeSortedNames(
        String namePath, Collection<String> names)
    {
        List<String> sortedPropertyNames = new ArrayList<String>(names);
        Collections.sort(sortedPropertyNames,new Comparator<String>()
        {
            @Override
            public int compare(String name0, String name1)
            {
                String namePath0 = namePath+"."+name0;
                String namePath1 = namePath+"."+name1;
                return namePathComparator.compare(
                    namePath0, namePath1);
            }
        });
        return sortedPropertyNames;
        
    }
    
    

    /**
     * Returns the {@link Converter} for the given {@link ValueModel}, or 
     * <code>null</code> if there is none.
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link Converter}
     */
    protected final Converter<?, ?> getConverter(ValueModel<?> valueModel)
    {
        if (valueModel instanceof StructuredValueModel<?>)
        {
            StructuredValueModel<?> structuredValueModel = 
                (StructuredValueModel<?>)valueModel;
            String namePath = 
                structuredValueModel.getNamePath().toLowerCase();
            Converter<?, ?> result = 
                convertersByNamePath.get(namePath);
            return result;
        }
        return null;
    }
    
    
    /**
     * Returns the {@link LabelProvider}  that was stored for the
     * given {@link StructuredValueModel}. If no dedicated
     * {@link LabelProvider} was registered, then this will return a 
     * default {@link LabelProvider} that provides a short description of
     * the value, based on the name.
     * 
     * @param structuredValueModel The {@link StructuredValueModel}
     * @return The {@link LabelProvider}
     */
    private LabelProvider getLabelProvider(
        StructuredValueModel<?> structuredValueModel)
    {
        String namePath = structuredValueModel.getNamePath().toLowerCase();
        LabelProvider labelProvider = labelProviders.get(namePath);
        if (labelProvider == null)
        {
            String label = BeanUtils.getDescription(
                structuredValueModel.getName());
            return p -> label;
        }
        return labelProvider;
    }

    /**
     * Create a supplier for the label of the GUI component for the given
     * {@link StructuredValueModel}. This supplier will be created from
     * the {@link #getLabelProvider(StructuredValueModel) label provider}
     * for the given model.
     *  
     * @param structuredValueModel The {@link StructuredValueModel}
     * @return The label supplier
     */
    private Supplier<String> createLabelSupplier(
        StructuredValueModel<?> structuredValueModel)
    {
        LabelProvider labelProvider = getLabelProvider(structuredValueModel);
        return () -> labelProvider.getLabel(structuredValueModel);
    }
    
    /**
     * Returns the {@link ValueViewFactory} that should be used for creating
     * {@link ValueView} instances for the given {@link ValueModel}. If no
     * matching {@link ValueViewFactory} can be found, then <code>null</code>
     * is returned.
     * 
     * @param valueModel The {@link ValueModel}
     * @return The {@link ValueViewFactory}
     */
    protected final ValueViewFactory<?, ? extends C> getValueViewFactory(
        ValueModel<?> valueModel)
    {
        if (valueModel instanceof StructuredValueModel<?>)
        {
            StructuredValueModel<?> structuredValueModel = 
                (StructuredValueModel<?>)valueModel;
            String namePath = 
                structuredValueModel.getNamePath().toLowerCase();
            
            ValueViewFactory<?, ? extends C> valueViewFactory = 
                 getFactoryByNamePath(namePath);
            if (valueViewFactory != null)
            {
                return valueViewFactory;
            }
        }
        Class<?> valueType = valueModel.getValueType();
        ValueViewFactory<?, ? extends C> valueViewFactory = 
            getFactoryByValueType(valueType);
        return valueViewFactory;
    }
    
    /**
     * Returns the {@link ValueViewFactory} for a name path.
     * 
     * @param namePath The name path
     * @return The {@link ValueViewFactory}
     */
    protected final ValueViewFactory<?, ? extends C> 
        getFactoryByNamePath(String namePath)
    {
        return factoriesByNamePath.get(namePath.toLowerCase());
    }

    /**
     * Returns the {@link ValueViewFactory} for a certain type. If no factory
     * has been registered for the given type, and the given type is an
     * enum type, then {@link #getFactoryForEnumType(Class, Object[])} will
     * be called. If no appropriate factory can be found, then 
     * <code>null</code> is returned.
     * 
     * @param valueType The value type
     * @return The {@link ValueViewFactory}
     */
    protected ValueViewFactory<?, ? extends C> getFactoryByValueType(
        Class<?> valueType)
    {
        ValueViewFactory<?, ? extends C> result =
            factoriesByValueType.get(valueType);
        if (result != null)
        {
            return result;
        }
        if (Enum.class.isAssignableFrom(valueType))
        {
            Object[] enumConstants=valueType.getEnumConstants();
            return getFactoryForEnumType(valueType, enumConstants);
        }
        return null;
    }
    
    /**
     * Returns the {@link ValueViewFactory} for an enum type, or 
     * <code>null</code> if no such factory exists.
     * 
     * @param valueType The value type
     * @param enumConstants The enum constants
     * @return The {@link ValueViewFactory}
     */
    protected abstract ValueViewFactory<?, ? extends C> getFactoryForEnumType(
        Class<?> valueType, Object[] enumConstants);
    
    
    @Override
    public final <T> ValueView<T, ? extends C> createValueView(
        Class<T> valueType)
    {
        StructuredValueModel<T> structuredValueModel = 
            ValueModels.createStructured(valueType);
        return createStructuredValueView(structuredValueModel);
    }
    
    @Override
    public final <T> ValueView<T, ? extends C> createSingleValueView(
        Class<T> valueType)
    {
        ValueModel<T> valueModel = ValueModels.create(valueType);
        return createValueView(valueModel);
    }

    @Override 
    public <T> ValueView<T, ? extends C> createValueView(
        ValueModel<T> valueModel)
    {
        logger.log(level, "createValueView           for " + valueModel);
        
        ValueViewFactory<?, ? extends C> valueViewFactory = 
            getValueViewFactory(valueModel);
        if (valueViewFactory != null)
        {
            logger.log(level, "Using factory " + valueViewFactory 
                + " for " + valueModel);

            @SuppressWarnings("unchecked")
            ValueViewFactory<T, ? extends C> typedValueViewFactory =
                (ValueViewFactory<T, ? extends C>) valueViewFactory;
            ValueView<T, ? extends C> valueView =
                typedValueViewFactory.create(valueModel);

            return valueView;
        }
        
        if (valueModel instanceof StructuredValueModel<?>)
        {
            StructuredValueModel<T> structuredValueModel = 
                (StructuredValueModel<T>)valueModel;
            
            ValueView<T, ? extends C> valueView = 
                createStructuredValueView(structuredValueModel);
            return valueView;
        }
        
        return createErrorValueView(valueModel);
    }
    
    /**
     * Create a {@link ValueView} for the given {@link StructuredValueModel}.
     * This will contain one {@link ValueView} for each of the
     * {@link StructuredValueModel#getChildren() children} of the given model.
     * 
     * @param <T> The value type
     * @param structuredValueModel The {@link StructuredValueModel}
     * @return The {@link ValueView}
     */
    private <T> ValueView<T, ? extends C> createStructuredValueView(
        StructuredValueModel<T> structuredValueModel)
    {
        logger.log(level, "createStructuredValueView for "
            + structuredValueModel.getNamePath() + " : "
            + structuredValueModel);
        
        MutableValueView<T, C> mutableValueView = 
            createMutableValueView(structuredValueModel);
        
        // If the value type of the given model is an array, then
        // create an ArrayValueView that will maintain its elements
        // based on an ArrayValueModel that is created from the
        // given model
        if (structuredValueModel instanceof StructuredArrayValueModel<?, ?>)
        {
            @SuppressWarnings("unchecked")
            ArrayValueModel<T, Object> structuredArrayValueModel =
                (ArrayValueModel<T, Object>) structuredValueModel;
                
             ValueView<?, ? extends C> arrayValueView = 
                createArrayValueView(structuredArrayValueModel);
             mutableValueView.addChild(
                () -> "", arrayValueView);
            return mutableValueView;
        }
        
        // Compute the list of child names, and sort them based on the
        // current sorting configuration of this builder
        Collection<String> childNames = 
            structuredValueModel.getChildren().keySet();
        String namePath =  structuredValueModel.getNamePath().toLowerCase();
        List<String> sortedChildNames = 
            computeSortedNames(namePath, childNames);
        
        // Create one ValueView for each child, and add it to the
        // MutableValueView that will be returned here.
        for (String childName : sortedChildNames)
        {
            StructuredValueModel<?> child = 
                structuredValueModel.getChild(childName);
            StructuredValueModel<Object> convertedChild = 
                createConverted(child);
            
            Supplier<String> labelSupplier = 
                createLabelSupplier(convertedChild);
            ValueView<?, ? extends C> childValueView = 
                createValueView(convertedChild);
            mutableValueView.addChild(
                labelSupplier, childValueView);
        }
        return mutableValueView;
    }

    /**
     * Return a {@link StructuredValueModel} that was created by applying the 
     * {@link #getConverter(ValueModel) converter} to the given 
     * model, or the value model itself if there is no converter.
     * 
     * @param structuredValueModel The {@link StructuredValueModel}
     * @return The converted {@link StructuredValueModel}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private StructuredValueModel<Object> createConverted(
        StructuredValueModel<?> structuredValueModel)
    {
        StructuredValueModel<?> converted = structuredValueModel;
        Converter converter = getConverter(structuredValueModel);
        if (converter != null)
        {
            converted = ValueModelsInternal.createStructuredValueModel(
                structuredValueModel.getParent(), 
                structuredValueModel.getName(),
                ValueModels.converting(structuredValueModel, converter),
                structuredValueModel.getPropertyExtractor());
            
        }
        return (StructuredValueModel<Object>) converted; 
    }

    @Override
    public String toString()
    {
        return "AbstractValueViewBuilder";
    }
    
}

