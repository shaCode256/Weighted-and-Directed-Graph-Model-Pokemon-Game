package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    /**
     * This is the main graph for tests in this class. Directed weighted graph with 15 nodes and 9 edges.
     * Total: 24 changes.
     */
    static DWGraph_DS Build15(){
        DWGraph_DS g = new DWGraph_DS();
        for (int i = 0; i < 15; i++) {
            node_data n = new NodeData();
            g.addNode(n);
        }
        NodeData initialization = new NodeData();
        initialization.setCounter(0);
        g.connect(0, 9, 2.5);
        g.connect(1, 9, 6.7);
        g.connect(4, 9, 40);
        g.connect(3, 10, 7);
        g.connect(3, 13, 27);
        g.connect(3, 2, 4.5);
        g.connect(14, 5, 21);
        g.connect(14, 8, 41);
        g.connect(11, 2, 17);
        return g;
    }
    /**
     * Tests for getEdge: we will check if getEdge methode return thr correct data about the edges.
     * In addition, we will check getMC methode (mc of new graph is always 0).
     */
    @Test
    void testGetEdge() {
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(Build15());
        DWGraph_DS g = (DWGraph_DS)algo.copy();
        assertTrue(g.getEdge(3,13).getWeight() == 27);
        assertFalse(g.getEdge(3,13).getWeight() != 27);
        assertTrue(g.getEdge(14,8).getWeight() == 41);
        assertFalse(g.getEdge(14,8).getWeight() != 41);
        assertTrue(g.getMC() == 0);
        assertFalse(g.getMC()!=0);

    }
    /**
     * Tests for connect methode:
     * 1. Copy Build15  for new graph
     * 2. Creation edges that actually already exist, and creation fake edge (negative weight).
     * Actually, no change.
     * 3. Now, we will create 3 new edge, and will update weight for 2 edges.
     * The result: edgeSize = 9+3, mc = 5;
     */
    @Test
    void testConnect() {
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(Build15());
        DWGraph_DS g = (DWGraph_DS)algo.copy();
        g.connect(0, 9, 2.5);
        g.connect(1, 9, 6.7);
        g.connect(4, 9, 40);
        g.connect(14,13,-2);
        assertTrue(g.getMC() == 0);
        assertFalse(g.getMC()!=0);
        g.connect(9, 0, 3.5);
        g.connect(9, 4, 6.7);
        g.connect(9, 1, 42);
        g.connect(14, 8, 32);
        g.connect(11, 2, 21);
        assertTrue(g.edgeSize()==12);
        assertFalse(g.edgeSize()!=12);
        assertTrue(g.getMC() == 5);
        assertFalse(g.getMC()!=5);
    }

    /**
     * Tests for removeNode:
     * Remove 9,11, 14 nodes. 9 connected to 3 nodes (1+3 changes), 11 connected to only one (1+1
     * changes), and 14 to 2 nodes (1+2 changes). mc = 9, edgeSize = 3.
     */
    @Test
    void testRemoveNode() {
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(Build15());
        DWGraph_DS g = (DWGraph_DS)algo.copy();
        g.removeNode(9);
        g.removeNode(14);
        g.removeNode(11);
        assertTrue(g.nodeSize() == 12);
        assertFalse(g.nodeSize()!=12);
        assertTrue(g.edgeSize() == 3);
        assertFalse(g.edgeSize()!=3);
        assertTrue(g.getMC() == 9);
        assertFalse(g.getMC()!=9);
    }

    /**
     * Tests for removeEdge:
     * 1. Remove fake edges, mc = 0;
     * 2. Remove 4 edges: mc =4, edgeSize = 5;
     */
    @Test
    void testRemoveEdge() {
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(Build15());
        DWGraph_DS g = (DWGraph_DS)algo.copy();
        g.removeEdge(0,5);
        g.removeEdge(11,7);
        g.removeEdge(8,14);
        g.removeEdge(9,1);
        g.removeEdge(10,10);
        assertTrue(g.getMC() == 0);
        assertFalse(g.getMC()!=0);
        g.removeEdge(1,9);
        g.removeEdge(3,13);
        g.removeEdge(14,5);
        g.removeEdge(14,8);
        assertTrue(g.getMC() == 4);
        assertFalse(g.getMC()!=4);
        assertTrue(g.edgeSize() == 5);
        assertFalse(g.edgeSize()!=5);

    }
    /**
     * Tests for edgeSize
     */
    @Test
    void testEdgeSize() {
        DWGraph_DS g = Build15();
        assertTrue(g.edgeSize() == 9);
        assertFalse(g.edgeSize() != 9);
    }

}