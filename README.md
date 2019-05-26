
# Souvenir Shop #

### How do I run an application? ###

* To get started you need to have java 1.8 installed, as well as Maven 
* Also you need PostgreSQL server up and running on 5432 port
  with database **souvenir_shop**, user **postgres** and password **root** 
  (if you need to specify other connection settings,
  please update src/main/resources/application.properties)
* Next you need to execute following commands in command line:
```
git clone https://github.com/Naprill/souvenir-shop
cd souvenir-shop
mvn package
cd target
java -jar souvenir-shop.jar
```