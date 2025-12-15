package com.steven.fiori_form_generator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cds.gen.mainservice.Entries;
import cds.gen.mainservice.FormButtons;
import cds.gen.mainservice.Forms;

public class FormGeneratorView {

    public static Document generateView(Forms form, List<Entries> allEntries, List<FormButtons> allButtons)
            throws ParserConfigurationException {
        Document document = createDocument();
        Element root = createRootElement(document);
        document.appendChild(root);

        Element page = createPageElement(document, form);
        root.appendChild(page);

        int totalColumns = form.getAmountOfColumns();
        float equalWidth = 100f / totalColumns;

        Element mainHBox = createMainHBox(document);
        page.appendChild(mainHBox);

        List<Element> vBoxes = createVBoxes(document, mainHBox, totalColumns, equalWidth);

        addEntriesToVBoxes(document, vBoxes, allEntries);
        addButtonsToPage(document, page, allButtons);

        return document;
    }

    private static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    private static Element createRootElement(Document document) {
        Element root = document.createElement("mvc:View");
        root.setAttribute("controllerName", "demo.controller.DemoView");
        root.setAttribute("xmlns:core", "sap.ui.core");
        root.setAttribute("xmlns:mvc", "sap.ui.core.mvc");
        root.setAttribute("xmlns", "sap.m");
        root.setAttribute("xmlns:macros", "sap.fe.macros");
        root.setAttribute("xmlns:html", "http://www.w3.org/1999/xhtml");
        return root;
    }

    private static Element createPageElement(Document document, Forms form) {
        Element page = document.createElement("Page");
        page.setAttribute("id", "Main");
        page.setAttribute("title", form.getFormTitle());
        switch (form.getFormAlignmentId()) {
            case "CE" -> page.setAttribute("titleAlignment", "Center");
            case "LE" -> page.setAttribute("titleAlignment", "Left");
            case "RI" -> page.setAttribute("titleAlignment", "Right");
        }
        return page;
    }

    private static Element createMainHBox(Document document) {
        Element mainHBox = document.createElement("HBox");
        mainHBox.setAttribute("id", "idMainHBox");
        mainHBox.setAttribute("class", "sapUiSmallMargin");
        mainHBox.setAttribute("alignItems", "Start");
        mainHBox.setAttribute("alignContent", "Start");
        return mainHBox;
    }

    private static List<Element> createVBoxes(Document document, Element mainHBox, int totalColumns, float equalWidth) {
        List<Element> vBoxes = new ArrayList<>();
        for (int i = 0; i < totalColumns; i++) {
            Element vBox = document.createElement("VBox");
            vBox.setAttribute("id", "id" + i + "VBox");
            vBox.setAttribute("class", "sapUiSmallMargin");
            vBox.setAttribute("alignItems", "Start");
            vBox.setAttribute("alignContent", "Start");
            vBox.setAttribute("width", equalWidth + "%");
            mainHBox.appendChild(vBox);
            vBoxes.add(vBox);
        }
        return vBoxes;
    }

    private static void addEntriesToVBoxes(Document document, List<Element> vBoxes, List<Entries> allEntries) {
        for (Entries entry : allEntries) {
            String entryNameNoSpaces = entry.getEntryName().replace(" ", "");
            Element hBox = document.createElement("HBox");
            hBox.setAttribute("id", "id" + entryNameNoSpaces + "HBox");
            hBox.setAttribute("alignItems", "Center");
            vBoxes.get(entry.getEntryColumn() - 1).appendChild(hBox);

            Element text = document.createElement("Text");
            text.setAttribute("id", "id" + entryNameNoSpaces + "Text");
            text.setAttribute("text", entry.getEntryName());
            text.setAttribute("class", "sapUiSmallMargin");
            hBox.appendChild(text);

            Element input = document.createElement("Input");
            input.setAttribute("id", "id" + entryNameNoSpaces + "Input");
            input.setAttribute("class", "sapUiSmallMargin");
            if (!entry.getEntryEditable()) input.setAttribute("editable", "false");
            setInputType(input, entry.getEntryTypeId());
            hBox.appendChild(input);
        }
    }

    private static void setInputType(Element input, String entryTypeId) {
        switch (entryTypeId) {
            case "NU" -> input.setAttribute("type", "Number");
            case "DA" -> input.setAttribute("type", "Date");
            case "TI" -> input.setAttribute("type", "Time");
            case "SC" -> input.setAttribute("maxLength", "1");
        }
    }

    private static void addButtonsToPage(Document document, Element page, List<FormButtons> allButtons) {
        Element buttonsHBox = document.createElement("HBox");
        buttonsHBox.setAttribute("id", "idButtonsHBox");
        buttonsHBox.setAttribute("class", "sapUiSmallMargin");
        buttonsHBox.setAttribute("width", "100%");
        buttonsHBox.setAttribute("justifyContent", "Center");
        page.appendChild(buttonsHBox);

        for (FormButtons button : allButtons) {
            String buttonNameNoSpaces = button.getButtonName().replace(" ", "");
            Element btn = document.createElement("Button");
            btn.setAttribute("id", "id" + buttonNameNoSpaces + "Button");
            btn.setAttribute("text", button.getButtonName());
            btn.setAttribute("icon", button.getButtonIconIcon());
            btn.setAttribute("press", "onButton" + buttonNameNoSpaces + "Press");
            btn.setAttribute("class", "sapUiSmallMargin");
            buttonsHBox.appendChild(btn);
        }
    }
}
