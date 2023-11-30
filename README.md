# virtual-cafe
CE303 - “Virtual Café” system, using a socket based client-server architecture

## Before you can use the project

You need to compile the source code
The project is made from java files so you can compile from the terminal with the javac command 
or by opening the folder in a Java IDE like IntelliJ and using the provided build option

## How to run the programs

1. First you need the server running - if you start the client first it will fail to connect and gracefully shutdown with an error message. 
To start the server run the Barista program in a terminal
This will start a server, listen for connections and print logs to the terminal output; once a client connects it will handle it in a different thread
2. Run the Customer program from a terminal to start a client - this will connect to the server and send the commands you input in the terminal
3. If you want you can have multiple clients running at the same time, just start Customer instances in separate terminals

   
## Interracting with the client

It will first ask you for your name, this will be used by the server to identify your orders
You can stop the program anytime by sending the `exit` message or pressing `CTRL+C` in the terminal
To send order commands start your message with "order" and list your desired beverages in the format <QUANTITY> <BEVERAGE_NAME = "coffee" or "tea">


Supported ways to order:
- `order 1 tea`
- `order 3 coffees`
- `order 1 tea and 2 coffees please`
- `order 1 coffee and 1 tea plus 2 coffees` it will correctly compute the total number of teas and coffees
- `order 1 coffee and any other gibberish you want to add, as long as the drinks are specified in the right format the server will parse the relevant information`

Not allowed: 
- negative quantities, `order -48 teas` will be refused
- not providing a quantity `order tea` or not immediatley following the quantity with the drink name (coffee or tea) `order 1 green tea`

If the same user orders multiple times their existing order will be updated instead of creating separate ongoing orders

To check the status of your order send the `order status` command
