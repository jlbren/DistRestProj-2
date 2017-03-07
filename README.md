#A Simple Client for Interacting with a Pokedex SQLite Database 

##Dependencies 
+ [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
+ [SQLite 3](https://www.sqlite.org/)
+ [Maven](http://maven.apache.org/index.html)

##Quick Start 
+ Download/extract or clone project from repository 
+ Open terminal in project root directory and run command `mvn install`  
+ All jar dependencies will be downloaded by maven, and unit tests executed  
+ Successful build and test confirmation indicates successful installation

###Database Schema and Sample Values
*Pokemon*  

| Id | Name      | Description         | Nature  |
|----|-----------|---------------------|---------|
| 1  | Bulbasaur | Is it a plant or..? | Relaxed |


*Trainer*

| Id | Name | Badges |
|----|------|--------|
| 1  | Ash  | 8      |


*Partners* 

| Id | trainerId | pkmnId | Location     |
|----|-----------|--------|--------------|
| 1  | 1         | 1      | Victory Road |
