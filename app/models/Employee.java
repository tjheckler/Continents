package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee
{
    @Id
   private int employeeId;

    private String firstName;
    private String lastName;
    private String city;
    private String notes;

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
}
