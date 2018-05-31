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

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}
