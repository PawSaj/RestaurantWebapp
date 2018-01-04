# Endpoints backend'u dla Administratora
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/

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

### ```/admin/meal/{mealId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id dania, które chcemy pobrać
* **co robi:**
  * zwraca JSON z daniem:
     * category kategoria dań
     * name nazwa dania
     * ingredients lista składników
         * wypisanych w formie indeksów (w bazie) + nazwa
     * price cena (jeśli danie jej nie ma, JSON nie zawiera tej danej)

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
	* name nazwa dania  
  * opcjonalne argumenty:
  	* ingredients lista składników
		* wypisanych kolejno samych nazw składników np. { "name":"kurczak", "name":"oliwki"}    
	* describe opis dania
    * price cena dania
    * *image* w formie nazwy obrazu, którą można uzyskać wysyłając obraz pod adres *```/sendImage```*
* **co robi:**
  * jeśli danie nie istnieje i udało się je zapisać w bazie:
    * zwraca JSON z danymi:
        * status *0*
        * descripton *Request completed with no errors.*
  * jeśli dodawanie dania zakończone neipowodzeniem, nazwa zduplikowana:
    * zwraca JSON z danymi:
        * status *-9*
        * descripton *Meal is already exist.*
  * jeśli brak jakiejś danej:
    * status *-14*
    * descripton *Missing data.*
    * missing dana, która jest wymagana, a której brakuje

### ```/admin/meal/{id}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id dania, które chcemy poddać modyfikacji
  * wymaga w ciele JSON'a z danymi:
	* name nazwa dania
  	* ingredients lista składników
		* wypisanych kolejno samych nazw składników np. { "name":"kurczak", "name":"oliwki"}    
	* describe opis dania
    * price cena dania
    * *image* w formie nazwy obrazu, którą można uzyskać wysyłając obraz pod adres *```/sendImage```*
  * **jeśli jakaś z danych nie została zmieniona należy ją także umieścić w podstawowej wersji**    
* **co robi:**
  * jeśli powodzenie aktualizacji danych dania zwraca JSON z danymi:
    * status *0*
	* descripton *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * danie nie istnieje błędne *id*:
        * status *-10*
        * description *Meal doesn't exist.*
    * nowa nazwa dania już jest zajęta:
        * status *-9*
        * descripton *Meal is already exist.*
    * jeśli brak jakiejś danej:
        * status *-14*
        * descripton *Missing data.*
        * missing dana, która jest wymagana, a której brakuje
    
### ```/admin/meal/{id}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji z rolą *ADMIN*
  * w adresie *{id}* jest to id dania, które chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia dania zwraca JSON z danymi:
    * status *0*
    * description *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * danie nie istnieje błędne *id*:
        * status *-10*
        * description *Meal doesn't exist.*       
  
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
