package org.example.backend;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Matti Tahvonen
 */
@Embeddable
public class InvoiceRow implements Serializable {
    
    @ManyToOne
    private Product product;

    @NotNull
    private String description;

    @Min(0)
    @NotNull
    private Double quantity = 1.0;

    @NotNull
    private String unit = "h";

    @Min(0)
    @NotNull
    private Double price = 1.0;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRowtotal() {
        if (price == null || quantity == null) {
            return 0.0;
        } else {
            return price * quantity;
        }
    }

}
