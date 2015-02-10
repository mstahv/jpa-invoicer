package org.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.example.backend.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

@CDIView
@ViewMenuItem(icon = FontAwesome.LIFE_BOUY, order = ViewMenuItem.END)
public class MyAccount extends MVerticalLayout implements View {

    @Inject
    UserSession session;

    @PostConstruct
    void init() {

        // TODO editor
        add(new RichText().withMarkDown("# email: " + session.getUser().getEmail()));

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
