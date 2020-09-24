A queue to allow company objects to be pushed and popped and persisted

To run:
java -jar target/customer-q-service-0.1.0.jar

To add a company:
curl -i -X POST -d @cc.json -H "Content-Type: application/json" http://localhost:8081/queueService/add
where:
cc.json--> {"name":"GavWebCo2","description":"The final description", userName="gavlad"}

To retrieve a company:
curl -i -X GET -H "Content-Type: application/json" http://localhost:8081/queueService/get
