# Interview planning application

## Running in Docker :rocket:

In order to run application in docker container, you should open terminal in the project directory
and run the following:

```sh
docker compose up
```

Application will run on localhost:8080 by default.

If it is needed, you can configure ports and other properties in docker-compose.yml.

## .properties files :twisted_rightwards_arrows:

application.properties contains properties about database connection

application-test.properties contains properties about database connection for testing

timezone.properties contains properties about current timezone system is working with.

If necessary change the zone or other properties it must be written in this files

## Postman collection :card_file_box:

Entire functionality is collected into a single postman collection for a better experience with API
usage. The collection is saved at the root directory of the project in the file named
as `Interview-planning-app-vanquishers.postman_collection.json`.
Just do some easy steps to be able to use it

- :twisted_rightwards_arrows: Import this collection into your postman;
- :globe_with_meridians: Change {{BASE_URL}} env variable value to the URL your app is being hosted
  on (http://localhost:8080 is a default one there);
- :lock: In Authentication folder use the `POST Correct Auth request` with your Facebook OAuth2.0
  token, submit the request and get your JWT token in response;
- :closed_lock_with_key: Go to collection root's Authorization tab, enter acquired JWT token into
  the `Token` field. Save the changes;
- :boom: Boom! Now you can use any endpoints that are allowed for your role, registered on your
  email in the app!

The collection has five folders:

- InterviewerController, with endpoints, related to interviewers
- CoordinatorController, with endpoints, related to coordinators
- CandidateController, with endpoints, related to candidates
- Authentication, with authentication and security related endpoints
- CommonController, with endpoints with generic purpose(like get next weekNum or get /me)