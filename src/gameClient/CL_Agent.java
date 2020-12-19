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
		private CL_Pokemon pokemonTarget;
		private List<node_data> path=new LinkedList<>();

    /** Constructor for an CL_Agent
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

    /**
     *This function returns the src node's key of the edge that this pokemon is located on.
     */
    public int getSrc() {return src; }

    /**
     *This function returns the dest node's key of the edge that this pokemon is located on.
     */
    public int getDest(){return dest;}

    /**
     *This function sets the pokemon target of an agent, which means tells this agent,
     * that its pokemon target is the CL-pokemon passed to this function.
     */
    public void setPokemonTarget(CL_Pokemon p){
        this.pokemonTarget = p;
    }

    /**
     *This function returns the CL_pokemon target of an agent.
     */
    public CL_Pokemon getTarget(){return this.pokemonTarget;}

    /**
     *This function returns the GeoLocation of the agent.
     */
    public geo_location getLocation(){return this.pos;}

    /**
     *This function returns the speed of an agent.
     */
    public double getSpeed(){return this.speed;}

    /**
     *This function returns the value of an agent, which means how much points he earned
     * at the moment you call the function on it.
     */
    public double getValue(){return this.value;}

    /**
     *This function sets the path (List<node_data>)
     * that this agent has to go through, to catch his desired pokemon, its target.
     */
    public void setPath(List<node_data>list){path=list;}

    /**
     *This function returned the path (List<node_data>)
     * that this agent has to go through, to catch his desired pokemon, its target.
     */
    public List<node_data> getPath(){return this.path;}


	}
