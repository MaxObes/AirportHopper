// --== CS400 File Header Information ==--
// Name: Muhammad Hareez FItri bin Ahnuar
// Email: mahnuar@wisc.edu
// Team: AA
// TA: Yuye Jiang
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.ArrayList;

public class CS400Graph_BD<NodeType,EdgeType extends Number> implements GraphADT<NodeType,EdgeType> {

    /**
     * Vertex objects group a data field with an adjacency list of weighted
     * directed edges that lead away from them.
     */
    protected class Vertex {
        public NodeType data; // vertex label or application specific data
        public LinkedList<Edge> edgesLeaving;

        public Vertex(NodeType data) {
            this.data = data;
            this.edgesLeaving = new LinkedList<>();
        }
    }

    /**
     * Edge objects are stored within their source vertex, and group together
     * their target destination vertex, along with an integer weight.
     */
    protected class Edge {
        public Vertex target;
        public EdgeType weight;

        public Edge(Vertex target, EdgeType weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    protected Hashtable<NodeType, Vertex> vertices; // holds graph verticies, key=data
    public CS400Graph_BD() { vertices = new Hashtable<>(); }

    /**
     * Insert a new vertex into the graph.
     * 
     * @param data the data item stored in the new vertex
     * @return true if the data can be inserted as a new vertex, false if it is 
     *     already in the graph
     * @throws NullPointerException if data is null
     */
    public boolean insertVertex(NodeType data) {
        if(data == null) 
            throw new NullPointerException("Cannot add null vertex");
        if(this.vertices.containsKey(data)) return false; // duplicate values are not allowed
        this.vertices.put(data, new Vertex(data));
        return true;
    }
    
    /**
     * Remove a vertex from the graph.
     * Also removes all edges adjacent to the vertex from the graph (all edges 
     * that have the vertex as a source or a destination vertex).
     * 
     * @param data the data item stored in the vertex to remove
     * @return true if a vertex with *data* has been removed, false if it was not in the graph
     * @throws NullPointerException if data is null
     */
    public boolean removeVertex(NodeType data) {
        if(data == null) throw new NullPointerException("Cannot remove null vertex");
        Vertex removeVertex = this.vertices.get(data);
        if(removeVertex == null) return false; // vertex not found within graph
        // search all vertices for edges targeting removeVertex 
        for(Vertex v : this.vertices.values()) {
            Edge removeEdge = null;
            for(Edge e : v.edgesLeaving)
                if(e.target == removeVertex)
                    removeEdge = e;
            // and remove any such edges that are found
            if(removeEdge != null) v.edgesLeaving.remove(removeEdge);
        }
        // finally remove the vertex and all edges contained within it
        return this.vertices.remove(data) != null;
    }
    
    /**
     * Insert a new directed edge with a positive edge weight into the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @param weight the weight for the edge (has to be a positive integer)
     * @return true if the edge could be inserted or its weight updated, false 
     *     if the edge with the same weight was already in the graph
     * @throws IllegalArgumentException if either source or target or both are not in the graph, 
     *     or if its weight is < 0
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
        if(source == null || target == null) 
            throw new NullPointerException("Cannot add edge with null source or target");
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) 
            throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
        if(weight.doubleValue() < 0) 
            throw new IllegalArgumentException("Cannot add edge with negative weight");
        // handle cases where edge already exists between these verticies
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex) {
                if(e.weight.doubleValue() == weight.doubleValue()) return false; // edge already exists
                else e.weight = weight; // otherwise update weight of existing edge
                return true;
            }
        // otherwise add new edge to sourceVertex
        sourceVertex.edgesLeaving.add(new Edge(targetVertex,weight));
        return true;
    }    
    
    /**
     * Remove an edge from the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge could be removed, false if it was not in the graph
     * @throws IllegalArgumentException if either source or target or both are not in the graph
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean removeEdge(NodeType source, NodeType target) {
        if(source == null || target == null) throw new NullPointerException("Cannot remove edge with null source or target");
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
        // find edge to remove
        Edge removeEdge = null;
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex) 
                removeEdge = e;
        if(removeEdge != null) { // remove edge that is successfully found                
            sourceVertex.edgesLeaving.remove(removeEdge);
            return true;
        }
        return false; // otherwise return false to indicate failure to find
    }
    
    /**
     * Check if the graph contains a vertex with data item *data*.
     * 
     * @param data the data item to check for
     * @return true if data item is stored in a vertex of the graph, false otherwise
     * @throws NullPointerException if *data* is null
     */
    public boolean containsVertex(NodeType data) {
        if(data == null) throw new NullPointerException("Cannot contain null data vertex");
        return this.vertices.containsKey(data);
    }
    
    /**
     * Check if edge is in the graph.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return true if the edge is in the graph, false if it is not in the graph
     * @throws NullPointerException if either source or target or both are null
     */
    public boolean containsEdge(NodeType source, NodeType target) {
        if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data"); 
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null) return false;
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex)
                return true;
        return false;
    }
    
