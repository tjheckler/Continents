package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UnitsByCategory
{
    @Id
    private int categoryId;

    private String categoryName;
    private int unitsInStock;

    public UnitsByCategory(int categoryId, String categoryName, int unitsInStock)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.unitsInStock = unitsInStock;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public int getUnitsInStock()
    {
        return unitsInStock;
    }
}
