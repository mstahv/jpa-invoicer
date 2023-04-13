package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({})
@XmlRootElement
public class Invoicer extends AbstractEntity {
    
    @OneToMany(mappedBy = "invoicer", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    
    @Lob
    @Basic(fetch=FetchType.EAGER)
    private byte[] template;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 40, message = "name must be longer than 3 and less than 40 characters")
    private String name;

    @NotNull(message = "Address is required")
    @Size(min = 1, max = 60, message = "Max 60 characters")
    private String address;

    @NotNull(message = "Phone is required")
    @Size(min = 1, max = 60, message = "Max 60 characters")
    private String phone;

    @NotNull(message = "Email is required")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
    @Size(min = 3, max = 40, message = "name must be longer than 3 and less than 40 characters")
    private String email;

    @NotNull
    private String bankAccount;

    private int nextInvoiceNumber = 1;

    @OneToMany(mappedBy = "invoicer")
    private List<Invoice> invoices = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<User> administrators = new ArrayList<>();
    
    @XmlTransient
    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public int getNextInvoiceNumber() {
        return nextInvoiceNumber;
    }

    public void setNextInvoiceNumber(int nextInvoiceNumber) {
        this.nextInvoiceNumber = nextInvoiceNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<User> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getAndIcrementNextInvoiceNumber() {
        return nextInvoiceNumber++;
    }

}