    /**
     * Return the weight of an edge.
     * 
     * @param source the data item contained in the source vertex for the edge
     * @param target the data item contained in the target vertex for the edge
     * @return the weight of the edge (a Number that represents 0 or a positive value)
     * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
     * @throws NullPointerException if either sourceVertex or targetVertex or both are null
     * @throws NoSuchElementException if edge is not in the graph
     */
    public EdgeType getWeight(NodeType source, NodeType target) {
        if(source == null || target == null) throw new NullPointerException("Cannot contain weighted edge adjacent to null data"); 
        Vertex sourceVertex = this.vertices.get(source);
        Vertex targetVertex = this.vertices.get(target);
        if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot retrieve weight of edge between vertices that do not exist");
        for(Edge e : sourceVertex.edgesLeaving)
            if(e.target == targetVertex)
                return e.weight;
        throw new NoSuchElementException("No directed edge found between these vertices");
    }
    
    /**
     * Return the number of edges in the graph.
     * 
     * @return the number of edges in the graph
     */
    public int getEdgeCount() {
        int edgeCount = 0;
        for(Vertex v : this.vertices.values())
            edgeCount += v.edgesLeaving.size();
        return edgeCount;
    }
    
    /**
     * Return the number of vertices in the graph
     * 
     * @return the number of vertices in the graph
     */
    public int getVertexCount() {
        return this.vertices.size();
    }

    /**
     * Check if the graph is empty (does not contain any vertices or edges).
     * 
     * @return true if the graph does not contain any vertices or edges, false otherwise
     */
    public boolean isEmpty() {
        return this.vertices.size() == 0;
    }

    /**
     * Path objects store a discovered path of vertices and the overal distance of cost
     * of the weighted directed edges along this path. Path objects can be copied and extended
     * to include new edges and verticies using the extend constructor. In comparison to a
     * predecessor table which is sometimes used to implement Dijkstra's algorithm, this
     * eliminates the need for tracing paths backwards from the destination vertex to the
     * starting vertex at the end of the algorithm.
     */
    protected class Path implements Comparable<Path> {
        public Vertex start; // first vertex within path
        public double distance; // sumed weight of all edges in path
        public List<NodeType> dataSequence; // ordered sequence of data from vertices in path
        public Vertex end; // last vertex within path

        /**
         * Creates a new path containing a single vertex.  Since this vertex is both
         * the start and end of the path, it's initial distance is zero.
         * @param start is the first vertex on this path
         */
        public Path(Vertex start) {
            this.start = start;
            this.distance = 0.0D;
            this.dataSequence = new LinkedList<>();
            this.dataSequence.add(start.data);
            this.end = start;
        }

        /**
         * This extension constructor makes a copy of the path passed into it as an argument
         * without affecting the original path object (copyPath). The path is then extended
         * by the Edge object extendBy. Use the doubleValue() method of extendBy's weight field
         * to get a double representation of the edge's weight.
         * @param copyPath is the path that is being copied
         * @param extendBy is the edge the copied path is extended by
         */
        public Path(Path copyPath, Edge extendBy) {
            this.start = copyPath.start;
            this.distance = copyPath.distance + extendBy.weight.doubleValue();
            this.dataSequence = new LinkedList<NodeType>();

            for (int i = 0; i < copyPath.dataSequence.size(); i++) {
                this.dataSequence.add(copyPath.dataSequence.get(i));
            }

            // extend the path
            this.dataSequence.add(extendBy.target.data);

            this.end = extendBy.target;
        }

