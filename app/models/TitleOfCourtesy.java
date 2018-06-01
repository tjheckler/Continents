package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TitleOfCourtesy
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setTitleOfCourtesyName(String titleOfCourtesyName)
    {
        this.titleOfCourtesyName = titleOfCourtesyName;
    }
}
