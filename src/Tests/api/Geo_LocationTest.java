package api;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Geo_LocationTest {
    Geo_Location P1 = new Geo_Location(5,6,7);
    Geo_Location P2 = new Geo_Location(7,8,9);
    Geo_Location P3 = new Geo_Location(10,11,1);
    Geo_Location P5 = new Geo_Location(18,19,21);
    Geo_Location P4 = new Geo_Location(1,2,3);


    @Test
    void x() {
        assertEquals(5, P1.x());
        assertEquals(7, P2.x());
        assertEquals(10, P3.x());
        assertEquals(1, P4.x());
        assertEquals(18,P5.x());
    }

    @Test
    void y() {
        assertEquals(6,P1.y());
        assertEquals(8,P2.y());
        assertEquals(11,P3.y());
        assertEquals(2,P4.y());
        assertEquals(19,P5.y());
    }

    @Test
    void z() {
        assertEquals(7,P1.z());
        assertEquals(9,P2.z());
        assertEquals(1,P3.z());
        assertEquals(3,P4.z());
        assertEquals(21,P5.z());
    }

    @Test
    void distance() {
        assertEquals(2*Math.sqrt(3),P1.distance(P2));
        assertEquals(Math.sqrt(82),P2.distance(P3));
    }
}