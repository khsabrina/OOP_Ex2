package api;

import api.Edge_Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import api.*;

class Edge_DataTest {
    Edge_Data e1 = new Edge_Data(1,2,0);
    Edge_Data e2 = new Edge_Data(2,1,0);
    Edge_Data e3 = new Edge_Data(3,4,5);
    @Test
    void getSrc() {
        assertEquals(1,e1.getSrc());
        assertEquals(2,e2.getSrc());
        assertEquals(3,e3.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(2,e1.getDest());
        assertEquals(1,e2.getDest());
        assertEquals(4,e3.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(0,e1.getWeight());
        assertEquals(0,e2.getWeight());
        assertEquals(5,e3.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("0", e1.getInfo());
        assertEquals("0",e2.getInfo());
        assertEquals("0",e3.getInfo());

    }

    @Test
    void setInfo() {
        e1.setInfo("1");
        e2.setInfo("2");
        e3.setInfo("3");
        assertEquals("1", e1.getInfo());
        assertEquals("2",e2.getInfo());
        assertEquals("3",e3.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(0,e1.getTag());
        assertEquals(0,e2.getTag());
        assertEquals(0,e3.getTag());
    }

    @Test
    void setTag() {
        e1.setTag(1);
        e2.setTag(2);
        e3.setTag(3);
        assertEquals(1,e1.getTag());
        assertEquals(2,e2.getTag());
        assertEquals(3,e3.getTag());
    }
}