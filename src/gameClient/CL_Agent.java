package gameClient;

import api.*;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class CL_Agent {
		private static final double EPS = 0.0001;
		private int id;
		private double value;
		private int src;
		private int dest;
		private double speed;
		private geo_location pos;
		private CL_Pokemon pokemonTaeget;
		private List<node_data> path=new LinkedList<>();

    /**Constructor
     */
    public CL_Agent(JsonObject agent) {
        this.id = agent.get("id").getAsInt();
        this.value = agent.get("value").getAsDouble();
        this.src = agent.get("src").getAsInt();
        this.dest = agent.get("dest").getAsInt();
        this.speed = agent.get("speed").getAsDouble();
        String str = agent.get("pos").getAsString();
        String[] arr = str.split(",");
        double x = Double.parseDouble(arr[0]);
        double y = Double.parseDouble(arr[1]);
        double z = Double.parseDouble(arr[2]);
        geo_location geo = new GeoLocation(x, y, z);
        pos = geo;
    }

    public int getSrc() {return src; }

    public int getDest(){return dest;}

    public void setPokemonTaeget (CL_Pokemon p){
        this.pokemonTaeget = p;
    }

    public CL_Pokemon getTarget(){return this.pokemonTaeget;}

    public geo_location getLocation(){return this.pos;}

    public double getSpeed(){return this.speed;}

    public double getValue(){return this.value;}

    public void setPath(List<node_data>list){path=list;}

    public List<node_data> getPath(){return this.path;}


	}
