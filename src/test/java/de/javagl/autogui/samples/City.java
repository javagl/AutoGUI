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
public class City extends AbstractBean
{
    private String name;
    private int zipCode;
    private float geoLocation[];
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        firePropertyChange("name", this.name, this.name = name);
    }

    public int getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(int zipCode)
    {
        firePropertyChange("zipCode", this.zipCode, this.zipCode = zipCode);
    }
    
    public float[] getGeoLocation()
    {
        return geoLocation;
    }

    public void setGeoLocation(float[] geoLocation)
    {
        firePropertyChange("geoLocation", this.geoLocation, this.geoLocation = geoLocation);
    }

}