        /**
         * Allows the natural ordering of paths to be increasing with path distance.
         * When path distance is equal, the string comparison of end vertex data is used to break ties.
         * @param other is the other path that is being compared to this one
         * @return -1 when this path has a smaller distance than the other,
         *         +1 when this path has a larger distance that the other,
         *         and the comparison of end vertex data in string form when these distances are tied
         */
        public int compareTo(Path other) {
            int cmp = Double.compare(this.distance, other.distance);
            if(cmp != 0) return cmp; // use path distance as the natural ordering
            // when path distances are equal, break ties by comparing the string
            // representation of data in the end vertex of each path
            return this.end.data.toString().compareTo(other.end.data.toString());
        }
    }

    /**
     * Uses Dijkstra's shortest path algorithm to find and return the shortest path 
     * between two vertices in this graph: start and end. This path contains an ordered list
     * of the data within each node on this path, and also the distance or cost of all edges
     * that are a part of this path.
     * @param start data item within first node in path
     * @param end data item within last node in path
     * @return the shortest path from start to end, as computed by Dijkstra's algorithm
     * @throws NoSuchElementException when no path from start to end can be found,
     *     including when no vertex containing start or end can be found
     */
    protected Path dijkstrasShortestPath(NodeType start, NodeType end) {
        
        // check inputs
        if (end == null || !this.containsVertex(end)) {
            throw new NoSuchElementException("End value not found: " + end);
        }
        if (start == null || !this.containsVertex(start)) {
            throw new NoSuchElementException("Start value not found: " + start);
        }

        // set up data structures
        PriorityQueue<Path> discoveredPaths = new PriorityQueue<Path>();
        HashSet<NodeType> visitedNodes = new HashSet<NodeType>();
        Path outPath = null; // output variable, initialize to null

        // base case: if end == start
        if (end.equals(start)) {
            outPath = new Path(this.vertices.get(start));
            return outPath;
        }

        // loop runs while not all nodes have been visited
        discoveredPaths.add(new Path(this.vertices.get(start)));
	    while ((visitedNodes.size() < this.getVertexCount()) && discoveredPaths.size() > 0) {

            // get shortest path from priority queue
		    Path currPath = discoveredPaths.remove();
            Vertex currNode = currPath.end;

            // if currNode already visited, then continue, otherwise add it to set of visited
            if (visitedNodes.contains(currNode.data)) {
                continue;
            } else {
                visitedNodes.add(currNode.data);
            }

            // if the currNode's data is equivalent to end, then we have found the target
            if (currNode.data.equals(end)) {
                outPath = currPath;
                break;
            }

            // otherwise, create the new paths and add them to the p-queue
            for (Edge currEdge : currNode.edgesLeaving) {
                discoveredPaths.add(new Path(currPath, currEdge));
            }

        }

        if ((outPath == null) || !(outPath.start.data.equals(start) && outPath.end.data.equals(end))) {
            throw new NoSuchElementException("Unable to find path from " + start.toString()
                                        + " to " + end.toString());
        }

        return outPath;
    }
    
    /**
     * Returns the shortest path between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the destination vertex for the path
     * @return list of data item in vertices in order on the shortest path between vertex 
     * with data item start and vertex with data item end, including both start and end 
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public List<NodeType> shortestPath(NodeType start, NodeType end) {
        return dijkstrasShortestPath(start,end).dataSequence;
    }
    
    /**
     * Returns the cost of the path (sum over edge weights) between start and end.
     * Uses Dijkstra's shortest path algorithm to find the shortest path.
     * 
     * @param start the data item in the starting vertex for the path
     * @param end the data item in the end vertex for the path
     * @return the cost of the shortest path between vertex with data item start 
     * and vertex with data item end, including all edges between start and end
     * @throws NoSuchElementException when no path from start to end can be found
     *     including when no vertex containing start or end can be found
     */
    public double getPathCost(NodeType start, NodeType end) {
        return dijkstrasShortestPath(start, end).distance;
    }	
    
