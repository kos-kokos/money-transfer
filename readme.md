###### MoneyTransfer example application

***How to run***

execute  './gradlew run'

###### Endpoints

**Create account** 

POST localhost:8080/accounts

Sample body:
`{"balance": 150.0}`

Sample response:

`{
    "payload": {
        "id": 1,
        "balance": 150
    },
    "success": true
}`

**Get account by id**

GET localhost:8080/accounts/<id>

Sample response:

`{
    "payload": {
        "id": 1,
        "balance": 150
    },
    "success": true
}`

**Transfer money**

POST localhost:8080/transfers

Sample body:

`{
	"fromAccountId":1,
	"toAccountId":2,
	"amount":100
}`

Sample response:

`{
    "payload": {
        "fromAccountId": 1,
        "toAccountId": 2,
        "amount": 100
    },
    "success": true
}`