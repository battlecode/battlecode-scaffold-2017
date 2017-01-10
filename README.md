Battlecode Project Scaffold
===========================

Here you'll find (almost) everything you need to write players for Battlecode
2017.

Other documentation and resources can be found at: https://www.battlecode.org/


## Overview

### Project structure

- `README.md`
    This file.
- `build.gradle`
    The Gradle build file used to build and run players.
- `src/`
    Player source code.
- `test/`
    Player test code.
- `client/`
    Contains the client.
- `build/`
    Contains compiled player code and other artifacts of the build process. Can be safely ignored.
- `matches/`
    The output folder for match files.
- `maps/`
    The default folder for custom maps.
- `gradlew`, `gradlew.bat`
    The Unix (OS X/Linux) and Windows versions, respectively, of the Gradle wrapper. These are nifty scripts that you can execute in a terminal to run the Gradle build tasks of this project. If you aren't planning to do command line development, these can be safely ignored.
- `gradle/`
    Contains files used by the Gradle wrapper scripts. Can be safely ignored.


### How does Battlecode work?

The Battlecode software consists of three major components:

- The player library/API: these are the classes that you will import and build
  against when writing a player.

- The server: this is the software that computes Battlecode matches. For most
  users, the server will run transparently, so you don't have to worry about it.
  However, advanced server setups are possible, allowing you to compute matches
  on one machine and view them on another.

- The client: this is the software that displays Battlecode matches. For most
  users, the client will automatically create a server for running a match and
  display that match as it computes. The client also plays match files like
  those from scrimmage matches and the tournaments. Finally, the client also
  contains a map editor, for those who would like to create their own maps
  to test their robots on.

This project scaffold handles installing and running these components using Gradle.


### What is Gradle?

Gradle is a build system that expands upon the features of earlier build systems like Apache Ant and Apache Maven, utilizing a domain-specific language based off of Groovy, a JVM language.

You can run it from a terminal or from an IDE; instructions are below.

You can find Gradle's documentation at: https://gradle.org/

You are not required to use the Gradle build script, but you should probably at
least read it to get an idea of how things work.

Note also that you are not required to install Gradle, even if you are working in command line.


## Getting started

First, you'll need a Java Development Kit compatible with Java 8 or later.

You can find JDK installers at:
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Alternatively, you can install a JDK yourself using your favorite package
manager. Make sure it's an Oracle JDK - we don't support anything else -
and is compatible with Java 8.

If you're unsure how to install the JDK, you can find instructions for
all operating systems here: 
https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html
Pay careful attention to anything about setting up your `PATH` or `CLASSPATH`.

Next, you'll need to choose how you want to work on battlecode - using an
IDE, using a terminal, or mixing and matching.

## Important note for Windows users

If you get errors while trying to execute Gradle tasks, make sure that you do not have the client open.


### Using Eclipse

- Install and open the latest version of Eclipse:
  http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon2

- Create a new Eclipse workspace. The workspace should NOT contain the
  `battlecode-scaffold-2017` folder.

- Run `File -> Import...`, and select `Gradle / Gradle Project`.

- In the `Select root directory` field, navigate to `battlecode-scaffold-2017`, the directory containing this README. Finish importing the project.

- Open `Window / Show View / Other...`. Select `Gradle / Gradle Tasks`.

- You should now see a list of available Gradle tasks somewhere in the IDE. Open the `battlecode` group, and double-click `build`. This will run tests to verify that everything is working correctly

- You're good to go; you can run other Gradle tasks using the other options in the "Gradle Tasks" menu. Note that you shouldn't need any task not in the `battlecode` group.

#### Caveats

- If you are unable to find import options for Gradle projects, you may be using an old version of Eclipse. Note that even old versions of Neon may lack the necessary plugins to import Gradle projects. If updating your Eclipse version still does not work, you may need to manually install the "Buildship" plugin from the Eclipse marketplace.

- If you rename or add jar files to the lib directory, Eclipse gets confused.
  You'll need to re-add them using `Project / Properties / Java Build Path`.

### Using IntelliJ IDEA
- Install IntelliJ IDEA Community Edition:
  https://www.jetbrains.com/idea/download/

- In the `Welcome to IntelliJ IDEA` window that pops up when you start IntelliJ,
  select `Import Project`

- In the `Open File or Project` window, select the `build.gradle` file in the scaffold folder.

- Check the options "Create separate module per source set", and "Use gradle wrapper task configuration". Set the "Gradle JVM" option to 1.8. (If you don't have a 1.8 option, see the "Getting Started" section above.

- Hit OK.

- Wait a minute or two for IntelliJ to finish configuring itself.

- When the bar at the bottom of the screen has stopped downloading things, we'll need to check that everything is set up correctly. Go to `View / Tool Windows / Gradle`. In the new window that pops up, select `Tasks / battlecode / build`, and double click it. You should now see a nice tree of tasks unfold at the bottom of the screen. If you run into an error here, you should get help. Try going to the forums (http://battlecodeforum.org) or to IRC (http://irc.lc/freenode/battlecode).

- If you haven't seen any errors, you should be good to go. There should now be a folder called `client` in your scaffold folder; if you go in there, and double click the `Battlecode Client` application, you should be able to run and watch matches. (Please don't move that application, it will be sad.)

