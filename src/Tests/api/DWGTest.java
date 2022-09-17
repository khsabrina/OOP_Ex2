package api;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class DWGTest {
    DWG gr = new DWG("data\\G1.json");
    DWGTest() throws Exception {
        DWG gr = new DWG("data\\G1.json");
    }
    //DWG gr = new DWG(1000000);

    @Test
    void getNode() {
        Geo_Location p = new Geo_Location(35.212111165456015,32.106235628571426,0.0);
        Geo_Location p1 = new Geo_Location(35.197400995964486,32.10510889579832,0.0);
        NodeData n = new Node_Data(5,p);
        NodeData n1= new Node_Data(10,p1);
        assertEquals(n.getKey(),gr.getNode(5).getKey());
        assertEquals(n.getLocation().x(),gr.getNode(5).getLocation().x());
        assertEquals(n1.getLocation().z(),0.0);
        assertEquals(n1.getLocation().y(), 32.10510889579832);
        assertEquals(n1.getWeight(),0);
        assertEquals(n.getWeight(),0);
        assertEquals(n1.getKey(),10);
        gr.removeNode(5);
        assertEquals(null,gr.getNode(5));
    }

    @Test
    void getEdge() {
        assertEquals(10,gr.getEdge(10,11).getSrc());
        assertEquals(1.4962204797190428,gr.getEdge(10,11).getWeight());
        assertEquals(null,gr.getEdge(16,14));
        assertEquals(11,gr.getEdge(10,11).getDest());
        gr.removeEdge(10,11);
        assertEquals(null, gr.getEdge(10,11));
        gr.connect(10,15,12.5);
        assertEquals(12.5,gr.getEdge(10,15).getWeight());
    }
    @Test
    void addNode() {
        Geo_Location p = new Geo_Location(35.212111165456015,32.106235628571426,0.0);
        NodeData n = new Node_Data(17,p);
        gr.addNode(n);
        assertEquals(n.getLocation().x(),gr.getNode(17).getLocation().x());
        Geo_Location p1 = new Geo_Location(34,34,0.0);
        NodeData n1 = new Node_Data(18,p1);
        assertEquals(null,gr.getNode(18));
        gr.addNode(n1);
        assertEquals(18,gr.getNode(18).getKey());
        assertEquals(34,gr.getNode(18).getLocation().x());
    }

    @Test
    void connect() {
        gr.connect(16,14,8);
        assertEquals(16,gr.getEdge(16,14).getSrc());
        assertEquals(8, gr.getEdge(16,14).getWeight());
        gr.connect(3,8,9);
        assertEquals(8,gr.getEdge(3,8).getDest());
        assertEquals(9, gr.getEdge(3,8).getWeight());
        gr.connect(3,8,20);
        assertEquals(9, gr.getEdge(3,8).getWeight());
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> it1= gr.nodeIter();
        NodeData n1= it1.next();
        assertEquals(0, n1.getKey());
        assertEquals(35.19589389346247,n1.getLocation().x());
        NodeData n2= it1.next();
        assertEquals(1,n2.getKey());
        assertEquals(35.20319591121872,n2.getLocation().x());
        int count = 2;
        while (it1.hasNext()){
            it1.next().getKey();
            count++;
        }
        assertEquals(count,gr.nodeSize());
        gr.removeNode(2);

        try
        {
            it1.next();
        }
        catch( final RuntimeException e )
        {
            final String msg = "graph was changed since the iterator was constructed";
            assertEquals(msg, e.getMessage());
        }
        try
        {
            it1.hasNext();
        }
        catch( final RuntimeException e )
        {
            final String msg = "graph was changed since the iterator was constructed";
            assertEquals(msg, e.getMessage());
        }
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> it1= gr.edgeIter();
        int count = 0;
        assertEquals(0, it1.next().getSrc());
        while (it1.hasNext()){
            it1.next().getSrc();
            count++;
        }
        assertEquals(count+1,gr.edgeSize());
        gr.removeEdge(0,16);
        try
        {
            it1.next();
        }
        catch( final RuntimeException e )
        {
            final String msg = "graph was changed since the iterator was constructed";
            assertEquals(msg, e.getMessage());
        }
        try
        {
            it1.hasNext();
        }
        catch( final RuntimeException e )
        {
            final String msg = "graph was changed since the iterator was constructed";
            assertEquals(msg, e.getMessage());
        }

    }

    @Test
    void testEdgeIter() {
        Iterator<EdgeData> it1= gr.edgeIter(0);
        EdgeData n1= it1.next();
        assertEquals(0, n1.getSrc());
        assertEquals(16,n1.getDest());


    }

    @Test
    void removeNode() {
        assertEquals(5, gr.removeNode(5).getKey());
        assertEquals(35.20797194027441,gr.removeNode(6).getLocation().x());
        assertEquals(null,gr.getNode(5));
    }

    @Test
    void removeEdge() {
        assertEquals(5,gr.removeEdge(4,5).getDest());
        assertEquals(6,gr.removeEdge(5,6).getDest());
        assertEquals(null ,gr.removeEdge(4,5));

    }

    @Test
    void nodeSize() {
        assertEquals(17,gr.nodeSize());
        gr.removeNode(16);
        assertEquals(16,gr.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(36,gr.edgeSize());
        gr.removeEdge(0,16);
        assertEquals(35,gr.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0,gr.getMC());
        gr.addNode(new Node_Data(17,new Geo_Location(5.5,6.3,0.0)));
        assertEquals(1, gr.getMC());
        gr.connect(17,2,25.3);
        assertEquals(2, gr.getMC());
    }
}