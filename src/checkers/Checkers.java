package checkers;
import java.util.*;
import java.lang.Math.*;
import java.util.Scanner;


/**
 *
 * CURRENT STATE: 
 * Taking player moves via command line.
 * Player moves working (CARE: NO LEGAL MOVE VALIDATION!!)
 * Appears to be working, tested up to a depth of 4. Currently has no end game
 * mechanics but these can be added.
 * Some work carried out on GUI so that board now represents game state 
 * accurately.
 * 
 * TODO (Not in priority order).
 * 
 * Player moves need processing in GUI. Mouse listeners need to be set up.
 * 
 * Heuristic needs improving as currently very basic.
 *  ----IDEA: How many blocking King crowning spaces as heuristic. 
 *  * 
 * Double Jump needs thinking about 
 * 
 * GUI needs finishing and tying in to logic.(Scaling for different resolutions)
 * 
 * Difficulty setting.
 * 
 * Alpha - Beta Pruning 
 * 
 * JavaDoc.
 * 
 * More Unit tests (where possible).
 * 
 * Report.
 * 
 * EXTENSIONS:
 * 
 * Intro to game, including rule set.
 * 
 * Help button incase player is stuck.
 * 
 * Back button (simple stack containing past game states).
 * 
 * Graphics option (this is unlikely given time constraints).
 * 
 * 
 * @author Jamie
 */
public class Checkers {
    private static int [][] nextMove;
   
    public Checkers(){}
    
    public static void main(String[] args) {
        
        startGame();
        
    }
       
    public static float miniMax(Node node,int depth){
        
        //get children first as if no more moves are found then node is leaf.
        ArrayList <Move> children = getChildren(node); // Appears to be functioning as expected
        if(depth == 0|children.isEmpty()){
            //nextMove = clone2D(node.getState());
            node.setBestScore(evaluate(node));
            return node.getScore();
        }
        
        if (node.getPlayer()== Node.minMax.MAX){
            float bestScore = Float.MIN_VALUE;
            for(int i = 0; i < children.size(); i++){

                int y = children.get(i).origPos[0];
                int x = children.get(i).origPos[1];
                int newY = children.get(i).newPos[0];
                int newX = children.get(i).newPos[1];
                
                int[][] state = clone2D(node.getState());
                
                // Make original space empty
                state[y][x]=0;
                // Put piece in new space
                state[newY][newX]= children.get(i).piece;
                
                
                // if piece taken, remove it from board
                // x = j, y = i. Y only needed for kings
                if(Math.abs(x-newX)==2|Math.abs(y-newY)==2){
                        if(children.get(i).piece==1){
                            if(x>newX){
                                state[y+1][x-1] = 0;
                            }
                            if(x<newX){
                                state[y+1][x+1]=0;
                            }
                        }
                        else{
                            state[y+1][x+1] =0;
                        }
                        if(children.get(i).piece==3){
                            if(y>newY&&x>newX){ //up and left
                                state[y-1][x-1]=0;
                            }
                            if(y>newY&&x<newX){ // up and right
                                state[y-1][x+1]=0;
                            }
                            if(y<newY&&x>newX){ // down and left
                                state[y+1][x-1]=0;
                            }
                            if(y<newY&&x<newX){
                                state[y+1][x+1]=0;
                            }
                        } 
                }                
                // if end of board is reached. Replace pawn with a king.
                if(newY==7 && children.get(i).piece ==1){
                    state[newY][newX]=3;
                }
                
                
                // Create new MIN node
                Node next = new Node(Node.minMax.MIN,node);
                // Pass updated state to new node.
                next.setState(clone2D(state));
                // Recursive Call to minimax
                float eval = miniMax(next,depth-1);
                bestScore = Math.max(eval, bestScore);
                if (bestScore == eval){
                    nextMove = next.getState();
                }
                
            }
            return bestScore;
        }
        
        if (node.getPlayer() == Node.minMax.MIN){
            for(int i = 0; i < children.size(); i++){
                float bestScore = Float.MAX_VALUE;
                int y = children.get(i).origPos[0];
                int x = children.get(i).origPos[1];
                int newY = children.get(i).newPos[0];
                int newX = children.get(i).newPos[1];
                // Get state of current node and clone
                
                int[][] state = clone2D(node.getState());               

                // Make original space empty
                state[y][x]=0;
                // Put peice in new space
                state[newY][newX]= children.get(i).piece;
                // if piece taken, remove it from board
                // x = j, y = i. Y only needed for kings
                if(Math.abs(x-newX)==2|Math.abs(y-newY)==2){
                        if(children.get(i).piece==2){
                            if(x>newX){
                                state[y-1][x-1] = 0;
                            }
                            if(x<newX){
                                state[y-1][x+1]=0;
                            }
                        }
                        else{
                            state[y+1][x+1] =0;
                        }
                        if(children.get(i).piece==4){
                            if(y>newY&&x>newX){ //up and left
                                state[y-1][x-1]=0;
                            }
                            if(y>newY&&x<newX){ // up and right
                                state[y-1][x+1]=0;
                            }
                            if(y<newY&&x>newX){ // down and left
                                state[y+1][x-1]=0;
                            }
                            if(y<newY&&x<newX){
                                state[y+1][x+1]=0;
                            }
                        } 
                }
                // if end of board is reached. Replace pawn with a king.
                if(newY==0 && children.get(i).piece ==2){
                    state[newY][newX]=4;
                }                
                
                
                // Create new max mode
                Node next = new Node(Node.minMax.MAX,node);
                next.setState(clone2D(state));
                
                
                
                
                float eval = miniMax(next,depth-1);
                bestScore = Math.min(eval, bestScore);
                if(bestScore == eval){
                    nextMove = next.getState();
                }
            }
            return node.getScore();
        }
        
        return 0;
    }
    
