package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;

import java.io.PrintWriter;
import java.util.*;
import java.util.Iterator;
import java.util.List;

public class DWGA implements DirectedWeightedGraphAlgorithms {
    private DWG graph;

    public DWGA(DirectedWeightedGraph gr) {
        Iterator<NodeData> ourNodes = gr.nodeIter();
        Iterator<EdgeData> ourEdges = gr.edgeIter();
        this.graph = new DWG();
        while (ourNodes.hasNext()) {
            NodeData grNode = ourNodes.next();
            this.graph.addNode(new Node_Data(grNode.getKey(), new Geo_Location(grNode.getLocation().x(), grNode.getLocation().y(), grNode.getLocation().z())));
        }
        while (ourEdges.hasNext()) {
            EdgeData grEdge = ourEdges.next();
            this.graph.connect(grEdge.getSrc(), grEdge.getDest(), grEdge.getWeight());
        }

    }

    //Default Builder c'tor
    public DWGA() {
        this.graph = new DWG();
    }

    public DWGA(String json) {
        DWG graph = new DWG();
        try {
            graph = new DWG(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.graph = graph;
    }

    //Initialize our DWGA by given DirectedWeightedGraph object
    @Override
    public void init(DirectedWeightedGraph g) {
        Iterator<NodeData> ourNodes = g.nodeIter();
        Iterator<EdgeData> ourEdges = g.edgeIter();
        this.graph = new DWG();
        while (ourNodes.hasNext()) {
            NodeData grNode = ourNodes.next();
            this.graph.addNode(new Node_Data(grNode.getKey(), new Geo_Location(grNode.getLocation().x(), grNode.getLocation().y(), grNode.getLocation().z())));
        }
        while (ourEdges.hasNext()) {
            EdgeData grEdge = ourEdges.next();
            this.graph.connect(grEdge.getSrc(), grEdge.getDest(), grEdge.getWeight());
        }

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    //Deep copy of self object DWGA
    @Override
    public DirectedWeightedGraph copy() {
        DWG here = new DWG();
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            NodeData node = it.next();
            Node_Data k = new Node_Data(node.getKey(), (Geo_Location) node.getLocation());
            here.addNode(k);
        }
        Iterator<NodeData> it2 = this.graph.nodeIter();
        while (it2.hasNext()) {
            Iterator<EdgeData> it3 = this.graph.edgeIter(it2.next().getKey());
            while (it3.hasNext()) {
                EdgeData edge = it3.next();
                here.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
            }
        }
        return here;
    }

    //Checking if the self DGWA is composed of only one component
    @Override
    public boolean isConnected() {
        Iterator<NodeData> iterator = this.graph.nodeIter();
        while (iterator.hasNext()) {
            HashMap<Integer, Double> routes = routes(iterator.next().getKey());
            Iterator<Double> iterator1 = routes.values().iterator();
            while (iterator1.hasNext()) {
                if (iterator1.next() == -1.0) {
                    return false;
                }
            }
        }
        return true;
    }

    //Checking what is the weght of the shortest path between two nodes
    @Override
    public double shortestPathDist(int src, int dest) {
        HashMap<Integer, Double> routes = routes(src);
        return routes.get(dest);
    }

    public HashMap<Integer, Double> routes(int src) {
        Queue<NodeData> q = new ArrayDeque<>();
        q.add(graph.getNode(src));
        // List<Double> weight= new LinkedList<>();
        HashMap<Integer, Double> weight = new HashMap<Integer, Double>();
        Iterator<NodeData> n = graph.nodeIter();
        for (int i = 0; i < graph.nodeSize(); i++) {
            NodeData n1 = n.next();
            if (n1.getKey() != src) {
                weight.put(n1.getKey(), Double.MAX_VALUE);
            }
        }
        weight.put(src, 0.0);
        while (!q.isEmpty()) {
            NodeData discover = q.poll();
            Iterator<EdgeData> edge_node = this.graph.edgeIter(discover.getKey());
            while (edge_node.hasNext()) {
                EdgeData edge = edge_node.next();
                if (weight.get(edge.getDest()) > weight.get(discover.getKey()) + edge.getWeight()) {
                    weight.put(edge.getDest(), weight.get(discover.getKey()) + edge.getWeight());
                    q.add(graph.getNode(edge.getDest()));
                }
            }
        }
        for (Integer i : weight.keySet()) {
            if (weight.get(i) == Double.MAX_VALUE) {
                weight.put(i, -1.0);
            }
        }
        return weight;
    }

    //Checking the route of the shortest path between two given nodes
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, Double> routes = routes(src);
        if (routes.get(dest) == -1) {
            return null;
        }
        List<NodeData> returnList = new ArrayList<>();
        NodeData node = graph.getNode(dest);
        returnList.add(node);
        double leftToGo = routes.get(dest);
        while (leftToGo > 0) {
            for (Integer i : this.graph.toMe.get(node.getKey()).keySet()) {
                if (node.getKey() == src) {
                    break;
                }
                if (routes.get(i) == -1) {
                    continue;
                }
                double somthing = this.graph.getEdge(i, node.getKey()).getWeight();
                if (Math.abs(leftToGo - (routes.get(i) + somthing)) < 0.00001) {
                    leftToGo = leftToGo - somthing;
                    node = graph.getNode(i);
                    returnList.add(node);
                    break;
                }
            }
            if (leftToGo < 0.00001) {
                break;
            }
        }
        Collections.reverse(returnList);
        return returnList;
    }

    //return the node thats is the center of the graph (footnote - center node is the node which the max shortest path to all the other nodes is minimized.)
    @Override
    public NodeData center() {
        NodeData ans = null;
        double min = Double.MAX_VALUE;
        Iterator<NodeData> iterator = this.graph.nodeIter();
        while (iterator.hasNext()) {
            double max = 0;
            NodeData srcNode = iterator.next();
            HashMap<Integer, Double> routes = routes(srcNode.getKey());
            for (Integer i : routes.keySet()) {
                if (routes.get(i) == -1.0) {
                    return null;
                }
                if (routes.get(i) > max) {
                    max = routes.get(i);
                }
            }
            if (max < min) {
                min = max;
                ans = srcNode;
            }
        }
        return ans;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.size() == 0) {
            return null;
        }
        List<NodeData> ans = new ArrayList<>();
        int idxToDelete = 0;
        NodeData here = cities.get(0);
        //ans.add(here);
        cities.remove(cities.get(idxToDelete));
        while (cities.size() > 0) {
            double min = Double.MAX_VALUE;
            HashMap<Integer, Double> routes = routes(here.getKey());
            for (int i = 0; i < cities.size(); i++) {
                if (routes.get(cities.get(i).getKey()) < min) {
                    min = routes.get(cities.get(i).getKey());
                    idxToDelete = i;
                }
            }
            List<NodeData> path = shortestPath(here.getKey(), cities.get(idxToDelete).getKey());
            ans.addAll(path);
            ans.remove(ans.size() - 1);
            here = cities.get(idxToDelete);
            cities.remove(cities.get(idxToDelete));
        }
        ans.add(here);
        return ans;
    }

    @Override
    public boolean save(String file) {
        try {
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            Map m;
            Iterator<EdgeData> iterator = this.graph.edgeIter();
            while (iterator.hasNext()) {
                m = new LinkedHashMap(3);
                EdgeData e = iterator.next();
                m.put("src", e.getSrc());
                m.put("w", e.getWeight());
                m.put("dest", e.getDest());
                ja.add(m);
            }
            jo.put("Edges", ja);
            ja = new JSONArray();
            Iterator<NodeData> iterator1 = this.graph.nodeIter();
            while (iterator1.hasNext()) {
                m = new LinkedHashMap(2);
                NodeData n = iterator1.next();
                m.put("pos", n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z());
                m.put("id", n.getKey());
                ja.add(m);
            }
            jo.put("Nodes", ja);
            PrintWriter pw = new PrintWriter(file);
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            this.graph = new DWG(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}