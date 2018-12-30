/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

/**
 * An example class with a custom structure
 */
@SuppressWarnings("javadoc")
public class CustomTypeContainer
{
    private CustomType element;
    private CustomType others[];
    
    public CustomType getElement()
    {
        return element;
    }
    public void setElement(CustomType element)
    {
        this.element = element;
    }
    
    public CustomType[] getOthers()
    {
        return others;
    }
    public void setOthers(CustomType others[])
    {
        this.others = others;
    }

}
