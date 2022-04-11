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



## Test cases / test plans run
- Change passwords.
- Input home city address used for delivery later.

Initiate an online trade (requires jump back and forth between accounts)
- Sign up with 2 user accounts
- Log in and post an item(s)
- Log in to admin account to approve the items
- Log back to one user account, browse the item posted and initiate a trade.
- Log in to another user account, accept the trade
- Edit the trade info (Change dates etc.) and agree on both accounts
- Create a user account for the driver and deliver the trade just been agreed.
- Log in the user accounts and click on confirm when they receive it.

Initiate an in-person trade
- Similar with the processed above but choose the in-person option this time.
- Input the time and location where you want to trade.
- After both agreed, the trade can be made and click on confirm when the trade is done.
- View user profile to see the past trading information.

Feel free to explore more:) 



