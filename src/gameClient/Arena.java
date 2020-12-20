package gameClient;

import api.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class Arena {
	private static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private game_service game;
	private directed_weighted_graph _graph;
	private LinkedList<CL_Pokemon> _pokemons=new LinkedList<>();
	private LinkedList<CL_Agent> _agents = new LinkedList();
	private List<String> _info;
	private Gson gson = new Gson();
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);

	/**
	 * This function returns the arena of the game.
	 */
	public Arena(game_service game){
		this.game=game;
		JsonObject graph = gson.fromJson(game.getGraph(), JsonObject.class);
		this.setGraph(graph);
	}

	/**
	 This function returns the level of the game.
	 */
	public int getLevel(){
		JsonObject ourGame=gson.fromJson(game.toString(), JsonObject.class);
		return ourGame.get("GameServer").getAsJsonObject().get("game_level").getAsInt();
	}

	/**
	 This function returns the game (game_service).
	 */
	public game_service getGame(){return this.game;}

	/**
	 This function returns the grade- "score" of the game.
	 */
	public double getGrade(){
		JsonObject ourGame=gson.fromJson(game.toString(), JsonObject.class);
		return ourGame.get("GameServer").getAsJsonObject().get("grade").getAsDouble();
	}

	/**
	 This function returns how many moves has been done in the game, the amount of moves.
	 */
	public int getSteps(){
		JsonObject ourGame=gson.fromJson(game.toString(), JsonObject.class);
		return ourGame.get("GameServer").getAsJsonObject().get("moves").getAsInt();
	}


	/**
	 This function returns returns the directed_weighted_graph thatthis game is based on.
	 */
	public directed_weighted_graph getGraph(){
		return _graph;
	}

	/**
	 This function sets the pokemons in this game, the pokemons that are going to be in this game.
	 */
	public void setPokemons(LinkedList<CL_Pokemon> pokemons){
		this._pokemons = pokemons;
	}

	/**
	 This function sets the agents in this game, the agents that are going to be in this game.
	 */
	public void setAgents(LinkedList<CL_Agent> agents){this._agents = agents;}

	/**
	 This function returns the agents in this game, the agents that are going to be in this game.
	 */
	public LinkedList<CL_Agent> getAgents() {return _agents;}

	/**
	 This function returns the pokemons in this game, the pokemons that are going to be in this game.
	 */
	public LinkedList<CL_Pokemon> getPokemons() {return _pokemons;}

	/**
	 This function returns the info of this game's Arena.
	 */
	public List<String> get_info() {
		return _info;
	}

	/**
	 This function sets the info of this game's Arena.
	 */
	public void set_info(List<String> _info) {
		this._info = _info;
	}

	/**
	 This function sets the graph of this game's Arena.
	 */
	public void setGraph(JsonObject graph){
		directed_weighted_graph g = new DWGraph_DS();
		_graph = g;
		JsonArray allNodes = graph.get("Nodes").getAsJsonArray();
		JsonArray allEdges = graph.get("Edges").getAsJsonArray();
		for (int i = 0; i< allNodes.size(); i++) {
			JsonObject n = allNodes.get(i).getAsJsonObject();
			node_data node = new NodeData(n.get("id").getAsInt());
			String str  = n.get("pos").getAsString();
			String[] arr = str.split(",");
			double x = Double.parseDouble(arr[0]);
			double y = Double.parseDouble(arr[1]);
			double z = Double.parseDouble(arr[2]);
			geo_location geo = new GeoLocation(x, y, z);
			node.setLocation(geo);
			_graph.addNode(node);
		}
		for (int i = 0; i< allEdges.size(); i++){
			JsonObject e = allEdges.get(i).getAsJsonObject();
			_graph.connect(e.get("src").getAsInt(), e.get("dest").getAsInt(), e.get("w").getAsDouble());
		}
	}

	/**
	 * This method sets an edge of pokemon- by this pokemon's location.
	 */
	public void updatekEdgeForPok(CL_Pokemon pokemon){
			for (node_data n: _graph.getV()){
				for (edge_data edge: _graph.getE(n.getKey())){
						if ((edge.getSrc()<edge.getDest()&&pokemon.getType()==1) || (edge.getSrc()>edge.getDest()&&pokemon.getType()==-1)){
							double totalDistance = _graph.getNode(edge.getSrc()).getLocation().distance(_graph.getNode(edge.getDest()).getLocation());
							double distance1 = _graph.getNode(edge.getSrc()).getLocation().distance(pokemon.getLocation());
							double distance2 = pokemon.getLocation().distance(_graph.getNode(edge.getDest()).getLocation());
							if (totalDistance > (distance1+distance2)-EPS){
								pokemon.setEdge(edge);
								return;
							}
						}
				}
			}
	}

	/**
	 * This method updates the current pokemons list for the game, in this Arena.
	 */
	public void updatePoks2Arena(game_service game){
		String pokList = game.getPokemons();
		JsonObject poks_object = gson.fromJson(pokList, JsonObject.class);
		JsonArray pok_array = poks_object.get("Pokemons").getAsJsonArray();
		LinkedList<CL_Pokemon> pok_list=new LinkedList<>();
		for (int i = 0; i<pok_array.size(); i++){
			CL_Pokemon pk = new CL_Pokemon(pok_array.get(i).getAsJsonObject().get("Pokemon").getAsJsonObject());
			pok_list.add(pk);
		}
		for(int i=0; i<pok_list.size(); i++)
			this.updatekEdgeForPok(pok_list.get(i));
		this.setPokemons(pok_list);
	}

	/**
	 * The method updates the current agents list for the game, in this Arena.
	 */
	public void updateAgents2Arena(game_service game){
		JsonObject agents_object = gson.fromJson(game.getAgents(), JsonObject.class);
		JsonArray agents_array = agents_object.get("Agents").getAsJsonArray();
		LinkedList<CL_Agent> agents_list=new LinkedList<>();
		for (int i = 0; i<agents_array.size(); i++){
			CL_Agent agent = new CL_Agent(agents_array.get(i).getAsJsonObject().get("Agent").getAsJsonObject());
			agents_list.add(agent);
		}
		this.setAgents(agents_list);
	}

	/**
	 * This method checks if all the agents already caught a pokemon.
	 */
	public boolean everyAgentAlreadyAte(){
		for (int i=0; i<this.getAgents().size(); i++){
			if(this.getAgents().get(i).getDest()!=-1)
				return false;
		}
		return true;
	}

	/**
	 * This function returns the average speed of the agents in this Arena.
	 */
	public int avgSpeed(){
		double avg=0;
		for (int i=0; i<this.getAgents().size(); i++)
			avg+=this.getAgents().get(i).getSpeed();
		avg/=this.getAgents().size();
		return (int)(avg);
	}

	/**
	 * This function returns the average weight of the edges in this Arena.
	 */
	public int avgWeight(){
		double sum=0;
		for (node_data n: _graph.getV()){
			for (edge_data edge: _graph.getE(n.getKey())){
				sum+=edge.getWeight();
			}
		}
		return (int)(sum/_graph.edgeSize());
	}

	/**
	 * This function returns the Range2D of this Arena's graph.
	 */
	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}

	/**
	 * This function returns the Range2Range of this Arena's graph and frame.
	 */
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}
}
