# Rest-Login-Service-UWP

Eine genauere Dokumentation ist im Verzeichnis `latex-protocol` zu finden.

## Funktion

Beim Rest Login Service handelt es sich um eine REST-Schnittstelle, diese Schnittstelle findet man unter `Cloud-Datenmanagement-Json`. Unter `GK10.2` befindet sich die UWP App, welche auf Windows 10 sowie Windows Mobile 10 lauffähig ist. Unter `Testing` befindet sich der Code zum Testen der GUI, welches mittels Microsoft UI Automation umgesetzt worden ist.

## Deployen der REST-Schnittstelle

Die REST-Schnittstelle ist im Unterverzeichnis `Cloud-Datenmangement-Json` zu finden, diese Schnittstelle kann mittels folgenden den Befehl gestartet:

`mvn tomcat7:run-war`

## App

Die App kann mittels Visual Studio 2017 geöffnet werden bzw. unter `GK10.2` befinden sich zwei AppPackages für ARM und x64. Diese können mittels dem PowerShell Skript, welches ebenfalls in diesem Ordner ist, installiert werden.

Derzeit ist die App so konfiguriert, dass diese die Adresse ~~37.252.185.24~~ verwendet.
