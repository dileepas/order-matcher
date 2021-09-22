
## Order Matcher
This is a programme to illustrate a real-time Electronic trading that all buy and sell orders are entered into a order book
and the prices are set according to specific rules.

## Possible Inputs
There are three types of commands: BUY, SELL and PRINT. The commands should be entered at the
console (stdin) and one command is entered on each line, thus a command ends with a newline.

            e.g:BUY 1000@25; means buy 1000 shares at the price of 25.
                SELL 500@30; means sell 500 shares at the price of 30.
                PRINT;   print all orders into command line.

## Used technologies
Project is written in java.
## Build
This project is built with maven.
Useful maven commands:

* `install` (simply compiles the project)
* `clean` (cleans the build)

example : `mvn clean install`

## Run the program
* Execute `com.ordermatcher.Main.java`

* `mvn exec:java -Dexec.mainClass="com.ordermatcher.Main"` (maven command to run the main class)
* Or run `com.ordermatcher.Main.java` in any java support IDE 


