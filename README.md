# Client side of the MiniRestProject


This project contains a console client to upload and retrieve customer data from the MiniRestProject customer REST API.


## Operations

The client allows three operations: READ, INSERT OR EXIT.

- **READ**: Allows searching for a Customer providing a reference, and retrieves the customer information in JSON format from the REST API.

- **INSERT**: Reads and parses a csv file with customer data and sends it to the REST API for storage.

- **EXIT**: Finishes the execution of the client side.


The path with the location of the CSV file is:   **"src/main/resources/data/Customers.csv"**

----------------------------------------------------------------------------------------------------------------------------------------------------------------
