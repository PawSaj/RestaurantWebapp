# Endpoints backend'u
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/

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
	
### ```/reservations/table/{date}```

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
		
### ```/restaurantReservation```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
* **co robi:**
  * zwraca JSON z listą wszystkich rezerwacji restauracji danego użytkownika, gdzie każda rezerwacja to kolejny element listy:
	* id - id
	* date - data rezerwacji restauracji w formacie "YYYY-MM-DD"
	* allDay - zawsze true, bo tylko takie obsługujemy
	* describe - opis wpisany przez użytkownika
	* floor - zawse 0, gdyż nie obsługujemy pięter
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*	

### ```/restaurantReservation/{restaurantReservationId}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji restauracji, które chcemy pobrać
* **co robi:**
  * zwraca JSON z rezerwacją restauracji:
    * id - id
	* date - data rezerwacji restauracji w formacie "YYYY-MM-DD"
	* allDay - zawsze true, bo tylko takie obsługujemy
	* describe - opis wpisany przez użytkownika
	* floor - zawse 0, gdyż nie obsługujemy pięter
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*	
  * jeśli próba pobrania rezerwacji innego użytkownika:
        * status - *-7*
        * descripton - *You try to access data of different user.*	
	
### ```/reservations/restaurant/{date}```

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{date}* jest to data początku okresu miesiąca który chcemy pobrać w formacie "YYYY-MM-DD
* **co robi:**
  * zwraca JSON z listą wszystkich wszystkich rezerwacji restauracji w danym okresie, gdzie każda rezerwacja restauracji to kolejny element listy:
	* id - id
	* date - data rezerwacji restauracji w formacie "YYYY-MM-DD"
	* allDay - zawsze true, bo tylko takie obsługujemy
	* describe - opis wpisany przez użytkownika
	* floor - zawse 0, gdyż nie obsługujemy pięter
  * jeśli brak rezerwacji JSON z danymi:
    * status - *-17*
    * descripton - *No reservation in database.*
   
### ```/restaurantReservation```

* **metoda:** POST
* **wymagania:** 
  * wymaga autoryzacji
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji restauracji:
	* restaurantReservationDate - data rezerwacji restauracji w formacie "YYYY-MM-DD"
	* opcjonalnie:
		*describe - opis do rezerwacji
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
		* reservation - dane rezerwacji jako obiekt z danymi:
			* id - id
			* date - data rezerwacji restauracji w formacie "YYYY-MM-DD"
			* allDay - zawsze true, bo tylko takie obsługujemy
			* describe - opis wpisany przez użytkownika
			* floor - zawse 0, gdyż nie obsługujemy pięter
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
		* status - *-19*
		* descripton - *Restaurant is occupied.*	

### ```/restaurantReservation/{restaurantReservationId}```

* **metoda:** PUT
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji restauracji, który chcemy zaktualizować
  * wymaga w ciele JSON'a z danymi do zapisania rezerwacji restauracji:
	* restaurantReservationDate - data rezerwacji restauracji w formacie "YYYY-MM-DD"
  * opcjonalnie:
	*describe - opis do rezerwacji
* **co robi:**
  * jeśli rezerwacja nie istnieje i udało się ją zapisać w bazie:
    * zwraca JSON z danymi:
        * status - *0*
        * descripton - *Request completed with no errors.*
		* reservation - dane rezerwacji jako obiekt z danymi:
			* id - id
			* date - data rezerwacji restauracji w formacie "YYYY-MM-DD"
			* allDay - zawsze true, bo tylko takie obsługujemy
			* describe - opis wpisany przez użytkownika
			* floor - zawse 0, gdyż nie obsługujemy pięter
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
		* status - *-19*
		* descripton - *Restaurant is occupied.*	
    
### ```/restaurantReservation/{restaurantReservationId}```

* **metoda:** DELETE
* **wymagania:** 
  * wymaga autoryzacji
  * w adresie *{id}* jest to id rezerwacji restauracji, którą chcemy usunąć  
* **co robi:**
  * jeśli powodzenie usunięcia restauracji zwraca JSON z danymi:
    * status - *0*
    * description - *Request completed with no errors.*
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędne *{id}*:
        * status - -20*
        * description - *Restaurant reservation doesn't exist.*  
	* próba modyfikacji rezerwacji innego użytkownika:
        * status - *-7*
        * descripton - *You try to access data of different user.*		
		
