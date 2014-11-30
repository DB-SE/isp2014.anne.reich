Libraries und executable Jar-Datei sind im Release "task2 - mangaDBv1" in meinem GitHub-Repo zu finden

+++++++++++++++++++++++++++++

Folgende Features sind eingebaut und an- bzw. abwählbar:
- reset (Zurücksetzen der kompletten Daten)
- dataTransfer (Import & Export von Daten)
- byLetter (Suche: Buttons)
- byTextInput (Suche: Textfeld)
- detailViewManga (Manga-Detailansicht)
- xml (Datenzugriff: xml)
- database (Datenzugriff: db)

Folgende Konfigurationen gibt es: 
- X - bedeutet aktiv gesetzt)
- Es existieren noch mehr Features im Modellbaum. Die sind aber nicht im Code implementiert)
- database ist nie aktiviert, weil dafür eine lokale Datenbank von nöten wäre

config 1:

| reset | dataTransfer | byLetter | byTextInput | detailViewManga | xml | database |
|-------|--------------|----------|-------------|-----------------|-----|----------|
|...x...|......x.......|....x.....|......x......|........x........|..x..|..........| 
|-------|--------------|----------|-------------|-----------------|-----|----------|

config 2:

| reset | dataTransfer | byLetter | byTextInput | detailViewManga | xml | database |
|-------|--------------|----------|-------------|-----------------|-----|----------|
|...x...|......x.......|..........|.............|.................|..x..|..........| 
|-------|--------------|----------|-------------|-----------------|-----|----------|

config 3:

| reset | dataTransfer | byLetter | byTextInput | detailViewManga | xml | database |
|-------|--------------|----------|-------------|-----------------|-----|----------|
|.......|..............|....x.....|.............|........x........|..x..|..........| 
|-------|--------------|----------|-------------|-----------------|-----|----------|

config 4:

| reset | dataTransfer | byLetter | byTextInput | detailViewManga | xml | database |
|-------|--------------|----------|-------------|-----------------|-----|----------|
|.......|......x.......|....x.....|......x......|.................|..x..|..........| 
|-------|--------------|----------|-------------|-----------------|-----|----------|

config 5:

| reset | dataTransfer | byLetter | byTextInput | detailViewManga | xml | database |
|-------|--------------|----------|-------------|-----------------|-----|----------|
|.......|......x.......|..........|......x......|........x........|..x..|..........| 
|-------|--------------|----------|-------------|-----------------|-----|----------|

+++++++++++++++++++++++++++++++++++++++++++++++



