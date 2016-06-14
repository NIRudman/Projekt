# Projekt

# Instructions on how to build project
## Windows
1. Download or clone https://github.com/NIRudman/Projeckt.git
2. Start MySql server and create database "user", import the "user.sql" file in Projekt folder to database.
3. Install a WildFly server.
4. Install jdbc driver to a Wildfly server.
  4.1. Create a admin user for through the guide by starting the "add-user.bat" file in the bin folder in your wildfly server location.
  (My Example:"G:\Java EE\wildfly-10.0.0.Final\bin\add-user.bat)
  4.2. Login to server with the created user on http://localhost:9990/ in your web browser.
  4.3. Click the "Start" button on Deploy an Application in Deployments.
  4.4. Click "Add" then Upload a new deployment, Find your MySQL jdbc driver in your computer
  (There is one in Libs in the project folder) and press next, have enable crossed in and press finish.
  4.5. Go to home and press "Start" in Create a Datasource. Select Subsystems and Datasources, 
  Select the Non-XA type and press the button "Add".
  4.6. Select MySQL Datasource and press next. Then next again. Press the detected driver flik and select the driver you deployed before.
  4.7. In "Connection URL" change to "jdbc:mysql://localhost:3306/user". Change username to root and have the password empty.
  4.8. Try the connection and press finished.
5. Run project on the WildFly server.
