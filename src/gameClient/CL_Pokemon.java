package gameClient;
import api.edge_data;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import org.json.*;
import api.*;
/**Class for representing one pokemon
 */
public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;

	/**
	 *Constructor for the CL_Pokemon
	 */
	public CL_Pokemon(JsonObject p) {
			_value = p.get("value").getAsDouble();
			_type = p.get("type").getAsInt();
			String str = p.get("pos").getAsString();
			String[] arr = str.split(",");
			double x = Double.parseDouble(arr[0]);
			double y = Double.parseDouble(arr[1]);
			double z = Double.parseDouble(arr[2]);
			Point3D geo = new Point3D(x, y, z);
			_pos = geo;
	}

	/**
	 *This function returns the GeoLocation of a pokemon.
	 */
	public Point3D getLocation(){
		return _pos;
	}

	/**
	 *This function returns the src nodeis key of the edge that this pokemon is located on.
	 */
	public int getSrc() {return _edge.getSrc();}

	/**
	 *This function returns the dest node's key of the edge that this pokemon is located on.
	 */
	public int getDest(){return _edge.getDest();}

	/**
	 *This function sets the edge that this pokemon is located on. which means,
	 * adds the information that this pokemon is located on the edge we pass to this function.
	 */
	public void setEdge(edge_data edge){_edge=edge;}

	/**
	 *This function returns the edge that this pokemon is located on. which means,
	 */
	public edge_data getEdge(){return _edge;}

	/**
	 *This function returns the type of the pokemon, which is "1" when the pokemon's located on
	 * an edge that the src's key of it is smaller than the dest's key of it,
	 * and "-1" when it's the opposite case (the src's key of the edge is bigger than the dest's key).
	 */
	public int getType(){return _type;}

	/**
	 *This function returns the value of the pokemon.
	 */
	public double getValue(){return _value;}
}