    /**
     * This protected class is an implementation of GraphADT that represents 
     * a minimum spanning tree
     * 
     * Key features of this implementation
     * 
     * - it can "lock", meaning that once locked, edges/vertices cannot be changed/added/removed
     * - this class will return null and 0 (respectively) for the shortest path related methods
     * - this class contains an implementation of toString which will output a nicely formatted
     *   depiction of the tree
     */
    protected class MST implements GraphADT<NodeType, EdgeType> {

        private Vertex root; // the root of the tree, mainly used for printing purposes
        private Hashtable<NodeType, Vertex> vertices; // holds graph verticies, key=data
        private boolean locked; // whether or not the tree is locked for editing

        /**
         * The public constructor for this class
         * 
         * Design choice: root is null until the first vertex is added
         */
        public MST() {
            this.vertices = new Hashtable<>();
            this.locked = false;
            this.root = null; // design choice: root is null until the first vertex is added
        }

        /**
         * This method will lock the MST to prevent it from being changed
         * by methods like insertVertex/Edge and removeVertex/Edge after it
         * is created
         */
        private void lockMST() {
            this.locked = true;
        }

        /**
         * Insert a new vertex into the tree.
         * 
         * If tree is in locked state, this method just returns false
         * 
         * @param data the data item stored in the new vertex
         * @return true if the data can be inserted as a new vertex, false if it is 
         *     already in the graph
         * @throws NullPointerException if data is null
         */
        @Override
        public boolean insertVertex(NodeType data) {

            if (this.locked) { // if locked, cannot change
                return false;
            }

            if(data == null) {
                throw new NullPointerException("Cannot add null vertex");
            }
            if(this.vertices.containsKey(data)) return false; // duplicate values are not allowed
            this.vertices.put(data, new Vertex(data));
            
            if (this.vertices.size() == 1) { // if this is the first vertex put in
                this.root = this.vertices.get(data);
            }

            return true;
        }

        /**
         * Remove a vertex from the graph.
         * Also removes all edges adjacent to the vertex from the graph (all edges 
         * that have the vertex as a source or a destination vertex).
         * 
         * If the tree is in a locked state, then this method just returns false
         * 
         * @param data the data item stored in the vertex to remove
         * @return true if a vertex with *data* has been removed, false if it was not in the graph
         * @throws NullPointerException if data is null
         */
        @Override
        public boolean removeVertex(NodeType data) {
            
            if (this.locked) { // if locked, just return false
                return false;
            }

            if(data == null) throw new NullPointerException("Cannot remove null vertex");
            Vertex removeVertex = this.vertices.get(data);
            if(removeVertex == null) return false; // vertex not found within graph
            // search all vertices for edges targeting removeVertex 
            for(Vertex v : this.vertices.values()) {
                Edge removeEdge = null;
                for(Edge e : v.edgesLeaving)
                    if(e.target == removeVertex)
                        removeEdge = e;
                // and remove any such edges that are found
                if(removeEdge != null) v.edgesLeaving.remove(removeEdge);
            }
            // finally remove the vertex and all edges contained within it
            return this.vertices.remove(data) != null;
        }

        /**
         * Insert a new directed edge with a positive edge weight into the graph.
         * 
         * If the tree is in a locked state, then this method just returns false
         * 
         * @param source the data item contained in the source vertex for the edge
         * @param target the data item contained in the target vertex for the edge
         * @param weight the weight for the edge (has to be a positive integer)
         * @return true if the edge could be inserted or its weight updated, false 
         *     if the edge with the same weight was already in the graph
         * @throws IllegalArgumentException if either source or target or both are not in the graph, 
         *     or if its weight is < 0
         * @throws NullPointerException if either source or target or both are null
         */
        @Override
        public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
        
            if (this.locked) { // if tree is locked, just return false
                return false;
            }

