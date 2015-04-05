package org.example.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Invoice extends AbstractEntity {

    public static enum Status {

        InProgress, Sent, Received
    }

    @NotNull
    private Integer invoiceNumber;

    @ManyToOne
    private Contact to;

    @ManyToOne
    private Invoicer invoicer;

    @ManyToOne
    private User lastEditor;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEdited;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<InvoiceRow> invoiceRows = new ArrayList<>();

    public User getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(User lastEditor) {
        this.lastEditor = lastEditor;
        lastEdited = new Date();
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<InvoiceRow> getInvoiceRows() {
        return invoiceRows;
    }

    public void setInvoiceRows(List<InvoiceRow> invoiceRows) {
        this.invoiceRows = invoiceRows;
    }

    @Transient
    public Double getTotal() {
        double sum = 0;
        List<InvoiceRow> invoiceRows1 = getInvoiceRows();
        for (InvoiceRow r : invoiceRows1) {
            sum += r.getQuantity() * r.getPrice();
        }
        return sum;
    }

    @Transient
    public String getReference() {
        int invoicenr = getInvoiceNumber();
        int checksum = 0;
        int counter = 0;
        int[] multipliers = {7, 3, 1};
        while (invoicenr > 0) {
            int d = invoicenr % 10;
            invoicenr = invoicenr / 10;
            checksum += d * multipliers[counter % multipliers.length];
            counter++;
        }
        checksum = checksum % 10;
        checksum = 10 - checksum;
        checksum = checksum % 10;
        String ref = "" + getInvoiceNumber() + checksum;
        return ref;
    }

    @XmlTransient
    public Invoicer getInvoicer() {
        return invoicer;
    }

    public void setInvoicer(Invoicer invoicer) {
        this.invoicer = invoicer;
    }

    public Contact getTo() {
        return to;
    }

    public void setTo(Contact to) {
        this.to = to;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
