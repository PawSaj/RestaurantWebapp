# Endpoints backend'u dla Administratora
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/
### ```/management/reservation/table/frequency/{startDate}/{endDate}``` 

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *MANAGER**
  * w adresie *{startDate}* jest to data od której ma być generowana statystyka w formacie "YYYY-MM-DD"
  * w adresie *{startDate}* jest to data do której ma być generowana statystyka w formacie "YYYY-MM-DD"
* **co robi:**
  * jeśli powodzenie zwraca JSON z listą, gdzie każdy kolejny element to obiekt zawierający:
    * tableId - id stolika
	* frequency - częstotliwość wybierania danego stolika w stosunku do wszystkich rezerwacji z zakresu 0-1
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędna którakolwiek z dat - niewłąściwy format:
        * status - -18*
        * description - *Incorrect date format.*  
	* brak rezerwacji w tym okresie:
        * status - *-17*
        * descripton - *No reservation in database.*	
		
### ```/management/reservation/traffic/{startDate}/{endDate}```      

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *MANAGER**
  * w adresie *{startDate}* jest to data od której ma być generowana statystyka w formacie "YYYY-MM-DD"
  * w adresie *{startDate}* jest to data do której ma być generowana statystyka w formacie "YYYY-MM-DD"
* **co robi:**
  * jeśli powodzenie zwraca JSON z listą, gdzie każdy kolejny element to obiekt zawierający:
    * date - data w formacie "YYYY-MM-DD"
	* sum - suma rezerwacji w danym dniu
	* body - lista zawierająca obiekty, które zawierają:
		* <godzina z UTC> - jako wartość ma ilość rezerwacji w danej godzinie
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędna którakolwiek z dat - niewłąściwy format:
        * status - -18*
        * description - *Incorrect date format.*  
	* brak rezerwacji w tym okresie:
        * status - *-17*
        * descripton - *No reservation in database.*	
		      
### ```/management/meal/order/{startDate}/{endDate}```    

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *MANAGER**
  * w adresie *{startDate}* jest to data od której ma być generowana statystyka w formacie "YYYY-MM-DD"
  * w adresie *{startDate}* jest to data do której ma być generowana statystyka w formacie "YYYY-MM-DD"
* **co robi:**
  * jeśli powodzenie zwraca JSON z listą, gdzie każdy kolejny element to obiekt zawierający:
    * date - data w formacie "YYYY-MM-DD"
	* sum - suma zamówień w danym dniu
	* body - lista zawierająca obiekaty, które zawierają:
		* <nazw dania> - jako wartość ma ilość rezerwacji w danej godzinie
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędna którakolwiek z dat - niewłąściwy format:
        * status - -18*
        * description - *Incorrect date format.*  
	* brak jakiegokolwiek dania w bazie:
        * status - *-10*
        * descripton - *Meal doesn't exist.*	
	* brak historii zamówień:
        * status - *-21*
        * descripton - *Meal order doesn't exist.*	
		
### ```/management/user/reservation/{startDate}/{endDate}/{topNumber}``` 

* **metoda:** GET
* **wymagania:** 
  * wymaga autoryzacji z rolą *MANAGER**
  * w adresie *{startDate}* jest to data od której ma być generowana statystyka w formacie "YYYY-MM-DD"
  * w adresie *{startDate}* jest to data do której ma być generowana statystyka w formacie "YYYY-MM-DD"
  * w adresie *{topNumber}* jest to maksymalna liczba użytkowników dla statystyki
* **co robi:**
  * jeśli powodzenie zwraca JSON z listą, zawierającą 2 elementy:
    * type - rodzaj statystyki, *table* lub *restaurant*
	* sum - suma rezerwacji
	* body - lista zawierająca obiekty, które zawierają:
		* <id_usera> - jako wartość ma ilość rezerwacji
  * JSON z danymi jeśli niepowodzenie z powodu:
    * rezerwacja nie istnieje - błędna którakolwiek z dat - niewłąściwy format:
        * status - -18*
        * description - *Incorrect date format.*  
	* brak rezerwacji w tym okresie:
        * status - *-17*
        * descripton - *No reservation in database.*
