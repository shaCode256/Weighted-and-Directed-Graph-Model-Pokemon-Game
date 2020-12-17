package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationTest {

    geo_location geo1 = new GeoLocation(2.5, 3.456, 5.43);
    geo_location geo2 = new GeoLocation(4.324, 12.873, 11);

    /**
     * This test for distance
     */
    @Test
    void distance() {
        assertEquals(11.09196849075943, geo1.distance(geo2));
        assertEquals(11.09196849075943, geo2.distance(geo1));
    }
}