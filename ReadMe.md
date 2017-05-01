# Pierre PEREZ tp Application.

## Résumer du travail effectués

Les difficultés que j'ai pus rencontrer durant le projet sont liés principalement à ICE,
En effet, la configuration de IceBox, m'a pausé problème pendant quelques heures.
De plus mettre en place IceStorm n'a pas était aisé.

Aussi j'avais des ambitions importantes pour ce porjet tel que faire communiqué mon méta serveur
via IceGrid pour les serveurs esclaves.

Cela m'a fait m'égarer sur de nombreux point... Au final, IceGrid n'est pas implémenté...
Le méta serveur communique avec un serveur esclave via iceStorm.

Le client mobile sous android n'a pas été finalisé. Celui-ci peux lire une musique en streaming
mais ou l'url et le nom de la musique est écrite en dure... donc il n'apporte que peu 
d'interrer pour le moment.

Ensuite le client Desktop pour les tests est non fini, en effet, j'ai pas réussi à faire fonctionner 
convenablement le subscriber, globalement il me manque un peu de temps pour finir.
Le client démarre mais le stream ne fonctionne pas. il faudrai que j'ajsute les méthodes que 
j'avais rélasier au tp du premier semestre.

Le coté réseaux sociaux n'est pas implémenter non plus. J'avais pour idée de réaliser des 
compte utilisateur avec leur playlist personnalisé qu'il pouvait partager sur un 'mur'.
Malheureusement, j'ai manqué de temps.

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