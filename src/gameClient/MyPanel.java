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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class MyPanel extends JPanel implements MouseListener {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private JLabel stage, time, grade, moves;


    public MyPanel(Arena _ar){
        super();
        this.setLayout(null);
        this._ar = _ar;
        stage=new JLabel("stage: "+this._ar.getLevel());
        this.add(stage);
        grade=new JLabel("score: "+this._ar.getGrade());
        this.add(grade);
        moves=new JLabel("moves "+this._ar.getSteps());
        this.add(moves);
        time=new JLabel("time to end: "+this._ar.getGame().timeToEnd()/1000);
        this.add(time);
    }

    @Override
    public void paintComponent(Graphics g){
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        this.updateFrame();
        drawGraph(g);
        drawPokemons(g);
        drawAgants(g);
        this.info();
    }
    private void updateFrame() {
        Range rx = new Range(30,this.getWidth()-30);
        Range ry = new Range(this.getHeight()-30,60);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }

    private void info(){
        int w = this.getWidth();
        int h = this.getHeight();
        stage.setText("stage: "+this._ar.getLevel());
        stage.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        stage.setBounds(w-300, 2, 200, 50);
        grade.setText("score: "+this._ar.getGrade());
        grade.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        grade.setBounds(95, 2, 200, 50);
        moves.setText("moves "+this._ar.getSteps());
        moves.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        moves.setBounds(200, 2, 200, 50);
        time.setText("time to end: "+this._ar.getGame().timeToEnd()/1000);
        time.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        time.setBounds(400, 2, 200, 50);
    }

    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while(iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.blue);
            drawNode(n,5,g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while(itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }
    private void drawPokemons(Graphics g) {
        List<CL_Pokemon> fs = _ar.getPokemons();
        if(fs!=null) {
            Iterator<CL_Pokemon> itr = fs.iterator();

            while(itr.hasNext()) {
                CL_Pokemon f = itr.next();
                Point3D c = f.getLocation();
                int r=10;
                g.setColor(Color.green);
                if(f.getType()<0) {g.setColor(Color.orange);}
                if(c!=null) {
                    geo_location fp = this._w2f.world2frame(c);
                    g.fillOval((int)fp.x()-r-5, (int)fp.y()-r-5, 2*r, 2*r);
                }
            }
        }
    }
    private void drawAgants(Graphics g) {
        List<CL_Agent> rs = _ar.getAgents();
        //	Iterator<OOP_Point3D> itr = rs.iterator();
        g.setColor(Color.red);
        int i=0;
        while(rs!=null && i<rs.size()) {
            geo_location c = rs.get(i).getLocation();
            int r=8;
            i++;
            if(c!=null) {

                geo_location fp = this._w2f.world2frame(c);
                g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
            }
        }
    }
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    }
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        if(gg.getNode(e.getSrc())!=null && gg.getNode(e.getDest())!=null){
            geo_location s = gg.getNode(e.getSrc()).getLocation();
            geo_location d = gg.getNode(e.getDest()).getLocation();
            geo_location s0 = this._w2f.world2frame(s);
            geo_location d0 = this._w2f.world2frame(d);
            g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
        }
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
