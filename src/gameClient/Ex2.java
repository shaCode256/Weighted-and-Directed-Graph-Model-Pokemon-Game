package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;

import java.util.LinkedList;

public class Ex2 implements Runnable{
    public static int loginID, level;
    public static Thread client;
    private game_service game;
    private static MyFrame frame = new MyFrame();
    private static MyPanel panel;
    private static String img= "C:\\Users\\97252\\OneDrive\\שולחן העבודה\\Ex2OopUpdate-master\\Pictures\\pokemonsAsh";
    private static LoginPanel enter=new LoginPanel();
    private static Arena zone;
    private LinkedList<CL_Pokemon> pok_list = new LinkedList<>();
    private static DWGraph_Algo algo=new DWGraph_Algo();
    private Gson gson = new Gson();


    /**
     *the main method for this class. it builds a frame and calls the game's client.
     */
    public static void main(String[] args)  throws JSONException {
        client= new Thread(new Ex2());
        frame.setSize(1000, 700);
        if(args.length==0){
            frame.add(enter);
            frame.setVisible(true);
        }
        else {
            loginID=Integer.parseInt(args[0]);
            level=Integer.parseInt(args[1]);
            client.start();
        }
    }

    /**
     *The run method- uses, just like its name, to run a game. it calls all the needed functions
     * for that
     */
    @Override
    public void run() {
        frame.remove(enter);
        frame.setTitle("Catch 'Em All");
        game = Game_Server_Ex2.getServer(level); // you have [0,23] games
        game.login(loginID);
        init(game);
        game.startGame();
        algo.init(zone.getGraph());
        game.startGame();
        boolean ate=false;
        while (game.isRunning()&&!ate){
            firstCatch(game);
            try {
                panel.repaint();
                Thread.sleep(150);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            ate= zone.everyAgentAlreadyAte();
        }
        while (game.isRunning()){
            pokemonsCatch(game);
            try {
                panel.repaint();
                if(zone.getAgents().size()==1)
                    Thread.sleep(110);
                else
                    Thread.sleep(310*zone.avgWeight()/zone.avgSpeed());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        game.stopGame();
    }

    /**
     * Init the game: the method gets data from the server, and updates the zone:
     * the graph, agents, pokemons. Every agent is located on the edge's src node
     * that his destined pokemon is on.
     */
    private void init(game_service game) {
        zone=new Arena(game);
        zone.updatePoks2Arena(game);
        pok_list=zone.getPokemons();
        JsonObject GameServer = gson.fromJson(game.toString(), JsonObject.class);
        int numAgents = GameServer.get("GameServer").getAsJsonObject().get("agents").getAsInt();
        pok_list = max2MinValue(pok_list);
        zone.setPokemons(pok_list);
        for (int i = 0; i<numAgents; i++)
            game.addAgent(pok_list.get(i).getSrc());
        zone.updateAgents2Arena(game);
        for (int i=0; i<zone.getAgents().size(); i++)
            zone.getAgents().get(i).setPokemonTarget(pok_list.get(i));
        panel=new MyPanel(zone);
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * In the first catch, every agent catches the pokemon, by order of value:
     * The first one, catches the max valued pokemon.
     */
    private void firstCatch (game_service game){
        for (int i = 0; i < zone.getAgents().size(); i++)
            game.chooseNextEdge(i, zone.getAgents().get(i).getTarget().getDest());
        game.move();
        zone.updatePoks2Arena(game);
        zone.updateAgents2Arena(game);
        for (int i = 0; i < zone.getAgents().size(); i++)
            zone.getAgents().get(i).setPokemonTarget(pok_list.get(i));
    }

    /**
     * The main strategy of the agents, as for catching pokemons:
     * Looks for the closest pokemon for every agent, uses
     * shortestPathDist & shortestPath methods
     * Looks for the pokemon with the closest src node.
     */
    private void pokemonsCatch(game_service game){
        zone.updatePoks2Arena(game);
        zone.updateAgents2Arena(game);
        for (int i=0; i<zone.getAgents().size(); i++){
            double dist=Double.POSITIVE_INFINITY;
            int inexOfTarget=0;
            for (int k=0; k<zone.getPokemons().size(); k++){
                if (algo.shortestPathDist(zone.getAgents().get(i).getSrc(), zone.getPokemons().get(k).getSrc())<dist){
                    dist=algo.shortestPathDist(zone.getAgents().get(i).getSrc(), zone.getPokemons().get(k).getSrc());
                    inexOfTarget=k;
                }
            }
            zone.getAgents().get(i).setPath(algo.shortestPath(zone.getAgents().get(i).getSrc(), zone.getPokemons().get(inexOfTarget).getSrc()));
            zone.getAgents().get(i).setPokemonTarget(zone.getPokemons().get(inexOfTarget));
        }
        for (int i = 0; i<zone.getAgents().size(); i++){
            if (zone.getAgents().get(i).getPath().size()==1)
                game.chooseNextEdge(i, zone.getAgents().get(i).getTarget().getDest());
            else
                game.chooseNextEdge(i, zone.getAgents().get(i).getPath().get(1).getKey());
        }
        game.move();
    }

    /**
     * this method sorts the pokemons list by their value-
     * from the max to the minimun values.
     * this helps the strategy of the agents- to decide which pokemon to go catch first.
     */
        private static LinkedList<CL_Pokemon> max2MinValue (LinkedList < CL_Pokemon > pok_list) {
            LinkedList<CL_Pokemon> list = new LinkedList<>();
            int numPoks = pok_list.size();
            double maxValue = 0;
            int indexOfMax = 0;
            for (int i = 0; i < numPoks; i++) {
                for (int k = 0; k < pok_list.size(); k++) {
                    if (pok_list.get(k).getValue() > maxValue) {
                        maxValue = pok_list.get(k).getValue();
                        indexOfMax = k;
                    }
                }
                list.add(pok_list.get(indexOfMax));
                pok_list.remove(indexOfMax);
                maxValue = 0;
            }
            pok_list = list;
            return pok_list;
        }
}


