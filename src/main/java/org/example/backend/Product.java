package org.example.backend;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mortoza Khan
 */

@Entity
public class Product extends AbstractEntity {
    
    public enum State {
        Active, Archived
    }

    @Size(max = 50)
    private String name;
    
    @NotNull
    private State productState = State.Active;
    
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

    @XmlTransient
    public Invoicer getInvoicer() {
        return invoicer;
    }

    public void setInvoicer(Invoicer invoicer) {
        this.invoicer = invoicer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}   
