# Caribou Inn - Project Setup Guide

## Prerequisites
-Eclipse IDE for Java EE Developers: This setup requires Eclipse IDE for Java EE Developers. Download it from "https://www.eclipse.org/downloads/packages/"

## Step 1: Clone the Repository
1. Open Eclipse IDE.
2. Go to `File > Import...`.
3. Choose `Git > Projects from Git`, then click `Next`.
4. Select `Clone URI` and click `Next`.
5. Enter the Repository UR: "https://github.com/PhoebeEvans/CS_472_Group_Project.git"
6. Choose the Master branch and click `Next`.
7. Specify the local storage location for the repository and click `Next`.
8. Select `Import as general project`, then click `Finish`.

## Step 2: Configure the Project
1. Right-click the project in the Project Explorer.
2. Select `Configure > JSP/Servlet project`

## Step 3: Setup Tomcat Server 8.5
1. Download Apache Tomcat 8.5 from https://tomcat.apache.org/download-80.cgi
2. In Eclipse, go to `Servers` tab at the bottom view.
3. Right-click in the `Servers` pane and select `New > Server`.
4. Select `Tomcat v8.5 Server` from the server type list and click `Next`.
5. Browse to the Tomcat installation directory (where you extracted the downloaded Tomcat zip file), then click `Finish`.

## Step 4: Add Servlet JARs
1. Extract the servlet JAR files from the zip located in the repository.
2. Right-click on the project in the Project Explorer and choose `Properties`.
3. Go to `Java Build Path > Libraries` and click `Add External JARs...`.
4. Select the extracted JAR files and click `OK`.

## Step 5: Configure Deployment Assembly
1. In the project properties, navigate to `Deployment Assembly`.
2. Click `Add...`, select `Java Build Path Entries`, and choose `Next`.
3. Select the `src/main/webapp/WEB-INF/lib` folder and the JARs you added, then click `Finish`.

## Step 6: Set Project Facets
1. Right-click the project and select `Properties`.
2. Go to `Project Facets`.
3. Check `Dynamic Web Module` and `Java`, and uncheck any others not used by the project.
4. Click `Apply and Close`.

## Step 7: Run the Project
1. Right-click the project in the `Servers` tab.
2. Choose `Run As > Run on Server`.
3. Select the configured Tomcat Server and click `Finish`.
4. Your application should now be accessible via the URL displayed in the Eclipse console.
