# Description

This is simple app that allows to play Tic Tac Toe game within simple UI
There are two ways to starts app:

a) Stable way

1) Run postgres service in docker-compose.yml (when Docker is installed before)
2) Run Spring Boot app with TicTacToeApplication main method
3) Go to localhost:8080 where you can enter numbers and play a game! You can reset
The data is persisted in the PostgreSQL DB, so you can refresh page (go to / ) and state of board persists
You can reset the boarder to init state and also you can view error screen if input data is invalid - be careful!

b) Risky way

launch the all services, listed in docker-compose.yml (rework for networks could be needed, i guess)
