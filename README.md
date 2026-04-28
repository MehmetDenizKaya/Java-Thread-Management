# 🐇 Rabbit Simulation Game

A concurrent Java application that simulates a competitive race among rabbits for carrots. The simulation uses multi-threading and synchronization to manage game state and interactions between rabbits and carrots.

## 📝 Overview

The **Rabbit Simulation Game** is a multi-threaded Java program where several rabbits (each with unique names) compete to reach the end of a track while collecting carrots along the way. The game demonstrates core concurrency concepts such as thread management, shared resource synchronization, and volatile variables.

## 🚀 Features

- **Multi-threaded Logic**: Separate threads for carrot spawning, rabbit movement, and carrot removal.
- **Thread Synchronization**: Uses `synchronized` blocks on a shared 2D array to ensure thread-safe interactions.
- **Dynamic Configuration**: Users can define the number of rabbits, track length, and simulation speeds.
- **Randomized Names**: Rabbits are assigned unique names from a predefined list (up to 15 unique names).
- **Point System**: Rabbits earn points for every carrot they consume.

## 🛠️ Implementation Details

### Concurrent Threads
1.  **Carrot Spawner**: Periodically places a carrot in a random box on the track.
2.  **Rabbit Mover**: Moves each rabbit forward and checks if they encounter a carrot.
3.  **Carrot Remover**: Removes carrots after a specific timeout if they haven't been eaten.

### Configuration Settings
When the program starts, it prompts for the following values:
- **Rabbit Count**: Number of competing rabbits (Max: 15).
- **Box Count**: The length of the track (number of columns).
- **X (ms)**: The interval at which new carrots spawn.
- **Y (ms)**: The timeout period after which an uneaten carrot is removed.
- **Z (ms)**: The interval at which rabbits move to the next box.

## 💻 Technical Stack

- **Language**: Java
- **Concurrency**: `Thread`, `synchronized`, `volatile`
- **Data Structures**: 2D Arrays, `ArrayList`, `Enum`

## 🏃 How to Run

1.  **Compile the program**:
    ```bash
    javac RabbitMain.java
    ```
2.  **Run the application**:
    ```bash
    java RabbitMain
    ```
3.  **Follow the on-screen prompts** to enter the game settings.

## 🏁 Ending the Game

The simulation continues until one of the rabbits reaches the final box in the track. Once the race ends, the final score (carrot count) for each rabbit is displayed in the console.
