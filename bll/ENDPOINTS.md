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
  * zwraca JSON z wszystkimi danymi użytkownika w postaci:
    * status - *-8*
    * description - *Login failed.*
    * user - dane użytkownika:
        * id
        * email
        * name
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
    * name – nazwa użytkownika
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
  * jeśli brak jakiejś danej:
        * status - *-14*
        * descripton - *Missing data.*
        * missing - dana, która jest wymagana, a której brakuje np. email

### ```/menu```

* **metoda:** GET
* **wymagania:** 
  * nie wymaga autoryzacji
* **co robi:**
  * zwraca JSON z listą wszystkich dań i ich składników, posegregowanych na kategorie w formie:
    * każdy kolejny element listy to obiekt składający się z:
        * category - kategoria dań
        * body - lista dań należących do danej kategorii
    * w liście informacji o daniu znajdują się:
        * name - nazwa dania
        * ingredients - lista składników
            * wypisanych w formie indeksów (w bazie) + nazwa
        * price - cena (jeśli danie jej nie ma, JSON nie zawiera tej danej)

### ```/admin/meal/{mealId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id dania, które chcemy pobrać
* **co robi:**
  * zwraca JSON z daniem:
     * category - kategoria dań
     * name - nazwa dania
     * ingredients - lista składników
         * wypisanych w formie indeksów (w bazie) + nazwa
     * price - cena (jeśli danie jej nie ma, JSON nie zawiera tej danej)

### ```/admin/meal/categories```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
* **co robi:**
  * zwraca JSON z istniejącymi kategoriami dań w formie listy, gdize każdy kolejny element to nowa kategoria
     
### ```/admin/meal```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
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
  * jeśli brak jakiejś danej:
    * status - *-14*
    * descripton - *Missing data.*
    * missing - dana, która jest wymagana, a której brakuje


### ```/admin/meal/{id}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id dania, które chcemy poddać modyfikacji
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
    * jeśli brak jakiejś danej:
        * status - *-14*
        * descripton - *Missing data.*
        * missing - dana, która jest wymagana, a której brakuje
    
### ```/admin/meal/{id}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
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
    * name
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
    * name
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
    * każdy kolejny element listy to użytkownik zawierający dane:
        * id
        * email
        * name
        * surname
        * password
        * phone
        * role
        * image - nazwę obrazu, który można pobrać korzystając z *```/getImage```*
             
### ```/admin/users/{userId}```
   
* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id użytkownika, które chcemy poddać modyfikacji
* **co robi:**
  * zwraca JSON z danymi wszystkich użytkownikóww formie:
     * id
     * email
     * name
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
    * name
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
    * jeśłi brak jakiejś danej:
        * status - *-14*
        * descripton - *Missing data.*
        * missing - dana, która jest wymagana, a której brakuje
    
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
        
### ```/admin/tables```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
* **co robi:**
  * zwraca JSON z listą wszystkich stolików, gdzie każdy stolik to kolejny element listy:
	* id - id
	* tableNumber - numer stolika
	* seats - ilość miejsc
	* floor - piętro

### ```/admin/tables/{tableId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id stolika, które chcemy poddać modyfikacji
* **co robi:**
  * zwraca JSON ze stolikiem:
    * id - id
	* tableNumber - numer stolika
	* seats - ilość miejsc
	* floor - piętro
   
### ```/admin/tables```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * wymaga w ciele JSON'a z danymi do zapisania stolika:
	* tableNumber - numer stolika
	* seats - ilość miejsc
  * opcjonalne argumenty:
  	* floor - piętro 
* **co robi:**
  * jeśli danie nie istnieje i udało się je zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie dania zakończone neipowodzeniem, nazwa zduplikowana:
    * zwraca JSON z danymi:
        * status - *-11*
        * descripton - *Table is already exist.*
  * jeśli dodawanie dania zakończone neipowodzeniem, brak danej:
	* zwraca JSON z danymi:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje


### ```/admin/tables/{tableId}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id stolika, które chcemy poddać modyfikacji
  * wymaga w ciele JSON'a z danymi:
	* tableNumber - numer stolika
	* seats - ilość miejsc
	* floor - piętro
  * **jeśli jakaś z danych nie została zmieniona należy ją także umieścić w podstawowej wersji**    
* **co robi:**
  * jeśli powodzenie aktualizacji danych dania zwraca JSON z danymi:
    * status - *0*
	* descripton - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * danie nie istnieje - błędne *id*:
        * status - -12*
        * description - *Table doesn't exist.*
    * nowa nazwa dania już jest zajęta:
        * status - *-11*
        * descripton - *Table is already exist.*
	* brak wymaganej danej:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje
    
### ```/admin/tables/{tableId}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id stolika, które chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia stolika zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * stolik nie istnieje - błędne *id*:
        * status - -12*
        * description - *Table doesn't exist.*
        
### ```/tableReservation```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
* **co robi:**
  * zwraca JSON z listą wszystkich rezerwacji stolików danego użytkownika, gdzie każda rezerwacja to kolejny element listy:
	* id - id
	* date - data stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik w postaci obiektu z danymi:
		* id - id
		* tableNumber - numer stolika
		* seats - ilość miejsc
		* floor - piętro
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*	

