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
  http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/neon2

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

## Debugging
Using a "debugger" lets you pause your code while its running and inspect its state - what your variables are set to, what methods you're calling, and so on. You can walk through your code step-by-step, and run arbitrary commands.

Battlecode supports "remote debugging", which means that you start up the battlecode server and tell it to pause, then connect to it with Eclipse or Intellij. It's easy to set up.

### Debugging vocabulary
Debugging has some new words that you might not know:

A **debugger** is a tool that runs your code and pauses when you tell it to. You'll be using Eclipse or Intellij as a debugger for battlecode (unless you're particularly hardcore.)

A **breakpoint** is an automatic pause point in the code. When the debugger gets to that line of code, it will pause, and wait for you to tell it what to do.

**Stepping** is telling the debugger to take a single "step" in the code, and then pause again.

You can also **resume** code, to keep running until you hit another breakpoint.

The **stack** and **stack frames** are fancy words for, basically, the list of methods that are currently being called. So, if you have the methods:

```
void doSomething() {
    goSomewhere();
}

void goSomewhere() {
    goLeft();
}

void goLeft() {
    rc.move(LEFT);
}
```

And you call `doSomething()`, the stack will look like:

```
   ...
    ^
rc.move(LEFT)
    ^
goLeft()
    ^
goSomewhere()
    ^
doSomething() 
```

If you have questions, ask in IRC.

### Starting the server in debug mode
Do `./gradlew runDebug -PteamA=examplefuncsplayer -PteamB=examplefuncsplayer -Pmaps=shrine,Barrier` in a terminal. (Or equivalent, for the teams and maps you want.) (This works exactly like `./gradlew run`.)

It should say `Listening for transport dt_socket at address: 5005` and pause.

This means that the server has started, and is waiting for the Eclipse or IntelliJ debugger to connect.

(You have to do this every time you want to debug.)

### Debugging in Intellij
#### Initial setup
- Go into IntelliJ
- Set a breakpoint by clicking on the area beside the line numbers to the left of your code.
- Right click the breakpoint and select `Suspend / Thread`, and then click "Make default." (This lets battlecode keep working while your code is paused.)
- Go to Run > Edit Configurations...
- Hit the "+" icon and select "Remote" 
    - Give it a name like "Debug Battlecode"
    - In "Settings":
        - Set Host to `localhost`
        - Set Port to `5005`
    - Hit OK
- In the top right corner of the screen, you should be able to select "Debug Battlecode" or equivalent from the little dropdown, and then hit the bug icon
- If it works:
    - IntelliJ should highlight the line you put a breakpoint on and pause. You should see a "Debug" window at the bottom of the screen.
      Congratulations! You're debugging.
- If it doesn't work:
    - If the match just runs and nothing happens, then make sure that your breakpoint is in a place that will actually run during the match (e.g. at the top of `RobotPlayer::run`.)
    - If you get `Unable to open debugger port (localhost:5005): java.net.ConnectException "Connection refused"`, make sure you've actually started the server in `runDebug` mode, and that your port is set correctly. (You may also have to mess with your firewall settings, but try the other stuff first.)

#### Ignoring battlecode internals
Sometimes you can step into battlecode internal stuff by accident. To avoid that:
- Go into Settings or Preferences > Build, Execution, Deployment > Debugger > Stepping
- Select the "Skip class loaders" button
- Select all the "Do not step into the classes..." options
- Add the following packages by hitting the `+`<sub>.*?</sub>
    - `battlecode.*`
    - `net.sf.*`
    - `gnu.trove.*`
    - `org.objectweb.*`

#### How to use the debugger
When the debugger is paused, you should be able to see a "Debug" window. It has the following stuff in it:

