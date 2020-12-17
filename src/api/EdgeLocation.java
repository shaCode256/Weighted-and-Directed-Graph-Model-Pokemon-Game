package api;
/**
 * This class represents a position on the graph (a relative position
 * on an edge - between two consecutive nodes).
 */
public class EdgeLocation implements edge_location{
    private directed_weighted_graph graph;
    private int src, dest;
    private geo_location loc;
    /**
     * Constructor
     */
    public EdgeLocation(directed_weighted_graph graph, int src, int dest, geo_location loc){
        this.graph = graph;
        this.src = src;
        this.dest = dest;
        this.loc = loc;
    }
    /**
     * Returns the edge on which the location is.
     * @return
     */
    @Override
    public edge_data getEdge() {
        edge_data e = graph.getEdge(src, dest);
        return e;
    }
    /**
     * Returns the relative ration [0,1] of the location between src and dest.
     * @return
     */
    @Override
    public double getRatio() {
        return graph.getNode(src).getLocation().distance(loc)/graph.getNode(src).getLocation().distance(graph.getNode(dest).getLocation());
    }
}
