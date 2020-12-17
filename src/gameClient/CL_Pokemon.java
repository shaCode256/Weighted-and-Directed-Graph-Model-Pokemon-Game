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
	/**Constructor
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

	public Point3D getLocation(){
		return _pos;
	}

	public int getSrc() {return _edge.getSrc();}

	public int getDest(){return _edge.getDest();}

	public void setEdge(edge_data edge){_edge=edge;}

	public edge_data getEdge(){return _edge;}

	public int getType(){return _type;}

	public double getValue(){return _value;}
}
