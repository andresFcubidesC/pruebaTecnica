# Use the official Tomcat image
FROM tomcat:9.0

# Remove the default web applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file into the webapps directory of Tomcat
COPY target/labortest.war /usr/local/tomcat/webapps/labortest.war

# Expose the port Tomcat runs on
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]