            if(source == null || target == null) 
            throw new NullPointerException("Cannot add edge with null source or target");
            Vertex sourceVertex = this.vertices.get(source);
            Vertex targetVertex = this.vertices.get(target);
            if(sourceVertex == null || targetVertex == null) 
                throw new IllegalArgumentException("Cannot add edge with vertices that do not exist");
            if(weight.doubleValue() < 0) 
                throw new IllegalArgumentException("Cannot add edge with negative weight");
            // handle cases where edge already exists between these verticies
            for(Edge e : sourceVertex.edgesLeaving)
                if(e.target == targetVertex) {
                    if(e.weight.doubleValue() == weight.doubleValue()) return false; // edge already exists
                    else e.weight = weight; // otherwise update weight of existing edge
                    return true;
                }
            // otherwise add new edge to sourceVertex
            sourceVertex.edgesLeaving.add(new Edge(targetVertex,weight));
            return true;
        }

        /**
         * Remove an edge from the tree.
         * 
         * If the tree is locked, then just return false
         * 
         * @param source the data item contained in the source vertex for the edge
         * @param target the data item contained in the target vertex for the edge
         * @return true if the edge could be removed, false if it was not in the graph
         * @throws IllegalArgumentException if either source or target or both are not in the graph
         * @throws NullPointerException if either source or target or both are null
         */
        @Override
        public boolean removeEdge(NodeType source, NodeType target) {
            
            if (this.locked) { // if tree is locked just return false
                return false;
            }

            if(source == null || target == null) throw new NullPointerException("Cannot remove edge with null source or target");
            Vertex sourceVertex = this.vertices.get(source);
            Vertex targetVertex = this.vertices.get(target);
            if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot remove edge with vertices that do not exist");
            // find edge to remove
            Edge removeEdge = null;
            for(Edge e : sourceVertex.edgesLeaving)
                if(e.target == targetVertex) 
                    removeEdge = e;
            if(removeEdge != null) { // remove edge that is successfully found                
                sourceVertex.edgesLeaving.remove(removeEdge);
                return true;
            }
            return false; // otherwise return false to indicate failure to find
        }

        /**
         * Check if the graph contains a vertex with data item *data*.
         * 
         * @param data the data item to check for
         * @return true if data item is stored in a vertex of the graph, false otherwise
         * @throws NullPointerException if *data* is null
         */
        @Override
        public boolean containsVertex(NodeType data) {
            if(data == null) throw new NullPointerException("Cannot contain null data vertex");
            return this.vertices.containsKey(data);
        }

        /**
         * Check if edge is in the graph.
         * 
         * @param source the data item contained in the source vertex for the edge
         * @param target the data item contained in the target vertex for the edge
         * @return true if the edge is in the graph, false if it is not in the graph
         * @throws NullPointerException if either source or target or both are null
         */
        @Override
        public boolean containsEdge(NodeType source, NodeType target) {
            if(source == null || target == null) throw new NullPointerException("Cannot contain edge adjacent to null data"); 
            Vertex sourceVertex = this.vertices.get(source);
            Vertex targetVertex = this.vertices.get(target);
            if(sourceVertex == null) return false;
            for(Edge e : sourceVertex.edgesLeaving)
                if(e.target == targetVertex)
                    return true;
            return false;
        }

        /**
         * Return the weight of an edge.
         * 
         * @param source the data item contained in the source vertex for the edge
         * @param target the data item contained in the target vertex for the edge
         * @return the weight of the edge (a Number that represents 0 or a positive value)
         * @throws IllegalArgumentException if either sourceVertex or targetVertex or both are not in the graph
         * @throws NullPointerException if either sourceVertex or targetVertex or both are null
         * @throws NoSuchElementException if edge is not in the graph
         */
        @Override
        public EdgeType getWeight(NodeType source, NodeType target) {
            if(source == null || target == null) throw new NullPointerException("Cannot contain weighted edge adjacent to null data"); 
            Vertex sourceVertex = this.vertices.get(source);
            Vertex targetVertex = this.vertices.get(target);
            if(sourceVertex == null || targetVertex == null) throw new IllegalArgumentException("Cannot retrieve weight of edge between vertices that do not exist");
            for(Edge e : sourceVertex.edgesLeaving)
                if(e.target == targetVertex)
                    return e.weight;
            throw new NoSuchElementException("No directed edge found between these vertices");
        }

