# Readme

#### Hand in by 208483008,322697483 and 207900440

## preface

This is a readme for our oop-Ex2- planing and design directed weighted graph.
Please follow those instructions:
1. In section "2. About this project" you could find an explanation about this project.
2. In section "3. Implementation" you can take a deeper look on the algorithms we actualized
3. In section "4. Analysis Algorithms" you could find a complete Analytics of our project.
4. In section “5. Dependencies” you can find which libraries are in the base of our project
5. In section “6. UML” there is our UML of our whole class
6. In section "7. How to run the algorithm and tests" you can find instruction how to run the programs.
7. Run the jar file with our code to test our performance. 
8. Have fun!


## 2. About this project
In this project we are assigned to implement a Directed Weighted Graph, and run algorithms on it. And we were asked to build a GUI were you could load graphs using a JSON file

### primary challenge
Our primary challenge was to provide the consumer requests. The end goal of the project was to give a GUI. the GUI presents numerous Algorithms implementations.  
We needed to build the project from scratch, starting with implications of the graph traits through the Algorithms and finally the GUI.


## 3. Implementation
In the project we Implement few arguments:

### GeoLocation
Basic GeoLocation implementation for 3 dimension objects in space

### NodeData
Implements node data, that has the following attribute:
* key - an unique ID for each node
* point - 3D GeoLocation (like the object above)
* weight - a weight for indicate the value of this node in the graph
* info - a string that shows all the node information
* tag - a tag for marking the node

Can run the following functions:
* getters and setters as known
* equals - equal to another object

### EdgeData
Implements node data, that has the following attribute:

* src - source of the edge
* dest - destination of the edge
* weight - a weight for indicate the value of this edge
* tag - a tag for marking the edge
* info - a string that shows all the edge information

Can run the following functions:

* getters and setters as known

### DWG - Directed Weighted Graph
Implements Directed Weighted Graph, that has the following attribute:
* nodes - HashMap that holds every node by its key as identifier
* Edges - HashMap on HashMap that holds every Edge by its source and destination nodes
* toMe - HashMap that holds every node and its nodes that have edges towards current node
* MC - Mode Counter that follows if the graph has been changed

Can run the following function:

* nodeSize - return how many nodes the graph has
* edgeSize - return how many edges the graph has
* getMC - return the mode counter of the DWG
* add node - adding a new node to the graph
* connect - adding a new edge to the graph
* remove node - removing existing node from the graph
* remove edge - removing existing edge from the graph
* nodeIter - return an iterator for the nodes
* edgeIter - return an iterator for the edges
* getNode -return a node by its given key
* getEdge - return an edge by its given source node and destination node

### DWGA - Directed Weighted Graph Algorithm
Implements Directed Weighted Graph, that has the following attribute:
* graph - DWG that indicates to our graph that rub the algorithm on it

Can execute the following algorithms:

* init - initialize the DWG
* copy - copy the current DWGA to another DWGA
* isConnected - checking if the graph is composed of one component
* shortestPathDist - checking what is the weight of the shortest path from a given  source node to a given destination node
* shortestPath - checking what is the route of the shortest path from a given  source node to a given destination node
* center - return the node that is the center of the graph<sup id="a1">[1](#f1)</sup>
* TSP - Computes a list of consecutive nodes which go over all the nodes in a given list(Travelling Salesman Problem)
* save - save this weighted (directed) graph to a given file, in JSON format
* load - loads a graph to this graph algorithm

<b id="f1">1</b> Center node is the node which the max shortest path to all the other nodes is minimized. [↩](#a1)

## 4. Analysis Algorithms

#### Building graphs
10 vertices with 68 edges - init and save in 32ms
100 vertices with 959 edges - init and save in 47ms
1000 vertices with 9956 edges - init and save in 127ms
10,000 vertices with 99,957 edges - init and save 590ms 
100,000 vertices with 999,954 edges - init and save in 3.5s
1,000,000 vertices with 4,999,989 edges - init and save in  30s

### Algorithms

#### isConnected
The isConnected algorithm running asymptote is O(V*(V+E)^2).  
When V stands for no. of nodes and E stands for no. of edges in the given graph.

10 vertices with 68 edges: 16ms
100 vertices with 959 edges: 64ms
1000 vertices with 9956 edges: 1.785s
10,000 vertices with 68 edges: 6mts and 20s
100,000 vertices with 68 edges: 14mts and 9s
1,000,000 vertices with 4,999,989 edges: 21mts and 44s (returned false)

#### Shortest path 1 to random i
The  O((V+E)^2) (dijakstra algorithm).  
When V stands for no. of nodes and E stands for no. of edges in the given graph.
When V stands for no. of nodes and E stands for no. of edges in the given graph.
10 vertices with 68 edges - init in 1ms
100 vertices with 959 edges: 17ms
1000 vertices with 9956 edges: 10ms
10,000 vertices with 68 edges: 200ms
100,000 vertices with 68 edges: 4.2s
10,000,000 vertices with 4,999,989 edges: 25s

#### Center
The Center algorithm running asymptote is O(V*(V+E)^2). 
When V stands for no. of nodes and E stands for no. of edges in the given graph.
10 vertices with 68 edges - init in 1ms
100 vertices with 959 edges: 64ms
1000 vertices with 9956 edges: 1.82s
10,000 vertices with 68 edges: 6mts and 35s
100,000 vertices with 68 edges: 13mts 20s
10,000,000 vertices with 4,999,989 edges: 1s (not connected graph)

#### TSP
The Traveling Salesman Problem running asymptote is O((given list.length)*(V+E)^2).  
When V stands for no. of nodes and E stands for no. of edges in the given graph.

10 vertices with 68 edges - init in 1ms
100 vertices with 959 edges: 1ms
1000 vertices with 9956 edges: 110ms
10,000 vertices with 99,957 edges:2s and 200ms
100,000 vertices with 999,954 edges: 1mts and 4s
10,000,000 vertices with 4,999,989 edges: 4s on simple route.

## 5. Dependencies
Those are the libraries we are using in our project. Please make sure you have them updated for use our project correctly:

* <b>JDK - version 17.0.1</b>
* <b>JSON - org.json:json</b>
* <b>Junit Tests - Junit.jupiter</b>
<b></b>

## 6.UML
![UML.png](UML.PNG)

## 7. How to run
How to rum our algorithm from the CMD:

```bash
java -jar OOP-Ex2.jar <graph.json> 
```

You can also run it and wait for our app to ask for an input:
```cmd
java -jar OOP-Ex2.jar
```
You will see this input request:
```cmd
Please enter a full dierectory for file to run the GUI:
```

You can also run manually our tests for our algorithm and function in the Tests folder

