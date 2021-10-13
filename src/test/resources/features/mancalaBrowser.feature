# Author EhsanAsaDev
# Date 13/6/2021
# Description
@smokeFeatures
Feature: feature to test Mancala game on web driver

  @smokeTestByBrowser
  Scenario: Validate Mancala game
    Given first user browser is open
    And first user browser is on main page
    And second user browser is open
    And second user browser is on main page
    When first user enter name in name box
    And first user hit create button
    Then one game is created
    And board game is updated "{\"pit_0\":\"6\", \"pit_1\":\"6\", \"pit_2\":\"6\", \"pit_3\":\"6\", \"pit_4\":\"6\", \"pit_5\":\"6\", \"pit_6\":\"0\", \"pit_7\":\"6\", \"pit_8\":\"6\", \"pit_9\":\"6\", \"pit_10\":\"6\", \"pit_11\":\"6\", \"pit_12\":\"6\", \"pit_13\":\"0\"}"
    When second user enter name in the name box
    And second user enter game id in the game id box
    And second user hit connect by game id button
    Then second user is joined to the game
    When first user hit pit#2
    Then board game is updated "{\"pit_0\":\"6\", \"pit_1\":\"0\", \"pit_2\":\"7\", \"pit_3\":\"7\", \"pit_4\":\"7\", \"pit_5\":\"7\", \"pit_6\":\"1\", \"pit_7\":\"7\", \"pit_8\":\"6\", \"pit_9\":\"6\", \"pit_10\":\"6\", \"pit_11\":\"6\", \"pit_12\":\"6\", \"pit_13\":\"0\"}"



