package api;
/**
 * This interface represents a geo location <x,y,z>, aka Point3D
 */
public class GeoLocation implements geo_location {
    private double x, y, z;
    /**
     * Constructor
     */
    public GeoLocation(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * This method returns this GeoLocation's X value
     */
    @Override
    public double x() {
        return this.x;
    }

    /**
     * This method returns this GeoLocation's Y value
     */
    @Override
    public double y() {
        return this.y;
    }

    /**
     * This method returns this GeoLocation's Z value
     */
    @Override
    public double z() {
        return this.z;
    }

    /**
     * This function returns the double distance value from this GeoLocation
     * to another GeoLocation.
     * By the formula: sqrt of [(x1-x2)^2+(y1-y2)^2+(z1-z2)^2].
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(this.x-g.x(), 2)+Math.pow(this.y-g.y(), 2)+Math.pow(this.z-g.z(), 2));
    }
}
