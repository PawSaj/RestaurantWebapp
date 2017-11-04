# Kroki do uruchomienia backend'u
## 1. Załóż bazę MySQL:
* [Zaloguj się/zarejestruj](https://login.oracle.com/mysso/signon.jsp) w Oracle'u
* [Pobierz instalator](http://dev.mysql.com/downloads/windows/installer/5.7.html) MySQL
* Wybierz jedną z pozycji (pełny instalator lub web instalator), klikanij *No thanks, just start my download* na kolejnej stronie. 
  * Po pobraniu uruchom instalator. Należy zainstalować komponenty: **MySQL Server** 	i 	**MySQL Workbench**.
  * Instalacja i wstępna konfiguracja przeprowadzana jest z poziomu instalatora, pomoc na temat instalacji [kliknij tutaj](http://dev.mysql.com/doc/refman/5.7/en/mysql-installer.html).
* Po instalacji i uruchomieniu serwera należy uruchomić **MySQL Workbench**. Niezbędne jest wykonanie następujących czynności:
  * Utworzenie bazy danych:
    * Należy kliknąć przycisk *Create a new schema in the connected server* (ikona bazy danych z plusikiem)
    *	Wpisać nazwę bazy (np. *restaurant_webapp*)
    *	Z listy *Collation* wybrać *utf8_polish_ci*
    *	Kliknąć *Apply* i w następnym oknie nowu *Apply*.
  * Dodanie użytkownika
    * Klikamy *Users and Privileges* -> *Add Account*, wpisujemy login (np. *restaurant*), hasło (dwa razy np.*znaczek338*).
    * Następnie klikamy zakładkę *Schema Privileges* -> *Add entry* -> *Selected schema*.
    * Wskazujemy utworzoną wcześniej bazę i *OK*. Klikamy *Select "ALL"* i klikamy *Apply*.
			
## 2. Przygotuj tomcat'a:
* [Pobierz ostatnią wersję](https://tomcat.apache.org/download-80.cgi) 8.* np. 8.5.23
* Otwórz plik *context.xml*, jest w folderze *conf*
* Wklej 
```xml
        <Resource name="jdbc/restaurant"
				auth="Container"
				type="javax.sql.DataSource"
				driverClassName="com.mysql.jdbc.Driver"
				url="jdbc:mysql://localhost:3306/restaurant_webapp"
				username="restaurant"
				password="znaczek338"
				maxActive="20"
				maxIdle="10"
				maxWait="-1"/>
```
w tagu *Context* po
```xml
      <!-- Uncomment this to disable session persistence across Tomcat restarts -->
      <!--
      <<Manager pathname="" />
      -->
```
## 3. Stwórz konfigurację uruchamiania w IntelliJ:
* Otwórz projekt
* Wybierz *Run* -> *Edit Configuration*
* Wybierz *"+"* -> *Tomcat Server* -> *Local*
* W *Application server* wskaż wcześniej skonfigurowanego tomcat'a wybierając *Configure* -> *"+"* -> w *Tomcat home* wskaż ścieżkę głównego folderu tomcat'a
* W części *Open browser* możesz odhaczyć *After lunch*
* Przejdź do zakładki *Deployment*
* Wybierz *"+"* -> *artifact* -> *bll:war exploded*
* Uruchom i czekaj, zasypię Cię długi log, poczekaj na linijki
```log
[2017-11-04 12:35:06,397] Artifact bll:war exploded: Artifact is deployed successfully
[2017-11-04 12:35:06,397] Artifact bll:war exploded: Deploy took X XXX milliseconds
```
* zweryfikuj czy aplikacja działa pod linkiem
		http://localhost:8080/test
* dodatkowo sprawdź czy w bazie stworzyła się tablica ***user***