        /**
         * This method just returns null for this implementation
         * 
         * @param start any val of NodeType
         * @param end any param of NodeType
         * @return null
         */
        @Override
        public List<NodeType> shortestPath(NodeType start, NodeType end) {
            return null;
        }

        /**
         * This method just returns 0 for this implementation
         * 
         * @param start any value of NodeType
         * @param end any value of NodeType
         * @return 0
         */
        @Override
        public double getPathCost(NodeType start, NodeType end) {
            return 0;
        }

        /**
         * This method returns true if the tree is empty, false if not
         * 
         * @return true if the tree is empty, false if not
         */
        @Override
        public boolean isEmpty() {
            return this.getVertexCount() == 0;
        }

        /**
         * This method returns the number of edges in this tree
         * 
         * It is undirected so will be 2*(N - 1) where N is the number of 
         * vertices in the tree
         * 
         * @returns the number of edges
         */
        @Override
        public int getEdgeCount() {
            int edgeCount = 0;
            for(Vertex v : this.vertices.values())
                edgeCount += v.edgesLeaving.size();
            return edgeCount;
        }

        /**
         * This method returns the number of vertices in the MST.
         * 
         * @return the number of vertices in the tree
         */
        @Override
        public int getVertexCount() {
            return this.vertices.size();
        }

        /**
         * This method will return a string representation of the tree
         * 
         * @return the string representation of the tree
         */
        @Override
        public String toString() {
            ArrayList<String> output = new ArrayList<String>();
            HashSet<NodeType> seen = new HashSet<NodeType>();
            toStringHelper(output, "", this.root, null, true, seen);

            return String.join("\n", output);
        }

