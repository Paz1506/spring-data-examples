package example.springdata.mongodb.geojson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildingRepositoryTests {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private MongoOperations operations;

    @Autowired
    private MongoTemplate template;

    //    https://stackoverflow.com/questions/41513112/cant-extract-geo-keys-longitude-latitude-is-out-of-bounds
    private static final GeoJsonPolygon GEO_JSON_POLYGON_SMALL = new GeoJsonPolygon(new Point(135.111319, 48.396702),
                                                                                    new Point(135.110783, 48.399224),
                                                                                    new Point(135.113776, 48.398682),
                                                                                    new Point(135.113658, 48.396666),
                                                                                    new Point(135.111319, 48.396702));

    private static final GeoJsonPolygon GEO_JSON_POLYGON_LARGE = new GeoJsonPolygon(new Point(135.104234, 48.395738),
                                                                                    new Point(135.106669, 48.401038),
                                                                                    new Point(135.117446, 48.398485),
                                                                                    new Point(135.116040, 48.394360),
                                                                                    new Point(135.104234, 48.395738));

    private static final GeoJsonLineString ROAD = new GeoJsonLineString(new Point(135.113948, 48.398107),
                                                                        new Point(135.113862, 48.397915),
                                                                        new Point(135.113798, 48.397701),
                                                                        new Point(135.113315, 48.397801));

    /**
     * Выведет все, кроме 48.396666, 135.108186 - buildNAU, 48.397670, 135.115557 - build48O
     *
     * @throws Exception
     */
    @Test
    public void findByLocationWithinPolygonSmall() throws Exception {
        List<Building> byLocationWithin = buildingRepository.findByLocationWithin(GEO_JSON_POLYGON_SMALL);
        System.out.println("SIZE SMALL: " + byLocationWithin.size());
        byLocationWithin.forEach(System.out::println);

    }

    /**
     * Выведет все
     *
     * @throws Exception
     */
    @Test
    public void findByLocationWithinPolygonLarge() throws Exception {
        List<Building> byLocationWithin = buildingRepository.findByLocationWithin(GEO_JSON_POLYGON_LARGE);
        System.out.println("SIZE LARGE: " + byLocationWithin.size());
        byLocationWithin.forEach(System.out::println);

    }

    /**
     * Выведет все
     *
     * @throws Exception
     */
    @Test
    public void findNearestByRoad() throws Exception {
        System.out.println("NEAREST: ");
        GeoResults<Building> geoResults = template.geoNear(NearQuery.near(new Point(135.113862, 48.397915)).spherical(true).num(2L).maxDistance(1000L), Building.class);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("name").is("build48O"));//work;
//        query.addCriteria(Criteria.where("location").near(new Point(135.113862, 48.397915)));
//        List<Building> all = template.find(query, Building.class);
//        all.forEach(System.out::println);
        geoResults.forEach(result -> System.out.println(result.getContent()));

    }
}