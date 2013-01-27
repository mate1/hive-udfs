#hive-udfs

Collection of useful Hive UDFs

##DecodeURL
UDF to decode a URI escaped URI. 

Example:

```sql

ADD JAR hdfs:///<path to jar>;
CREATE TEMPORARY FUNCTION decode_url AS 'com.sharethrough.hive.udfs.DecodeURL';

SELECT decode_url(escaped_url)
FROM logs
LIMIT 10;

```


## Haversine Distance

UDF to compute the [haversine distance](http://en.wikipedia.org/wiki/Haversine_formula) between
two pairs of coordinates.

Example:

```sql

ADD JAR hdfs:///<path to jar>;
CREATE TEMPORARY FUNCTION haversine_distance AS 'com.sharethrough.hive.udfs.HaversinceDistance';

SELECT 
  haversine_distance(lon1, lat1, lon2, lat2) as distance
FROM user_checkins
LIMIT 10;

```
