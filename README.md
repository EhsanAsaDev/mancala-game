<a href="https://foojay.io/today/works-with-openjdk">
   <img align="right" 
        src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png"   
        width="100">
</a>

Mancala Challenge
---

### What I did
I design and develop this project base on Spring framework.

- `Spring Boot`
- `WebSocket` , for getting changes and refreshing  game board
- `MongoDB`, for persisting the Game information
- `HTML and js`, for providing simple UI
- `Docker`, for containerization of services
- `Docker-Compose`, to link the containers


### How to launch
First, you should build and package jar file with Maven.
Then,  I provided a docker file, so you should create an image.
Finally, by the docker-compose file, you can launch it.
Via the home page (http://localhost:8080/) you can access to the game UI.