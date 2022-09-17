package api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGATest {
    DWG gr = generate();
    DWGA graph = new DWGA(gr);

    @Test
    void init() {
        DWGA graph1 = new DWGA();
        graph1.init(gr);
        Iterator<EdgeData> original = gr.edgeIter();
        Iterator<EdgeData> initgraph = graph1.getGraph().edgeIter();
        while (original.hasNext()){
            assertEquals(original.next().getWeight(),initgraph.next().getWeight());
        }
    }

    @Test
    void getGraph() {
        Iterator<EdgeData> original = gr.edgeIter();
        Iterator<EdgeData> initgraph = graph.getGraph().edgeIter();
        while (original.hasNext()){
            assertEquals(original.next().getWeight(),initgraph.next().getWeight());
        }
    }

    @Test
    void copy() {
        DWG copy = (DWG) graph.copy();
        assertEquals(copy.edgeSize(),gr.edgeSize());
        assertEquals(copy.nodeSize(), gr.nodeSize());
        Iterator<NodeData> iterator = copy.nodeIter();
        Iterator<NodeData> iterator1 = gr.nodeIter();
        while (iterator.hasNext()){
            assertEquals(iterator.next().getLocation(), iterator1.next().getLocation());
        }
        Iterator<EdgeData> iterator2 = copy.edgeIter();
        Iterator<EdgeData> iterator3 = gr.edgeIter();
        while (iterator.hasNext()){
            assertEquals(iterator2.next().getSrc(),iterator3.next().getSrc());
        }
    }

    @Test
    void isConnected() {
        assertEquals(true, graph.isConnected());
        graph.getGraph().removeEdge(5,0);
        assertEquals(false, graph.isConnected());
        graph.getGraph().connect(2,0,0.9);
        assertEquals(true, graph.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(graph.shortestPathDist(2,6), 12.9, 0.0001);
        assertEquals(graph.shortestPathDist(0,2),7.8, 0.0001);
        assertEquals(graph.shortestPathDist(2,4),1.5, 0.0001);
        graph.getGraph().connect(2,0,0.2);
        graph.getGraph().removeEdge(0,4);
        graph.getGraph().connect(0,4,0.3);
        assertEquals(graph.shortestPathDist(2,4),0.5, 0.0001);
    }

    @Test
    void shortestPath() {
        List<NodeData> route = graph.shortestPath(2,4);
        Iterator<NodeData> iterator = route.iterator();
        assertEquals(iterator.next(),graph.getGraph().getNode(2));
        assertEquals(iterator.next(),graph.getGraph().getNode(3));
        assertEquals(iterator.next(),graph.getGraph().getNode(4));
        graph.getGraph().removeEdge(5,0);
        List<NodeData> route1 = graph.shortestPath(2,1);
        iterator = route1.listIterator();
        assertEquals(iterator.next(),graph.getGraph().getNode(2));
        assertEquals(iterator.next(),graph.getGraph().getNode(3));
        assertEquals(iterator.next(),graph.getGraph().getNode(4));
        assertEquals(iterator.next(),graph.getGraph().getNode(5));
        assertEquals(iterator.next(),graph.getGraph().getNode(6));
        assertEquals(iterator.next(),graph.getGraph().getNode(7));
        assertEquals(iterator.next(),graph.getGraph().getNode(8));
        assertEquals(iterator.next(),graph.getGraph().getNode(9));
        assertEquals(iterator.next(),graph.getGraph().getNode(1));
        List<NodeData> route2 = graph.shortestPath(0,8);
        iterator = route2.listIterator();
        assertEquals(iterator.next(),graph.getGraph().getNode(0));
        assertEquals(iterator.next(),graph.getGraph().getNode(8));
    }

    @Test
    void center() {
        assertEquals(graph.center().getKey(),0);
    }

    @Test
    void tsp() {
        List<NodeData> check = new ArrayList<>(),check2 = new ArrayList<>(), check3 = new ArrayList<>();
        for (int i = 0; i < 9; i=i+2) {
            check.add(this.gr.getNode(i));
        }
        check2.add(this.gr.getNode(0));
        check2.add(this.gr.getNode(3));
        check2.add(this.gr.getNode(7));
        List<NodeData> ans = new ArrayList<>(), ans2 = new ArrayList<>(), ans3 = new ArrayList<>();
        ans.add(this.gr.getNode(0));
        ans.add(this.gr.getNode(8));
        ans.add(this.gr.getNode(9));
        ans.add(this.gr.getNode(1));
        ans.add(this.gr.getNode(2));
        ans.add(this.gr.getNode(3));
        ans.add(this.gr.getNode(4));
        ans.add(this.gr.getNode(5));
        ans.add(this.gr.getNode(6));

        ans2.add(this.gr.getNode(0));
        ans2.add(this.gr.getNode(7));
        ans2.add(this.gr.getNode(8));
        ans2.add(this.gr.getNode(9));
        ans2.add(this.gr.getNode(1));
        ans2.add(this.gr.getNode(2));
        ans2.add(this.gr.getNode(3));

        check3.add(this.gr.getNode(0));
        check3.add(this.gr.getNode(5));
        check3.add(this.gr.getNode(4));

        ans3.add(this.gr.getNode(0));
        ans3.add(this.gr.getNode(5));
        ans3.add(this.gr.getNode(0));
        ans3.add(this.gr.getNode(4));

        List<NodeData> test = this.graph.tsp(check);
        List<NodeData> test2 = this.graph.tsp(check2);
        List<NodeData> test3 = this.graph.tsp(check3);

        assertEquals(test,ans);
        assertEquals(test2,ans2);
        assertEquals(test3, ans3);
    }

    @Test
    void save() {
        graph.save("data\\G1TEST.json");
        DWGA graph2 = new DWGA();
        graph2.load("data\\G1TEST.json");
        assertEquals(graph.getGraph().nodeSize(),graph2.getGraph().nodeSize());
    }

    @Test
    void load() {
        graph.save("data\\G1TEST.json");
        DWGA graph2 = new DWGA();
        graph2.load("data\\G1TEST.json");
        assertEquals(graph.getGraph().nodeSize(),graph2.getGraph().nodeSize());
    }

    public DWG generate(){
        DWG gr = new DWG();
        Node_Data a0 = new Node_Data(0, new Geo_Location(0,0,0));
        Node_Data a1 = new Node_Data(1, new Geo_Location(1,6,0));
        Node_Data a2 = new Node_Data(2, new Geo_Location(4,5,0));
        Node_Data a3 = new Node_Data(3, new Geo_Location(-1,4,0));
        Node_Data a4 = new Node_Data(4, new Geo_Location(3,0,0));
        Node_Data a5 = new Node_Data(5, new Geo_Location(2,-2,0));
        Node_Data a6 = new Node_Data(6, new Geo_Location(-1,-8,0));
        Node_Data a7 = new Node_Data(7, new Geo_Location(-6,-6,0));
        Node_Data a8 = new Node_Data(8, new Geo_Location(-7,-1,0));
        Node_Data a9 = new Node_Data(9, new Geo_Location(-4,-1,0));
        gr.addNode(a0);
        gr.addNode(a1);
        gr.addNode(a2);
        gr.addNode(a3);
        gr.addNode(a4);
        gr.addNode(a5);
        gr.addNode(a6);
        gr.addNode(a7);
        gr.addNode(a8);
        gr.addNode(a9);
        gr.connect(0,1,1.5);
        gr.connect(0,2,20);
        gr.connect(0,3,8.2);
        gr.connect(0,4,5.2);
        gr.connect(0,5,4.9);
        gr.connect(0,6,2.3);
        gr.connect(0,7,1.3);
        gr.connect(0,8,0.4);
        gr.connect(0,9,1.2);
        gr.connect(9,1,4.2);
        gr.connect(1,2,6.3);
        gr.connect(2,3,1.2);
        gr.connect(3,4,0.3);
        gr.connect(4,5,5.3);
        gr.connect(5,6,6.1);
        gr.connect(6,7,4.3);
        gr.connect(7,8,3.9);
        gr.connect(8,9,9.2);
        gr.connect(5, 0, 10);
        return gr;
    }
}