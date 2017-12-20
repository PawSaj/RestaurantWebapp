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
  * String z nazwą użytkownika, hasłem oraz rolami

### ```/login``` 

* **metoda:** POST
* **wymagania:** 
  * nie wymaga autoryzacji
  * wymaga argumentów:
    * *username* – nazwa użytkownika jako **EMAIL**
    * *password* – hasło (czysty tekst)
* **co robi:**
  * jeśli użytkownik istnieje i udało się go zalogować:
    * przenosi pod adres ```/successfulLogin```
    * wymaga on autoryzacji
    * zwraca w nagłówku wiadomości Cookie będące tokenem aktualnej sesji. Należy je umieszczać w nagłówku kolejnych requestów
    * zwraca JSON z wszystkimi danymi użytkownika:
        * id
        * email
        * username
        * surname
        * password
        * phone
        * role
        * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
  * jeśli logowanie zakończone neipowodzeniem:
    * przenosi pod adres ```/failedLogin```
    * nie wymaga autoryzacji, ale dostępne tylko po nieudanym logowaniu
    * zwraca JSON z danymi:
        * status - *-8*
        * description - *Login failed.*

### ```/logout```

* **metoda:** GET
* **wymagania:** 
  * nie wymaga autoryzacji (użycie logout bez zalogowania kończy się sukcesem)
  * wymaga argumentów:
    * *coockie* aktualnej sesji w nagłówku
* **co robi:**
  * wylogowuje użytkownika o zadanym *coockie*:
    * przenosi pod adres ```/successfulLogout``` (przy nieudanym nic się nie dzieje)
    * zwraca JSON z danymi:
        * status - *0*
        * description - *Request completed with no errors.*

### ```/registration```

* **metoda:** POST
* **wymagania:** 
  * nie wymaga autoryzacji
  * wymaga w ciele JSON'a z danymi:
    * email - email
    * username – nazwa użytkownika
    * surname - nazwisko
    * password – hasło (czysty tekst)
  * opcjonalne argumenty:
    * *phone* - jako int numer telefonu
    * *image* - w formie nazwy obrazu, którą można uzyskać wysyłając obraz pod adres *```/sendImage```*
* **co robi:**
  * jeśli użytkownik nie istnieje i udało się go zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *3*
        * descripton - *Registration successful*
  * jeśli rejestracja zakończone neipowodzeniem, email zduplikowany:
    * zwraca JSON z danymi:
        * status - *-2*
        * descripton - *Email is taken. Try another.*

### ```/menu```

* **metoda:** GET
* **wymagania:** 
  * nie wymaga autoryzacji
* **co robi:**
  * zwraca JSON z listą wszystkich wszystkich dań i ich składników w formie:
    * każdy kolejny element listy to:
        * para *id* (będące *id* produktu w bazie) jako nazwa, a wartośc to lista informacji o daniu
    * w liście informacji o daniu znajdują się:
        * name - nazwa dania
        * ingredients - lista składników
            * wypisanych w formie indeksów (w bazie) + nazwa
        * describe - opis dania

### ```/admin/meal```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji
  * wymaga w ciele JSON'a z danymi:
	* name - nazwa dania  
  * opcjonalne argumenty:
  	* ingredients - lista składników
		* wypisanych kolejno samych nazw składników np. { "name":"kurczak", "name":"oliwki"}    
	* describe - opis dania
    * price - cena dania
    * *image* - w formie nazwy obrazu, którą można uzyskać wysyłając obraz pod adres *```/sendImage```*
* **co robi:**
  * jeśli danie nie istnieje i udało się je zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie dania zakończone neipowodzeniem, nazwa zduplikowana:
    * zwraca JSON z danymi:
        * status - *-9*
        * descripton - *Meal is already exist.*


### ```/admin/meal/{id}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id użytkownika, które chcemy poddać modyfikacji
  * wymaga w ciele JSON'a z danymi:
	* name - nazwa dania
  	* ingredients - lista składników
		* wypisanych kolejno samych nazw składników np. { "name":"kurczak", "name":"oliwki"}    
	* describe - opis dania
    * price - cena dania
    * *image* - w formie nazwy obrazu, którą można uzyskać wysyłając obraz pod adres *```/sendImage```*
  * **jeśli jakaś z danych nie została zmieniona należy ją także umieścić w podstawowej wersji**    
* **co robi:**
  * jeśli powodzenie aktualizacji danych dania zwraca JSON z danymi:
    * status - *0*
	* descripton - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * danie nie istnieje - błędne *id*:
        * status - *-10*
        * description - *Meal doesn't exist.*
    * nowa nazwa dania już jest zajęta:
        * status - *-9*
        * descripton - *Meal is already exist.*
    
