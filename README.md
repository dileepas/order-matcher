
## Order Matcher
This is a programme to illustrate a real-time Electronic trading that all buy and sell orders are entered into a order book
and the prices are set according to specific rules.

## Possible Inputs
There are three types of commands: BUY, SELL and PRINT. The commands should be entered at the
console (stdin) and one command is entered on each line, thus a command ends with a newline.

**BUY**

"BUY volume@price" enters a buy order with the specified volume and price. Both price and volume
are positive (> 0) integers. Example "BUY 1000@25" means buy 1000 shares at the price of 25.

**SELL**

"SELL volume@price" enters a sell order with the specified volume and price. Both price and volume
are positive (> 0) integers. Example "SELL 500@30" means sell 500 shares at the price of 30.

**PRINT**

"PRINT" means that all orders in the order book should be printed to the console (stdout), sorted
with the best price at the top (lowest for sell and highest for buy). The order output is the same as
the order input, example

            e.g:
                > PRINT
                ------SELL ------
                SELL 500@30; 
                SELL 100@50 
                ------BUY---------
                BUY 100@25
                BUY 100@50

## Trading rules
* It is a match if a BUY order exist at a higher price or equal to a SELL order in the order book.
* The volume of both orders is reduced as much as possible
* An order can match several other orders if the volume is big enough and the price is
  correct
* The priority of the orders to match is based on the following:
  * On the price that is best for the active order (the one just entered)
  * On the time the order was entered (first come first served)
  * The price of the trade is computed as the order that was in the order book first (the passive
    part)
* When there is a trade a "TRADE price@volume" should be printed out to the console (stdout).


            e.g:
                > SELL 100@10 
                > SELL 100@15
                > BUY 120@17

                TRADE 100@10
                TRADE 20@15
                
                > PRINT
                ---SELL---
                SELL 80@15
                ---BUY-----

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


