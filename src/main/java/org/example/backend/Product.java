package org.example.backend;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mortoza Khan
 */

@Entity
public class Product extends AbstractEntity {
    
    public enum State {
        Active, New, Archived
    }

    @Size(max = 250)
    private String description;
    
    @NotNull
    private State productState = State.New;
    
    private Double price = 0.0;
    
    private String unit = "h";
    
    @ManyToOne
    private Invoicer invoicer;

    public State getProductState() {
        return productState;
    }

    public void setProductState(State productState) {
        this.productState = productState;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double defaultPrice) {
        this.price = defaultPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String defaultUnit) {
        this.unit = defaultUnit;
    }

    public Invoicer getInvoicer() {
        return invoicer;
    }

    public void setInvoicer(Invoicer invoicer) {
        this.invoicer = invoicer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    @Override
    public String toString() {
        return description;
    }
    
}   
