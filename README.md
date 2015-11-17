# Dots and Boxes
Dots and Boxes game running on local network

## Network Project phase1,
### Dr. Mahyar, fall 1394,
#### Rules:
* The goal of the project is for you to learn and practice network programming and Java. I ask
you to use standard Java library in your project.
* You are free to implement your project with any programming languages, but our suggestion
is Java.
* You can work in a team of two, or you can work alone. You are encouraged to work in teams.
* Deadline of this project is up to 1394.9.6 at 11:59 PM.
* This project contains 2 marks of your final mark.

----------------

#### Description:
##### Dot and Line:
- There is a board consists of an ”n×n” grid of points, which makes an “n-1×n-1” grid of cubes if you
were to connect the dots. “n” is a random number between 6 and 9, inclusive.
- Player 1 has color Green, Player 2 has color Red.
- On each player’s turn, the player fills in the horizontal or vertical line segment connecting two
neighboring points
- If filling in a line segment completes a box on the grid, the player becomes the owner of that square
and gets a point. The first letter of his/her name will be written in that box too. As a reward, the player
also gets to place another line segment on the same turn; until he/she cannot complete a box any more.
- The player with the most squares/points at the end of the game wins. There could be a tie too.
The server application should decide on “n”. Also, it should also ask the first name of each Player. The
server application should make sure that the two players do not have the same first letter in their
name.
The following figure shows the game when “n” is 8 and, first player has scored two points.
