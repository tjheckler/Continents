package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Employee
{
    @Id
    private int employeeId;

    private byte[] picture;
    private String firstName;
    private String lastName;
    private String city;
    private String notes;
    private BigDecimal salary;
    private int titleOfCourtesyId;

    public BigDecimal getSalary()
    {
        return salary;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getCity()
    {
        return city;
    }

    public String getNotes()
    {
        return notes;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public int getTitleOfCourtesyId()
    {
        return titleOfCourtesyId;
    }

    public void setTitleOfCourtesyId(int titleOfCourtesy)
    {
        this.titleOfCourtesyId = titleOfCourtesy;
    }

    public byte[] getPicture()
    {
        return picture;
    }

    public void setPicture(byte[] picture)
    {
        this.picture = picture;
    }
}
