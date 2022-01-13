
package org.example;

import javax.annotation.PostConstruct;
import org.vaadin.firitin.components.RichText;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

//@ViewMenuItem(icon = FontAwesome.LIFE_BOUY, order = ViewMenuItem.BEGINNING)
public class AboutView extends VVerticalLayout /*implements View*/ {
    
    @PostConstruct
    void init() {
        add(new RichText().withMarkDownResource("/about.md"));
    }

}
