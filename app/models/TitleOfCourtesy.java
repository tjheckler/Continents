package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TitleOfCourtesy
{
    @Id
    private int titleOfCourtesyId;
    private String titleOfCourtesyName;

    public int getTitleOfCourtesyId()
    {
        return titleOfCourtesyId;
    }

    public String getTitleOfCourtesyName()
    {
        return titleOfCourtesyName;
    }
}
