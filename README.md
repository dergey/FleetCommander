# README #

Fleet Commander is a turn-based strategy game inspired by "Strategic Commander" for Palm OS from Zindaware.

## Which technologies are used ##

* The game rules are coded in Java
* The AngularJS front-end talks to the server via the REST interface
* Currently there is no persistence layer
* Spring Boot

## How to get it up and running ##

### Prerequisites ###
* Java SDK 8
* Maven 3

### Working with the project ###
* Clone the repository
* **mvn verify** to run Unit & integration tests on the back-end
* **mvn package** will produce a runnuble .jar file

## TODOs
* Allow building research facilities instead of factories, that
    * advance the power of your ships in combat, and
    * increase ship travelling speed
* Add planet classes, that determine how effective local factories are on that planet
* Let planets have different amounts of facilities
* Offer different levels of AI enemies
