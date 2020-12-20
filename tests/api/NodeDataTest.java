package api;

import api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeDataTest {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    List<node_data> nodes= new ArrayList<>();
    long start = new Date().getTime();

    /**
     *     Executed before each test. It is used to prepare the test environment
     *     (e.g., read input data, initialize the class).
     */

    @BeforeEach

    void setUp() {
        start = new Date().getTime();
        System.out.println(ANSI_GREEN+"Setting up the environment. creating the nodes..."+ANSI_RESET);
        double di;
        Integer obj=0;
        String si=null;
        node_data node=null;
        for(int i=0;i<20; i++){ //create a new array of nodes, with new nodes
            node= new NodeData();
            nodes.add(node);
        }
        for(int i=0;i<20; i++){ //initializing the nodes with tags and infos.
            di= i;
            obj = i;
            si = obj.toString();
            nodes.get(i).setTag((int) (di+0.5));
            nodes.get(i).setInfo(si+"+0.5");
        }

//        public list<node_data> getNodes(){
//            return nodes;
//        }
    }

    /**
     * Executed after each test. It is used to cleanup the test environment
     *         (e.g., delete temporary data, restore defaults). It can also save memory by cleaning up
     *          expensive memory structures.
     */
    @AfterEach

    /**
     * This function returns the time that this test took
     */
    void tearDown() {
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        System.out.println("This test took "+dt+" seconds");

    }

    /**
     * This function checks if the keys getting of the nodes is ok
     */
    @Test
    void getKey() {
        System.out.println(ANSI_GREEN+"Checking if keys of nodes are ok..."+ANSI_RESET);
        for(int i=0; i<20; i++) {
            assertEquals(nodes.get(i).getKey(), i);
        }
    }

    /**
     * This function checks if the info getting of the nodes is ok
     */
    @Test
    void getInfo() {
        System.out.println(ANSI_GREEN+"Checking if getting infos of nodes is ok..."+ANSI_RESET);
        Integer obj=0;
        String si=null;
        String strcomp=null;
        for(int i=0; i<20; i++) {
            obj = i;
            si = obj.toString();
            strcomp=si+"+0.5";
            assertEquals(nodes.get(i).getInfo(), strcomp);
        }

    }

    /**
     * This function checks if the info setting of the nodes is ok
     */
    @Test
    void setInfo() {
        System.out.println(ANSI_GREEN+"Checking if setting infos of nodes is ok..."+ANSI_RESET);
        Integer obj=0;
        String si=null;
        String strcomp=null;
        for(int i=0; i<20; i++) {
            obj = i;
            si = obj.toString();
            strcomp=si+"+0.5";
            nodes.get(i).setInfo(strcomp);
            assertEquals(nodes.get(i).getInfo(), strcomp);
        }
    }

    /**
     * This function checks if the tag getting of the nodes is ok
     */
    @Test
    void getTag() { //check the get tags, than change some nodes tags, to see if it messes up anything.
        System.out.println(ANSI_GREEN+"Checking if getting tags of nodes is ok..."+ANSI_RESET);
        double di;
        for(int i=0; i<20; i++) {
            di= i;
            assertEquals(nodes.get(i).getTag(),(int)(di+0.5));
        }
//        nodes.remove(3);
//        nodes.remove(5);
//        nodes.remove(7);
//        nodes.remove(10);
        //Removes the element at the specified position in this list (optional operation).
        // Shifts any subsequent elements to the left (subtracts one from their indices).
        // Returns the element that was removed from the list.-- messes up our test,
        // according to this list data structure feature. so we won't use it.
        //instead, we'll try to change some nodes tags, to see it THIS messes up sth, as THIS is a problem.
        nodes.get(3).setTag(300);
        nodes.get(5).setTag(301);
        nodes.get(7).setTag(302);
        nodes.get(10).setTag(303);

        for(int i=0; i<20; i++) {
            //      System.out.println(nodes.get(i).getKey());
            if( i!=3&& i!=5&& i!=7&& i!=10) {
                di = i;
                assertEquals(nodes.get(i).getTag(), (int)(di + 0.5));
            }
        }
    }

    /**
     * This function checks if the tag setting of the nodes is ok
     */
    @Test
    void setTag() {
        System.out.println(ANSI_GREEN+"Checking if setting tags of nodes is ok..."+ANSI_RESET);
        double di;
        for(int i=0; i<20; i++) {
            di= i;
            nodes.get(i).setTag((int) (di+0.5));
        }
    }
}