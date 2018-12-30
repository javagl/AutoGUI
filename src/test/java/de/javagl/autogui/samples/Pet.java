/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import de.javagl.common.beans.AbstractBean;

/**
 * A simple bean for the samples
 */
@SuppressWarnings({ "javadoc", "serial" })
public class Pet extends AbstractBean
{
    private String name;
    private int age;
    private float weight;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        firePropertyChange("name", this.name, this.name = name);
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        firePropertyChange("age", this.age, this.age = age);
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        firePropertyChange("weight", this.weight, this.weight = weight);
    }

}
