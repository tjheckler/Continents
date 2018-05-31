package models;

import java.math.BigDecimal;

public class Product
{
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
