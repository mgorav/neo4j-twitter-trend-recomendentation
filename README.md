## Twitter Trend Analysis Using neo4j

Paradigm shift from relational to graph based modelling in my opinion is the future of domain modelling and is a natural fit for domain driven design. It also facilitates real time data analytics,machine learning & artificial intelligence. The shift is difficult as for years tables are in mind - forget tables!! :-) & give graph DB a chance. Check out following project build using neo4j + Spring REST + Spring data neo4j + Spring Social. The application aims to:
1) analyse tweets trend using live twitter stream
2) provide recommendations. 
3) demonstrate ACID behaviour of neo4j & no sql querying using cypher (neo4j QL based open [opencypher](http://opencypher.org).

Graph based database can be of great asset in the field of payment services, financial services, health insurance, IoT, fraud.... 
 - as the graph based modelling is simple for complex requirements (white board drawing can be translated to graph model), 
- is performant (no need to think about intersection tables,joins, indexes etc), 
- is fast querying using cypher , dynamic evolving (in relational terminology, columns/attributes can be added at will), 
- is scalable, 
- fast & complex querying without multiple joins & intersection table

### Instructions to install

1. Install neo4j 
```
docker run \
    --publish=7474:7474 --publish=7687:7687 \
    --volume=$HOME/neo4j/data:/data \
    --volume=$HOME/neo4j/logs:/logs \
    neo4j
```
neo4j browser url:
```   
http://localhost:7474/
```    
2. Specify following properties in application.properties file
```
#
# neo4j properties
spring.data.neo4j.username=yoursusername
spring.data.neo4j.password=yourspassword
#
# application port
server.port=5050
#
# Twitter credentials
spring.social.twitter.appId=yoursappid
spring.social.twitter.appSecret=yoursappsecret

spring.social.twitter.accessToken=yoursaccesstoken
spring.social.twitter.accessTokenSecret=yoursaccesstokensecret

#
# If true, will start gathering live stream for analytics and scheduled based on -Dgm.twitter.search
# will not occur
gm.twitter.live.stream=false
```

**NOTE** Create twitter consumerKey (spring.social.twitter.appId) and consumerSecret (spring.social.twitter.appSecret)

4. Run the application 
```
mvn spring-boot:run [-Dgm.twitter.search="twitter query"]
```

**NOTE** By default there is default neo4j cypher is provided

### REST Metadata APIs
```
http://localhost:5050/users
http://localhost:5050/tweets
http://localhost:5050/tags
 http://localhost:5050/users/search/
```

To find trending tweets use API:
```
http://localhost:5050/tweets/trend
```

neo4j will depict tweets as graphs shown below:

  ![alt text](./neo4j.png)


    
    
 
