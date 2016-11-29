package checkers;
import java.util.*;
import java.lang.Math.*;


/**
 *
 * @author Jamie
 */
public class Checkers {
    private static int [][] nextMove;
   
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
    
    
    public static float miniMax(Node node,int depth){
        if(depth == 0|node.isLeaf() ==true){
            return evaluate(node);
        }
        
        if (node.getPlayer()== Node.minMax.MAX){
            ArrayList <Node> children = getChildren(node);
            for(int i = 0; i < children.size(); i++){
                float eval = miniMax(children.get(i),depth-1);
                node.setBestScore(Math.max(node.getScore(), eval));
                nextMove = node.getState().clone();
            }
            return node.getScore();
        }
        
        if (node.getPlayer() == Node.minMax.MIN){
            ArrayList <Node> children = getChildren(node);
            for(int i = 0; i < children.size(); i++){
                float eval = miniMax(children.get(i),depth-1);
                node.setBestScore(Math.min(node.getScore(), eval));
                nextMove = node.getState().clone();
            }
            return node.getScore();
        }
        
        return 0;
    }
    
    public static ArrayList<Node> getChildren(Node n){
        ArrayList <Node> children = new ArrayList();
        int [][] state = n.getState();
        //loop through board
        for (int i =0; i<8;i++){
            for (int j = 0; j<8; j++){
                //if a black pawn is found
                if(state[i][j]==1){
                    //if the pawn is not on the left or bottom board boundary
                    if(j!=0 && i!=7){
                        // if the space to the bottom left is unoccupied
                        if(state[i+1][j-1]==0){
                            int [][] newState = state.clone();
                            // create a copy of state and move piece to new pos
                            newState[i][j]=0;
                            newState [i+1][j-1] = 1;
                            // if parent node is MAX add MIN child node 
                            if (n.getPlayer()==Node.minMax.MAX){
                                Node child = new Node(Node.minMax.MIN,n);
                                child.setState(newState);
                                children.add(child);
                                
                            }
                            // else add MAX child node
                            /*else{
                                Node child = new Node(Node.minMax.MAX,n);
                                child.setState(newState);
                                children.add(child);
                            }*/
                        }
                    }
                    //if the pawn is not on the right or bottom board boundary
                    if(j!=7 && i!=7){
                        //if the space to the bottom right is unoccupied
                        if(state[i+1][j+1]==0){
                            //create a copy of state and move peice to new pos
                            int [][] newState = state.clone();
                            newState[i][j]=0;
                            newState[i+1][j+1] = 1; 
                            // if parent mode is MAX, add Min child node
                            if(n.getPlayer() == Node.minMax.MAX){
                                Node child = new Node(Node.minMax.MIN,n);
                                child.setState(newState);
                                children.add(child);
                            }
                            /*else{
                                Node child = new Node(Node.minMax.MAX,n);
                                child.setState(newState);
                                children.add(child);
                            }*/
                        }
                    }
                    // If there is room to take an opponent in the bottom left
                    if(j>1 && i<6){
                        // if there is an opponent in the bottom left space
                        if(state[i+1][j-1]==2){
                            // and there is opportunity to take that peice
                            if(state[1+2][j-2]==0){
                                int[][] newState = state.clone();
                                newState[i][j] = 0;
                                newState[i+1][j-1] =0;
                                newState[i+2][j-2] =1;
                                
                                if(n.getPlayer()== Node.minMax.MAX){
                                    Node child = new Node(Node.minMax.MIN,n);
                                    child.setState(newState);
                                    children.add(child);
                                }
                                /*else{
                                    Node child = new Node(Node.minMax.MAX,n);
                                    child.setState(newState);
                                    children.add(child);
                                }*/
                            }
                        }
                    }
                    // If there is room to take an opponent in the bottom right
                    if(j<6 && i<6){
                        // if there is an opponent in the bottom right space
                        if(state[i+1][j+1]==2){
                            // and ther is an opportunity to take that peice
                            if(state[i+2][j+2]==0){
                                int[][] newState = state.clone();
                                newState[i][j] = 0;
                                newState[i+1][j+1]=0;
                                newState[i+2][j+2]=1;
                                
                                if(n.getPlayer() == Node.minMax.MAX){
                                    Node child = new Node(Node.minMax.MIN,n);
                                    child.setState(newState);
                                    children.add(child);
                                }
                                /*else{
                                    Node child = new Node(Node.minMax.MAX,n);
                                    child.setState(newState);
                                    children.add(child);
                                }*/
                            }
                        }
                    }
                }
                //if a white piece is found and AI is MIN
                if (state[i][j]==2 && n.getPlayer()== Node.minMax.MIN){
                    // If the piece is not on the left or upper board boundary
                    if(j>0 && i>0){
                        // if the space to the upper left is empty
                        if(state[i-1][j-1]==0){
                            int[][] newState = state.clone();
                            newState[i][j]=0;
                            newState[i-1][j-1] = 2;                            
                            
                            Node child = new Node(Node.minMax.MAX,n);
                            child.setState(newState);
                            children.add(child);
                            
                        }
                    }
                    // if the peice is not on the right or upper board boundary
                    if(j<7 &&  i>0){
                        //if the space to the upper right is empty
                        if(state[i-1][j+1]==0){
                            int[][] newState = state.clone();
                            newState[i][j] = 0;
                            newState[i-1][j+1] = 2;                            
   
                            Node child = new Node(Node.minMax.MAX,n);
                            child.setState(newState);
                            children.add(child);
                            
                        }
                    }
                    
                    // if there room to take a peice to the upper left
                    if(i>1 && j >1){
                        // if there is an opponent to the upper left
                        if(state[i-1][j-1]==1){
                            // and there is opportunity to take it
                            if (state[i-2][j-2]==0){
                                int[][] newState = state.clone();
                                newState[i][j]=0;
                                newState[i-1][j-1]=0;
                                newState[i-2][j-2] = 2;
                                
                                Node child = new Node(Node.minMax.MAX,n);
                                child.setState(newState);
                                children.add(child);
                            }
                        }
                    }
                    // if there is room to take a peice to the upper right
                    if (i>1 && j <6){
                        // if there is an opponent in the upper right
                        if (state[i-1][j+1] ==1){
                            // and there is opportunity to take it
                            if(state[i-2][j+2]==0){
                                int [][] newState = state.clone();
                                newState[i][j] = 0;
                                newState[i-1][j+1] = 0;
                                newState[1-2][j-2] = 2;
                                
                                Node child = new Node(Node.minMax.MAX,n);
                                child.setState(newState);
                                children.add(child);
                            }
                        }
                    }
                }

            }
        }
        
        // Get each child node and set isLeaf
        
        return children;
       
    }
    
