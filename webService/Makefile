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

all: precompile compile generationWS clean

precompile:
	rm -rf $(TMPDIR)
	mkdir $(TMPDIR)
	rm -rf WEB-INF
	mkdir -p WEB-INF/classes

compile:
	javac $(SRCDIR)/*.java -d $(TMPDIR)/

generationWS:
	cp -r $(TMPDIR)/* WEB-INF/classes/
	wsgen -cp WEB-INF/classes:$$CLASSPATH -d WEB-INF/classes -wsdl $(CLASSNAME)
	jar cvf $(SERVICE_NAME).war WEB-INF

clean:
	rm -rf $(TMPDIR)

startGlassfish:
	~/glassfish4/bin/asadmin start-domain

deploy:
	~/glassfish4/bin/asadmin deploy --force $(SERVICE_NAME).war

