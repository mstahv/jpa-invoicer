
package org.example;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.appframework.MenuItem;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import javax.annotation.PostConstruct;

@MenuItem(icon = VaadinIcon.LIFEBUOY, order = MenuItem.BEGINNING)
@Route(value = "", layout = MainLayout.class)
public class AboutView extends VVerticalLayout {
    
    @PostConstruct
    void init() {
        add(new RichText().withMarkDownResource("/about.md"));
    }

}
