# Endpoints backend'u bez opisu z podziałem na dostęp
## Main path takie, jakie zdefiniowane w konfiguracji.
Domyślnie http://localhost:8080/

### Ogólnodostępne
##### ```/test``` GET/POST/PUT/DELETE
##### ```/test2``` GET/POST/PUT/DELETE
<br />

##### ```/login``` POST
##### ```/registration``` POST
<br />

##### ```/menu``` GET
<br />

##### ```/image/{imageName}``` GET

### Ogólnodostępne po zalogowaniu
##### ```/logout``` GET
##### ```/image``` POST

### Dostępne dla użytkownika      
##### ```/user``` GET 
##### ```/user/{id}``` PUT
##### ```/user/{id}``` DELETE
<br />

##### ```/tableReservation``` GET
##### ```/tableReservation/{tableReservationId}``` GET	
##### ```/reservations/table/{date}``` GET   
##### ```/tableReservation``` POST
##### ```/tableReservation/{tableReservationId}``` PUT  
##### ```/tableReservation/{tableReservationId}``` DELETE
<br />

##### ```/restaurantReservation``` GET
##### ```/restaurantReservation/{restaurantReservationId}``` GET	
##### ```/reservations/restaurant/{date}``` GET   
##### ```/restaurantReservation``` POST
##### ```/restaurantReservation/{restaurantReservationId}``` PUT  
##### ```/restaurantReservation/{restaurantReservationId}``` DELETE
<br />

### Dostępne dla administratora
        
##### ```/admin/users``` GET
##### ```/admin/users/{userId}``` GET            
##### ```/admin/users/{id}``` PUT    
##### ```/admin/user/{id}``` DELETE
<br />

##### ```/admin/meal/{mealId}``` GET
##### ```/admin/meal/categories``` GET    
##### ```/admin/meal``` POST
##### ```/admin/meal/{id}``` PUT
##### ```/admin/meal/{id}``` DELETE
<br />

##### ```/admin/tables``` GET
##### ```/admin/tables/{tableId}``` GET  
##### ```/admin/tables``` POST
##### ```/admin/tables/{tableId}``` PUT 
##### ```/admin/tables/{tableId}``` DELETE
<br />
        		
##### ```/admin/tableReservation``` GET
##### ```/admin/tableReservation/{tableReservationId}``` GET  
##### ```/admin/tableReservation``` POST
##### ```/admin/tableReservation/{tableReservationId}``` PUT  
##### ```/admin/tableReservation/{tableReservationId}``` DELETE
<br />

##### ```/admin/restaurantReservation``` GET
##### ```/admin/restaurantReservation/{restaurantReservationId}``` GET	
##### ```/admin/restaurantReservation``` POST
##### ```/admin/restaurantReservation/{restaurantReservationId}``` PUT  
##### ```/admin/restaurantReservation/{restaurantReservationId}``` DELETE

### Dostępne dla menedżera
##### ```/management/reservation/table/frequency/{startDate}/{endDate}``` GET
##### ```/management/reservation/traffic/{startDate}/{endDate}``` GET            
##### ```/management/meal/order/{startDate}/{endDate}``` GET    
##### ```/management/user/reservation/{startDate}/{endDate}/{topNumber}``` GET
<br />
