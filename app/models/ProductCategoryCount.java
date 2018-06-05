package models;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class ProductCategoryCount
{
    @Id
    private int categoryId;

    private String categoryName;
    private long count;


    public ProductCategoryCount(int categoryId, String categoryName, long count)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.count = count;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public long getCount()
    {
        return count;
    }
}
