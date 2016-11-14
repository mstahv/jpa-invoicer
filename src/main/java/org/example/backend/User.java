package org.example.backend;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

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
