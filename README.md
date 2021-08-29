# WSBarCode
The educational project.  
Started on August 22.

## Main idea
The main idea is to create a web-service that performs transformation
from a **number** to a **barcode** and vice versa.

## Application description
The system contains *algorithm*s and *client*s.  
Every *client* can use the list of *algorithm*s allowed and tuned for him.  
The *algorithm* contains the list of steps with tasks or *actions* to do to generate **barcode** and to revert it back.  
The *client* is linked with *algorithms* in relation of many-to-many.  
Each *link* contains some additional *client* specific data for generation (it's just a number for now).  

### Generating barcodes 
So the *client* invokes the *generation* method with **value**s list and next things happen:
1. The system checks that the *client* is allowed to use the *algorithm*.
2. The system gets special data (the number) from *client* - *algorithm* relation.
3. For every string in the list of **value**s:  
3.1. The system tests **value**'s format.  
3.2. The *algorithm* performs transformation steps with tasks or *action*s to generate **barcode** using:
     - **value**
     - *client*'s special data
     - *action*'s inner data
4. Returns results list back to the caller. Each list's item contains either generated **barcode** or error description.

### Parsing barcodes
If the *client* invokes the *parse* method with **barcode**s list then steps are almost the same:
1. (same) The system checks that the *client* is allowed to use the *algorithm*. 
2. (same) The system gets special data (the number) from *client* - *algorithm* connection.
3. For every string in the list of **barcode**s:  
3.1. The system tests **barcode**'s format.  
3.2. The *algorithm* performs transformation steps backwards with tasks or *actions* to parse **barcode** using:
     - **barcode**
     - *client*'s special data
     - *action*'s inner data  
3.3. The algorithm also validates **barcode**'s correctness:
     - tests **barcode**'s check number
     - *client*'s special data matches given **barcode**'s digits
     - *action*'s inner data matches given **barcode**'s digits  
(all these points depends on actual *algorithm*'s steps or *action*s)
4. Returns results list back to the caller. Each list's item contains either parsed **value** or error description.

### Manage DB operations
The Web-service also provides REST API to hold *algorithm*s and *client*s data in DB.

## Database
Tests on: `Oracle Database 12c Enterprise Edition Release 12.2.0.1.0 - 64bit Production`  
Database scripts: [barcode_db_scripts.sql](/misc/barcode_db_scripts.sql)

![Database image](/misc/barcode_db_schema.jpg)

## Swagger
The swagger can be viewed here [barcode.yaml](/misc/barcode.yaml)

## Request and response examples

### Manage DB operations

#### Create the algorithm
HTTP `POST /manage/algorithm`  
Request:
```
    {
        "name": "SIMPLE10",
        "inPattern": "[0-9]{8}",
        "outPattern": "[0-9]{11}",
        "description": "Simple algorithm v1.0",
        "actions": [
            {
                "task": "SWP",
                "orderNum": 1,
                "ind1": 2,
                "ind2": 4,
                "description": "Swaps ind1 and ind2 digits"
            },
            {
                "task": "CHKNUM1",
                "orderNum": 4,
                "description": "Adds simple check num to the end using (1,3,1,3,1,3,1,3) multipliers"
            },
            {
                "task": "ADD_CLT",
                "orderNum": 2,
                "ind1": 5,
                "ind2": 3,
                "description": "Inserts digit from client's specValue at position ind2 into position ind1"
            },
            {
                "task": "INS",
                "orderNum": 3,
                "ind1": 1,
                "ind2": 2,
                "description": "Inserts fixed digit with value ind2 into position ind1"
            }
        ]
    }
```

- *name* - unique name of the algorithm
- *inPattern* - regular expression to test **value**s
- *outPattern* - regular expression to test **beracode**s
- *description* - description of algorithm
- *actions* - the list of steps or tasks to do transformation
- *actions.task* - task identifier (or name, or code)
- *actions.orderNum* - the order number of action
Actions apply in the increasing order to generate the barcode and in the decreasing order to parse it back
- *actions.ind1*, *actions.ind2*, *actions.count* - *action*'s inner data
- *actions.description* - unnecessary action description. Should be replaced with generated description in the future


The response body has the same structure as request.  
All response fields contain actual DB values (even "null"s). 
 
> Http-code for success response: `201 Created`

#### Update the algorithm
HTTP `PUT /manage/algorithm`  
The request body is the same as in *create the algorithm* except most fields can be "null"ed.
"Null" fields will not be updated in DB.

The reponse body is also the same.
> Http-code for success response: `200 OK`

#### Delete the algorithm by name
HTTP `DELETE /manage/algorithm/{name}`  
- *name* - the name of deleting algorithm.  
No request body.  

No response body.  
> Http-code for success response: `204 No Content`

#### Get the algorithm by name
HTTP `GET /manage/algorithm/{name}`  
*{name}* - the name of getting algorithm.  
No request body.

The response body is the same as in *create the algorithm*.
> Http-code for success response: `200 OK`

#### Get all algorithms
HTTP `GET /manage/algorithms`  
No request body. 
 
The response body contains the list of *create the algorithm*'s request bodies.  
Response for example:  
```
    {
        "count": 2,
        "items": [
            {
                "name": "SIMPLE10",
                "inPattern": "[0-9]{8}",
                "outPattern": "[0-9]{11}",
                "description": "Simple algorithm v1.0",
                "actions": [
                    {
                        "task": "SWP",
                        "orderNum": 1,
                        "ind1": 2,
                        "ind2": 4,
                        "description": "Swaps ind1 and ind2 digits"
                    },
                    {
                        "task": "CHKNUM1",
                        "orderNum": 4,
                        "description": "Adds simple check num to the end using (1,3,1,3,1,3,1,3) multipliers"
                    },
                    {
                        "task": "ADD_CLT",
                        "orderNum": 2,
                        "ind1": 5,
                        "ind2": 3,
                        "description": "Inserts digit from client's specValue at position ind2 into position ind1"
                    },
                    {
                        "task": "INS",
                        "orderNum": 3,
                        "ind1": 1,
                        "ind2": 2,
                        "description": "Inserts fixed digit with value ind2 into position ind1"
                    }
                ]
            },
            {
                "name": "NOTRNSF10",
                "inPattern": "[0-9]{8}",
                "outPattern": "[0-9]{10}",
                "description": "No transform v1.0. The algorithm only adds '2' in the beginning and check num in the end",
                "actions": [
                    {
                        "task": "CHKNUM1",
                        "orderNum": 3,
                        "description": "Adds simple check num to the end using (1,3,1,3,1,3,1,3) multipliers"
                    },
                    {
                        "task": "INS",
                        "orderNum": 3,
                        "ind1": 1,
                        "ind2": 2,
                        "description": "Inserts fixed digit with value ind2 into position ind1"
                    }
                ]
            }
        ]
    }
```

> Http-code for success response: `200 OK`

#### Create the client
HTTP `POST /manage/client`  
Request:
```
    {
        "code":"CLT1",
        "name":"TFC: The First Client",
        "description":"This is the first client ever created",
        "algorithms":[
            {
                "algorithmName":"NOTRNSF10",
                "specValue":"11111111"
            },
			{
                "algorithmName":"SIMPLE10",
                "specValue":"12345678"
            }
        ]
    }
```
	
- *code* - unique code of the client
- *name* - title of the client
- *description* - description of client
- *algorithms* - list of allowed algorithms with specific data
- *algorithms.algorithmName* - the name of allowed algorithm 
- *algorithms.specValue* - specific client-algorithm data (yes, it's just a number for now)
	
The response body has the same structure as request.  
All response fields contain actual DB values (even "null"s). 
 
> Http-code for success response: `201 Created`

#### Update the client
HTTP `PUT /manage/client`  
The request body is the same as in *create the client* except most fields can be "null"ed.
"Null" fields will not be updated in DB.

The reponse body is also the same.
> Http-code for success response: `200 OK`

#### Delete the client by code
HTTP `DELETE /manage/client/{code}`  
- *code* - the code of deleting client.
No request body.

No response body.
> Http-code for success response: `204 No Content`

#### Get the client by the code
HTTP `GET /manage/client/{code}`  
*{code}* - the name of getting algorithm.
No request body.

The response body is the same as in *create the client*.
> Http-code for success response: `200 OK`

#### Get all clients
HTTP `GET /manage/clients`  
No request body.

The response body contains the list of *create an client*'s request bodies in the same manner as in *Get all algorithms*.
> Http-code for success response: `200 OK`

### Barcode operations

#### Generate barcode from value
HTTP `POST /tobarcode`  
Request:
```
    {
        "clientCode":"CLT1",
        "algorithm":"SIMPLE10",
        "values":["12345678", "66666666", "345"]
    }
```
- *clientCode* - code of client
- *algorithm* - name of algorithm to use
- *values* - the list of values to generate barcode's list

Response:
```
    {
        "count": 3,
        "results": [
            {
                "generatedValue": "21432356787",
                "status": "OK"
            },
            {
                "generatedValue": "26666366663",
                "status": "OK"
            },
            {
                "status": "ERROR",
                "errorDescription": "Value [345] doesn't match algorithm pattern [[0-9]{8}]"
            }
        ]
    }
```

- *count* - length of the list
- *results* - results list
- *results.generatedValue* - generated barcode
- *results.status* - generating status: "OK" or "ERROR"
- *results.errorDescription* - error description if occurs any

> Http-code for success response: `200 OK`

#### Parse barcode to value
HTTP `POST /frombarcode`  
Request:
```
    {
        "clientCode":"CLT1",
        "algorithm":"SIMPLE10",
        "values":["21432356787", "26666366663", "26666366669", "26666466660"]
    }
```
- *clientCode* - code of client
- *algorithm* - name of algorithm to use
- *values* - the list of values to generate barcode's list

Response:
```
    {
        "count": 4,
        "results": [
            {
                "generatedValue": "12345678",
                "status": "OK"
            },
            {
                "generatedValue": "66666666",
                "status": "OK"
            },
            {
                "status": "ERROR",
                "errorDescription": "Invalid check number: real = 9, expected = 3"
            },
            {
                "status": "ERROR",
                "errorDescription": "Invalid client number: real = 4, expected = 3"
            }
        ]
    }
```

- *count* - length of the list
- *results* - results list
- *results.generatedValue* - parsed value
- *results.status* - parsing status: "OK" or "ERROR"
- *results.errorDescription* - error description if occurs any

> Http-code for success response: `200 OK`

### Error response
Currently errors have structure:
```
{
   "errorCode": "Barcode.Error.BadRequest.MalformedRequest",
   "errorDescr": "HttpMessageNotReadableException : JSON parse error: Unexpected character ('2' (code 50)): was expecting double-quote to start field name; nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character ('2' (code 50)): was expecting double-quote to start field name\n at [Source: (PushbackInputStream); line: 3, column: 25]",
   "errorTrace": "org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unexpected character ('2' (code 50)): was expecting double-quote to start field name; nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character ('2' (code 50)): was expecting double-quote to start field name\n at [Source: (PushbackInputStream); line: 3, column: 25]\n\tat org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:389)\n\tat org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.read(AbstractJackson2HttpMessageConverter.java:342)\n\tat ..."
}
```
- *errorCode* - specific error code
- *errorDescr* - description of error (exception)
- *errorTrace* - trace of error (exception)

> Http-codes for responses: `400 Bad Request`, `404 Not Found`, `500 Internal Server Error`

Error handling will be chenged in the future.

## TODO list
- Rebuild error responses
- Add request values validation: not only for "null"s and emptiness
- Add algorithm data validation: test algorithm before saving in DB
- Add security features: ip-address checking
- Optimize some details:
  - dynamically generate action's description
  - change task function generating process

## Used applications and frameworks

### Java
- Java Version: 1.8.0_301

### Frameworks
[pom.xml](pom.xml)

- Spring Boot 2.5.3
  - Web
- Hibernate (Spring Boot)
- Log4j2 (Spring Boot)

### Applications
- IntelliJ IDEA 2020.1 (Community Edition)
- Oracle SQL Developer 19.2.1.247
- SoaupUI 5.5.0
- Docker 20.10.6
- Apache Tomcat/9.0.50
- TortoiseGit 2.8.0.0 (git version 2.23.0)
