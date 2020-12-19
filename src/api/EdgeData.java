package api;



public class EdgeData implements edge_data {
    private int src, dest, tag;
    private double weight;
    private String info;
    /**
     * Constructor.
     */
    public EdgeData (node_data nSrc, node_data nDest, double weight){
        this.src = nSrc.getKey();
        this.dest = nDest.getKey();
        this.weight = weight;
        //TO CHECK THIS LATER^ should it be just "weight", or this distance calculation?
    }
    /**
     * The id of the source node of this edge.
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }
    /**
     * The id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }
    /**
     * Returns the remark (meta data) associated with this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return this.getInfo();
    }
    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return
     */

    @Override
    public int getTag() {
        return this.tag;
    }
    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    /**
     * This method returns this edge's edge_data
     */
    public edge_data getEdge(int src, int dest){
        return this;
    }

    /**
     * This method allows setting the "weight" value of an edge
     */
    public void setWeight (double weight){
        this.weight = weight;
    }
}
