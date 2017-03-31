#======================================================
#
#  Makefile du client desktop (pour les tests)
#
#  Auteur : Pierre PEREZ
#
#  Version Actuelle du projet: 1.0.0
#
#======================================================

# Compilateur
CC=javac
SLICE=slice2java

# Répertoires
SRCDIR=sources
CLASSDIR=classes
GENDIR=generated
LIBDIR=lib
ICEDIR=ice

# Options de compilations
JDIRFLAGS=-d classes
JFLAGS=-classpath classes:./lib/jl1.0.1.jar
J2FLAGS=-classpath classes:./lib/ice-3.6.3.jar
OUTPUTICE=--output-dir $(CLASSDIR)/$(GENDIR)

all: compile

createDir:
	mkdir -p $(CLASSDIR)/$(GENDIR)

generate: createDir
	$(SLICE) $(OUTPUTICE) $(ICEDIR)/Bibliotheque.ice

Jexport: generate
	export CLASSPATH=$CLASSPATH:./classes:./lib/ice-3.6.3.jar:./lib/jl1.0.1.jar

compile: Jexport
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(SRCDIR)/Sound.java
	$(CC) $(JDIRFLAGS) $(J2FLAGS) $(SRCDIR)/Client.java $(CLASSDIR)/$(GENDIR)/Bibliotheque/*.java

run:
	java Client

clean:
	rm -rf $(CLASSDIR)/*
