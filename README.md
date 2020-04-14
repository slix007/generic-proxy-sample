# Generic proxy
The main points for the app:
* Accept requests with various data
* keep client ip and time
* send requests forward to FinalBackend
* handle FinalBackend downtime without loosing data
* the initial client gets success/fail response on saving in the proxy(not FinalBackend)

It uses docker-compose to create an environment:
* mq - activeMq image from docker hub
* db - mongodb image from docker hub
* keeper-app - the app. 

Keeper-app receives any plain text messages on the endpoint http://localhost:8080/keeper/item
Each request is saved in DB(mongodb) with boolean flag 'isSent'.
Jms(activeMq) implements redelivery policy.  
The FinalBackend emulator: emulate 3 exceptions; emulates success sending(just write logs).

# Manual testing:
```bash
# build:
gradle build

# run:
docker-compose up -d

curl -i -H "content-type: text/plain;charset=UTF-8" -X POST -d "some data" http://localhost:8080/keeper/item
 
docker-compose logs | grep keeper-app

# stop:
docker-compose down
```

## Possible improvements
The solution can be scaled horizontally by creating several instances of the app.
