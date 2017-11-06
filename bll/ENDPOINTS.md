# Endpoints backend'u
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/
### ```/test``` 
* **metoda:** GET/POST/DELETE/PUT
* **wymagania:** nie wymaga autoryzacji
* **co robi:** 
  * nic 
  * zwraca String *good*

### ```/login``` 

* **metoda:** POST
* **wymagania:** 
  * nie wymaga autoryzacji
  * wymaga argumentów:
    * *username* – nazwa użytkownika
    * *password* – hasło (czysty tekst)
* **co robi:**
  * jeśli użytkownik istnieje i udało się go zalogować:
    * przenosi pod adres ```/successfulLogin```
    * wymaga on autoryzacji
    * zwraca String *login successful*
  * jeśli logowanie zakończone neipowodzeniem:
    * przenosi pod adres ```/failedLogin```
    * nie wymaga autoryzacji, ale dostępne tylko po nieudanym logowaniu
    * zwraca String *error*

### ```/logout```

* **metoda:** GET
* **wymagania:** 
  * nie wymaga autoryzacji (użycie logout bez zalogowania kończy się sukcesem)
  * wymaga argumentów:
    * *coockie* aktualnej sesji w nagłówku
* **co robi:**
  * wylogowuje użytkownika o zadanym *coockie*:
    * przenosi pod adres ```/successfulLogout``` (przy nieudanym nic się nie dzieje)
    * zwraca String *logout successful*

### ```/registration```

* **metoda:** POST
* **wymagania:** 
  * nie wymaga autoryzacji
  * wymaga argumentów:
    * *username* – nazwa użytkownika
    * *password* – hasło (czysty tekst)
* **co robi:**
  * jeśli użytkownik nie istnieje i udało się go zapisać w bazie:
    * zwraca String *register successful*
  * jeśli rejestracja zakończone neipowodzeniem:
    * zwraca String *user already exist*
