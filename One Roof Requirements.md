#### M3 Requirements CPEN 321

Madeline Ferguson 30986012

# One Roof Requirements

1. Description:

   As students living in an expensive city, we face the problem of inflated living expenses; lots of us mitigate these costs by living with one or more roommates. This brings up another problem: how are we going to keep track of these shared expenses? I propose One Roof as a solution to this problem. The app will be targeted at groups of individuals who live together. After making accounts through Google authentication, this app will allow the group to keep an inventory of shared purchases (through storage of receipt photos) and will use this data to figure out who owes who how much. When the roommates decide it's time to settle up, the app can send everyone a notification of how much is owed.

2. Use case diagram:

   <img src="C:\Users\madel\AppData\Roaming\Typora\typora-user-images\image-20200928095655523.png" height="500px" />

   

3. Non-functional requirements:

   1. Only authorized sources can view user information: This is a requirement because it's important to the users that their information is safe.
   2. Maximum group size is 10 people: This is a requirement because we don't want groups of more than ten roommates per house.
   3. Calculating how much every one is owed/owes should take no longer than 1s: This is a requirement because we don't want users to leave the app while it's calculating.

4. Main components:

   1. Front end: app that runs on an Android mobile phone
   2. Back end: system that runs on a major could service
   3. Calculator: system that calculates how much each roommate is owed/owes
   4. Database: system that stores group purchase information

5. Sequence diagrams:

   1. Login with Google authentication (external API  call): 

      <img src="C:\Users\madel\AppData\Roaming\Typora\typora-user-images\image-20200928100944267.png" height="500px" />

      

   2. Calculate how much everyone is owed/owes (non-trivial logic calculation):

      <img src="C:\Users\madel\AppData\Roaming\Typora\typora-user-images\image-20200928101055113.png" height = "500px"/>

      

   3. Send notification to roommates who owe you (live update):

      <img src="C:\Users\madel\AppData\Roaming\Typora\typora-user-images\image-20200928101348764.png" height = "500px" />

6. App Sketch:

<img src="C:\Users\madel\OneDrive\Desktop\app.png" height = "500px"/>