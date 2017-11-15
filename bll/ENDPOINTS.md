# Endpoints backend'u
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/
### ```/test``` 
* **metoda:** GET/POST/DELETE/PUT
* **wymagania:** nie wymaga autoryzacji
* **co robi:** 
  * nic 
  * zwraca String *good*
  
### ```/test2``` 
* **metoda:** GET/POST/DELETE/PUT
* **wymagania:** wymaga autoryzacji
* **co robi:** 
  * nic 
  * zwraca String *good* lub błąd 401 jeśli się nie zalogowano

### ```/login``` 

* **metoda:** POST
* **wymagania:** 
  * nie wymaga autoryzacji
  * wymaga argumentów:
    * *username* – nazwa użytkownika jako email
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
    * *email* - email
    * *username* – nazwa użytkownika
    * *surname* - nazwisko
    * *password* – hasło (czysty tekst)
  * opcjonalne argumenty:
    * *phone* - jako int numer telefonu
    * *image* - avatar jako byte[]
* **co robi:**
  * jeśli użytkownik nie istnieje i udało się go zapisać w bazie:
    * zwraca String *register successful*
  * jeśli rejestracja zakończone neipowodzeniem:
    * zwraca String *user already exist*