- The "Frames" tab, which shows all the methods that have been called to get to where we are. (You can ignore the methods below "run"; they're battlecode magic.)
- The "Variables" tab, which shows the values of variables that are currently available.
- A line of icons:
    - "Step over", which goes to the next line in the current file, ignoring any methods you call.
    - "Step into", which goes into whatever method you call next.
    - "Force step into", which does the same thing as Step into, but also shows you inscrutable JVM internals while it goes. You shouldn't need this.
    - "Step out", which leaves the current method.
    - "Drop Frame", which pretends to rewind, but doesn't really. Don't use this unless you know what you're doing.
    - "Run to Cursor", which runs until the code hits the line the cursor is on.
    - "Evaluate Expression", which lets you put in any code you want and see what its value is.
- The "Threads" tab, which you shouldn't mess with, because you might break the server.

To learn more about these tools, see the [Intellij documentation](https://www.jetbrains.com/help/idea/2016.3/debugger-basics.html).

#### Conditional breakpoints
Sometimes, you might only want to pause if your robot is on team A, or the game is in round 537, or if you have fewer than a thousand bytecodes left.
To make these changes, right click the breakpoint, and in the condition field, put the condition; you can use any variables in the surrounding code.
If I have the method:

```
import battlecode.common.Clock;
import battlecode.common.RobotController;

class RobotPlayer {
    // ...
    public static void sayHi(RobotController rc) {
        rc.broadcast(rc.getID(), rc.getType().ordinal());
    }
}
```

I could make the following breakpoint conditions:
- `rc.getTeam() == Team.A`
- `rc.getRoundNum() == 537`
- `Clock.getBytecodesLeft() < 1000`
- `rc.getTeam() == Team.A && rc.getRoundNum() == 537 && Clock.getBytecodesLeft() < 1000`

### Debugging in Eclipse
#### Initial setup
- Go into Eclipse
- Set a breakpoint in your code by clicking on the margin to the left of it so that a blue bubble appears
- Go to Run > Debug configurations 
- Select "Remote Java Application"
- Hit the "new" button
- Set up the debug configuration:
    - Give it a name (i.e. Debug Battlecode Bot)
    - Hit Browse, and select your project
    - Make sure connection type is "Standard"
    - Set Host to `localhost`
    - Set Port to `5005`
    - If there's an error about selecting preferred launcher type at the bottom of the dialog, pick one;
      scala if you have scala code, java otherwise; although they should both work.
- Hit "Apply"
- Hit "Debug"
    - If it works:
        - Eclipse should ask to open the "Debug" view; let it.
        - You should see new and exciting windows, and eclipse should pause and highlight a line of your code.
        - Congratulations; you're debugging.
    - If it doesn't:
        - You may get a "failed to connect to VM; connection refused."
          Make sure you've [started the server in debug mode](#starting-the-server-in-debug-mode).
- You can also start debugging by selecting the little triangle next to the beetle in the toolbar and selecting "Debug Battlecode Bot".

#### Ignoring battlecode internals
Oftentimes while debugging you can often step into classes you don't care about - battlecode internal classes, or java classes.
To avoid this, right click a stack frame in the "Debug" window - i.e. the thing beneath a Thread labeled `RobotPlayer.run` or whatever - and:
- Select "Use Step Filters"
- Select "Edit Step Filters".
    - Select all the predefined ones
    - Add filter...
        - `battlecode.*`
        - `net.sf.*`
        - `gnu.trove.*`
        - `org.objectweb.*`
    - Hit "Ok"

And you should be good to go!

#### Using the debugger.
See the [eclipse documentation](http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fviews%2Fdebug%2Fref-debug_view.htm).

#### Conditional Breakpoints
Sometimes, you might only want to pause if your robot is on team A, or the game is in round 537, or if you have fewer than a thousand bytecodes left.
To make these changes:
- Right click the breakpoint
- Go to "Breakpoint Properties"
- Check "Conditional"
- Write a condition in the text box

If I have the method:

```
import battlecode.common.Clock;
import battlecode.common.RobotController

class RobotPlayer {
    // ...
    public static void sayHi(RobotController rc) {
        rc.broadcast(rc.getID(), rc.getType().ordinal());
    }
}
```

I could make the following conditions:
- `rc.getTeam() == Team.A`
- `rc.getRoundNum() == 537`
- `Clock.getBytecodesLeft() < 1000`
- `rc.getTeam() == Team.A && rc.getRoundNum() == 537 && Clock.getBytecodesLeft() < 1000`
