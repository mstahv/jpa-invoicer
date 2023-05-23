package org.example;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import jakarta.annotation.PostConstruct;

@MenuItem(icon = VaadinIcon.LIFEBUOY, order = MenuItem.BEGINNING)
@Route(value = "", layout = MainLayout.class)
@CdiComponent
public class AboutView extends VVerticalLayout {
    
    @PostConstruct
    void init() {
        add(new RichText().withMarkDownResource("/about.md"));
    }

}
