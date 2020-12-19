package api;


//import ex1.src.WGraph_DS;
import java.util.Collection;
import java.util.HashMap;


public class DWGraph_DS implements directed_weighted_graph{
    private final HashMap<Integer, node_data> groupNodes= new HashMap<Integer, node_data>(); //this contains a hashmap of all the nodes, their keys and node_infos.
    private final HashMap<Integer, HashMap< Integer, edge_data>> groupEdges= new HashMap<Integer, HashMap<Integer, edge_data>>();
    //^A hashmap with KEYS as nodes keys, VALUES of hashmaps with VALUE of neighbor nodes keys,
    //AND the size of the edge between them as VALUES.
    private int counterEdge =0;
    private int mc = 0;

    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (this.groupNodes.containsKey(key)) {
            return groupNodes.get(key);
        }
        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) { //check whether it should return null if there is no such edge.
        if(groupEdges.containsKey(src)){
            if (groupEdges.get(src).containsKey(dest)){
                return groupEdges.get(src).get(dest);
            }
            return null;
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        int key= n.getKey();
        if (groupNodes.containsKey(key)){
            return;
            //do none, there's already a node with this key
        }
        else{
        //    System.out.println(this.nodeSize);
       //     groupNodes.put(key, new NodeInfo(key)); need to check, do they want a new one or an existing one..?
      //      neinodes.put(key, new HashMap<>());
            groupNodes.put(key, n);
            groupEdges.put(key, new HashMap<Integer, edge_data>()); //initialize the potential edges map for this node
            mc++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(w<0){
    //        System.out.println("WARNING. I can't do that. this graph accepts ONLY edges with weight 0 or greater.");
        }
        else {
            if (!groupNodes.isEmpty()) { //can't connect something in an empty graph
                if (!this.groupNodes.containsKey(src) || !this.groupNodes.containsKey(dest)) { ///if this node doesnt exist in the graph, you can't connect it to another one
                    // sout("one of the nodes doesn't exist in the graph");
                    return;
                } else { //src and dest are in the groupNodes
                    if (src == dest) {
                        //do nothing or throw exception of "you're trying to connect a node to itself"
                        return;
                    } else {
                        if (!this.groupEdges.get(src).containsKey((dest))){
                            edge_data edge=new EdgeData(groupNodes.get(src), groupNodes.get(dest), w);
                            groupEdges.get(src).put(dest, edge);
                            counterEdge++;
                            mc++;

                        }
                        else {
                            if (this.groupEdges.get(src).get(dest).getWeight() == w){
                                return;
                            }
                            //if there's an already existed edge, just update the weight
                            ((EdgeData)groupEdges.get(src).get(dest)).setWeight(w);
                            mc++;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        return this.groupNodes.values();
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * Note: this method should run in O(k) time, k being the collection size.
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) { //this one depends on the complexity needed. should check which one is needed,
        //as it might require a change in the groupEdges hashmap.
        if(this.groupEdges.get(node_id)!=null) {
            return this.groupEdges.get(node_id).values();
        }
        return null;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
         node_data removedata=null; //should it return the removed node_data?
        if(!groupNodes.containsKey(key)|| groupNodes.get(key)==null){
            return null;
        }
        else {
            removedata = this.groupNodes.get(key);
            for (Integer nd: this.groupEdges.keySet()){
                //for each (nd-->key) edge, remove key
                this.removeEdge(nd, key);
            }
            counterEdge -= groupEdges.get(key).size();
            mc+=groupEdges.get(key).size();
            groupEdges.remove(key);
            this.groupNodes.remove(key);
            mc++;
        }
        return removedata;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param src
     * @param dest
     */
    @Override
    public void removeEdge(int src, int dest) {
        if (!this.groupNodes.containsKey(src) || !this.groupNodes.containsKey(dest)) {
            //throw exception: "no key/s like this/these in the graph"
            //    System.out.println("this1");
            return;
        } else {
            if (!this.groupEdges.get(src).containsKey(dest)) {
                //they're not neighbors so don't do anything!
                //      System.out.println("this2");
                return;

            } else {
                this.groupEdges.get(src).remove(dest);
                counterEdge--;
                mc++;
            }
        }
    }    //check if needs to remove this semicolon

    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return this.groupNodes.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return this.counterEdge;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }

    /**
     * Initialize the Mode Count to 0, for testing changes in the graph.
     * @return
     */
    public void initMc() {
        mc=0;
    }

    /**
     * Checks and returns if two graphs are identical, by their structure and nodes.
     * return true if they are, and false if they're not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS compare = (DWGraph_DS)o;
        if(this.counterEdge!=compare.counterEdge || nodeSize()!=compare.nodeSize())
            return false;
        for (Integer src: this.groupNodes.keySet()){
            if (!compare.groupNodes.containsKey(src))
                return false;
            if (this.getNode(src).getLocation().x()!=compare.getNode(src).getLocation().x()||this.getNode(src).getLocation().y()!=compare.getNode(src).getLocation().y()||this.getNode(src).getLocation().z()!=compare.getNode(src).getLocation().z())
                return false;
            for (edge_data edge: this.getE(src)){
                if (!compare.groupEdges.containsKey(src))
                    return false;
                if (!compare.groupEdges.get(src).containsKey(edge.getDest()))
                    return false;
                if (this.groupEdges.get(src).get(edge.getDest()).getWeight()!=compare.groupEdges.get(src).get(edge.getDest()).getWeight())
                    return false;
            }
        }
        return true;
    }
}
