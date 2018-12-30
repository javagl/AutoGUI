/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.Color;
import java.util.Date;

import de.javagl.common.beans.AbstractBean;

/**
 * A simple bean for the samples
 */
@SuppressWarnings({ "javadoc", "serial" })
public class Person extends AbstractBean
{
    private String name;
    private int age;
    private float weight;
    private double height;
    private boolean male;
    private long money;

    private int ticTacToe[][];
    
    private Music music;
    private Pet[] pets;
    private Address address;
    private Date birthDate;
    private Color eyeColor;
    
    
    public Person()
    {
    }

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

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        firePropertyChange("height", this.height, this.height = height);
    }

    public boolean isMale()
    {
        return male;
    }

    public void setMale(boolean male)
    {
        firePropertyChange("male", this.male, this.male = male);
    }
    
    
    public long getMoney()
    {
        return money;
    }

    public void setMoney(long money)
    {
        firePropertyChange("money", this.money, this.money = money);
    }
    

    public Music getMusic()
    {
        return music;
    }

    public void setMusic(Music music)
    {
        firePropertyChange("music", this.music, this.music = music);
    }

    public Pet[] getPets()
    {
        return pets;
    }

    public void setPets(Pet[] pets)
    {
        firePropertyChange("pets", this.pets, this.pets = pets);
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        firePropertyChange("address", this.address, this.address = address);
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        firePropertyChange("birthDate", this.birthDate, this.birthDate =
            birthDate);
    }

    public Color getEyeColor()
    {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor)
    {
        firePropertyChange("eyeColor", this.eyeColor, this.eyeColor = eyeColor);
    }

    public int[][] getTicTacToe()
    {
        return ticTacToe;
    }

    public void setTicTacToe(int ticTacToe[][])
    {
        firePropertyChange("ticTacToe", this.ticTacToe, this.ticTacToe = ticTacToe);
    }

    
    
}
