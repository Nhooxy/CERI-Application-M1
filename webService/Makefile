#======================================================
#
#  Makefile du WebService
#
#  Auteur : Pierre PEREZ
#
#  Version Actuelle du projet: 1.0.0
#
#======================================================

CLASSNAME=webservice.stream.WebServiceStream
SERVICE_NAME=WebServiceStream

SRCDIR=sources/webservice/stream
WEBINFCLASSDIR=WEB-INF/classes
GENDIR=generated/
GENDIRSRC=generated/MetaServeur
ICEDIR=ice

SLICE=slice2java
JFLAGS=-classpath WEB-INF/classes:./lib/ice-3.6.3.jar

all: precompile generateICE compile generationWS

precompile:
	rm -rf WEB-INF
	mkdir -p WEB-INF/classes
	rm -rf $(GENDIR)/*

generateICE: jexport
	$(SLICE) --output-dir $(GENDIR) $(ICEDIR)/MetaServeur.ice

jexport:
	echo "si l'export auto ne fonctionne pas, le faire manuellement."
	echo "CLASSPATH=$CLASSPATH:.WEB-INF/classes:./lib/ice-3.6.3.jar"
	export CLASSPATH=$$CLASSPATH:.WEB-INF/classes:./lib/ice-3.6.3.jar

compile:
	javac $(GENDIRSRC)/*.java -d $(WEBINFCLASSDIR)/
	javac $(SRCDIR)/*.java -d $(WEBINFCLASSDIR)/ $(JFLAGS)

generationWS:
	wsgen -cp $(WEBINFCLASSDIR):$$CLASSPATH -d $(WEBINFCLASSDIR) -wsdl $(CLASSNAME)
	jar cvf $(SERVICE_NAME).war WEB-INF

startGlassfish:
	~/glassfish4/bin/asadmin start-domain

deploy:
	~/glassfish4/bin/asadmin deploy --force $(SERVICE_NAME).war

