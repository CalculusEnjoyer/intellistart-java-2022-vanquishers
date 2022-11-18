# Interview planning application 
## Running in Docker
In order to run application in docker container, you should open terminal in the project directory and run the following:
```sh
docker compose up
```
Application will run on localhost:8080 by default.

If it is needed, you can configure ports and other properties in docker-compose.yml.

## .properties files
application.properties contains properties about database connection

application-test.properties contains properties about database connection for testing

timezone.properties contains properties about current timezone system is working with.

If necessary change the zone or other properties it must be written in this files