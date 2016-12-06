package checkers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jamie
 */
public class Node {
    //instance variables
    private float bestScore;
    public enum minMax {MIN,MAX};
    private minMax player;
    private Node parent;
    private int[][] state;
    
    //----- Methods-----//
    
    //Constructor
    public Node(minMax player, Node parent){
        this.player = player;
        this.parent = parent;
        if (player == minMax.MAX){
            bestScore = Float.MIN_VALUE;
        }
        else{
            bestScore = Float.MAX_VALUE;
        }
    }
    
    //Mutators
    
    public void setState(int[][] state){
        this.state = state;
    }
    
    public void setBestScore(float bestScore){
        this.bestScore = bestScore;
    }
    
    //Accessors
    public minMax getPlayer(){
        return player;
    }
    
    public float getScore(){
        return bestScore;
    }
    
    public Node getParent(){
        return parent;
    }
    
    public int[][] getState(){
        return state;
    }
    
   
}
