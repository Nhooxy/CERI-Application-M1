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

# Répertoires
SRCDIR=sources
CLASSDIR=classes
LIBDIR=lib

# Options de compilations
JDIRFLAGS=-g -d classes
JFLAGS=-classpath classes:./lib/jl1.0.1.jar
OUTPUTICE=--output-dir $(CLASSDIR)/$(GENDIR)

# Nom de l'executable
EXEC=Client.jar

# Variable
CLASSNAME=Client
SERVICE_NAME=WebServiceStream
SERVICE_URL=http://127.0.0.1:8080/WebServiceStream/WebServiceStreamService?wsdl

all: precompile wsimport jar

precompile:
	rm -rf $(CLASSDIR)
	mkdir $(CLASSDIR)

wsimport:
	wsimport $(SERVICE_URL)
	mv webservice $(CLASSDIR)/webservice

jexport:
	echo "si l'export auto ne fonctionne pas, le faire manuellement."
	export CLASSPATH=$$CLASSPATH:./classes:./lib/jl1.0.1.jar

compile: jexport
	$(CC) $(JDIRFLAGS) $(JFLAGS) $(SRCDIR)/client/*

jar: compile
	unzip $(LIBDIR)/jl1.0.1.jar -d $(LIBDIR)
	rm -rf $(LIBDIR)/META-INF
	jar cvmf META-INF/MANIFEST.MF $(EXEC) $(CLASSDIR)/* $(LIBDIR)/*
	rm -rf $(LIBDIR)/javazoom

run:
	java -jar Client.jar
