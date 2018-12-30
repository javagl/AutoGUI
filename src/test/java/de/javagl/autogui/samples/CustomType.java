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
public class CustomType
{
    private double array[];

    public CustomType()
    {
        this.array = new double[3];
    }

    public int getNumberOfElements()
    {
        return array.length;
    }

    public void setValue(int index, double value)
    {
        array[index] = value;
    }

    public double getValue(int index)
    {
        return array[index];
    }
}
