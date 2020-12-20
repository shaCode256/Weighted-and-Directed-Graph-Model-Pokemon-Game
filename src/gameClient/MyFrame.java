package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * This class is the game's GUI's frame class.
 */
public class MyFrame extends JFrame{
    private int _ind;
    private Arena _ar;
    private MyPanel panel;
    private gameClient.util.Range2Range _w2f;

    /**
     * This function configures the frame.
     */
    public MyFrame(){
        this.setTitle("Catch 'Em All");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
