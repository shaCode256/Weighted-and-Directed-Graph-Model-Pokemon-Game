package api;



public class NodeData implements node_data {
    private int key;
    private geo_location geoLocation;
    private double weight;
    private String info;
    private int tag;
    private node_data parent = null;
    private boolean visited;
    private static int counter = 0;
    public double dub;
    private double tagB;

    /**
     * Constructor for NodeData. the key defined by general counter.
     *
     * @return
     */
    public NodeData() {
        this.key = counter;
        counter++;
    }

    public NodeData(int key){
        this.key=key;
    }

    /**
     * Copy constructor.
     *
     * @return
     */
    public NodeData(NodeData n) {
        this.key = n.getKey();
        this.geoLocation = n.getLocation();
        this.weight = n.getWeight();
        this.info = n.getInfo();
        this.tag = n.getTag();
    }

    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return this.geoLocation;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        if (p==null)
            return;
        this.geoLocation = p;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    } //the key of the father in this shortest path

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    } //distance of weight, //whats the weight in the shortest path from the src up until this one (to dest)

    public double getTagB() {
        return this.tagB;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */

    public void setTagB(double t) {
        this.tagB = t;
    } //distance of weight, //whats the weight in the shortest path from the src up until this one (to dest)

    /**
     * Tostring methode
     */
    public String toString() {
        return "NodeData{key=" + key + '}';
    }

    public void setDub(double e){
        this.dub=e;
    }

    public void setCounter(int count) {
        counter =count;
    }
}
