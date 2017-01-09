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


### Using Eclipse

- Install and open the latest version of Eclipse:
  http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon2

- Create a new Eclipse workspace. The workspace should NOT contain the
  `battlecode-scaffold-2017` folder.

- Run `File -> Import...`, and select `Gradle / Gradle Project`.

- In the `Select root directory` field, navigate to `battlecode-scaffold-2017`, the directory containing this README. Finish importing the project.

- Open `Window / Show View / Other...`. Select `Gradle / Gradle Tasks`.

- You should now see a list of available Gradle tasks somewhere in the IDE. Open the `battlecode` group, and double-click `unpackClient`.

- You're good to go; you can run other Gradle tasks using the other options in the "Gradle Tasks" menu. Note that you shouldn't need any task not in the `battlecode` group.

#### Caveats

- If you are unable to find import options for Gradle projects, you may be using an old version of Eclipse. Note that even old versions of Neon may lack the necessary plugins to import Gradle projects. If updating your Eclipse version still does not work, you may need to manually install the "Buildship" plugin from the Eclipse marketplace.

- If you rename or add jar files to the lib directory, Eclipse gets confused.
  You'll need to re-add them using `Project / Properties / Java Build Path`.

### Using IntelliJ IDEA
//TODO: update this section
- Install IntelliJ IDEA Community Edition:
  https://www.jetbrains.com/idea/download/

- In the `Welcome to IntelliJ IDEA` window that pops up when you start IntelliJ,
  select `Open` (NOT `Import project` or `Create new project`)

- In the `Open File or Project` window, select `battlecode-scaffold/ide/intellij`
  (NOT just `battlecode-scaffold`).

- Select the `Update Battlecode` ant target in the run configurations box (a
  rounded rectangle next to a green triangle) and run it (using the green
  triangle). You can also run it using the `Run / Run...` menu option.

- You're good to go; you can run other ant tasks using the other run
  configurations, or using the `Ant Build` tool window (accessible from
  `View / Tool Windows / Ant Build`)


### Using a terminal

