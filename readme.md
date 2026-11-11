# IEC Shipping API

Spring Boot REST API for shipping quotes from **Canada Post** and **UPS**. Both endpoints accept a shared JSON request and return a common rate format.

Canada Post uses XML with Basic authentication. UPS uses JSON with OAuth client credentials. FedEx configuration placeholders exist, but no FedEx endpoint is implemented.

## Requirements

- JDK 25, with `JAVA_HOME` pointing to your JDK installation
- API credentials and an account for each carrier you want to query
- Network access to download Maven dependencies and call carrier APIs

The project uses Spring Boot 4.1.2 and includes a Maven wrapper.

## Configuration

Settings live in [`src/main/resources/application.yaml`](src/main/resources/application.yaml). Override them using environment variables. The configuration currently contains fallback account values; use your own credentials and keep secrets out of source control.

| Variable | Purpose / default |
| --- | --- |
| `CANADA_POST_USERNAME` | Canada Post API username |
| `CANADA_POST_PASSWORD` | Canada Post API password |
| `CANADA_POST_CUSTOMER_NUMBER` | Canada Post customer number |
| `CANADA_POST_ENDPOINT` | Full rate URL; defaults to `https://ct.soa-gw.canadapost.ca/rs/ship/price` (sandbox) |
| `CANADA_POST_CARRIER` | Response carrier label; defaults to `Canada Post` |
| `UPS_CLIENT_ID` | UPS OAuth client ID |
| `UPS_CLIENT_SECRET` | UPS OAuth client secret |
| `UPS_ACCOUNT_NUMBER` | UPS shipper and billing account number |
| `UPS_Shipper_Name` | UPS shipper name; use this exact variable spelling |
| `UPS_ENDPOINT` | UPS base URL; defaults to `https://wwwcie.ups.com` (sandbox) |
| `UPS_CARRIER` | Configuration label; responses currently use the fixed label `UPS` |
| `SERVER_PORT` | Application port; defaults to `8124` |

UPS appends `/security/v1/oauth/token` and `/api/rating/v2403/shop` to its base URL. Supply the base URL without a trailing slash.

PowerShell example:

```powershell
$env:CANADA_POST_USERNAME = "your-api-username"
$env:CANADA_POST_PASSWORD = "your-api-password"
$env:CANADA_POST_CUSTOMER_NUMBER = "your-customer-number"

$env:UPS_CLIENT_ID = "your-client-id"
$env:UPS_CLIENT_SECRET = "your-client-secret"
$env:UPS_ACCOUNT_NUMBER = "your-account-number"
$env:UPS_Shipper_Name = "Your Company"
```

## Run locally

From the project root on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```sh
./mvnw spring-boot:run
```

The default base URL is `http://localhost:8124`.

## Request shipping rates

| Method | Endpoint | Carrier |
| --- | --- | --- |
| `POST` | `/canadapost/getrates` | Canada Post |
| `POST` | `/ups/getrates` | UPS |

Send `Content-Type: application/json`. Save the following as `request.json`. It includes address fields used by UPS and can also be sent to Canada Post. Addresses are illustrative; use actual shipment details for quotes.

```json
{
  "shippingFromAddress": {
    "firstName": "Alex",
    "lastName": "Sender",
    "address1": "100 Main Street",
    "city": "Vancouver",
    "province": "BC",
    "countryCode": "CA",
    "postalCode": "V6B1A1"
  },
  "shippingToAddress": {
    "firstName": "Sam",
    "lastName": "Recipient",
    "address1": "200 King Street West",
    "city": "Toronto",
    "province": "ON",
    "countryCode": "CA",
    "postalCode": "M5V3L9"
  },
  "packageInfo": {
    "shippingWeight": 1.5,
    "shippingInsurance": 0,
    "dimensionLength": 30,
    "dimensionWidth": 20,
    "dimensionHeight": 10,
    "groupPackageCount": 1,
    "currency": "CAD",
    "weightUnits": "KGS",
    "dimensionUnits": "CM"
  }
}
```

Call either endpoint from PowerShell:

```powershell
Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8124/ups/getrates" `
  -ContentType "application/json" `
  -InFile "request.json"

Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8124/canadapost/getrates" `
  -ContentType "application/json" `
  -InFile "request.json"
```

Or with curl on macOS or Linux:

```sh
curl -X POST http://localhost:8124/ups/getrates \
  -H 'Content-Type: application/json' \
  --data-binary @request.json
```

### Response

Both endpoints return a JSON array with these fields:

| Field | Description |
| --- | --- |
| `id` | Sequential identifier within the response, starting at 1 |
| `carrier` | Carrier label |
| `serviceName` | Canada Post service name or locally mapped UPS name; unknown UPS codes use the code as the name |
| `serviceCode` | Carrier service code |
| `price` | Canada Post amount due or UPS total charges |
| `deliveryDay` | Canada Post expected delivery date, or `null` if unavailable; UPS currently returns an empty string |

The response does not include a currency field.

### Validation and current limitations

- Both address objects and `packageInfo` are required.
- Weight is required and must be positive. Dimensions and package count must be positive integers. Insurance must be nonnegative. Currency and unit strings must be nonblank.
- Address fields have no bean-validation constraints. Supply both postal codes for Canada Post and complete shipment addresses for UPS. Missing address details can fail during processing or at the carrier.
- Canada Post builds domestic Canadian requests only. It strips whitespace from postal codes and converts them to uppercase. Supply kilograms and centimetres; unit strings are ignored by this integration.
- UPS forwards uppercase unit codes without converting values. Use carrier-compatible codes such as `KGS` and `CM`. Packaging type is fixed to code `02`.
- Both integrations build one package. Insurance, currency, and package count are accepted but are not included in carrier requests.
- UPS retrieves a new OAuth token for each rate lookup. Its outbound rating payload, including shipment addresses, is currently printed to standard output.
- Carrier failures do not have a dedicated public error format. UPS rejects non-success HTTP statuses; Canada Post attempts to parse the response body as rate XML.

Malformed JSON and bean-validation failures return HTTP 400 with `message` and `errors`. Each field error contains `field` and `message`, for example:

```json
{
  "message": "Request validation failed",
  "errors": [
    {
      "field": "packageInfo.shippingWeight",
      "message": "Shipping weight must be greater than zero"
    }
  ]
}
```

## Build and test

```powershell
# Run the test suite
.\mvnw.cmd test

# Compile, test, and package
.\mvnw.cmd clean verify

# Run the packaged application
java -jar target/shippingapi-1.0.0.jar
```

On macOS or Linux, replace `.\mvnw.cmd` with `./mvnw`.

Tests cover application context startup, request and package validation, Canada Post XML binding, and UPS JSON binding. They do not verify live carrier credentials or rate calls.

## Project structure

```text
src/main/java/com/iec/api/shippingapi/
  config/             Carrier account configuration properties
  controller/         Canada Post and UPS REST endpoints
  dto/                Shared request records and validation constraints
  exception/          JSON and bean-validation error handling
  model/canadaPost/   Canada Post XML models
  model/ups/          UPS JSON models
  service/            Carrier authentication, requests, and rate mapping
  vo/                 Shared shipping rate response record
src/main/resources/application.yaml
src/test/java/com/iec/api/shippingapi/
```
