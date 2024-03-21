## Solution
___
The solution is aimed at generating a report to address the following points based on the transaction records:
* The report shows if a transaction reference is duplicate.
* Validates that the end balance is the difference between start balance and mutation.
* Start and End balance are greater than or equal to zero.
* Transaction dates are not in the future.

## Running the application
* Clone this repo `git clone https://github.com/ranadeepak/be-test.git` into your IDE
* Checkout my branch `git checkout elbashir-adam`
* Compile the project `mvn compile`
* Run the project by executing main `BeTestApplication`

## Endpoints
### POST File validation
Can be reached on `/api/statements/validate/file`. Accepts a csv, json or xml file containing the transactions
and does the processing to generate the validation report.

### POST Validation
Can be reached on `/api/statements/validate`. Accepts request body of csv, json or xml and does the processing to generate the validation report.

