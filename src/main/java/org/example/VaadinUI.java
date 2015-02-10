package org.example;

import org.example.auth.LoginWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.Extension;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.example.backend.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuUI;

@CDIUI("")
@Theme("valo")
@Title("Invoicer")
public class VaadinUI extends ViewMenuUI {

    @Inject
    UserSession userSession;
    
    @Inject
    Instance<LoginWindow> loginWindow;

    @Override
    protected void init(VaadinRequest request) {
        super.init(request);
        if (!userSession.isLoggedIn()) {
            getContent().setVisible(false);
            addWindow(loginWindow.get());
        }
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        workaroundForFirefoxIssue(initial);
        super.beforeClientResponse(initial);
    }

    protected void workaroundForFirefoxIssue(boolean initial) {
        if (initial && Page.getCurrent().getWebBrowser().getBrowserApplication().
                contains("Firefox")) {
            // Responsive, FF, cross site is currently broken :-(
            Extension r = null;
            for (Extension ext : getExtensions()) {
                if (ext instanceof Responsive) {
                    r = ext;
                }
            }
            removeExtension(r);
        }
    }

}
