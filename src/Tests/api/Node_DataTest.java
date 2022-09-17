package api;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Node_DataTest {
    Geo_Location P5 = new Geo_Location(18,19,21);
    Geo_Location P4 = new Geo_Location(1,2,3);
    Geo_Location P3 = new Geo_Location(5,6,7);
    Geo_Location P1 = new Geo_Location(8,9,10);
    Node_Data n1= new Node_Data(1,P1);
    Node_Data n2 = new Node_Data(2,P3);
    Node_Data n3 = new Node_Data(3,P4);
    Node_Data n4 = new Node_Data(4,P5);
    @Test
    void getKey() {
        assertEquals(1, n1.getKey());
        assertEquals(2,n2.getKey());
        assertEquals(3,n3.getKey());
        assertEquals(4,n4.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(8, n1.getLocation().x());
        assertEquals(9, n1.getLocation().y());
        assertEquals(6, n2.getLocation().y());
        assertEquals(3,n3.getLocation().z());
    }

    @Test
    void setLocation() {
        Geo_Location P6 = new Geo_Location(1,2,3);
        n2.setLocation(P6);
        assertEquals(1, n2.getLocation().x());
        assertEquals(2,n2.getLocation().y());

    }

    @Test
    void getWeight() {
        assertEquals(0,n1.getWeight());
        assertEquals(0,n2.getWeight());
        assertEquals(0,n3.getWeight());
        assertEquals(0,n4.getWeight());
    }

    @Test
    void setWeight() {
        n1.setWeight(1);
        n2.setWeight(2);
        n3.setWeight(3);
        n4.setWeight(4);
        assertEquals(1,n1.getWeight());
        assertEquals(2,n2.getWeight());
        assertEquals(3,n3.getWeight());
        assertEquals(4,n4.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("", n1.getInfo());
        assertEquals("",n2.getInfo());
        assertEquals("",n3.getInfo());
        assertEquals("",n4.getInfo());
    }

    @Test
    void setInfo() {
        n1.setInfo("1");
        n2.setInfo("2");
        n3.setInfo("3");
        n4.setInfo("4");
        assertEquals("1",n1.getInfo());
        assertEquals("2",n2.getInfo());
        assertEquals("3",n3.getInfo());
        assertEquals("4",n4.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0,n1.getTag());
        assertEquals(0,n2.getTag());
        assertEquals(0,n3.getTag());
        assertEquals(0,n4.getTag());
    }

    @Test
    void setTag() {
        n1.setTag(1);
        n2.setTag(2);
        n3.setTag(3);
        n4.setTag(4);
        assertEquals(1,n1.getTag());
        assertEquals(2,n2.getTag());
        assertEquals(3,n3.getTag());
        assertEquals(4,n4.getTag());
    }
}