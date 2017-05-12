# Pierre PEREZ tp Application.

## voir le rapport.pdf

## Pour tester l'application

Chaque répertoire qui contiennent des éléments ont un makefile. (client/ serveur:meta-serveur/
serveur:serveur-esclave / configIceBox)

make : compiler le tout.

make run : démarre l'application concernée.

(
1 : make run dans configIceBox

2 : make dans serveur/meta-serveur

3 : make run dans serveur/meta-serveur

4 : make dans serveur/serveur-esclave

5 : make run dans serveur/serveur-esclave

6 : make dans client

7 : make run dans client
)