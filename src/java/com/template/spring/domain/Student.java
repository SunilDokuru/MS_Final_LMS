/**
 *
 * @author Dokuru
 */

package com.template.spring.domain;

public class Student {
    private String firstName, lastName, country, status, ethnicity, city, department;
    //char gender;
    //int Zipcode;

    public Student(String firstName, String lastName, String country, String status, String ethnicity, String city, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.status = status;
        this.ethnicity = ethnicity;
        this.city = city;
        this.department = department;
    }

    public Student() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *@return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *@param firstName
     *  the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *@return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *@return the country
     */
    public String getCountry() {
        return country;
    }
    
    /**
     *@param country
     *  the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *@return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     *@param status
     *  the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *@return the ethnicity
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     *@param ethnicity
     *  the ethnicity to set
     */
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    /**
     *@return the city
     */
    public String getCity() {
        return city;
    }

    /**
     *@param city
     *  the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     *@return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     *@param department
     *  the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
