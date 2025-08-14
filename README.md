# My Personal Project: Buenos Aries Sheep Farm

## A game where you take care of sheep.
Introtuction: You recieve a letter from your great grand-uncle, who has left you a small sheep farm in Argentina. As a student failing all their courses, you decide to head on over and live a comfortable life raising sheep. Little did you know, the devil has taken the form of a sheep, that may appear in your farm...

A user will start off with two sheep and breed them to create more sheep.
Sheeps will have:
- A colour (inherited from their parents, with the ability to be dyed)
- A personality trait (inherited from their parents, affects how they interact with other sheep)
- An emotional status (happy, sad, angry, etc. This is affected by their food, personality, and who they are around)
- A hunger value (when it gets low, they get sad)
- The ability to make more sheep (putting two well-fed, happy sheep into "the box" causes another sheep to be made with traitsfrom their parents)

The user will be anyone who enjoys playing casual games. In this casual game, the user will be able to collect sheep of different colour while avoiding sheep with bad personalities that may cause problems by dyeing other sheep black or *causing them to dissapear*.

As a user, I want to create a new purple sheep from a red sheep and a blue sheep, which will be added to the ranch.  
As a user, I want to view all the sheep I own.  
As a user, I want to move where my sheep are, so I put a "Calm" sheep between "Party" sheep.  
As a user, I want to change my purple sheep to be yellow.  
As a user, I want to create a new sheep from my red sheep and yellow sheep, to create a new orange sheep which will be added to the ranch.
As a user, I want to create more sheep by creating a new sheep from two sheep, one at a time.


Phase 2:  
As a user, I want to have the choice of creating a new ranch, or loading the previous one.  
As a user, I want to have the choice of quitting and saving my ranch, or to quit without saving.  


Phase 4: Task 2  


Thu Mar 27 18:01:08 PDT 2025
New Sheep  has been created.
Thu Mar 27 18:01:08 PDT 2025
New Sheep  has been created.
Thu Mar 27 18:01:08 PDT 2025
Sheep  has been renamed to Jim.
Thu Mar 27 18:01:08 PDT 2025
Sheep  has been renamed to Bob.
Thu Mar 27 18:01:14 PDT 2025
New Sheep has been made with personality of Calm, and colour value of 127,0,127
Thu Mar 27 18:01:14 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:01:14 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:14 PDT 2025
Sheep 's hunger went down.
Thu Mar 27 18:01:25 PDT 2025
Sheep  has been renamed to Carl.
Thu Mar 27 18:01:25 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:01:25 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:25 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Carl has been recoloured to have a red value of 0
Thu Mar 27 18:01:41 PDT 2025
Carl has been recoloured to have a green value of 0
Thu Mar 27 18:01:41 PDT 2025
Carl has been recoloured to have a blue value of 0
Thu Mar 27 18:01:41 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:41 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Jim has been moved to position 2
Thu Mar 27 18:01:57 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:01:57 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:02:01 PDT 2025
Sheep Bob has been fed.
Thu Mar 27 18:02:01 PDT 2025
Sheep Carl has been fed.
Thu Mar 27 18:02:01 PDT 2025
Sheep Jim has been fed.
Thu Mar 27 18:02:01 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:02:01 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:02:01 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:02:10 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:02:10 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:02:10 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:02:12 PDT 2025
New Sheep has been made with personality of Party, and colour value of 127,0,0
Thu Mar 27 18:02:12 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:02:12 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:02:12 PDT 2025
Sheep 's hunger went down.
Thu Mar 27 18:02:12 PDT 2025
Sheep Jim is now happy.
Thu Mar 27 18:02:12 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:02:14 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:02:14 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:02:14 PDT 2025
Sheep Jim's hunger went down.
Thu Mar 27 18:02:16 PDT 2025
Sheep Bob's hunger went down.
Thu Mar 27 18:02:16 PDT 2025
Sheep Carl's hunger went down.
Thu Mar 27 18:02:16 PDT 2025
Sheep Jim's hunger went down.


Phase 4: Task 3  
A refactoring change I can make is to convert Mood into an Enumeration or an integer. This would make the mood change function much easier, as I could simply increase or decrease it, instead of having a switch which takes the current mood (which is a string) and returns the changed mood as another string. Another refactoring change could be to have Ranch extend or implement List or ArrayList. This is because there are many elements that Ranch has in common with the List type, and the majority of Ranch functions involve the existing ArrayList within the Ranch class. 

Another refactoring change would be to change the Reader, Writer, or GameTicker into a singleton design pattern, as they are used sparingly, and there are never more than one of these instances in the program at any time.

A change to support future expansion is to create an abstract class or interface called Animal, which would allow for more than just sheep to be in the ranch.
