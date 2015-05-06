package org.example;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.example.backend.service.InvoiceFacade;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.viritin.button.DownloadButton;

/**
 *
 * @author Matti Tahvonen
 */
public class TemplateField extends UploadField {

    public TemplateField() {
        super(StorageMode.MEMORY);
        setFieldType(FieldType.BYTE_ARRAY);
        setCaption("Custom template");
        setButtonCaption("Choose new");
        // Only allow ODT files, smaller than 4MB
        setAcceptFilter("application/vnd.oasis.opendocument.text");
        setMaxFileSize(4000000);
    }

    @Override
    protected Component createDisplayComponent() {
        return new CssLayout();
    }
    
    @Override
    protected void updateDisplay() {
        if(display == null) {
            display = createDisplayComponent();
        }
        if (display.getParent() == null) {
            getRootLayout().addComponent(display);
        }
        CssLayout dspl = (CssLayout) display;
        dspl.removeAllComponents();
        byte[] value = (byte[]) getValue();
        if (value == null || value.length == 0) {
            dspl.addComponent(new Label("not set"));
            // Allow user to download the default template for customization
            dspl.addComponents(new DownloadButton(stream -> {
                try {
                    IOUtils.copy(InvoiceFacade.getDefaultTemplate(), stream);
                } catch (IOException ex) {
                    Logger.getLogger(InvoicerForm.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }).setFileName("template.odt").withCaption("download default"));
        } else {
            // Allow user to download the template for customization
            dspl.addComponents(new DownloadButton(stream -> {
                try {
                    IOUtils.copy(getContentAsStream(), stream);
                } catch (IOException ex) {
                    Logger.getLogger(InvoicerForm.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }).setFileName("template.odt").withCaption("download"));
        }
    }
}
