all: compile

export:
	slice2java --output-dir classes/generer Bibliotheque.ice
	export CLASSPATH=$CLASSPATH:./classes:./lib/ice-3.6.3.jar:./lib/jl1.0.1.jar

compile: export
	javac -d classes -classpath classes:./lib/jl1.0.1.jar Sound.java
	javac -d classes -classpath classes:./lib/ice-3.6.3.jar Client.java classes/generer/Bibliotheque/*.java

run:
	java Client
