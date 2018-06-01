package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;
    private String description;

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
