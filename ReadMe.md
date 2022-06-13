![test workflow](https://github.com/1-alex98/billing/actions/workflows/test.yml/badge.svg)
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
App needs to be connected to Postgres DB or launched with dev profile (set SPRING_PROFILES_ACTIVE=dev as environment
variable).

### Docker

Otherwise, install as well `docker` and `docker compose` and build with `./gradlew bootBuildImage` then
run `docker compose up -d`.

## How to communicate

Below you can see request that can be made to the Backend.
See insomnia collection [import](docs/billing.yaml).

### Types

Getting all type:
Get on `/types` results in

```json
[
  {
    "id": "other",
    "name": "Other",
    "description": "Products that do not fall into any other category",
    "valueAddedTax": 0.1,
    "customs": 0.05
  },
  {
    "id": "books",
    "name": "Books",
    "description": "Books can be read",
    "valueAddedTax": 0.0,
    "customs": 0.05
  },
  {
    "id": "meds",
    "name": "medical products",
    "description": "Needed for medicine",
    "valueAddedTax": 0.0,
    "customs": 0.05
  },
  {
    "id": "food",
    "name": "food",
    "description": "food and other eatable things",
    "valueAddedTax": 0.0,
    "customs": 0.05
  }
]
```

Post on `/types` with

```json
{
  "id": "wood",
  "name": "Wood",
  "description": "Wood can burn",
  "valueAddedTax": 0.10,
  "customs": 0.05
}
```

creates the corresponding type.
Post on `/types/initialization` initializes standard types.

### Authetification

Writing actions require authetification. In development mode user and password is admin. HTTP basic auth is used.

### Bill creation

POST on `/bill` with

```json
[
  {
    "price": 2799,
    "name": "bottle of perfume",
    "quantity": 1,
    "imported": true
  },
  {
    "price": 1899,
    "name": "bottle of perfume",
    "quantity": 1,
    "imported": false
  },
  {
    "price": 975,
    "name": "packet of headache pills",
    "quantity": 1,
    "typeId": "meds"
  },
  {
    "price": 1125,
    "name": "chocolates",
    "quantity": 1,
    "typeId": "food",
    "imported": true
  }
]
```
results in

```json
{
  "items": [
    {
      "name": "bottle of perfume",
      "quantity": 1,
      "price": 2799,
      "imported": true,
      "typeId": null,
      "sumRawPrice": 2799,
      "sumTaxes": 420,
      "sumAfterTaxesPrice": 3219
    },
    {
      "name": "bottle of perfume",
      "quantity": 1,
      "price": 1899,
      "imported": false,
      "typeId": null,
      "sumRawPrice": 1899,
      "sumTaxes": 190,
      "sumAfterTaxesPrice": 2089
    },
    {
      "name": "packet of headache pills",
      "quantity": 1,
      "price": 975,
      "imported": false,
      "typeId": "meds",
      "sumRawPrice": 975,
      "sumTaxes": 0,
      "sumAfterTaxesPrice": 975
    },
    {
      "name": "chocolates",
      "quantity": 1,
      "price": 1125,
      "imported": true,
      "typeId": "food",
      "sumRawPrice": 1125,
      "sumTaxes": 55,
      "sumAfterTaxesPrice": 1180
    }
  ],
  "sumRawPrice": 6798,
  "sumTaxes": 665,
  "sumAfterTaxesPrice": 7463
}
```

Alternatively you can request this in a pdf table. For this set `Accept` header to `application/pdf`.

## Could be improved / one day

- More generic taxation system with a special entity that hold customs and VAT Taxes specific to a point in time
- OPEN API specs
- Maybe GRAPHQL over JSON
- Testing PDF content
- PDF generation in Front End might be preferable
- Upload Docker image to registry