- Gradle commands may be ran in two different ways. You can install Gradle (https://gradle.org/gradle-download/) and use the binaries it installs, or you may use the Gradle wrapper.

- If using the former option, simply start every Gradle command with `gradle`.

- If opting to use the wrapper, start every Gradle command with `./gradlew`, if using Unix, or `gradlew`, if using Windows.

- On every system you will need to set the `JAVA_HOME` environment variable to
  point to the installation path of your JDK.

- Navigate to the root directory of the project, and run `gradle unpackClient`.

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

Local matches are the most common way to run a match - they are computed and
rendered simultaneously on the same machine. Invoke the ant `run` target to run
a local match, using the command line or an IDE. For the IDE, pick `Battlecode
Client` the same way you picked `Update Battlecode`.

A dialog box will appear that allows you to choose the teams and maps to run.
The teams and maps that appear in the dropdown boxes are those that the software
knows about at runtime. If the team or map you're trying to run does not appear
in the dropdown box, it isn't on your classpath or map path.

When running a local match, you also have the option of saving the match to a
file so that you can play it back without having to recompute it. To do this,
check the `Save match to file` box on the main dialog and choose the location of
the file. Note that the file will be overwritten if it already exists.

If you're not using Ant, you can run the `battlecode.client.Main` class from the
command line or an IDE. You should pass an argument `-c [CONF_FILE]` to point it
at a battlecode configuration file.

#### Client Basics

After you start your match, you should see a map with a bunch of robots on it.
The top left has controls for playing through the game: playing, pausing, skipping
to a certain round, etc.

You can click on a robot to get its detailed information. Details about that robot
will show up on the top panel, such as its bytecode usage and its indicator strings.
You can also hover over a map tile to get information about its location and the number
of parts and rubble on that tile.

The left pane shows the number of units of each type. In addition, there are four bars.
The first and third bars show how many parts each team has. The second and fourth bars
display the total part values of the red and blue armies. This value is computed by
summing the part cost of each team's robots and is meant to be used as an indicator of
which team has the stronger army.

Some basic animations:
- colored lines represent attacks
- purple rings represent broadcasts
- circles on the map indicate parts
- the darkness of a map tile represents how much rubble there is
- brackets around a unit indicate infection (green = zombie, purple = viper)

There are also a number of keyboard shortcuts below that you can use to play around
with the cilent.

### Headless matches

Headless matches run a match without viewing it - they run the server without
the client. Invoke the gradle `run` task to run a headless match.

This task takes several paramters: `teamA`, `teamB`, and `maps`. These can be specified on command line with `gradle run -PteamA=<team A> -PteamB=<team B> -Pmaps=<maps>`.

`teamA` and `teamB` correspond to the packages containing teams A and B, respectively. `maps` should be set to a comma-separated list of maps. If you are unsure as to what format to use for entering, refer to the output of `gradle listMaps listPlayers`.


### Playing Back from a File

If you have a match file that you'd like to play back (i.e., from running a match in headless mode) you can play this back using the client.

These match files have the extension `bc17`. You can also play back scrimmage match files that are downloaded from the website.


### Match Sets

This year, each game between two teams consists of a set of matches. To run multiple matches in a game, use the menu in the client to select a series of maps. A match will be played on each map in the list, in order.


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


## Appendix A: Configuration Properties and Command-line Arguments

### Computation Throttling

When running a local match, the game engine attempts to periodically delay to
prevent starving the match viewer of CPU time. Altering the following two
settings may yield better local match performance:

- `bc.server.throttle`: determines how to delay match computation; can be set to
   either "yield" or "sleep"
- `bc.server.throttle-count`: the number of rounds between sleep/yield


### Engine Settings

The following settings can be used to enable or disable certain aspects of the
engine.

- `bc.engine.silence-a` and `bc.engine.silence-b`: "true" or "false"; whether or
   not the engine will suppress printouts for the appropriate team
- `bc.engine.gc`: "true" or "false"; whether or not to periodically force
   garbage collection in the game engine -- this option causes decreased
   performance but may help if the virtual machine runs out of memory during
   computation
- `bc.engine.gc-rounds`: how many rounds between forced invocation of the
   garbage collector; the default is 50
- `bc.engine.upkeep`: "true" or "false"; if "false", the engine will not charge
   units their energon upkeep each round
- `bc.engine.breakpoints`: "true" or "false"; if "false", the engine will skip
   breakpoints in player code


### Client Settings
- `bc.game.renderprefs2d`: preferences for the 2d client.  For example, if
   you wanted to turn off the rendering of broadcasts and gridlines, you could
   set this property to "bg".  See the "Shortcut Keys" section for a complete
   listing of toggles.


### Miscellaneous Settings

- `bc.game.map-path`: the folder in which to look for map files (aside from the
   ones packaged into the battlecode software)
- `bc.dialog.skip`: "true" or "false"; whether or not to show the setup dialog
   when the software is started. If "true", the parameters most recently entered
   into the dialog will be used.


### Client Settings

- `bc.client.renderprefs2d`: A list of toggles to set in the 2D client.
   (See Shortcut Keys below.)  For example, if you want to turn off broadcasts,
   grid lines, and transfers, you would set `bc.client.renderprefs2d=bgt`.
- `bc.client.sound-on`: "true" or "false"; whether or not to play sound
   effects.


### Shortcut Keys

| Key | Effect 
|-----|--------
|  A  | Toggle between detailed and non-detailed client view
|  B  | Toggle unit broadcasts
|  D  | Toggle discrete movement mode
|  E  | Toggle HP bars
|  F  | Toggle fast forward
|  G  | Toggle grid lines
|  H  | Toggle action lines
|  I  | Rewind 50 rounds
|  J  | Toggle slow mo
|  L  | Toggle infection indicators
|  K  | Toggle attack lines
|  R  | Show attack/sight ranges when examining a unit
|  S  | Skip 100 rounds
|  U  | Toggle parts
|  V  | Toggle indicator dot/line display (none, one team, both teams)
|  X  | Toggle unit explosions
|  /  | Find unit by ID
|  <  | Pause
|  >  | Pause
| Esc | Quit


## Scala

Most contestants choose to write their players in Java, but we also support
Scala (or a mix of Java and Scala). If you want to use Scala, simply add a
.scala file to any of your players or tests, and re-run `ant update`.
Everything you need should now be installed.

### Scala with Eclipse

To run Scala with Eclipse, you'll want to install the Scala IDE Plugin for
Eclipse: http://scala-ide.org/download/current.html

Make sure you install it using `Help / Install New Software`.

Things should just work, although you may have trouble running the different
`New Scala <thing>` wizards in battlecode-scaffold, because it is not
configured as a scala project. To fix this, just make new scala files using
the `New / File` option, and name them whatever you want your scala files to be
named.

## Scala with IntelliJ

To use Scala with IntelliJ, make sure you have the Scala plugin installed and
enabled:
https://plugins.jetbrains.com/plugin/?id=1347

When you start editing files, it will probably yell at you about "No Scala SDK
In Module". To fix this, click the link next to the error, and add the auto-
configured SDK. You can also add a scala SDK in `File / Project Structure /
Battlecode / Dependencies`; note that the resources for one should be installed
in the `lib` folder.
