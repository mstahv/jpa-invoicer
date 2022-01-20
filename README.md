# Invoicing example application

Note: This project is currently beeing upgraded to Vaadin 22. The old Vaadin 7 version is available in a [separate branch](https://github.com/mstahv/jpa-invoicer/tree/vaadin-7). This version misses certain things, like authentication has not been upgraded/tested.

Simple app to collaboratively create invoices. This application is built as an example application for Vaadin UI framework and various Java EE technologies. Still it should be perfectly usable as such in a real use.

Features:

 * Based on Java EE stack, persistency with JPA to RDBMS, UI built with Vaadin
 * Multiple organisations that can send invoices, shareable with other users
 * Customer registry, used fluently via invoice view
 * PDF/ODT export for invoices, configurable ODT template
 * User backups via XML export
* Google OAuth2 based login (pending upgrad from Vaadin 7 version)

This is a suitable basis for small to medium sized apps. For larger applications,
consider using MVP to structure your UI code. See e.g. [this example 
application](https://github.com/peterl1084/cdiexample).

## Quickstart

Start the application with an embedded wildfly:
```
mvn clean package wildfly:run
```

After startup the application is available here: [http://localhost:8080/invoicer](http://localhost:8080/invoicer)
