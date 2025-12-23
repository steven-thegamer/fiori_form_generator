# fiori_form_generator

## Overview

**fiori_form_generator** is a full-stack SAP Fiori application generator built with SAP CAP (Cloud Application Programming Model), Java (Spring Boot), and SAPUI5. It enables users to design and generate Fiori form UIs dynamically, storing form definitions in a database and producing XML views for SAPUI5 applications.

---

## Architecture

- **Backend:**  
  - Java (Spring Boot) service ([srv/src/main/java/com/steven/fiori_form_generator](srv/src/main/java/com/steven/fiori_form_generator))
  - CAP service definitions ([srv/services.cds](srv/services.cds)), database schema ([db/schema.cds](db/schema.cds)), and data ([db/data/](db/data/))
  - Generates XML views based on form definitions and entries

- **Frontend:**  
  - SAPUI5 Fiori applications in [app/form](app/form) and [app/demo](app/demo)
  - UI5 List Report and Basic templates, supporting OData V4
  - Portal configuration for Fiori Launchpad ([app/portal/portal-site](app/portal/portal-site))

---

## Main Features

- **Dynamic Form Generation:**  
  Users can define forms, entries, and buttons in the UI, which are persisted in the database and used to generate SAPUI5 XML views.

- **OData V4 Service:**  
  Exposes form, entry, and button entities via OData for UI consumption.

- **Fiori Launchpad Integration:**  
  Portal site configuration for easy deployment and access.

- **Multi-language Support:**  
  i18n resource bundles for UI texts.

---

## Modules

- **srv/**  
  - Java Spring Boot application for CAP service logic and XML view generation ([FormGeneratorService.java](srv/src/main/java/com/steven/fiori_form_generator/FormGeneratorService.java), [FormGeneratorView.java](srv/src/main/java/com/steven/fiori_form_generator/FormGeneratorView.java))
  - Service event handlers for form creation and validation

- **db/**  
  - CDS schema for forms, entries, buttons, and supporting data
  - Sample CSV data for icons, entry types, and alignments

- **app/form/**  
  - Main Fiori app for form management and generation
  - UI5 List Report template, OData V4, i18n, and test suite

- **app/demo/**  
  - Demo Fiori app for displaying generated forms

- **app/portal/**  
  - Portal site configuration for Fiori Launchpad

---

## Setup & Prerequisites

1. **Node.js LTS** and NPM (see [nodejs.org](https://nodejs.org))
2. **Java 21** (see [pom.xml](pom.xml))
3. **Maven** 3.6.3+
4. **SAP CAP CLI** (optional, for local development)

---

## Build & Run

1. **Install dependencies:**
   ```sh
   npm install
   ```

2. **Start the backend (CAP + Spring Boot):**
   ```sh
   mvn spring-boot:run
   ```

3. **Access the Fiori apps:**
   - Form Generator: [http://localhost:8080/form/webapp/index.html](http://localhost:8080/form/webapp/index.html)
   - Demo App: [http://localhost:8080/demo/webapp/index.html](http://localhost:8080/demo/webapp/index.html)

---

## How to Use

1. **Start the Backend Service**
   - Run the following command in the project root to start the CAP Java backend:
     ```
     mvn spring-boot:run
     ```

2. **Access the Fiori Form Generator App**
   - Open your browser and go to:  
     [http://localhost:8080/form/webapp/index.html](http://localhost:8080/form/webapp/index.html)

3. **Create a New Form**
   - In the app, use the UI to define a new form:
     - Set the number of columns, form title, alignment, and file name (no spaces).
     - Add entries (fields) to the form, specifying their type, column, row, and whether they are editable.
     - Add buttons and select icons for them.

4. **Generate the Form**
   - Use the "Create New Form" action in the UI.  
   - The backend will validate your input and generate an XML view for your form.

5. **View the Generated Form**
   - The generated XML view is saved in the `ViewResult` folder and also updates the demo app.
   - To preview the generated form, open:  
     [http://localhost:8080/demo/webapp/index.html](http://localhost:8080/demo/webapp/index.html)

6. **Modify or Regenerate**
   - You can edit your form definition and regenerate as needed. Each generation overwrites the previous XML view.

---

**Note:**  
- Make sure Node.js, Java, and Maven are installed as described in the prerequisites.
- For advanced deployment or integration with SAP Fiori Launchpad, use the portal configuration in the `app/portal` folder.

## Key Entities

- **Form:**  
  - Columns, title, alignment, file name, entries, and buttons

- **Entry:**  
  - Name, type (String, Number, Date, Time, SingleChar), column, row, editable

- **FormButton:**  
  - Name, icon, order

- **ButtonIcon:**  
  - SAP icon URI

---

## How Form Generation Works

- User defines a form and its entries/buttons in the UI.
- On "CreateNewForm" action, the backend validates the form and generates an XML view ([FormGeneratorView.java](srv/src/main/java/com/steven/fiori_form_generator/FormGeneratorView.java)).
- The XML is saved to [ViewResult/](ViewResult/) and [app/demo/webapp/view/DemoView.view.xml](app/demo/webapp/view/DemoView.view.xml).
- The demo app displays the generated form.

---

## Testing

- QUnit and OPA5 integration tests in [app/form/webapp/test/](app/form/webapp/test/)

---

## Project Structure

- [pom.xml](pom.xml): Maven multi-module project
- [package.json](package.json): Node.js dependencies for CAP
- [srv/](srv/): Java Spring Boot CAP service
- [db/](db/): CDS schema and data
- [app/form/](app/form/): Main Fiori app
- [app/demo/](app/demo/): Demo Fiori app
- [app/portal/](app/portal/): Portal site config
- [ViewResult/](ViewResult/): Generated XML views

---

## Dependencies

- **Backend:**  
  - Spring Boot, SAP CAP Java SDK, SQLite JDBC

- **Frontend:**  
  - SAPUI5, @ui5/cli, @sap/ux-ui5-tooling

---

## License

See [package.json](package.json) for license information.

---

## Authors

- Steven (as per Java package naming)
- Generated with SAP Fiori Application Generator

---

## References

- [SAP CAP Documentation](https://cap.cloud.sap/docs/)
- [SAPUI5 Documentation](https://sapui5.hana.ondemand.com/)