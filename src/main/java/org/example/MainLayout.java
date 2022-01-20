package org.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;

import java.util.Optional;

public class MainLayout extends org.vaadin.firitin.appframework.MainLayout {

    public static MainLayout get() {
        Optional<Component> first = UI.getCurrent().getChildren().findFirst();
        return (MainLayout) first.get();
    }

    public MainLayout() {

    }

    @Override
    protected String getDrawerHeader() {
        return "Invoices";
    }
}
