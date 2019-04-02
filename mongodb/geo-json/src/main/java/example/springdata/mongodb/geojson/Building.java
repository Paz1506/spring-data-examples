package example.springdata.mongodb.geojson;

import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "starbucks")
public class Building {
    String id;
    String name;
    //    https://github.com/spring-projects/spring-data-mongodb/blob/master/spring-data-mongodb/src/test/java/org/springframework/data/mongodb/repository/Person.java
//    https://github.com/Automattic/mongoose/issues/6429
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    GeoJsonPoint location;
//    GeoJsonLineString geoJsonLineString;
//    GeoJsonPolygon geoJsonPolygon;
}
