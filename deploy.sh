#!bin/sh

git pull
mvn clean package

sudo /etc/init.d/tomcat7 stop
sudo cp ./highlightr-web/target/highlightr-web.war /var/lib/tomcat7/webapps/ROOT.war
sudo rm -rf /var/lib/tomcat7/webapps/ROOT/
sudo /etc/init.d/tomcat7 start