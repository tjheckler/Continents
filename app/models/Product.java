package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product
{
    @Id
    private int productId;
    private String productName;
    private BigDecimal unitPrice;

    public String getProductName()
    {
        return productName;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }
}
