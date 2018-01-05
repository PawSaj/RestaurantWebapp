#Walidatory backend'u
##User
* email
    * [\w]{1,20}@[\w]{1,10}(\.[a-zA-Z]{1,5}){1,6}
    * zwykły email lub z dodatkowymi przyrostkami typu .wat.edu.pl
* name
    * ^[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20}$
    * zaczynające się od wielkiej litery, conajmniej 2 znaki, max 20. dozwolone PL znaki
* surname
    * ^[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20}((-|\s)[A-ZĆŁŚŻŹ][a-ząćęłńóśźż]{1,20})?$
    * tak jak imię, ale można dodać drugi człon nazwiska poprzez oddzielenie spacją lub -
* password
    * (?=.*[A-Z])(?=.*[0-9]).{6,20}$
    * min 6, max 20, co najmniej jedna duża litera i conajmniej cyfra. Dozwolone znaki specjalne
* phone
    * ^\d{9}$
    * dozwolone tylko 9 cyfr
* image
    * ^[a-zA-Z0-9-]{1,40}\.[a-zA-Z]{1,5}$
    * ciąg znaków 1-40 z dozwolonym znakiem "-" oraz "." i po niej rozszerzenie
##Meal