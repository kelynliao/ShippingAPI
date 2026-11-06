# IEC Shipping API

Spring Boot REST API for retrieving Canada Post shipping rates. The application converts a JSON shipping request into Canada Post rate XML and returns the available services as JSON.

## Requirements

- JDK 25 (`JAVA_HOME` should point to your JDK installation)
- Canada Post API credentials and customer number
- Network access to download Maven dependencies and call Canada Post

The project uses Spring Boot 4.1.2 and includes a Maven wrapper.

## Configuration

Settings are defined in `src/main/resources/application.yaml`. Set these environment variables to use your own Canada Post account:

| Variable | Purpose |
| --- | --- |
| `CANADA_POST_USERNAME` | Canada Post API username |
| `CANADA_POST_PASSWORD` | Canada Post API password |
| `CANADA_POST_CUSTOMER_NUMBER` | Canada Post customer number |
| `CANADA_POST_ENDPOINT` | Rate endpoint; defaults to `https://ct.soa-gw.canadapost.ca/rs/ship/price` (sandbox) |

PowerShell example:

```powershell
$env:CANADA_POST_USERNAME = "your-api-username"
$env:CANADA_POST_PASSWORD = "your-api-password"
$env:CANADA_POST_CUSTOMER_NUMBER = "your-customer-number"
```

Keep account credentials out of source control. The configuration currently contains fallback account values; explicitly set the environment variables for your environment.

## Run locally

From the project root on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```sh
./mvnw spring-boot:run
```

The API listens at `http://localhost:8124`. Set `SERVER_PORT` to override the port.

## Get shipping rates

Send a JSON request to `POST /canadapost/getrates` with `Content-Type: application/json`.

```json
{
  "shippingFromAddress": {
    "postalCode": "V6B 1A1"
  },
  "shippingToAddress": {
    "postalCode": "M5V 3L9"
  },
  "packageInfo": {
    "shippingWeight": 1.5,
    "shippingInsurance": 0,
    "dimensionLength": 30,
    "dimensionWidth": 20,
    "dimensionHeight": 10,
    "groupPackageCount": 1,
    "currency": "CAD",
    "weightUnits": "KG",
    "dimensionUnits": "CM"
  }
}
```

Save the example as `request.json`, then call the API from PowerShell:

```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8124/canadapost/getrates" `
  -ContentType "application/json" `
  -InFile "request.json"
```

The response is an array of rate objects with these fields:

| Field | Description |
| --- | --- |
| `id` | Sequential identifier within the response, starting at 1 |
| `carrier` | `Canada Post` |
| `serviceName` | Carrier service name |
| `serviceCode` | Carrier service code |
| `price` | Amount due returned by Canada Post |
| `deliveryDay` | Expected delivery date, or `null` if unavailable |

### Validation and current behavior

- Both address objects and `packageInfo` are required. Provide a postal code in each address; the service removes whitespace and converts it to uppercase.
- Weight, dimensions, and package count must be positive. Insurance must be nonnegative. Currency and unit strings must be nonblank.
- Malformed JSON and bean-validation failures return HTTP 400 with `message` and `errors` fields. Each field error contains `field` and `message`.
- The service currently builds domestic Canadian requests only, regardless of address country fields.
- Weight and dimensions are passed through without unit conversion. Supply kilograms and centimetres; the unit strings do not control conversion.
- Insurance, currency, and package count are accepted but are not currently included in the carrier request. Each request builds one parcel.
- Carrier error responses do not currently have a dedicated API error mapping.

## Build and test

```powershell
# Run the test suite
.\mvnw.cmd test

# Compile, test, and package the application
.\mvnw.cmd clean verify

# Run the packaged application
java -jar target/iecapi-shippingapi-1.0.0.jar
```

On macOS or Linux, replace `.\mvnw.cmd` with `./mvnw`.

Tests cover application context startup, request and package validation, and Canada Post XML binding.

## Project structure

```text
src/main/java/com/iec/api/shippingapi/
  config/             Canada Post configuration properties
  controller/         Rate endpoint and validation error handling
  dto/                Request records and validation constraints
  model/canadaPost/   Canada Post XML models
  service/            Carrier requests and response mapping
  vo/                 Shipping rate response record
src/main/resources/application.yaml
src/test/java/com/iec/api/shippingapi/
```
