## MVP calculator
This program calculates the Most Valuable Player (MVP) of the tournament.

### Input
Expected input is a set of CSV files, each one containing the stats of one game.\
Each file starts with a row indicating the sport it refers to.\
Next rows represent one player stats, with the specific format.\
If one file is wrong, the whole set of files is considered to be wrong and the MVP won’t be calculated.

### MVP calculation
The MVP is the player with the most rating points, adding the rating points in all games.\
A player receives 10 additional rating points if their team won the game.\
Every game has a winner team.\
One player may play in different teams and positions in different games, but not in the same game.\
Only two sports are currently supported: basketball and handball.

#### _Basketball_:
  ##### File format:
  - `player name;nickname;number;team name;scored points;rebounds;assists`
  ##### Rating points each player in a basketball game receives are calculated by formula:
  - `2 * scoredPoints + rebounds + assists`

#### _Handball_:
  ##### File format:
  - `player name;nickname;number;team name;goals made;goals received`
  ##### Rating points each player in a handball game receives are calculated by formula:
  - `2 * goalsMade - goalsReceived`
