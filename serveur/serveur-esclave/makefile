#======================================================
#
#  Makefile du Serveur Esclabe Ice
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
ICEDIR=ice
GENDIR=generated

# Options de compilations
CFLAGS=-lIce -lIceUtil -lpthread -lvlc -lIceStorm
GENFLAGS=-I $(GENDIR)/.
OUTPUTICE=--output-dir $(GENDIR)

# Sources
SRC=$(wildcard $(SRCDIR)/*.cpp)
SRCGEN=$(wildcard $(GENDIR)/*.cpp)
OBJSRC=$(SRC:$(SRCDIR)/%.cpp=$(OBJDIR)/%.o)
OBJGEN=$(SRCGEN:$(GENDIR)/%.cpp=$(OBJDIR)/%.o)

# Nom de l'executable
EXEC=ServeurEsclave.run

all: generate $(EXEC)

generate:
	$(SLICE) $(OUTPUTICE) $(ICEDIR)/MetaServeur.ice

$(EXEC): $(OBJSRC) $(OBJGEN)
	$(CC) -o $@ $^ $(CFLAGS)

$(OBJDIR)/%.o: $(GENDIR)/%.cpp
	$(CC) -o $@ -c $< $(GENFLAGS) $(CFLAGS)

$(OBJDIR)/%.o: $(SRCDIR)/%.cpp
	$(CC) -o $@ -c $< $(CFLAGS)

run:
	./$(EXEC)

clean:
	rm -rf $(OBJDIR)/*.o

mrproper: clean
	rm -rf $(EXEC)