### ```/admin/meal/{id}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id dania, które chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia dania zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * danie nie istnieje - błędne *id*:
        * status - *-10*
        * description - *Meal doesn't exist.*       
        
### ```/sendImage```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji
  * JSON z obrazu w formie:
    * *image* - nazwa, *<sam_kod_obrazu_zakodowany_za_pomocą_BASE64>* - wartość String
  * dozwolone obrazy tylko w formacie *.jpg*
* **co robi:**
  * jeśli powodzenie zwraca JSON z danymi:
    * status - *1*
    * description - *Image saved successfully.*
    * filename - nazwa zapisanego obrazu
  * jeśli niepowodzenie JSON z danymi dla różnych błędów:
    * niewłaściwy ciąg BASE64:
        * status - *-4*
        * description - *Wrong encode of base64.*
    * pusty ciąg danych obrazu
        * status - *-6*
        * description - *No data to save or data is corrupted.*
    * błąd zapisu na serwerze
        * status - *-5*
        * description - *Image saving failure.*
        
### ```/getImage/{imageName}```

* **metoda:** GET
* **wymagania:** 
  * nie wymaga autoryzacji
  * w adresie *{imageName}* oznacza PathVariable, jest to nazwa obrazu który chcemy uzyskać
* **co robi:**
  * jeśli powodzenie zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
    * image - ciąg zakodowany w base64 będący danymi obrazu
  * jeśli niepowodzenie JSON z danymi dla błędu plik nie istnieje:
    * status - *-3*
    * description - *No file found.*
        
### ```/user```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji 
* **co robi:**
  * zwraca JSON z wszystkimi danymi użytkownika:
    * id
    * email
    * username
    * surname
    * password
    * phone
    * role
    * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
    
### ```/user/{id}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id użytkownika, które chcemy poddać modyfikacji
  * JSON z danymi użytkownika:
    * email
    * username
    * surname
    * password
    * phone
    * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
  * **jeśli jakaś z danych nie została zmieniona należy ją także umieścić w podstawowej wersji**    
* **co robi:**
  * jeśli powodzenie aktualizacji danych usera zwraca JSON z danymi:
    * status - *2*
    * description - *User updated.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * user nie istnieje - błędne *id*:
        * status - *-1*
        * description - *User doesn't exist.*
    * nowy adres email już jest zajęty:
        * status - *-2*
        * description - *Email is taken. Try another.*
    * użytkownik próbuję zmienić dane innego użytkownika:
        * status - *-7*
        * description - *You try to change data of different user.*
    
### ```/user/{id}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id użytkownika, które chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia usera zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
    * redirect - */logout*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * user nie istnieje - błędne *id*:
        * status - *-1*
        * description - *User doesn't exist.*
    * użytkownik próbuje usunąć innego użytkownika:
        * status - *-7*
        * description - *You try to change data of different user.*        
        
### ```/admin/users```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
* **co robi:**
  * zwraca JSON z danymi wszystkich użytkownikóww formie:
    * każdy kolejny element listy to para *id* użytkownika oraz lista z pozostałymi danymi użytkownika
        * id
        * email
        * username
        * surname
        * password
        * phone
        * role
        * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
            
### ```/admin/users/{id}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id użytkownika, które chcemy poddać modyfikacji
  * JSON z danymi użytkownika:
    * email
    * username
    * surname
    * password
    * phone
    * role
    * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
  * **jeśli jakaś z danych nie została zmieniona należy ją także umieścić w podstawowej wersji**    
* **co robi:**
  * jeśli powodzenie aktualizacji usera zwraca JSON z danymi:
    * status - *2*
    * description - *User updated.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * user nie istnieje - błędne *id*:
        * status - *-1*
        * description - *User doesn't exist.*
    * nowy adres email już jest zajęty:
        * status - *-2*
        * description - *Email is taken. Try another.*
    
### ```/admin/user/{id}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id użytkownika, które chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia usera zwraca JSON z danymi:
    * jeśli admin sam siebie:
        * status - *0*
        * description - *Request completed with no errors.*
        * redirect - */logout*
    * jeśli admin innego użytkownika:
        * status - *0*
        * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * user nie istnieje - błędne *id*:
        * status - *-1*
        * description - *User doesn't exist.*
    * użytkownik próbuje usunąć innego użytkownika:
        * status - *-7*
        * description - *You try to change data of different user.*           
        
        
        
        
        
        
        
        
        
        
        
