# Project Structure Guide - Team Caribou


## Directory Overview

- **src/main/java: Contains Java source files for the project, organized into subdirectories:
  - controller: Includes Java servlets and utilities handling the business logic.
    - `AppContextListener.java`: Initializes context and settings on startup.
    - `DatabaseController.java`: Manages common database operations.
    - Various servlets such as `CancelReservation.java`, `CheckAvailability.java`, handling specific functionalities across the site.
  - model: Contains Java classes related to the data model.
    - `DatabaseModel.java`: Encapsulates database access and manipulation.

- **Web App Libraries: Libraries provided by the web application framework.

- **Referenced Libraries: External libraries referenced in the project which include:
  - Server Runtime Tomcat 8.5: The Apache Tomcat server used for deploying the project.

- **build: Contains compiled output files.

- **src/main/webapp: Root directory for web application content.
  - assets: Static assets like CSS, JS, and font files.
  - images: Contains image files used in the web application.
  - META-INF: Includes metadata for the application.
  - WEB-INF: Contains web application configuration and libraries.
    - lib: Library files necessary for the project.
    - `web.xml`: XML configuration file for web application.

## Important Files

- `web.xml`: Defines servlet mappings and configuration.
- `DatabaseModel.java`: Core model handling all database interactions.
- `AppContextListener.java`: Used for setting up application-wide initial data.

## Deployment and Running

- Ensure that all servlet JARs are included in the `WEB-INF/lib` directory.
- The project uses Tomcat 8.5 as the runtime server. Set this server in the Eclipse server runtime configuration to run the project.


