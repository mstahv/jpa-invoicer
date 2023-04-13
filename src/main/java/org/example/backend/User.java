package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="APP_USER")
@NamedQueries({})
public class User extends AbstractEntity {

    @NotNull(message = "Email is required")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
    private String email;

    @ManyToMany(mappedBy = "administrators", fetch = FetchType.LAZY)
    private List<Invoicer> administrates = new ArrayList<>();

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Invoicer> getAdministrates() {
        return administrates;
    }

    public void setAdministrates(List<Invoicer> administrates) {
        this.administrates = administrates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }

}