        /**
         * Recursive helper function to print out the tree.  It will append its current line to the arraylist
         * 
         * FORMAT:
         * root
         *    |----w----child
         *    |----w----child
         *    |            |-----w----child
         *    |            |-----w----child
         *    |----w----child
         * 
         * @param cumString the current cumulative string
         * @param indent the current level of indentation
         * @param vertex the current vertex to process for the current line
         * @param currWeight the weight leading to that vertex
         * @param last whether or not the current node is the last of the children
         */
        private void toStringHelper(ArrayList<String> outListRef, String indent, Vertex curr, EdgeType currWeight, boolean last, HashSet<NodeType> seen) {
            seen.add(curr.data);
            
            String currStr = "";
            currStr = currStr + indent;
            
            if (curr.data == this.root.data) {
                indent = "  ";
            } else if (last) {
                indent = indent + "          ";
            } else {
                indent = indent + "|         ";
            }

            if (curr.data.equals(this.root.data)) { // if the current vertex is root
                currStr = "> ";
            } else {
                String weightStr = currWeight.toString();
                int weightStrLen = weightStr.length();
                String leadingDashes = "";
                int lenLeadingDash = 7 - 2 - weightStrLen;
                for (int i = 0; i < lenLeadingDash; i++) {
                    leadingDashes = leadingDashes + "-";
                }
                currStr = currStr + "|--" + weightStr + leadingDashes + "> ";
            }

            currStr = currStr + curr.data.toString(); // end of current line

            outListRef.add(currStr);

            // get num edges
            int numEdges = 0;
            for (Edge edge : curr.edgesLeaving) {
                if (!seen.contains(edge.target.data)) {
                    numEdges++;
                }
            }

            int i = 0;
            for (Edge edge : curr.edgesLeaving) {
                if (edge.target.data.equals(curr.data) || seen.contains(edge.target.data)) {
                    continue; // if it leads back on itself, just continue
                }

                if (i == numEdges - 1) { // if last
                    toStringHelper(outListRef, indent, edge.target, edge.weight, true, seen);
                } else {
                    toStringHelper(outListRef, indent, edge.target, edge.weight, false, seen);
                }
            
                i++;
            }
            
        }
        
    }

    /**
     * This is a helper class that stores information about a given edge
     * and allows it to be compared to other edges.  It's sole purpose is to help
     * with the implementation of Prim's algo
     */
    protected class MSTEdge implements Comparable<MSTEdge> {
        private Vertex start;
        private Vertex end;
        private double weight;
        private EdgeType weightRaw;

        /**
         * Public constructor for this protected helper class
         * 
         * @param edge the edge from which to get target and weight information
         * @param start the node from which the edge originates
         */
        public MSTEdge(Edge edge, Vertex start) {
            this.start = start;
            this.end = edge.target;
            this.weight = edge.weight.doubleValue();
            this.weightRaw = edge.weight;
        }

        /**
         * Accessor method to get the start vertex reference
         * 
         * @return the vertex at which this MSTEdge starts
         */
        public Vertex getStart() {
            return this.start;
        }

        /**
         * Accessor method to get the end vertex reference
         * 
         * @return the end vertex
         */
        public Vertex getEnd() {
            return this.end;
        }

        /**
         * Accessor method to get the weight of the current edge
         * (converted to double for simplicity)
         * 
         * @return the weight of the edge converted to double
         */
        public double getWeight() {
            return this.weight;
        }

        /**
         * This accessor method gets the raw weight (as it was specified in the edge)
         * @return
         */
        public EdgeType getWeightRaw() {
            return this.weightRaw;
        }

        /**
         * Compares the weights of the two edges.  If the weights are equal,
         * ties are broken by comparing the string representation of the end 
         * Vertices
         * 
         * @param o the other MSTEdge to compare it to
         * @return -1 when this path has a smaller distance
         *         1 when this path has larger distance
         *         will NEVER return a 0 due to lack of duplicate node values
         */
        @Override
        public int compareTo(MSTEdge o) {
            int cmp = Double.compare(this.weight, o.weight);
            if (cmp != 0) {
                return cmp;
            } else {
                return this.end.data.toString().compareTo(o.end.data.toString());
            }
        }
    }

    /**
     * This method will generate an MST using prim's algo
     * 
     * @param start the value of the node at which to start
     * @return mst the MST that is generated by prim's algo when starting at that node
     * @throws NoSuchElementException if vertex containing NodeType value start cannot be found
     * @throws NullPointerException if start is null
     */
    public MST generateMST(NodeType start) {

        if (start == null) {
            throw new NullPointerException("start cannot be null");
        }

        if (!this.containsVertex(start)) {
            throw new NoSuchElementException("Start value " + start.toString() + " not found");
        }

        MST mst = new MST(); // initialize empty MST

        // initialize data structures to help with the algorithm
        HashSet<NodeType> visitedNodes = new HashSet<NodeType>();
        PriorityQueue<MSTEdge> discoveredEdges = new PriorityQueue<>();

        Vertex currNode = this.vertices.get(start);
        MSTEdge currEdge = null;
        while (visitedNodes.size() < this.getVertexCount()) {

            if (!currNode.data.equals(start)) { // if not the start and queue is empty
                if (discoveredEdges.size() == 0) {
                    break;
                }
            }

            if (visitedNodes.contains(currNode.data)) {
                currEdge = discoveredEdges.remove();
                currNode = currEdge.getEnd();
                continue; // if already visited this node, get next and continue
            }

            visitedNodes.add(currNode.data); // add current node to visitedNodes
            mst.insertVertex(currNode.data); // insert vertex
            if (currEdge != null) { // this should only be false on the first iteration
                // bi-directional to show connectedness and undirected
                mst.insertEdge(currEdge.getStart().data, currNode.data, currEdge.getWeightRaw());
                mst.insertEdge(currNode.data, currEdge.getStart().data, currEdge.getWeightRaw());
            }

            for (Edge edge_iter : currNode.edgesLeaving) {
                MSTEdge edge = new MSTEdge(edge_iter, currNode);
                discoveredEdges.add(edge);
            }

            currEdge = discoveredEdges.remove();
            currNode = currEdge.getEnd();

        }

        
        mst.lockMST(); // lock it from editing before returning
        return mst;
    }
    
}
