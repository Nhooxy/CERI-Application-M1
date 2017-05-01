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
LIBDIR=lib
GENDIR=generated
ICEDIR=ice

# Options de compilations
JDIRFLAGS=-g -d classes
JFLAGS=-classpath classes:./lib/jl1.0.1.jar:./lib/ice-3.6.3.jar:./lib/icestorm-3.6.3.jar
OUTPUTICE=--output-dir $(CLASSDIR)/$(GENDIR)

# Nom de l'executable
EXEC=Client.jar

all: jar

createDir:
	mkdir -p $(CLASSDIR)/$(GENDIR)

generate: createDir
	$(SLICE) $(OUTPUTICE) $(ICEDIR)/Client.ice
	$(SLICE) $(OUTPUTICE) $(ICEDIR)/MetaServeur.ice

Jexport: generate
	echo "si l'export auto ne fonctionne pas, le faire manuellement."
	export CLASSPATH=$CLASSPATH:./classes:./lib/ice-3.6.3.jar:./lib/jl1.0.1.jar:./lib/icestorm-3.6.3.jar

compile: Jexport
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(SRCDIR)/Sound.java
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(CLASSDIR)/$(GENDIR)/Client/*.java
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(CLASSDIR)/$(GENDIR)/MetaServeur/*.java
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(SRCDIR)/*.java

jar: compile
	jar cvmf META-INF/MANIFEST.MF $(EXEC) $(CLASSDIR)/* config/* $(LIBDIR)/*

run:
	java Client

start:
	java -jar Client.jar

clean:
	rm -rf $(CLASSDIR)/*
	rm -rf $(EXEC)
