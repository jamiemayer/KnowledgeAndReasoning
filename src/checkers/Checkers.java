package checkers;
import java.util.*;
import java.lang.Math.*;


/**
 *
 * @author Jamie
 */
public class Checkers {
    
    public Checkers(){}
    
    public static void main(String[] args) {
        Board game = new Board(); // set up new game
        startGame(game);
        
    }
    
    // Checks if the game state is Black Wins
    public boolean blackWins(Node node){
       int[][] state = node.getState();
       for (int i =0; i <8;i++){
           for(int j = 0; j<8;j++){
               if(state[i][j]==2 |state[i][j] ==4){
                   return false;
               }
           }
       }
       return true;
    }

    // Checks if game state is White Wins
    public boolean whiteWins(Node node){
        int [][] state = node.getState();
        for(int i = 0; i <8; i++){
            for(int j = 0; j<8;j++){
                if(state[i][j]==1 | state[i][j]==3){
                    return false;
                }
            }
        }        
        return true;
    }
    
    
    public static float miniMax(Node root,int depth){
        if(depth == 0|root.isLeaf() ==true){
            return root.getScore();
        }
        
        if (root.getPlayer()== Node.minMax.MAX){
            float bestVal = Float.NEGATIVE_INFINITY;
            Node next = null;
            ArrayList <Node> children = getChildren(root);
            for(int i = 0; i < children.size(); i++){
                float eval = miniMax(children.get(i),depth-1);
                if(eval > bestVal){
                    bestVal = eval;
                    next = children.get(i);
                }
            }
            return bestVal;
        }
        
        if (root.getPlayer() == Node.minMax.MIN){
            float bestVal = Float.POSITIVE_INFINITY;
            ArrayList <Node> children = getChildren(root);
            for(int i = 0; i < children.size(); i++){
                float eval = miniMax(children.get(i),depth-1);
                bestVal = Math.min(bestVal, eval);
            }
            return bestVal;
        }
        
        return 0;
    }
    
    public static ArrayList<Node> getChildren(Node n){
        ArrayList <Node> children = new ArrayList();
        
        // Get each child node and set the score using setScore() method.
        
        return children;
       
    }
    
    public static void startGame(Board board){
        while(!board.isGameOver()){
            if(board.getPlayerTeam()==board.getCurrentTurn()){
                //Process Player Turn
                
                if(board.isGameOver()){
                    break;
                }
            }
            else{
                
                //Copy current state of board to new 2D arrayList
                int [][] copy = board.getState().clone();
                
                // Instatiate new Node
                if(board.getPlayerTeam()== Board.Team.BLACK){
                    Node node = new Node(Node.minMax.MAX,null);
                }
                else{
                    Node node = new Node(Node.minMax.MIN, null);
                }
                
                // Pass node to Minimax
                
                
                
                // Make move and update board
            }
        }
            
        
    }
    
    public static int[][] copyState(Board board){
        int [][] state = null;
        return state;
    }
}
