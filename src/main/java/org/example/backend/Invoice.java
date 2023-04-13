package org.example.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;

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
    private LocalDate lastEdited;

    @NotNull
    private LocalDate invoiceDate;

    @NotNull
    private LocalDate dueDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<InvoiceRow> invoiceRows = new ArrayList<>();

    public User getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(User lastEditor) {
        this.lastEditor = lastEditor;
        lastEdited = LocalDate.now();
    }

    public LocalDate getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDate lastEdited) {
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

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}