### ```/tableReservation/{tableReservationId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji stolika, które chcemy pobrać
* **co robi:**
  * zwraca JSON z rezerwacją stolika:
    * id - id
	* date - data stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik w postaci obiektu z danymi:
		* id - id
		* tableNumber - numer stolika
		* seats - ilość miejsc
		* floor - piętro
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*
  * jeśli próba pobrania rezerwacji innego użytkownika:
        * status - *-7*
        * descripton - *You try to access data of different user.*	
	
### ```/getReservedTables/{date}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{date}* jest to data początku tygodnia który chcemy pobrać w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
* **co robi:**
  * zwraca JSON z listą wszystkich wszystkich rezerwacji stolików w danym okresie, gdzie każda rezerwacja stolika to kolejny element listy:
    * id - id
	* date - data rezerwacji stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik w postaci obiektu z danymi:
		* id - id
		* tableNumber - numer stolika
		* seats - ilość miejsc
		* floor - piętro
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*
   
### ```/tableReservation```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji stolika:
	* tableReservationDate - data rezerwacji stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik jako obiekt z danymi:
		* id - id stolika
		* reszta niepotrzebna, opcjonalnie
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak stolika:
    * zwraca JSON z danymi:
        * status - *-12*
        * descripton - *Table doesn't exist.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, próba modyfikacji rezerwacji innego użytkownika:
    * zwraca JSON z danymi:
        * status - *-7*
        * descripton - *You try to access data of different user.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak danej:
	* zwraca JSON z danymi:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, stolik w tym czasie zajęty:
	* zwraca JSON z danymi:
		* status - *-13*
		* descripton - *Table is occupied.*	

### ```/tableReservation/{tableReservationId}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji stolika, który chcemy zaktualizować
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji stolika:
	* tableReservationDate - data rezerwacji stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik jako obiekt z danymi:
		* id - id stolika
		* reszta niepotrzebna, opcjonalnie
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak stolika:
    * zwraca JSON z danymi:
        * status - *-12*
        * descripton - *Table doesn't exist.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, próba modyfikacji rezerwacji innego użytkownika:
    * zwraca JSON z danymi:
        * status - *-7*
        * descripton - *You try to access data of different user.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak danej:
	* zwraca JSON z danymi:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, stolik w tym czasie zajęty:
	* zwraca JSON z danymi:
		* status - *-13*
		* descripton - *Table is occupied.*	
    
### ```/tableReservation/{tableReservationId}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji stolika, którą chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia stolika zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędne *{id}*:
        * status - -15*
        * description - *Table reservation doesn't exist.*  
	* próba modyfikacji rezerwacji innego użytkownika:
        * status - *-7*
        * descripton - *You try to access data of different user.*		
		
### ```/admin/tableReservation```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
* **co robi:**
  * zwraca JSON z listą wszystkich rezerwacji stolików, gdzie każda rezerwacja to kolejny element listy:
	* id - id
	* date - data stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* userId - id użytkownika, którego dotyczy rezerwacja
	* table - stolik w postaci obiektu z danymi:
		* id - id
		* tableNumber - numer stolika
		* seats - ilość miejsc
		* floor - piętro
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*

### ```/admin/tableReservation/{tableReservationId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id stolika, które chcemy poddać modyfikacji
* **co robi:**
  * zwraca JSON z rezerwacją stolika:
    * id - id
	* date - data stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* userId - id użytkownika, którego dotyczy rezerwacja
	* table - stolik w postaci obiektu z danymi:
		* id - id
		* tableNumber - numer stolika
		* seats - ilość miejsc
		* floor - piętro
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*
   
### ```/admin/tableReservation```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji stolika:
	* user - użytkownik jako obiekt z danymi:
		* id - id użytkownika
		* reszta niepotrzebna
	* tableReservationDate - data rezerwacji stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik jako obiekt z danymi:
		* id - id stolika
		* reszta niepotrzebna, opcjonalnie
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak stolika:
    * zwraca JSON z danymi:
        * status - *-12*
        * descripton - *Table doesn't exist.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak danej:
	* zwraca JSON z danymi:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, stolik w tym czasie zajęty:
	* zwraca JSON z danymi:
		* status - *-13*
		* descripton - Table is occupied.*

### ```/admin/tableReservation/{tableReservationId}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji stolika, który chcemy zaktualizować
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji stolika:
  	* user - użytkownik jako obiekt z danymi:
		* id - id użytkownika
		* reszta niepotrzebna
	* tableReservationDate - data rezerwacji stolika w formacie "YYYY-MM-DDTHH:MM:SS+HH:MM", gdzie litera T to po prostu litera T, musi być, oddziela datę od czasu, a +HH:MM to strefa czasowa UTC
	* table - stolik jako obiekt z danymi:
		* id - id stolika
		* reszta niepotrzebna, opcjonalnie
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak stolika:
    * zwraca JSON z danymi:
        * status - *-12*
        * descripton - *Table doesn't exist.*
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, brak danej:
	* zwraca JSON z danymi:
		* status - *-14*
		* descripton - *Missing data.*
		* missing - dana, która jest wymagana, a której brakuje
  * jeśli dodawanie rezerwacji zakończone neipowodzeniem, stolik w tym czasie zajęty:
	* zwraca JSON z danymi:
		* status - *-13*
		* descripton - *TTable is occupied.*
    
### ```/admin/tableReservation/{tableReservationId}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji stolika, którą chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia stolika zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędne *{id}*:
        * status - -15*
        * description - *Table reservation doesn't exist.*  
        
        
        
        
        
        
        
        
        
        
