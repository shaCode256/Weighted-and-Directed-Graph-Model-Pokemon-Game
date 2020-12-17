package api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    List<node_data> nodes= new ArrayList<>();
    List<directed_weighted_graph> w_graphs= new ArrayList<>();
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    DWGraph_DS g1= new DWGraph_DS();
    DWGraph_DS g2= new DWGraph_DS();
    DWGraph_DS millions= new DWGraph_DS();
    dw_graph_algorithms ag1 = null;
    dw_graph_algorithms ag2 = null;
    dw_graph_algorithms ag3= null;

    long start = new Date().getTime();

    @BeforeEach
    void setUp() {
        start = new Date().getTime();
        System.out.println(ANSI_GREEN+"Setting up the environment. creating the graphs..."+ANSI_RESET);
        double di;
        Integer obj=0;
        String si=null;
        NodeData initialization = new NodeData();
        initialization.setCounter(0);
        nodes.add(initialization);
        for(int i=1;i<20; i++){ //create a new array of nodes, with new nodes
            node_data nd= new NodeData();
            //          System.out.println("this new node's key is "+nd.getKey());
            nodes.add(nd);
        }
        for(int i=0;i<20; i++){ //initializing the nodes with tags and infos.
            di= i;
            obj = i;
            si = obj.toString();
            nodes.get(i).setTag((int) (di+0.5));
            nodes.get(i).setInfo(si+"+0.5");
        }
//////////////////////////////////////////////starting creating graph1/////////////////////
        for (int i = 1; i < 20; i++) { //initialize a new graph of nodes, with new nodes
            //      System.out.println("this new node's key is "+nodes.get(i).getKey());
            g1.addNode(nodes.get(i));
        }
        g1.connect(1,3,5);
        // int edgeExp= 5;
        g1.connect(1,4,0.1);
        g1.connect(2,4,0.1);
        g1.connect(1,5,12);
        g1.connect(2,9,10);
        g1.connect(9,2,0.1);
        g1.connect(2,5,1);
        g1.connect(1,2, 2);
        g1.connect(2,5,1);
        g1.connect(9,2,0.1);
        g1.connect(1,9,0.1);
        g1.connect(1,4,3);
        g1.connect(9,4,0.2);

        ag1 = new DWGraph_Algo();
        ag1.init(g1);
//////////////////////////////////////////////end of creating graph1/////////////////////

//////////////////////////////////////////////starting creating graph2/////////////////////

        ag2 = new DWGraph_Algo();
        NodeData initializationB = new NodeData();
        initializationB.setCounter(0);
        nodes.add(initializationB);
        for (int i = 1; i < 3; i++) { //initialize a new graph of nodes, with new nodes
            //      System.out.println("this new node's key is "+nodes.get(i).getKey());
            g2.addNode(nodes.get(i));
        }

        //g2.connect(0,1, 3);
        g2.connect(1,0, 4);
        // g2.connect(,1, 2);

        ag2.init(g2);

        //////////////////////////////////////////////end of creating graph2/////////////////////

        //////////////////////////////////////////////starting creating graph3/////////////////////

        ag3 = new DWGraph_Algo();

        //////////////////////////////////////////////end of creating graph3/////////////////////

        dw_graph_algorithms algo = new DWGraph_Algo();
        //   algo.init(Build15());

    }

    @AfterEach
    void tearDown() {
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        System.out.println("This test took "+dt+" seconds");

    }

    @Test
    void init() {
        assertEquals(ag1.getGraph() ,g1); //the getGraph will be correct if&only the init is correct.
    }

    @Test
    void getGraph() {
        System.out.println(ANSI_GREEN+"Checking if the getGraph works ok..."+ANSI_RESET);
        assertEquals(ag1.getGraph(), g1);
    }

    @Test
    void copy() {
        System.out.println(ANSI_GREEN+"Checking if the copy method works ok..."+ANSI_RESET);
        //going through all the graph, seeing how mny nodes in both of them, and then iterating
        //on both to see those with the same key, and than count how many there are,
        //then we see if their infos and tags are equal as needed. if there were exactly same
        //nodes with the same keys in both of them and this all was true (if tag/info was different than
        //the count would mess up as I programmed it to), than it's really a deep copy.

        directed_weighted_graph copied =ag1.copy();
        int countNodes = 0;
        if (copied.getV().size()==g1.getV().size()) {
            for (node_data copnode : copied.getV()) {
                for (node_data orgnode : g1.getV()) {
                    if (copnode.getKey() == orgnode.getKey()) {
                        countNodes++;
                        if (copnode.getTag() != orgnode.getTag()) {
                            countNodes = -99;
                        }
                        if (copnode.getInfo() != orgnode.getInfo()) {
                            countNodes = -99;
                        }
                    }
                }
            }
        }
        assertEquals(countNodes, g1.nodeSize());
    }

    @Test
    void isConnected() {
        System.out.println(ANSI_GREEN+"Checking if the isConnected works ok..."+ANSI_RESET);
        //   assertFalse(ag1.isConnected()); //ag1 is not connected.
        //      System.out.println("getE is "+ag2.getGraph().getEdge(1, 0));
        //      System.out.println("getE is "+ag2.getGraph().getEdge(0, 1));
        assertFalse(ag2.isConnected()); //ag2 is connected.
    }

    @Test
    void shortestPathDist() {
        System.out.println("g2.getV is "+g2.getV());
//        System.out.println("edge is "+g2.getEdge(1,0));
        //       System.out.println(ag2.shortestPath(1,0).size());
//        System.out.println("last one's tag is "+algo.shortestPath(2,5).get(0).getTag());
        assertEquals(ag2.shortestPathDist(1,0),4);
        //     System.out.println(g.getNode(
    }

    @Test
    void ifGetVWorks(){
        System.out.println("meaningGetVWorks");
        System.out.println("the neighbors are "+g1.getE(2));
    }
    @Test
    void shortestPath() {
        System.out.println("g1.getV is "+g1.getV());

        System.out.println(ANSI_GREEN+"Checking if the shortestPath works ok..."+ANSI_RESET);
        //     System.out.println("neighbores " +g2.getE(0));
        //       System.out.println(g1.getEdge(2,5).getWeight());
//        System.out.println(g1.getEdge(5,6).getWeight());
        List <node_data> listy=ag1.shortestPath(1,4);
        System.out.println("the dist is "+ag1.shortestPathDist(1,4));
        //   System.out.println("listy is "+listy);
        System.out.println("The path is: ");
        System.out.print("start --> ");
        for (int i=0; i<listy.size(); i++) { //print the path!
            System.out.print(listy.get(i).getKey()+" --> ");
        }
        System.out.print("end");
        assertEquals(listy.size(), 3);

        System.out.println();
    }

    public static DWGraph_DS Connected(){
        DWGraph_DS g = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new NodeData();
            g.addNode(n);
        }
        GeoLocation geo0 = new GeoLocation(1.34, 5.4542, 5.32);
        GeoLocation geo1 = new GeoLocation(2.34, 6.4324, 2);
        GeoLocation geo2 = new GeoLocation(1.34, 3, 7);
        GeoLocation geo3 = new GeoLocation(3.6, 6.4324, 11);
        GeoLocation geo4 = new GeoLocation(3, 5.4542, 17);
        g.getNode(0).setLocation(geo0);
        g.getNode(1).setLocation(geo1);
        g.getNode(2).setLocation(geo2);
        g.getNode(3).setLocation(geo3);
        g.getNode(4).setLocation(geo4);
        NodeData initialization = new NodeData();
        initialization.setCounter(0);
        g.connect(0, 1, 2.5);
        g.connect(1, 2, 6.7);
        g.connect(2, 3, 40);
        g.connect(3, 4, 10);
        g.connect(4, 3, 1);
        g.connect(3, 2, 16);
        g.connect(2, 1, 0.5);
        g.connect(1, 0, 20);
        return g;
    }

    public static DWGraph_DS IsNotConnected(){
        DWGraph_DS g = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new NodeData();
            g.addNode(n);
        }
        GeoLocation geo0 = new GeoLocation(1.34, 5.4542, 5.32);
        GeoLocation geo1 = new GeoLocation(2.34, 6.4324, 2);
        GeoLocation geo2 = new GeoLocation(1.34, 3, 7);
        GeoLocation geo3 = new GeoLocation(3.6, 6.4324, 11);
        GeoLocation geo4 = new GeoLocation(3, 5.4542, 17);
        g.getNode(0).setLocation(geo0);
        g.getNode(1).setLocation(geo1);
        g.getNode(2).setLocation(geo2);
        g.getNode(3).setLocation(geo3);
        g.getNode(4).setLocation(geo4);
        NodeData initialization = new NodeData();
        initialization.setCounter(0);
        /////////////////connected only from one side so-is NOT connected./////////////////
        g.connect(0, 1, 2.5);
        g.connect(1, 2, 6.7);
        g.connect(2, 3, 10);
        g.connect(3, 4, 10);

        return g;
    }

    @Test
    void testIsConnected(){
        DWGraph_DS g1 = Connected();
        DWGraph_DS g2 = IsNotConnected();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(g1);
        assertTrue(algo.isConnected());
    }

    /**
     * Initialization  operator on Connected. Then, save Connected in file whose name is "myGraph".
     * After this, replace the graph of operator to IsNotConnected.
     * Load operator on "myGraph" file. Now, operator operates Connected again, instead of IsNotConnected.
     */
    @Test
    void saveAndLoad() {
        DWGraph_Algo operator = new DWGraph_Algo();
        operator.init(Connected());
        assertFalse(operator.save(""));
        assertTrue(operator.save("myGraph"));
        operator.init(IsNotConnected());
        assertFalse(operator.load(""));
        assertFalse(operator.load("fakeFile"));
        assertTrue(operator.load("myGraph"));
        assertFalse(operator.getGraph().equals(IsNotConnected()));
        assertTrue(operator.getGraph().equals(Connected()));
    }

    @Test
    void testShortestPathDist(){
        DWGraph_Algo operator = new DWGraph_Algo();
        operator.init(Connected());
        assertEquals(0, operator.shortestPathDist(1,1));
        assertEquals(9.2, operator.shortestPathDist(0,2));
        assertEquals(49.2, operator.shortestPathDist(0,3));
        assertEquals(59.2, operator.shortestPathDist(0,4));
        assertEquals(1, operator.shortestPathDist(4,3));
        assertEquals(17, operator.shortestPathDist(4,2));
        assertEquals(17.5, operator.shortestPathDist(4,1));
        assertEquals(37.5, operator.shortestPathDist(4,0));
    }

    DWGraph_DS graph_creator(int nodes, int edges) {
        DWGraph_DS graph= new DWGraph_DS();
        System.out.println(ANSI_GREEN+"Creating a graph..."+ANSI_RESET);
        // assertFalse(ag1.isConnected()); //ag1 is not connected.
        //     System.out.println(ANSI_GREEN+"Checking if isConnected on graph with miliions of nodes works ok..."+ANSI_RESET);
        //     System.out.println(ANSI_GREEN+"Checking if isConnected on graph with miliions of nodes works ok..."+ANSI_RESET);

        for(int i=0;i<nodes;i++) { //add 1000000 nodes
            graph.addNode(new NodeData());
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        Random ran = new Random();
        int range = nodes + 1;     //connect 10000000 edges
        int randomNum;
        while(graph.edgeSize() < edges) {
            randomNum =  ran.nextInt(range);
            int a = randomNum;
            randomNum =  ran.nextInt(range);
            int b = randomNum;
            randomNum =  ran.nextInt(range);
            double w = randomNum;
            graph.connect(a,b, w);
        }
        return graph;
    }
}
