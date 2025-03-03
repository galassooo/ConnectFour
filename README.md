# Connect Four Game

## Overview
This project is a Java implementation of the classic Connect Four game, featuring a robust multi-layer architecture with separate frontend and backend components. The game offers a graphical user interface for playing Connect Four, with support for game persistence, customizable player tokens, and multilingual support.

## Features
- Complete implementation of Connect Four game rules
- Graphical user interface built with JavaFX
- Save and load game functionality
- Multiple language support
- Customizable player symbols and colors
- Help screens and about information

## Architecture
The project follows a layered architecture pattern:

### Backend
The backend module contains the game logic and business rules, organized into several packages:
- `application`: Interface layer between frontend and business logic
- `business`: Core game logic and player management
- `dataaccess`: Persistence handling for saving/loading games

### Frontend
The frontend module provides the user interface, organized using the MVC pattern:
- `controller`: Handles user input and application flow
- `model`: Manages application state
- `view`: Renders the UI and responds to state changes
- `dispatcher`: Routes events between components

## Project Structure

### Key Packages
- `ch.supsi.connectfour.backend.application`: Application-level interfaces and event handling
- `ch.supsi.connectfour.backend.business`: Core business logic
- `ch.supsi.connectfour.backend.dataaccess`: Data persistence
- `ch.supsi.connectfour.frontend.controller`: UI control logic
- `ch.supsi.connectfour.frontend.model`: Frontend data models
- `ch.supsi.connectfour.frontend.view`: View components

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven

### Building the Project
To build the project, run:

```bash
mvn clean package
```
The build produces a JAR file with dependencies in the *target* directory.

### Running the Game
To run the game:
```bash
java -jar frontend/target/connectfour-jar-with-dependencies.jar
```
### Game Instructions

#### Launch the application
- Use the menu to start a new game or load a saved game
- Click on the column selectors at the top to drop your token
- The first player to connect four tokens horizontally, vertically, or diagonally wins

### Customization
The game offers several customization options:

- Change player tokens and colors through the Preferences menu
- Switch languages through the Preferences menu

### Save/Load Functionality

- Games can be saved at any point using the Save or Save As menu options
- Saved games can be loaded using the Open menu option
- The game uses JSON serialization for game state persistence

### Development Notes
#### Design Patterns
The project implements several design patterns:

- Singleton pattern for controllers and managers
- Observer pattern for event handling
- MVC pattern for UI components
- Strategy pattern for player behaviors

### Extensibility
The code is designed to be extensible in several ways:

- New player symbols can be added by placing new images in the resources directory
- Additional languages can be supported by adding new property files

### Dependencies

- JavaFX for the user interface
- Jackson for JSON serialization
- Spring Core for resource management

### Contributors

Martina Galasso
Alex Rodrigues

## License
This project is provided for educational purposes.
