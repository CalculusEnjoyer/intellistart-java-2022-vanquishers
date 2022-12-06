# Interview planning application 

This back-end application is made for coordinating the interviewer's and candidate's assignments. It was created using Spring with Hibernate framework. PostgresSQL was used as a database because of its reliability and excellent performance. Facebook API and Spring Security OAuth2 were used for authentication through Facebook account. Additionally, stress-testing was conducted using Apache JMeter.<br />
<br />
Main functional requirements described <a href="https://github.com/gavluk-intellias/intellistart-java-project/blob/main/docs/REQUIREMENTS.md">here.</a>

# Deployment

## Running in Docker :rocket:

In order to run application in docker container, you should open terminal in the project directory and run the following:
```sh
docker compose up
```

Application will run on localhost:8080 by default.

If it is needed, you can configure ports and other properties in .env file (default values for quick testing have been already set).<br />
<br />
First Coordinator and other needed records in the database that will be initialized before the application starts can be configured in data.sql. 

## .properties files :twisted_rightwards_arrows:

Application properties can be configured in .env file (default values for quick test are already set)

application-test.properties contains properties about database connection for testing

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

## Performance testing :zap:
Load-testing was performed using Apache Jmeter with the following test plan: <br />
* 8 threads perform simultaneously. 
* Each user (thread) adds booking, requests dashboard and delete created booking. Every request performs with a random delay in the range of 0.5 seconds.
* Load testing was performed for 10 minutes straight.<br />
<br />
After conducting test plan, 17411 requests were made without any errors. <br />
<br />
<img src="https://github.com/CalculusEnjoyer/intellistart-java-2022-vanquishers/blob/main/jmeter.testing/Test-summary.png">
<br />

Average latency was under 15 ms, and all requests latency was under 500 ms (as was mentioned in requirements) .<br />
<br />
<img src="https://github.com/CalculusEnjoyer/intellistart-java-2022-vanquishers/blob/main/jmeter.testing/Latency_graph.png">
<br />
To conclude, those performance results totally satisfies the requirements.
