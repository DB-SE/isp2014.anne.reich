Libraries und executable Jar-Datei sind im Release "task2 - mangaDBv1" in meinem GitHub-Repo zu finden

+++++++++++++++++++++++++++++

Projekt starten (ohne jar, über cmd)

- in den Ordner bin\ hinbewegen
- AllManga.xml muss unbedingt im Ordner bin\ und evtl zusätzlich in den Überordner von bin\ liegen
- folgendes eingeben:

java -cp .:<path>/lib/* isp1415.ar.Main <features>

++++++++++++++++++++++++++++++

Projekt starten (mit jar, über cmd)

- in den Ordner gehen, wo die jar Datei liegt (/jar/)
- AllManga.xml sollte sich im selben Ordner befinden
- folgendes im CMD eingeben:

java -jar <executable>.jar <features>

++++++++++++++++++++++++++++++++++++

Bsp.: <path> = /home/yurili/eclipse_workspaces/isp2014_anne_reich/task2
Folgende Bibliotheken werden benötigt: jdom-2.0.5.jar , mysql-connector-java-5.1.23-bin.jar 

####Bitte beachten: Die features in der richtigen Reihenfolge wie hier benannt angeben!!####
<features> = 
<db|xml> - Dateninhalte aus lokaler Datenbank oder XML-Datei (jetzt bitte xml nutzen)
<transferY|transferN> - Soll Import/Export möglich sein? (Unter Menü -> Datei sind die Felder dann sichtbar) Die Funktionen Import und Export kann erzeit noch zu crashs führen, also nicht benutzen 
<buttons|field> - besteht die Durchsuchmöglichkeit in der Bibliotheksübersicht aus Buttons(von A-Z) oder einer Suchleiste (bei Suchleiste Bsp: Autor: Arina Tanemura, Verlag: Tokyopop)
<detailsY|detailsN> - Sollen Detail-Ansicht der einzelnen Reihen sichtbar sein? (In der Bibliotheksübersicht unten)

+++++++++++++++++++++++++++++++++++++++++++++++


