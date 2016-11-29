/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import checkers.Node;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamie
 */
public class LogicTest {
    Node testNodeMin = new Node(Node.minMax.MIN,null);
    Node testNodeMax = new Node(Node.minMax.MAX,null);
    public LogicTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void testNodePlayer(){
        assertEquals(testNodeMin.getPlayer(), Node.minMax.MIN);
        assertEquals(testNodeMax.getPlayer(), Node.minMax.MAX);
    }
    
    @Test
    public void testScore(){
        assertEquals(testNodeMin.getScore(), Float.MAX_VALUE, 0.1);
        assertEquals(testNodeMax.getScore(), Float.MIN_VALUE, 0.1);
    }
    @Test
    public void testParent(){
        assertEquals(testNodeMin.getParent(),null);
    }
}
