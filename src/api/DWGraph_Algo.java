package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private Object node_data;

    public DWGraph_Algo() {

    }

    /**
     * This interface represents the "regular" Graph Theory algorithms including:
     * 0. clone();
     * 1. init(String file_name);
     * 2. save(String file_name);
     * 3. isConnected();
     * 5. int shortestPathDist(int src, int dest);
     * 6. List<Node> shortestPath(int src, int dest);
     * 7. load(String file_name);
     * @author boaz.benmoshe
     *
     */
    /**
     * Init this set of algorithms on the parameter - graph.
     * @param g
     */

    private directed_weighted_graph myGraph;

    public void init(directed_weighted_graph g) { //I DONT GET WHAT IS THIS
        this.myGraph=g;
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.myGraph;
    }

    /**
     * Compute a deep copy of this graph.
     * @return
     */

    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DWGraph_DS(); //creating a new graph
        for (node_data n: this.myGraph.getV()){
            node_data node = new NodeData((NodeData)n);
            g.addNode(node);
        }
        for (node_data n: this.myGraph.getV()){
            for (edge_data e: this.myGraph.getE(n.getKey())){
                g.connect(e.getSrc(), e.getDest(), e.getWeight());
            }
        }
        ((DWGraph_DS)g).initMc();
        return g;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume directional graph - a valid path (a-->b) does NOT imply a valid path (b-->a).
     * @return
     */


    //   PESUDO CODE- kosaraju's algorithm
    //   https://iq.opengenus.org/kosarajus-algorithm-for-strongly-connected-components/

    public boolean isConnected() { //SHOULD CHANGE THIS.
        // System.out.println("stuck");
        if (this.myGraph == null) {
            return true;
        }
        Collection c = myGraph.getV();
        if (c.size() == 0 || c.size() == 1) {
            return true; //Boaz said an empty graph is considered connected, and one node is connected to itself.
        }
        //       System.out.println("edg size"+myGraph.edgeSize());
//        System.out.println("ndsize"+myGraph.nodeSize());
        if(myGraph.edgeSize()< 2*myGraph.nodeSize()-2) {
//            System.out.println("failed here");
            return false;
        }
        ArrayList<node_data> list = new ArrayList<>(c);
        Map<Integer, node_data> groupNodes = new HashMap<Integer, node_data>();
        for (node_data i : list) groupNodes.put(i.getKey(), i); //putting the collection in a hashmap
        int first = list.get(0).getKey();
        if (myGraph == null || myGraph.getV() == null || myGraph.getE(first) == null || !myGraph.getE(first).stream().findFirst().isPresent()){
            //if there's no neighbor to the node than it's not a connected graph by definition,
            // as the graph size is bigger than 1.
            System.out.println("is mygraph== null "+myGraph==null);
            System.out.println("is mygraph.getV== null "+myGraph.getV()==null);
            System.out.println("is mygraph.getV== null "+myGraph.getE(first)==null);
            System.out.println("hoooooooooola");
            return false;
        }
        else { //it does have a neighbor node. //perform a nodified DFS!
//            int src = first;
//            int value = myGraph.getE(first).stream().findFirst().get().getDest(); //finding a key in that random hashmap of neighbors
//            int dest = value;
            NodeData node= null;
            // check if graph is strongly connected or not
            ////////////////////////////////////Init the DFS on the graph////////////////////////////////////
            for (node_data gNode : myGraph.getV()) { //for every u ∈ myGraph
                ((NodeData) gNode).setTagB(0); //meaning it has NOT been yet visited
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////
            DFS(myGraph, first); // Do a DFS on the original graph
            for (node_data nodea: myGraph.getV()) {//if there's one which isn't visited
                node= (NodeData)nodea;
                System.out.println(node.getTagB());
                //this graph is not connected.
                if(node.getTagB()!=1){ //meaning it's unvisited
                    System.out.println("lala");
                    return false;
                }
            }

            int src=0;
            int dest=0;
            DWGraph_DS newGraph= new DWGraph_DS();
            for (node_data nd: myGraph.getV()) { //Reverse the original graph
                newGraph.addNode(nd); //add the nodes to the new graph
                for (edge_data edge : myGraph.getE(nd.getKey())) {
                    src=edge.getSrc();
                    dest=edge.getDest();
                    newGraph.connect(dest, src, 1); //1 as a defult weight, as it doesn't matter for this DFS algorithm
//                ((EdgeData)(edge)).setSrc(dest);
//                ((EdgeData)(edge)).setDest(src);
                }
            }
            /////////////////////////////////init DFS for the new graph//////////////////////////////////
            for (node_data gNode : newGraph.getV()) { //for every u ∈ myGraph
                ((NodeData) gNode).setTagB(0); //meaning it has NOT been yet visited
            }
            /////////////////////////////////////////////////////////////////////////////////////////////
            DFS(newGraph, first); //Do DFS again
            //I assume it labels the nodes,
            for (node_data nodea: newGraph.getV()) {//if there's one which isn't visited
                node= (NodeData)nodea;
                //              System.out.println(node.getTagB());
                //this graph is not connected.
                if(node.getTagB()!=1){ //meaning it's unvisited
                    //                  System.out.println("lolo");
                    return false;
                }
            }
        }
        // so we can iterate through the nodes in the getV() to check the result
        return true;
    }

    // DFS algorithm implementation-
    //used the psudocode: https://www.programiz.com/dsa/graph-dfs
    //you can have a "complete" one below, which also checks the connectivity:
    //https://www.techiedelight.com/check-given-graph-strongly-connected-not/
    //and adjust it as implemented here.
    //think about using a COUNTER istead of going through all the nodes

    void DFS(directed_weighted_graph myGraph, int vertex) {
        ArrayList<node_data> neilist = new ArrayList<>();
        ((NodeData)myGraph.getNode(vertex)).setTagB(1); // 1- mark it as a VISITED node
        //initate a list of the neighbors of Vertex:
        for (edge_data edge : myGraph.getE(vertex)) {  //where v has not yet been removed from Q.
            //get node data from edge_data
            //           System.out.println("the node is "+vertex);
//            System.out.println("the dest of edge is "+edge.getDest());
            neilist.add(myGraph.getNode(edge.getDest()));
        }
        NodeData node= null;
        for (node_data nodea : neilist) {//for every neighbor
            //          System.out.println("the nrighbor is "+nodea.getKey());
            node=(NodeData) nodea;
            if (node.getTagB() != 1) { //if this node hasn't been visited yet
                DFS(myGraph, node.getKey());
            }
        }
    }

//            for (node_data gNodeB : myGraph.getV()) { //for every vertex
//                DFS(myGraph, gNodeB.getKey());
//            }
//        }


    /**
     * returns the length of the shortest path between src to dest
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

    /**
     * returns the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    public double shortestPathDist(int src, int dest){
        if(src==dest){ //the distance from a node to itself is 0.
            return 0;
        }
        List<node_data> aj=shortestPath(src,dest);
        //  System.out.println(aj.size());
        if(aj.isEmpty()){
            //         System.out.println("empty");
            return -1; //EMPTY aj MEANS THERE'S NO PATH FROM SRC TO DEST
        }
        double weight = ((NodeData)(aj.get(aj.size()-1))).getTagB();
        return weight; //  with -1 cuz of the edges num, if there are 2 nodes, theres only one edge connecting them
    }

    //I used Dijkstra Algorithm: Short terms and Pseudocode: http://www.gitta.info/Accessibiliti/en/html/Dijkstra_learningObject1.html
    public List<node_data> shortestPath(int src, int dest) { // by Dijkstra's method

        Collection c=myGraph.getV(); //get and store the graphs' collection of vertices.
        Map<Integer, Integer> parents = new HashMap<Integer, Integer>(); //for (space I guess.. wanted to achieve time) complexity reason-
        //a hashmap of parents, to restore the parent of each node in the shortest path
        List<node_data> myListg = new ArrayList<node_data>(); //create a list for returning the shortest path.
        ArrayList<node_data> list = new ArrayList<>(c); //put the nodes in an arraylist.
        Map<Integer, node_data> groupNodes = new HashMap<Integer, node_data>(); //create a new nodes hashmap for the actions required.
        for (node_data i : list) {
            groupNodes.put(i.getKey(), i); //for every key in the nodes list put it in the hashmap with its key.
        }
        int desty= dest; //for further use

        if (dest== src) { //the path of node from itself to itself is itself.
            myListg.add(groupNodes.get(src));
            return myListg;
        }

        if (!groupNodes.containsKey(src)||!groupNodes.containsKey(dest)){ //if one of them doesn't exist there's no path
            //         System.out.println("someone doesnt exist");
            return myListg;
        }

//        if(myGraph.getEdge(src, dest)!=null){ //if they're neighbors so this is the shortest path
//       //     System.out.println("they are neighbores");
//            ((NodeData)(myGraph.getNode(dest))).setTagB(myGraph.getEdge(src, dest).getWeight());
//            myListg.add(groupNodes.get(src));
//            myListg.add(groupNodes.get(dest));
//            return myListg;
//        }
        //int k= Collections.max(groupNodes.keySet())+1; ///gets the biggest key value, to initialize a big enough array (I don't use array anymore)
        //to face problems with keying as for the array of parents, every index is the key of node.
        // node_data[] parents = new node_data[k];
        //how to get infinity -https://stackoverflow.com/questions/12952024/how-to-implement-infinity-in-java

        for (Integer key : groupNodes.keySet()) { //initializing- prepearing the nodes for the proccess.
            groupNodes.get(key).setInfo(""); //previous node, that from it we got to this node,
            // in the shortest path: is an empty string because we didn't start the paving yet
            ((NodeData)(groupNodes.get(key))).setTagB(Double.POSITIVE_INFINITY); //Shortest distance- we set it to infinity cuz we didn't check it yet
            parents.put(key, null);                       // null because initially, we don't have any path to reach
        }

        ((NodeData)(groupNodes.get(src))).setTagB(0); // distance a node to itelf is 0, and it's the distance of src from itself
        Deque<node_data> q = new ArrayDeque();
        for (Integer key:groupNodes.keySet()) { // all nodes in the graph are unoptimized - thus are in Q
            q.add(groupNodes.get(key));
        }
        double minDist=Double.POSITIVE_INFINITY;
        int minKey=-1;
        double dist=0;
        node_data minNode=null;

        while (!q.isEmpty()) { //	// main loop
            //System.out.println("does it contain key"+q.contains(groupNodes.get(src)));
            for (node_data node : q) { //getting the smallest dist node
                // System.out.println("key is"+node.getKey());
                if(((NodeData)(node)).getTagB()<=minDist){
                    minDist=((NodeData)(node)).getTagB();
                    minKey=node.getKey();
                    minNode=node;
                }
            }
            q.remove(minNode);
            //for every neighbor of minKey= neinode
            for (edge_data edge: myGraph.getE(minKey)) {  //where v has not yet been removed from Q.
                //get node data from edge_data
                NodeData neinode= (NodeData) myGraph.getNode(edge.getDest());
                //we're taking a node from myGraph, which is the neighbor of MINKEY node,
                //which is the DEST in the edge between MINKEY and DEST.
                // System.out.println("heya");
                if(q.contains(neinode)){ //where v has not yet been removed from Q.//if it contains the neighbor node
                    //   if(getGraph().getEdge(edge.getKey(), minKey)!=-1) { should be^^ for
                    dist = minDist + myGraph.getEdge(minKey, neinode.getKey()).getWeight(); //alt := dist[u] + dist_between(u, v)
                    if (dist < neinode.getTagB()) {
                        ((NodeData)neinode).setTagB(dist); // it's the path's weight sum, why is it double?
                        //because it's requested like that. TO GO THROUGH LATER
                        neinode.setInfo(String.valueOf(minKey));
                    }
                    //   }
                }
            }
            minDist=Double.POSITIVE_INFINITY;
        } //done with while loop.
        desty=dest;
//        System.out.println("the nodedata is "+((NodeData) groupNodes.get(2)));
/////        System.out.println("It's tagB is "+((NodeData) groupNodes.get(2)).getTagB());
//        System.out.println("the nodedata is "+((NodeData) groupNodes.get(5)));
//        System.out.println("It's tagB is "+((NodeData) groupNodes.get(5)).getTagB());
//        System.out.println("the nodedata is "+((NodeData) groupNodes.get(desty)));
//        System.out.println("It's tagB is "+((NodeData) groupNodes.get(desty)).getTagB());

        while(((NodeData) groupNodes.get(desty)).getTagB()!=0&&groupNodes.get(desty).getInfo()!=""){ //means you haven't yet reached the src because only the src has tag=0 (dist)
            //of 0 from itself.
            myListg.add(groupNodes.get(desty));
            desty = Integer.parseInt(groupNodes.get(desty).getInfo()); //get the "father"
        }
        myListg.add(groupNodes.get(src));
//                for (int i=0; i<myListg.size(); i++) {
//                        System.out.println(myListg.get(i).getKey());
//                }
        Collections.reverse(myListg); //reverse the list, 'cuz it came reversed, as we got from the dest to src by parents.
        //   System.out.println("myListg.size ISSSSS"+myListg.size());
        if(myListg.get(myListg.size()-1).getKey()!=dest){
            //  System.out.println("YES");
            myListg.clear();
        }

        //       System.out.println(myListg);
        return myListg;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try{
            JSONObject graph = new JSONObject();
            JSONArray allNodes = new JSONArray();
            JSONArray allEdges = new JSONArray();
            if(myGraph.getV()!=null){
                for(node_data node: myGraph.getV()){
                    JSONObject n = new JSONObject();
                    n.put("id",node.getKey());
                    n.put("pos", node.getLocation().x()+","+node.getLocation().y()+","+node.getLocation().z());
                    allNodes.put(n);
                }
                graph.put("Nodes", allNodes);
                for(node_data node: myGraph.getV()){
                    if(myGraph.getE(node.getKey()) != null) {
                        for (edge_data edge : myGraph.getE(node.getKey())) {
                            JSONObject e = new JSONObject();
                            e.put("src", edge.getSrc());
                            e.put("dest", edge.getDest());
                            e.put("w", edge.getWeight());
                            allEdges.put(e);
                        }
                    }
                }
                graph.put("Edges", allEdges);
            }
            FileWriter saved = new FileWriter(file);
            saved.write(graph.toString());
            saved.flush();
            saved.close();
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override


    public boolean load(String file) {
        try {
            directed_weighted_graph g = new DWGraph_DS();
            Scanner s = new Scanner(new File(file));
            String jsonString = s.useDelimiter("\\A").next();
            s.close();
            JSONObject graph = new JSONObject(jsonString);
            JSONArray allNodes = graph.getJSONArray("Nodes");
            JSONArray allEdges = graph.getJSONArray("Edges");
            for (int i = 0; i< allNodes.length(); i++) {
                JSONObject n = allNodes.getJSONObject(i);
                node_data node = new NodeData(n.getInt("id"));
                String str  = n.getString("pos");
                String[] arr = str.split(",");
                double x = Double.parseDouble(arr[0]);
                double y = Double.parseDouble(arr[1]);
                double z = Double.parseDouble(arr[2]);
                geo_location geo = new GeoLocation(x, y, z);
                node.setLocation(geo);
                g.addNode(node);
            }
            for (int i = 0; i< allEdges.length(); i++){
                JSONObject e = allEdges.getJSONObject(i);
                g.connect(e.getInt("src"), e.getInt("dest"), e.getDouble("w"));
            }
            this.init(g);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
} // #last_one_standing