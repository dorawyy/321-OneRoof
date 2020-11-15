#### M3 Requirements CPEN 321

Madeline Ferguson, Alyssa da Costa, Jackson Dagger, Sam Schweigel

# One Roof Requirements

## Description

As students living in an expensive city, we face the problem ofinflated living expenses; lots of us mitigate these costs by livingwith one or more roommates. This brings up another problem: how are wegoing to keep track of these shared expenses? I propose One Roof as asolution to this problem. The app will be targeted at groups ofindividuals who live together. After making accounts through Googleauthentication, this app will allow the group to keep an inventory ofshared purchases (through storage of receipt photos) and will use thisdata to figure out who owes who how much. When the roommates decideit's time to settle up, the app can send everyone a notification ofhow much is owed.

## Use case diagram

   <img src="img/use_case_diagram.png" height="500px" />

## Non-functional requirements

1.  Only authorized sources can view user information: Only members (or the owner, or a site admin) can request information from a house.  Only the owner, or a site admin can delete a house or change settings on it.
2.  The backend should be scalable: We should be able to spin up multiple instances of the server to handle more requests.  This implies that all state is stored in the database.
3.  Calculating how much every one is owed/owes should take no longer than 1s: This is a requirement because we don't want users to leave the app while it's calculating.

## Main components

### Backend 

1.  **Database**: To store purchase information such as purchaser, amount and receipt photo.
2.  **Notification Engine**: To take requests for notifications of owed money and and send them to the correct roommate.
3.  **Amount Owed Calculator**: To figure out who id owed/owes and how much.
4.  **Spending Predictor**: Predicts spending for the current month and compares with previous months.

### Frontend 
1.  **Notifications**: To display notifications.
2.  **Send Notification**: To send notifications of owed money.
3.  **Display Amount Owed**: To display who owes whom how much.
4.  **Roommate Profile**: To edit your profile.
5.  **House Overview**: To view your roommates' profiles.
6.  **Add Purchase**: To add purchases.

## Sequence diagrams

1.  Login with Google authentication (external API  call): 

<img src="img/sequence_diagram1.png" height="500px" />

2.  Predict next month's spending (non-trivial logic calculation):

<img src="img/sequence_diagram2.png" height = "500px"/>      

3.  Send notifications to roommates who owe you (live update):

<img src="img/sequence_diagram3.png" height = "500px" />

## App Sketch

<img src="img/app_sketch.png" height = "500px"/>
