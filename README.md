# java-simple-message-system
The SimpleMessageSystem Program divide into 2 program
	1. Client
	2. Server

# How to run
In order to run the programs. You need to execute and run both client and server.
By using these commands.

##      First, run the client
		1. cd Client/src
		2. javac *.java
		3. java ClientSystem

##      Second, run the server
		1. cd Server/src
		2. javac *.java
		3. java ThreadPooledServer

# Detail of the program
After you run the program and do some operation like register.
The server will create the database folder.
The database after you register choose looks like this

	database
	       |- message-collection
	       |                   |- {username}
	       |                               |- inbox
           |                               |- outbox
           |- User
	             |- {username}.txt
