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
public class Address extends AbstractBean
{
	private City city;
    private String street;
    private int number;

    public Address()
    {
    }

    public void setCity(City city)
    {
        firePropertyChange("city", this.city, this.city = city);
    }

    public City getCity()
    {
        return city;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        firePropertyChange("street", this.street, this.street = street);
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        firePropertyChange("number", this.number, this.number = number);
    }

}
