package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.*;
import java.util.function.Consumer;

public class DWG implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> Nodes;
    private HashMap<Integer, HashMap<Integer,EdgeData>> Edges;
    public HashMap<Integer,HashMap<Integer,Integer>> toMe;
    private int MC;


    public DWG(String json) throws Exception {
        this.Nodes = new HashMap<Integer, NodeData>();
        this.Edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.toMe = new HashMap<Integer, HashMap<Integer, Integer>>();
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(json));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        // getting Nodes
        JSONArray ja = (JSONArray) jo.get("Nodes");
        Iterator nodeIterator = ja.iterator();
        while (nodeIterator.hasNext()) {
            Object[] data = new Object[3];
            Iterator<Map.Entry> nodeData = ((Map) nodeIterator.next()).entrySet().iterator();
            int i = 0;
            while (nodeData.hasNext()) {
                data[i] = nodeData.next().getValue();
                i++;
            }
            data[2] = ((Long) data[1]).intValue();
            String n = (String) data[0];
            String[] split = n.split(",");
            Double[] loc = new Double[]{Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2])};
            Node_Data node = new Node_Data((Integer) data[2], new Geo_Location(loc[0], loc[1], loc[2]));
            addNode(node);
        }
        ja = (JSONArray) jo.get("Edges");
        Iterator edgeIterator = ja.iterator();
        while (edgeIterator.hasNext()) {
            Object[] data = new Object[3];
            Iterator<Map.Entry> edgeData = ((Map) edgeIterator.next()).entrySet().iterator();
            int i = 0;
            while (edgeData.hasNext()) {
                data[i] = edgeData.next().getValue();
                i++;
            }
            connect(((Long) data[0]).intValue(), ((Long) data[2]).intValue(), (Double) data[1]);
            this.MC = 0;
        }
    }

    public DWG(int n){
        this.Nodes = new HashMap<Integer, NodeData>();
        this.Edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.toMe = new HashMap<Integer,HashMap<Integer,Integer>>();
        Random rand = new Random();
        Random rand2 = new Random();

        for (int i = 0; i < n; i++) {
            double rand_dub1 = rand.nextDouble();
            rand_dub1 = rand_dub1*1000;
            rand_dub1 = rand_dub1 - 500;
            double rand_dub2 = rand2.nextDouble();
            rand_dub2 = rand_dub2*1000;
            rand_dub2 = rand_dub2 - 500;
            Node_Data z = new Node_Data(i, new Geo_Location(rand_dub1,rand_dub2,0.0));
            addNode(z);
        }
        for (int i = 0; i < this.Nodes.size(); i++) {
            for (int j = 0; j < 5; j++) {
                int rand_int1 = rand.nextInt(this.Nodes.size());
                int rand_int2 = rand.nextInt(this.Nodes.size());
                double rand_dub1 = rand.nextDouble();
                rand_dub1 = rand_dub1*10;
                connect(i,rand_int2,rand_dub1);
            }
        }
        this.MC= 0;
    }

    public DWG(){
        this.Nodes = new HashMap<Integer, NodeData>();
        this.Edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        this.toMe = new HashMap<Integer,HashMap<Integer,Integer>>();
        this.MC = 0;
    }

    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (Edges.containsKey(src) && Edges.get(src).containsKey(dest)) {
            return this.Edges.get(src).get(dest);
        }
        else {
            return null;
        }
    }

    @Override
    public void addNode(NodeData n) {
        if (Nodes.containsKey(n.getKey())){
            System.out.println("this node id("+n.getKey()+") is already taken, choose another");
        }
        else {
            Nodes.put(n.getKey(), n);
            toMe.put(n.getKey(), new HashMap<Integer, Integer>());
            this.MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (!Nodes.containsKey(src)){
            System.out.println("there is no such "+ src+" Node");
        }
        if (!Nodes.containsKey(dest)){
            System.out.println("there is no such " + dest+ " Node");
        }
        else {
            if (!Edges.containsKey(src)){
                Edges.put(src, new HashMap<>());
            }
            if (Edges.get(src).containsKey(dest)){
                System.out.println("there is already edge between "+ src+" and "+ dest);
            }
            else {
                EdgeData edge = new Edge_Data(src, dest, w);
                Edges.get(src).put(dest, edge);
                toMe.get(dest).put(src, src);
                this.MC++;
            }
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>() {
            public int hereMC = MC;
            private final Iterator<NodeData> iterator = Nodes.values().iterator();

            @Override
            public boolean hasNext() {
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                return iterator.hasNext();
            }

            @Override
            public NodeData next() {
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                return iterator.next();
            }

            @Override
            public void remove(){
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                if (this.next()!=null){
                    DWG.this.removeNode(this.next().getKey());
                    this.hereMC =DWG.this.MC;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super NodeData> action){
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                Iterator.super.forEachRemaining(action);
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            private final Iterator<HashMap<Integer,EdgeData>> iterator = Edges.values().iterator();
            public int hereMC = MC;
            private Iterator<EdgeData> iterator1 = null;
            @Override
            public boolean hasNext() {
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                return (iterator.hasNext() || (iterator1!=null &&(iterator1.hasNext() || iterator1 == null)));
            }

            @Override
            public EdgeData next() {
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                if (iterator1 == null || !iterator1.hasNext()){
                    iterator1 = iterator.next().values().iterator();
                }
                return iterator1.next();
            }

            @Override
            public void remove(){
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                if (this.next()!=null){
                    DWG.this.removeEdge(this.next().getSrc(),this.next().getDest());
                    this.hereMC =DWG.this.MC;
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action){
                if (this.hereMC != MC){
                    throw new RuntimeException ("graph was changed since the iterator was constructed");
                }
                Iterator.super.forEachRemaining(action);
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        if (Edges.containsKey(node_id)) {
            return new Iterator<EdgeData>() {
                public int hereMC = MC;
                public final Iterator<EdgeData> iterator = Edges.get(node_id).values().iterator();

                @Override
                public boolean hasNext() {
                    if (this.hereMC != MC) {
                        throw new RuntimeException("graph was changed since the iterator was constructed");
                    }
                    return iterator.hasNext();
                }

                @Override
                public EdgeData next() {
                    if (this.hereMC != MC) {
                        throw new RuntimeException("graph was changed since the iterator was constructed");
                    }
                    return iterator.next();
                }

                @Override
                public void remove() {
                    if (this.hereMC != MC) {
                        throw new RuntimeException("graph was changed since the iterator was constructed");
                    }
                    if (this.next() != null) {
                        DWG.this.removeEdge(this.next().getSrc(), this.next().getDest());
                        this.hereMC = DWG.this.MC;
                    }
                }

                @Override
                public void forEachRemaining(Consumer<? super EdgeData> action) {
                    if (this.hereMC != MC) {
                        throw new RuntimeException("graph was changed since the iterator was constructed");
                    }
                    Iterator.super.forEachRemaining(action);
                }
            };
        }
        List<EdgeData> noNodes = new ArrayList<>();
        return noNodes.iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        NodeData nodi = this.Nodes.get(key);
        if (this.Edges.containsKey(key)) {
            for (Integer i : this.Edges.get(key).keySet()) {
                this.toMe.get(i).remove(key);
            }
            this.Edges.remove(key);
        }
        if (this.toMe.containsKey(key)) {
            for (Integer i : this.toMe.get(key).keySet()) {
                this.Edges.get(i).remove(key);
                if (Edges.get(i).size()==0){
                    Edges.remove(i);
                }
            }
        }
        this.Nodes.remove(key);
        this.MC++;
        return nodi;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edgi = this.Edges.get(src).get(dest);
        this.toMe.get(dest).remove(src);
        this.Edges.get(src).remove(dest);
        if (Edges.get(src).size()==0){
            this.Edges.remove(src);
        }
        this.MC++;
        return edgi;
    }

    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    @Override
    public int edgeSize() {
        int counter = 0;
        for (Integer i: this.Edges.keySet()){
            counter = counter + this.Edges.get(i).size();
        }
        return counter;
    }

    @Override
    public int getMC() {
        return MC;
    }
}