    public static void startGame(Board board){
        while(!board.isGameOver()){
            if(board.getPlayerTeam()==board.getCurrentTurn()){
                //Process Player Turn
                
                //Update game state
                
                if(board.isGameOver()){
                    break;
                }
            }
            else{
                
                //Copy current state of board to new 2D array
                int [][] copy = board.getState().clone();
                
                Node node = null;
                
                // Instatiate new Node
                if(board.getPlayerTeam()== Board.Team.BLACK){
                    node = new Node(Node.minMax.MAX,null);
                }
                else{
                    node = new Node(Node.minMax.MIN, null);
                }
                
                // Pass node to Minimax
                miniMax(node,4);
                
                
                // Make move and update board
                board.setState(nextMove);

                // Check game over
                if(board.isGameOver()){
                    break;
                }
                
                // Change turn
                if(board.getCurrentTurn()== Board.Team.BLACK){
                    board.setTurn(Board.Team.WHITE);
                }
                else{
                    board.setTurn(Board.Team.BLACK);
                }
                
                
                
            }
        }
            
        
    }
    
    private static float evaluate(Node node){
        float eval = (float) 0;
        int [][] state = node.getState();
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                if(state[i][j]%2 != 0){
                    eval++;
                }
                if(state[i][j]==2|state[i][j]==4){
                    eval--;
                }
            }
        }        
        return eval/50;
    }
}
