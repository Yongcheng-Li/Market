# README
This is the group project for CS2063.


## notifications
* We do not allow changes in usernames, but we do offer password changing functionality
* There is a functionality to make sure a meeting is only confirmed after the meeting has passed.
For the TA's convenience we have commented out the code responsible for that in EditTradeActivity to
make testing the program easier. If desired, the code can be un-commented so that it checks the date.
* Because there it is very hard to oversee "item security" with online meetings we made it so that a
trader cannot have a temporary online meeting- All online trades are permanent.
* We keep controller and presenter together to avoid some unnecessary code smells.

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

## UndoActions
### Undo propose a Trade 
We thought this was a unreasonable thing to allow Traders to undo because they should really only be able to undo
a trade proposal before both users have agreed to a meeting to prevent abuse of the meeting system.
In that case we have provided the option to decline a trade for both users so there is no reason to undo.

### Undo Decline a trade 
We thought this was unreasonable as well because after a trade is declined there is no guarantee that either trader will still
be in possession of their items, so a declination should not be able to be undone. If the item is available, they can propose again.

### Undo edit a trade   
This is a reasonable thing to do and we allow a trader to undo an edit as long as both traders have not agreed to a meeting
(this is to prevent abuse of the meeting system)

### Undo agreeing on trade
We only undo a trade agreement if the other user has not agreed to the trade yet. This is so a user doesn't agree and then
undo an agreement after being unable to show up to the meeting, which would flag them.

### Undo adding/removing item from wishlist
Unreasonable to undo this as the user can edit the wishlist however they want. No need for an admin to edit it for them.

### Undo proposing Items to be added to wantToLend list 
Unreasonable as 
* The admin can just reject the item
* If the item is approved the user can just remove it from their wantToLend list.

### Undo removing Items from wantToLend list
This is a reasonable thing to undo so we have added that functionality.
Note that we don’t let inactive or frozen users undo a removed item as a
frozen user is frozen and will not be trading with their restored item,
and an inactive user would also not be trading with the restored item. So a
trader must be unfrozen or made active so they can undo this change.

### Undo requesting to unfreeze
Unreasonable as it doesn’t make sense for an admin to undo this as they can just reject users,
also doesn’t make sense for the user to undo their request as the assumption is users
 would want to continue using the system. No harm is done in being unfrozen.

### Undo activating/deactivating an account
Unreasonable as they can do those options whenever they want to.

### Undo changing password 
Unreasonable as an administrator should not edit a user’s security directly with just an "email". If needed the user
can change their password after logging in.

### Undo confirming a trade 
This is a reasonable thing to allow users to undo so we have implemented that functionality.
However this is not possible to do once both users confirm as the trade is completed and no further
edits should be made, as edits past this date can be used to abuse the system (making a trade incomplete
and thus potentially flagging a user).
