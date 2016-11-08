####File Uploader

This project meant to be an example of setup for basic environment with Java + Spring Boot
with Angular JS. In this example it basically makes a file upload, letting the user see
file details and download the file.

##Backend
- Backend is implemented in spring boot using default spring boot workflow ( Controllers (Rest) -> Services -> Repository(JPA))
- Since there is no much logic the unit tests goes on service classes where there is the main logic.
- swagger is used for testing the api via browser ( localhost:8080/swagger-ui.html )
- It uses H2 in memory database. File data is saved as Blob. Data can be seen in /console url ( check DatabaseConfig )
- Table is created by JPA create ( no liquibase/flyway for simplicity. No initial data. )

##Front-end
- Front-end has a very simple infrastructure using angularjs with common bootstrap layouts
- For simplicity (but with some regrets after using more js library than expected) no compilers/dependency managers/css-pre-processors were added (Gulp.js, Bowser.js, SASS, etc )
- Uses default AngularJs design. One file for each controller, application and service.
- Mostly JS libs were referred over the web. So needs to be connected over the internet. 
- No fancy design, but used some classes of bootstrap ( Not my expertise )

##Usage
- Normal Spring running. Do 'mvn clean install' then 'cd target' then 'java -jar file-uploader-1.0-SNAPSHOT.jar'
- Go to localhost:8080 ( index.html will be the only page )
- Select a file, add some description. ( Both obligatory )
- Click upload and check a green confirmation
- Click Load whenever want to check loaded files.
- In the list you can check details and Download the file.

## Future work
- Fixes UI bugs that eventually show up get ideas in how to make a better layout.
- Add proper UI infrastructure with sass, gulp, bower and routing..
- Unit test for exception cases
- Serializer for domain classes since blob is not being returned to frontend as is
- Add a upload progress tab
- change hardcoded urls to apis.

