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
TMPDIR=tmp
WEBINFCLASSDIR=WEB-INF/classes
GENDIR=generated/MetaServeur
ICEDIR=ice

SLICE=slice2java
JFLAGS=-classpath classes:./lib/jl1.0.1.jar

all: precompile generateICE compile generationWS clean

precompile:
	rm -rf $(TMPDIR)
	mkdir $(TMPDIR)
	rm -rf WEB-INF
	mkdir -p WEB-INF/classes

generateICE:
	$(SLICE) --output-dir $(GENDIR) $(ICEDIR)/MetaServeur.ice

compile:
	javac $(SRCDIR)/*.java -d $(TMPDIR)/
	javac $(GENDIR)/*.java -d $(TMPDIR)/
	javac -classpath classes:./ice-3.6.3.jar Client.java classes/generer/Bibliotheque/*.java


generationWS:
	cp -r $(TMPDIR)/* WEB-INF/classes/
	wsgen -cp $(WEBINFCLASSDIR):$$CLASSPATH -d $(WEBINFCLASSDIR) -wsdl $(CLASSNAME)
	jar cvf $(SERVICE_NAME).war WEB-INF

clean:
	rm -rf $(TMPDIR)

startGlassfish:
	~/glassfish4/bin/asadmin start-domain

deploy:
	~/glassfish4/bin/asadmin deploy --force $(SERVICE_NAME).war