    public static ArrayList<Move> getChildren (Node node){
        ArrayList <Move> children = new ArrayList();
        int [][] state = node.getState();
        
        for(int i = 0; i <8; i++){
            for (int j = 0; j < 8; j++){
                // If a black pawn or king is found and it is black's turn
                if((state[i][j]==1|state[i][j]==3) && node.getPlayer()== Node.minMax.MAX){
                    
                    // If the piece is not on the left or bottom board boundary
                    // and there is an empty space to the lower left
                    if((j>0 && i<7)&&(state[i+1][j-1]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+1, j-1}, state[i][j]));
                    }
                    // If the piece is not on the right or bottom boundary
                    // and there is an empty space to the lower right
                    if((j<7 && i<7)&&(state[i+1][j+1]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+1, j+1},state[i][j]));
                    }
                    // If there is room to make a take to the lower left
                    // and there is an opponent that can be taken in this space
                    // and a take is possible
                    if((j>1 && i <6)&&(state[i+1][j-1]==2|state[i+1][j-1]==4)&&(state[i+2][j-2]== 0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+2, j-2},state[i][j]));                        
                    }
                    // If there is room to make a take to the lower right
                    // and there is an opponent that can be taken in this space
                    // and a take is possible
                    if((j<6 && i <6)&&(state[i+1][j+1]==2|state[i+1][j+1]==4)&&(state[i+2][j+2]== 0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+2, j+2},state[i][j]));
                    }
                    
                    // Moves exclusive to king
                    if(state[i][j]==3){
                        // If the piece is not on the left or upper board boundary
                        // and there is an empty spaceto the upper left
                        if((j!=0 && i!=0)&&(state[i-1][j-1]==0)){
                            children.add(new Move(new int[]{i,j}, new int[]{i-1, j-1},state[i][j]));                        
                        }
                        // If the piece is not on the right or upper board boundary
                        // and there is an empty space to the upper right
                        if((j!=7&&i!=0)&&(state[i-1][j+1]==0)){
                            children.add(new Move(new int[]{i,j}, new int[]{i-1, j+1},state[i][j]));
                        }
                        // If there is room to make a take on the upper left
                        // and thre is an opponent that can be taken in this space
                        // and a take is possible
                        if((i>1 && j>1)&&(state[i-1][j-1]==2|state[i-1][j-1]==4)&&(state[i-2][j-2]==0)){
                            children.add(new Move(new int[]{i,j}, new int[]{i-2, j-2},state[i][j]));
                        }
                        // If there is room to make a take on the upper right
                        // and there is an opponent that can be taken in this space
                        // and a take is possible
                        if((i>1 && j < 6)&&(state[i-1][j+1]==2|state[i-1][j+1]==4)&&(state[i-2][j+2]==0)){
                            children.add(new Move(new int[]{i,j}, new int[]{i-2, j+2},state[i][j]));
                        }
                    }
                    
                    
                }
                
                // If a white pawn is found and it is white's turn
                if((state[i][j]==2|state[i][j]==4) && node.getPlayer()==Node.minMax.MIN){
                    
                    
                    // If the piece is not on the left or upper board boundary
                    // and there is an empty spaceto the upper left
                    if((j!=0 && i!=0)&&(state[i-1][j-1]==0)){
                        
                        children.add(new Move(new int[]{i,j}, new int[]{i-1, j-1},2));                        
                    }
                    // If the piece is not on the right or upper board boundary
                    // and there is an empty space to the upper right
                    if((j!=7&&i!=0)&&(state[i-1][j+1]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i-1, j+1},2));
                    }
                    // If there is room to make a take on the upper left
                    // and thre is an opponent that can be taken in this space
                    // and a take is possible
                    if((i>1 && j>1)&&(state[i-1][j-1]==1|state[i-1][j-1]==3)&&(state[i-2][j-2]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i-2, j-2},2));
                    }
                    // If there is room to make a take on the upper right
                    // and there is an opponent that can be taken in this space
                    // and a take is possible
                    if((i>1 && j < 6)&&(state[i-1][j+1]==1|state[i-1][j+1]==3)&&(state[i-2][j+2]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i-2, j+2},2));
                    }
                    
                    // Moves exclusive to king
                    if (state[i][j]==4){
                    // If the piece is not on the left or bottom board boundary
                    // and there is an empty space to the lower left
                    if((j>0 && i<7)&&(state[i+1][j-1]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+1, j-1}, state[i][j]));
                    }
                    // If the piece is not on the right or bottom boundary
                    // and there is an empty space to the lower right
                    if((j<7 && i<7)&&(state[i+1][j+1]==0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+1, j+1},state[i][j]));
                    }
                    // If there is room to make a take to the lower left
                    // and there is an opponent that can be taken in this space
                    // and a take is possible
                    if((j>1 && i <6)&&(state[i+1][j-1]==1|state[i+1][j-1]==3)&&(state[i+2][j-2]== 0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+2, j-2},state[i][j]));                        
                    }
                    // If there is room to make a take to the lower right
                    // and there is an opponent that can be taken in this space
                    // and a take is possible
                    if((j<6 && i <6)&&(state[i+1][j+1]==1|state[i+1][j+1]==3)&&(state[i+2][j+2]== 0)){
                        children.add(new Move(new int[]{i,j}, new int[]{i+2, j+2},state[i][j]));
                    }                        
                    }
                    
                }
            }
        }
        
        
        return  children;
    }
       
    
    public static void startGame(){
        Board board = new Board(); // set up new game
        nextMove = clone2D(board.getState());
        Scanner keyboard = new Scanner(System.in);
        
        while(board.getPlayerTeam()==null){
            // For some reason loop won't break unless this line is executed?
            System.out.println(board.getPlayerTeam());
        }

        while(!board.isGameOver()){
            if(board.getPlayerTeam().equals(board.getCurrentTurn())){
                
                System.out.println("Player Turn");
                
                //Process Player Turn
                printBoard(board.getState());

                
                System.out.println("Please input location of pawn");
                int x = keyboard.nextInt();
                int y = keyboard.nextInt();
                System.out.println("Please input space to move to");
                int nX = keyboard.nextInt();
                int nY = keyboard.nextInt();

                
                //Update game state
                int [][] state = clone2D(board.getState());
                state[y][x] = 0;
                if(board.getPlayerTeam() == Board.Team.WHITE){
                    state[nY][nX] = 2;
                }
                else{
                    state[nY][nX] = 1;
                }
                // Will only work for black with no kings
                if(Math.abs(x-nX)>1|Math.abs(y-nY)>1){
                    if(x>nX){
                        state[y+1][x-1] = 0;
                    }
                    else{
                        state[y+1][x+1] = 0;
                    }
                }
                
                board.setState(state);
                
                printBoard(board.getState());
                
                keyboard.next();
                
                //Switch turns
                if(board.getCurrentTurn()== Board.Team.BLACK){
                    board.setTurn(Board.Team.WHITE);
                }
                else{
                    board.setTurn(Board.Team.BLACK);
                }
                
                board.updateGUI();
                
                if(board.isGameOver()){
                    break;
                }
            }
            else{
                System.out.println("CPU Turn");
                //Copy current state of board to new 2D array
                int [][] copy = clone2D(board.getState());
                Node node = null;
                
                // Instatiate new Node
                
                // If AI is playing white...
                if(board.getPlayerTeam()== Board.Team.BLACK){
                    System.out.println("Min node initialised");
                    node = new Node(Node.minMax.MIN,null);
                    node.setState(copy);
                }
                else{
                    System.out.println("Max node initialised");
                    node = new Node(Node.minMax.MAX, null);
                    node.setState(copy);
                }
                
                // Pass node to Minimax
                float score = miniMax(node,4);                
                
                // Make move and update board
                board.setState(clone2D(nextMove));
                System.out.println("One move made");
                
                board.updateGUI();
                
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
            System.out.println("One loop complete");
        }  
    }
    
    private static float evaluate(Node node){
        float eval = (float) 0;
        int whiteCount = 0;
        int blackCount = 0;
        
        
        int [][] state = node.getState();
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                if(state[i][j]==1){
                    eval++;
                    blackCount++;
                }
                if(state[i][j]==2){
                    eval--;
                    whiteCount++;
                }
                if (state[i][j]==3){
                    eval = eval+5;
                    blackCount++;
                }
                if (state[i][j]==4){
                    eval = eval -5;
                    whiteCount++;
                }
            }
        }
        if (whiteCount ==0){
            return 1;
        }
        if (blackCount ==0){
            return -1;
        }
        return eval/50;
    }
    
    private static void printBoard(int[][] board){
        int bc = 0;
        int wc = 0;
        
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                
                System.out.print(board[i][j]+"\t");
                if(board[i][j]==2){
                    wc++;
                }
                if (board[i][j]==1){
                    bc++;
                }
            }
            
            System.out.println("\n");
        }
        System.out.println("\n");
        System.out.println("White count: "+wc+"\nBlack count: "+bc+"\n");
    }
    
    private static int[][] clone2D(int[][] arr){
        int[][] clone = new int[8][8];
        for (int i = 0;i<8;i++){
            clone[i] = arr[i].clone();
        }
        return clone;
    }
    
}
