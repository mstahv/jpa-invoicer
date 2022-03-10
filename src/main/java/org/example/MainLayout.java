package org.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Span;
import org.example.backend.UserSession;

import javax.inject.Inject;
import java.util.Optional;

public class MainLayout extends org.vaadin.firitin.appframework.MainLayout {

    @Inject
    UserSession session;

    public static MainLayout get() {
        Optional<Component> first = UI.getCurrent().getChildren().findFirst();
        return (MainLayout) first.get();
    }

    public MainLayout() {

    }

    @Override
    protected Footer createFooter() {
        Footer footer = super.createFooter();
        if(session.isLoggedIn()) {
            Avatar avatar = new Avatar();
            avatar.setImage(session.getImage());
            avatar.setName(session.getUser().getEmail());
            footer.add(avatar);

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Logout", e -> {
                session.logout();
            });

            Span name = new Span(session.getUser().getEmail());
            footer.add(name);

        }
        return footer;
    }

    @Override
    protected String getDrawerHeader() {
        return "Invoices";
    }

}
