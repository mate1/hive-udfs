#hive-udfs

Collection of useful Hive UDFs

## Building the Project

Building a jar containing these UDFs is simple. 

First you will need:

* Scala 2.9.2 or later
* SBT 0.12.0 

Then all you have to do is execute 1 command:

```
./sbt assembly
```

This will create a jar including dependencies in ```target/hive-udf-assembly-VERSION.jar```

## Available UDFs

### GeocodeIP
UDF to transform an IP address into a useful piece of geoip data

Requires GeoLiteCity database to be present on all nodes, which can be accomplished
using ```ADD FILE```.

Example:

```sql
ADD FILE hdfs://<path to GeoLiteCity.dat>;
ADD JAR hdfs:///<path to jar>;
CREATE TEMPORARY FUNCTION geocode_ip AS 'com.edate.data.hive.udf.GeocodeIP';

SELECT
  geocode_ip(remote_ip, 'city', 'GeoLiteCity.dat') as city,
  geocode_ip(remote_ip, 'dma_code', 'GeoLiteCity.dat') as dma_code,
FROM nginx_requests

```

### Haversine Distance

UDF to compute the [haversine distance](http://en.wikipedia.org/wiki/Haversine_formula) between
two pairs of coordinates.

Example:

```sql

ADD JAR hdfs:///<path to jar>;
CREATE TEMPORARY FUNCTION haversine_distance AS 'com.edate.data.hive.udf.HaversinceDistance';

SELECT 
  haversine_distance(lon1, lat1, lon2, lat2) as distance
FROM user_checkins
LIMIT 10;

```

###DecodeURL
UDF to decode a URI escaped URI. 

Example:

```sql

ADD JAR hdfs:///<path to jar>;
CREATE TEMPORARY FUNCTION decode_url AS 'com.edate.data.hive.udf.DecodeURL';

SELECT decode_url(escaped_url)
FROM logs
LIMIT 10;

```

