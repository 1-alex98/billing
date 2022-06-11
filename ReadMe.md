# Billing service
Allows creating bills by sending information about the items in json. The application outputs either json or an PDF.
## Assumptions made
- The application is used in a web context
- The application is a microservice
- The type of products is known and does need to be parsed from its name
- The output can be either PDF or JSON
## Implementation
- Uses Spring Boot
- Architecture is orientated at Ports & Adapters
## Running the app
You can run the app using `./gradlew bootRun`, Java 18 is required.

### Docker
Otherwise, install as well `docker` and `docker compose` and build with `./gradlew bootBuildImage` then run `docker compose up -d`.
## How to communicate
### Types
Getting all type:
Get on `/types` results in 
```json

```
TODO
### Bill creation
POST on `/bill` with
```json

```
results in
```json

```
## Could be improved / one day
- More generic taxation system with a special entity that hold customs and VAT Taxes specific to a point in time
- OPEN API specs
- Maybe GRAPHQL over JSON
- Testing PDF content
- Upload Docker image to registry