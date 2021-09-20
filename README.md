<a href="https://foojay.io/today/works-with-openjdk">
   <img align="right" 
        src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png"   
        width="100">
</a>

Mancala Game
---

The complete walk-through tutorial about this implementation, I've published blog post here:

[Create interactive game with Spring Boot and WebSocket](https://ehsanasadev.github.io/Create_interactive_game_with_Spring_Boot_and_WebSocket/)

The goal is to design and develop an interactive game by using Spring boot and WebSocket. 

Technologies
------------
- `Spring Boot`
- `WebSocket`  for getting changes and refreshing game board
- `MongoDB` for persisting the Game information
- `HTML and js` for providing simple UI and calling rest service
- `Docker` for containerization of services
- `Docker-Compose`  to link the containers


### How to launch
First, you should build and package jar file with Maven.
Then,  I provided a docker file, so you should create an image.
Finally, by the docker-compose file, you can launch it.
Via the home page (http://localhost:8080/) you can access to the game UI.


![game UI](/spring-websocket-mancala-game.png)