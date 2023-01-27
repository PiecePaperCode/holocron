# Holocron - XWing Tournament Software

![](https://github.com/PiecePaperCode/holocron/raw/main/img/tournament.png)

Holocron is a tournament management program for X-Wing Miniatures. Easily generate pairings, track scores, and determine winners with our user-friendly interface. Perfect for tournament organizers looking to streamline their events.
Holocron is a tournament software written in Java 17 with the use of jlink and jpackage to produce native executables for Windows, Linux, and MacOS.

## Features
- Swiss tournament support
- 2.5 tournament rule support
- Export Tournaments to ListFortress

## Installation

Native distributions can be found in the [releases](https://github.com/PiecePaperCode/holocron/releases) section of this repository. Simply download the appropriate executable for your operating system and run it.

## Building

Holocron is built on JavaFX and can be built with Maven. Simply run `mvn clean package` to create a jar file with all dependencies included.
The resulting jar file can be found in the target directory and can be run with ´java -jar holocron.jar.

## Future Plans

I plan to introduce more features to Holocron in the future, such as support for additional tournament rules and the ability to customize tournaments. Stay tuned!