# Author EhsanAsaDev
# Date 10/6/2021
# Description
@smokeFeatures
Feature: feature to test Mancala rest webservice

  @smokeTest
  Scenario Outline: play mancala game
    When first user create a game <firstUserName>
    Then one game is created and returned

    When second user connect to the game <secondUserName>
    Then second user joined to the game

    When first user play for the first time and sow pit <pitIndex>
    Then pits changed <boardGame>

    When first user try to play out of turn
    Then raise message it's not your turn
    Examples:
      | firstUserName | secondUserName | pitIndex | boardGame                                                                     |
      | "firstUser"   | "secondUser"   | 2        | "[1:6, 2:0, 3:7, 4:7, 5:7, 6:7, 7:1, 8:7, 9:6, 10:6, 11:6, 12:6, 13:6, 14:0]" |
      | "Ehsan"       | "Shantool"     | 3        | "[1:6, 2:6, 3:0, 4:7, 5:7, 6:7, 7:1, 8:7, 9:7, 10:6, 11:6, 12:6, 13:6, 14:0]" |

