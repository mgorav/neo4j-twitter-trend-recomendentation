## Twitter Trend Analysis Using neo4j

Using neo4j and Spring Social + Spring REST + Spring DATA neo4j, this application shows:
1. Trending tweets
2. Recommendation engine (friend recommendations)

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
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=<passWord>
spring.social.twitter.appId=<appId>  
spring.social.twitter.appSecret=<appId>
```

**NOTE** Create twitter consumerKey (spring.social.twitter.appId) and consumerSecret (spring.social.twitter.appSecret)

4. Run the application 
```
mvn spring-boot:run [-Dtwitter.search="springsource"]
```

**NOTE** By default there is default neo4j cypher is provided

### REST Metadata APIs
```
http://localhost:5050/users
http://localhost:5050/tweets
http://localhost:5050/tags
 http://localhost:5050/users/search/
```

All set to run the twitter trend APIS

neo4j will depicts graphs as:

  ![alt text](./neo4j.png)


    
    
 
