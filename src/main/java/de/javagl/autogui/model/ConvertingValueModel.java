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

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.javagl.autogui.Converter;

/**
 * Implementation of a {@link ValueModel} that is backed by a delegate
 * and a {@link Converter}
 * 
 * @param <S> The source (delegate) value type
 * @param <T> The value type
 */
class ConvertingValueModel<S, T> extends AbstractValueModel<T> 
    implements ValueModel<T>, Detachable
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ConvertingValueModel.class.getName());
    
    /**
     * The default log level
     */
    private static final Level level = Level.FINER;
    
    /**
     * The delegate
     */
    private final ValueModel<S> delegate;
    
    /**
     * The {@link Converter}
     */
    private final Converter<S, T> converter;
    
    /**
     * The current value
     */
    private T currentValue;

    /**
     * The listener that will listen for changes in the delegate
     */
    private final ValueListener<S> convertingListener;
    
    /**
     * Creates a new instance
     * 
     * @param delegate The delegate
     * @param converter The {@link Converter}
     */
    ConvertingValueModel(
        ValueModel<S> delegate,
        Converter<S, T> converter)
    {
        super(converter.getTargetType());
        this.delegate = Objects.requireNonNull(
            delegate, "The delegate may not be null");
        this.converter = Objects.requireNonNull(
            converter, "The converter may not be null");
        
        this.currentValue = getValue();
        
        this.convertingListener = (oldS, newS) ->
        {
            T oldValue = currentValue;
            T newValue = getValue();
            currentValue = newValue;
            if (!Objects.equals(oldValue, newValue))
            {
                fireValueChanged(oldValue, newValue);
            }
        };
        delegate.addValueListener(convertingListener);
    }
    
    @Override
    public void detach()
    {
        delegate.removeValueListener(convertingListener);
    }

    @Override
    public final T getValue()
    {
        S delegateValue = delegate.getValue();
        T value = converter.forward().apply(delegateValue);
        
        logger.log(level, "Converted delegate value " 
            + delegateValue + " to " + value);
        
        return value;
    }

    @Override
    public final void setValue(T newValue)
    {
        S newDelegateValue = converter.backward().apply(newValue);
        
        logger.log(level, "Converted value " 
            + newValue + " to " + newDelegateValue);
        
        delegate.setValue(newDelegateValue);
    }
    
    @Override
    public String toString()
    {
        return "ConvertingValueModel[" 
            + "delegate=" + delegate + ","
            + "converter=" + converter + "]";
    }

}
