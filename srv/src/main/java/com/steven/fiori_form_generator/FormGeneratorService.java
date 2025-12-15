package com.steven.fiori_form_generator;

import java.io.File;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.sap.cds.ql.Select;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.messages.Messages;

import cds.gen.mainservice.Entries;
import cds.gen.mainservice.Entries_;
import cds.gen.mainservice.FormButtons;
import cds.gen.mainservice.FormButtons_;
import cds.gen.mainservice.Forms;
import cds.gen.mainservice.FormsCreateNewFormContext;
import cds.gen.mainservice.Forms_;
import cds.gen.mainservice.MainService;
import cds.gen.mainservice.MainService_;

@Component
@ServiceName(MainService_.CDS_NAME)
public class FormGeneratorService implements EventHandler {

    @Autowired
    private MainService maindb;

    @Autowired
    Messages messages; // CAP Messages API

    @Before(event = FormsCreateNewFormContext.CDS_NAME, entity = Forms_.CDS_NAME)
    public void beforeGenerateForm(FormsCreateNewFormContext context) {
        Forms form = maindb.run(context.getCqn()).single(Forms.class);
        if (form.getIsActiveEntity() == false)
            throw new ServiceException("Draft cannot be used to generate xml view!");
        if (form.getAmountOfColumns() <= 0)
            throw new ServiceException("Columns must be greater than 0!");
        if (form.getFileName().contains(" "))
            throw new ServiceException("File name must not have spaces!");
        List<Entries> allEntries = maindb
                .run(Select.from(Entries_.CDS_NAME).where(t -> t.get(Entries_.FORM_PARENT_ID).eq(form.getId())))
                .listOf(Entries.class);
        allEntries.forEach(entry -> {
            if (entry.getEntryColumn() <= 0 || entry.getEntryColumn() > form.getAmountOfColumns())
                throw new ServiceException("Invalid column value for entry " + entry.getEntryName());
        });
    }

    @On(event = FormsCreateNewFormContext.CDS_NAME, entity = Forms_.CDS_NAME)
    public String onGenerateForm(FormsCreateNewFormContext context) {
        // Get the entry file
        Forms form = maindb.run(context.getCqn()).single(Forms.class);
        // Get all entries
        // For some reason, this doesn't work. So temporarily, we're changing methods to
        // get the entries.
        // List<Entries> allEntries = form.getEntryChildren();
        List<Entries> allEntries = maindb.run(Select.from(Entries_.CDS_NAME)
                .where(t -> t.get(Entries_.FORM_PARENT_ID).eq(form.getId())).orderBy(t -> t.get("entry_row").asc()))
                .listOf(Entries.class);
        List<FormButtons> allButtons = maindb.run(Select.from(FormButtons_.CDS_NAME)
                .where(t -> t.get(FormButtons_.FORM_PARENT_ID).eq(form.getId())).orderBy(t -> t.get("order").asc()))
                .listOf(FormButtons.class);
        String formName = form.getFileName();
        try {
            Document xmlFile = FormGeneratorView.generateView(form, allEntries, allButtons);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xmlFile);
            StreamResult result = new StreamResult(new File("ViewResult/" + formName + ".xml"));
            StreamResult demoView = new StreamResult(new File("app/demo/webapp/view/DemoView.view.xml"));
            transformer.transform(source, result);
            transformer.transform(source, demoView);
            messages.success("Successfully created an xml file!");

        } catch (ParserConfigurationException | TransformerException e) {
            throw new ServiceException(e.getMessage());
        }
        return "";
    }
}
