# README
Group project for CS2063 Intro to mob dev.


## notifications
* We do not allow changes in usernames, but we do offer password changing functionality
* There is a functionality to make sure a meeting is only confirmed after the meeting has passed.
* Because there it is very hard to oversee "item security" with online meetings we made it so that a
trader cannot have a temporary online meeting- All online trades are permanent.

## DesignPatterns
### Dependency Injection
Many of our Use Case Classes take in a List of objects as the parameter 
rather than create that object directly in the class.
### Factory Method
We create a class called DialogFactory generating Dialogs for many of our Activities.
This reduces duplicate codes and separates the construction of dialogs from Activities.
### Observer 
* We used Button onClick Listeners that were built into Android Studio. This allowed us to
launch Activities from an entirely separate Activity.
* We created our own form of the Observer design pattern with regard to the abstract 
BundleActivity class that fit with our Android program design. We update the Use Case Classes
whenever we press the back button in order to update the bundle inside the BundleActivity class.






