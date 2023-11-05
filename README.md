# nl-postcodes-distance-calculator

# Description

Web application that exposes REST endpoints for:

- Calculating the geographic (straight line) distance between two postcodes in the Netherlands
- Comparing the distance and time of a trip between two Dutch postcodes using various transportation methods

The results can be checked against over 450.000 Dutch postcodes which can be red from the CSV postcodes-coordinates-NL.csv and persisted in the database
Any persisted postcode can be updated using the dedicated REST endpoint

# Technologies used

- Java 17
- Spring Boot
- PostgreSQL
- OpenAPI
- Flyway
- Google API client



