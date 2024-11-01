# Town sq Api Challenge

### Dependencies

- OpenJDK 21
- Maven

### Dev Dependencies

- docker
- docker compose
- lombok

## Running the application

To run the application, first start the docker mysql container then star te .jar file provided:

```shell
docker compose up -d
java -jar target/townsq-challenge-0.0.1-SNAPSHOT.jar
```

## Api Endpoints

The **/authenticate** endpoint uses the http basic authentication, wich can perform like the example http request
below:

```bash
curl --location --request POST 'localhost:8080/authenticate' \
--header 'Authorization: basic {your-base64-string-of-username:password}'
```

All others endpoints are protected by a JWT, so you must use the returned token on the previous method on the Bearer
token authentication header.

```bash
curl --location 'localhost:8080/users/all' \
--header 'Authorization: bearer {your-jwt-token}'
```

> For the detailed endpoints description, use the postman collection that can be imported from the file **./Townsq
challenge collection.postman_collection.json** 