### Using a terminal

- Gradle commands may be ran in two different ways. You can install Gradle (https://gradle.org/gradle-download/) and use the binaries it installs, or you may use the Gradle wrapper.

- If using the former option, simply start every Gradle command with `gradle`.

- If opting to use the wrapper, start every Gradle command with `./gradlew`, if using Unix, or `gradlew`, if using Windows.

- On every system you will need to set the `JAVA_HOME` environment variable to
  point to the installation path of your JDK.

- Navigate to the root directory of the project, and run `gradle build`. This will run tests, to verify that everything is working.

- You're good to go. Run `gradle -q tasks` to see the other Gradle build
  tasks available. You shouldn't need to use any tasks outside of the "battlecode" group.


## Writing Players

The included `build.gradle` file allows you to compile your player and prepare it
for submission without having to worry about the Java classpath and other
settings. To take advantage of this, simply place the source code for your
player(s) in a subdirectory of `src` folder in your Battlecode installation
directory.

This year, you can store your code in packages as you like; the only restriction is that your `run(RobotController rc)` method must be placed in a file called `RobotPlayer.java`. (or `RobotPlayer.scala`!)

## Running Matches

### Local

Following the instructions above should download a client into the `client` folder (using `gradle unpackClient` if you don't see it). This is the app for running matches. Double click this application to open it; you are now looking at the game client for Battlecode 2017! **NOTE: Do not move any application files in the `client` folder, as you may lose the ability to run matches properly!**

#### Client Basics

There are a few sections to the client that you should be aware of:

**Side Panel** - The side panel to the left has a information and controls for managing Battlecode matches. The first section has information regarding the number of units each team has, as well as that teams total victory point and bullet counts. The bottom of the side panel also has a game queue, which will display games and their matches when they are added to the client. The top of this side panel also has tabs to switch between a map editor, game view, help panel, and match runner.

**Control Panel** - The top of the screen has a control panel which can be used to control the current match being viewed. The timeline can be clicked to seek to a specific turn in the match, while standard play, pause, seek forward, seek backward, and restart buttons can also be used to control the playback of a match. The add button [`+`] can be used to run `.bc17` files, which hold games.

**Game Area** - The majority of the screen is taken up by the game panel, where matches will be displayed (note that this will be empty until a game is loaded).

#### Playing a Saved Local Match

Since games are saved as `.bc17` files, these files can be saved and run on demand. Simply use the + button as mentioned above to load a game.

#### Creating a Match

Clicking the "Run Match" button in the side panel will allow you to run robots against each other on multiple maps (note that loading this tab may take a few seconds, as it searches for your players and maps). Select your two teams, and use the checkboxes to choose which maps to run within the game. Then click "Run Match" at the bottom of the form to compute and display the match within the client. **Note that it may take a few seconds to load the players, run the matches, and begin displaying them in the client**.

The maps which can be used are located in the `map/` folder within the root directory of this scaffold. There are also some default maps which we include for you to test your players on. The players which can be used will be any `RobotPlayer.java` file found in the `src/` folder in the root directory of this scaffold.

### Headless matches

Headless matches run a match without viewing it - they run the server without
the client. Invoke the gradle `run` task to run a headless match.

This task takes several paramters: `teamA`, `teamB`, and `maps`. These can be specified on command line with `gradle run -PteamA=<team A> -PteamB=<team B> -Pmaps=<maps>`.

`teamA` and `teamB` correspond to the packages containing teams A and B, respectively. `maps` should be set to a comma-separated list of maps. If you are unsure as to what format to use for entering, refer to the output of `gradle listMaps listPlayers`.


## Uploading your Player

You should upload a jar file containing your team's source code.

This year, there are no restrictions on the package your player's code may be placed in; only that your `run(RobotController rc)` method be in a file named `RobotPlayer.java`. (or `RobotPlayer.scala`!)

To build this jar, run the gradle task `jarForUpload`. This can be done from an IDE, or from the command line.

Then, go to http://www.battlecode.org/contestants/upload/ and upload this file. The website will attempt to compile your program and if it succeeds, then you can go challenge other teams to scrimmages.

## Maps

This year, the map files are packaged into the battlecode jar.
You can access the map files at
https://github.com/battlecode/battlecode-server/tree/master/src/main/battlecode/world/resources
if you are curious. In addition, you can write your own maps and place them in
the `maps` folder. Any maps placed there will be discovered by the client. For more help
about how to write your own map files, check the specs.

We recommend using the map editor to create maps. The map editor can be ran from the client. Instructions can be found within the client.

## Scala

Most contestants choose to write their players in Java, but we also support
Scala (or a mix of Java and Scala) out of the box, with the standard install.
