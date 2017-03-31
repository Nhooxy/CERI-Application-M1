#======================================================
#
#  Makefile du WebService
#
#  Auteur : Pierre PEREZ
#
#  Version Actuelle du projet: 1.0.0
#
#======================================================

# Compilateur
CC=g++
SLICE=slice2cpp

# Répertoires
SRCDIR=sources
OBJDIR=tmp
LIBDIR=lib
CONFDIR=conf
GENDIR=generated
ICEDIR=ice
WSDLDIR=wsdl

# Options de compilations
CFLAGS=-g -Wno-deprecated -I /usr/include -I /usr/local/include -D _POSIX_THREADS -std=c++0x -D _REENTRANT
LDFLAGS=-lIce -lIceUtil -lpthread -lvlc
GENFLAGS=-I $(GENDIR)/.
OUTPUTICE=--output-dir $(GENDIR)

# Sources
SRC=$(wildcard $(SRCDIR)/*.cpp)
SRCLIB=$(wildcard $(LIBDIR)/*.cpp)
SRCGEN=$(wildcard $(GENDIR)/*.cpp)
OBJSRC=$(SRC:$(SRCDIR)/%.cpp=$(OBJDIR)/%.o)
OBJLIB=$(SRCLIB:$(LIBDIR)/%.cpp=$(OBJDIR)/%.o)
OBJGEN=$(SRCGEN:$(GENDIR)/%.cpp=$(OBJDIR)/%.o)

# Nom de l'executable
EXEC=WSListenator.run

# Ordre
all: $(EXEC)

generate:
	wsdl2h -g -k -o $(SRCDIR)/WSListenator.h $(WSDLDIR)/WSListenator.wsdl
	soapcpp2 -S -d $(GENDIR)/ $(SRCDIR)/WSListenator.h
	rm -f $(GENDIR)/soapServerLib.cpp
	$(SLICE) $(OUTPUTICE) $(ICEDIR)/*.ice

install: generate $(EXEC)

$(EXEC): $(OBJSRC) $(OBJLIB) $(OBJGEN)
	$(CC) -o $@ $^ $(LDFLAGS)

$(OBJDIR)/%.o: $(GENDIR)/%.cpp
	$(CC) -o $@ -c $< $(GENFLAGS)

$(OBJDIR)/%.o: $(LIBDIR)/%.cpp
	$(CC) -o $@ -c $< $(CFLAGS)

$(OBJDIR)/%.o: $(SRCDIR)/%.cpp
	$(CC) -o $@ -c $< $(CFLAGS)

clean:
	rm -f $(OBJDIR)/*.o

mrproper: clean
	rm -f $(GENDIR)/*
	rm -f $(EXEC)

run:
	./$(EXEC) $(CONFDIR)/WSListenator.conf
