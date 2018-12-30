/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 */
package de.javagl.autogui.samples;

import java.awt.Color;
import java.util.Date;

/**
 * Methods to create example bean instances
 */
public class ExampleBeans
{
    /**
     * Create an example {@link Person}
     * 
     * @return The {@link Person}
     */
    public static Person createPerson()
    {
        Person person = new Person();
        person.setName("John Doe");
        person.setAge(42);
        person.setWeight(50);
        person.setHeight(175);
        person.setMoney(1000000);
        person.setMale(true);
        person.setBirthDate(new Date(System.currentTimeMillis()));
        person.setMusic(Music.METAL);
        person.setEyeColor(Color.GREEN);
        
        int ticTacToe[][] = { { 0, 1, 2 },  { 1, 0, 2 },  { 2, 0, 1 } };  
        person.setTicTacToe(ticTacToe);
        
        person.setPets(new Pet[] { createPetA(), createPetB() });
        
        City city = new City();
        city.setName("Jakarta");
        city.setZipCode(12345);
        city.setGeoLocation(new float[] { 1.234f, 5.678f });
        
        Address address = new Address();
        address.setStreet("Main Street");
        address.setNumber(123);
        address.setCity(city);
        person.setAddress(address);

        return person;
    }
    
    /**
     * Creates an example {@link Pet}
     * 
     * @return The {@link Pet}
     */
    static Pet createPetA()
    {
        Pet pet = new Pet();
        pet.setName("Snowball II");
        pet.setAge(6);
        pet.setWeight(2);
        return pet;
    }
    
    /**
     * Creates an example {@link Pet}
     * 
     * @return The {@link Pet}
     */
    static Pet createPetB()
    {
        Pet pet = new Pet();
        pet.setName("Santa's Little Helper");
        pet.setAge(6);
        pet.setWeight(12);
        return pet;
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private ExampleBeans()
    {
        // Private constructor to prevent instantiation
    }

    

}
