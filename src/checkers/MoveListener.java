/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jamie
 */
public class MoveListener implements MouseListener {
    
    int[] loc;
    int[][] state;
    int piece;
    JPanel[][] panels;
    Board board;
    
    MoveListener(int[] location, int[][] state,int piece, JPanel[][] panels, Board board){
        this.loc = location;
        this.state = Checkers.clone2D(state);
        this.piece = piece;
        this.panels = panels;
        this.board = board;
    }
    
    @Override
    public void mouseExited(MouseEvent e){
        // When black pawn no longer hovered over. Return the JPanel to black.
        if((loc[0]!=7&&loc[1]!=7)&&state[loc[0]+1][loc[1]+1]==0&&piece ==1){
            panels[loc[0]+1][loc[1]+1].setBackground(Color.black);
        }
        if((loc[0]!=7&&loc[1]!=0&&state[loc[0]+1][loc[1]-1]==0&&piece ==1)){
            panels[loc[0]+1][loc[1]-1].setBackground(Color.black);
        }
        // When white pawn no longer hovered over. Return the JPanel to black.
        if((loc[0]!=0&&loc[1]!=7)&&state[loc[0]-1][loc[1]+1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]+1].setBackground(Color.black);
        }
        if((loc[0]!=0&&loc[1]!=0)&&state[loc[0]-1][loc[1]-1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]-1].setBackground(Color.black);
        }
    }
    
    @Override 
    public void mouseEntered(MouseEvent e){
        // If black pawn is hovered over. Highlight next possible moves.
        if((loc[0]!=7&&loc[1]!=7)&&state[loc[0]+1][loc[1]+1]==0&&piece ==1){
            panels[loc[0]+1][loc[1]+1].setBackground(Color.blue);
        }
        if((loc[0]!=7&&loc[1]!=0)&&state[loc[0]+1][loc[1]-1]==0&&piece ==1){
            panels[loc[0]+1][loc[1]-1].setBackground(Color.blue);
        }
        
        // if white pawn is hovered over. Highlight possible moves.
        if((loc[0]!=0&&loc[1]!=7)&&state[loc[0]-1][loc[1]+1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]+1].setBackground(Color.blue);
        }
        if((loc[0]!=0&&loc[1]!=0)&&state[loc[0]-1][loc[1]-1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]-1].setBackground(Color.blue);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        // If black pawn clicked and down and right move available
        if((loc[0]!=7&&loc[1]!=7)&&state[loc[0]+1][loc[1]+1]==0&&piece ==1){
            panels[loc[0]+1][loc[1]+1].addMouseListener(new SecondListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
                    state[loc[0]+1][loc[1]+1] = 1;
                    state[loc[0]][loc[1]]=0;
                    if(loc[0]+1==7){
                        state[loc[0]+1][loc[1]+1] = 3;
                    }
                    board.setState(Checkers.clone2D(state));
                    board.updateGUI();
                    board.setTurn(Board.Team.WHITE);
                    }
                });
        }
        // If black pawn clicked and down and left move available
        if((loc[0]!=7&&loc[1]!=0)&&state[loc[0]+1][loc[1]-1]==0&&piece ==1){
            panels[loc[0]+1][loc[1]-1].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    state[loc[0]+1][loc[1]-1] = 1;
                    state[loc[0]][loc[1]]=0;
                    if(loc[0]+1==7){
                        state[loc[0]+1][loc[1]-1] = 3;
                    }
                    board.setState(Checkers.clone2D(state));
                    board.updateGUI();
                    board.setTurn(Board.Team.WHITE);
                    }
            });
        }
        
        // If a black pawn clicked and right take available
        if((loc[0]<6&&loc[1]<6)&&(state[loc[0]+1][loc[1]+1]==2|state[loc[0]+1][loc[1]+1]==4)&&state[loc[0]+2][loc[1]+2]==0&&piece ==1){
            panels[loc[0]+2][loc[1]+2].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                //upadate game state
                state[loc[0]][loc[1]]=0; // set original position to 0
                state[loc[0]+2][loc[1]+2]=1; // set new position to 1
                state[loc[0]+1][loc[1]+1]=0; // set jumped position to 0      
                if(loc[0]+2==7){
                    state[loc[0]+2][loc[1]+2] = 3;
                }    
                board.setState(Checkers.clone2D(state));
                board.updateGUI();
                board.setTurn(Board.Team.WHITE);
                }
            });
        }
        // If a black pawn clicked and left take available
        if((loc[0]<6&&loc[1]>1)&&(state[loc[0]+1][loc[1]-1]==2|state[loc[0]+1][loc[1]-1]==4)&&state[loc[0]+2][loc[1]-2]==0&&piece ==1){
            panels[loc[0]+2][loc[1]-2].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                //upadate game state
                state[loc[0]][loc[1]]=0; // set original position to 0
                state[loc[0]+2][loc[1]-2]=1; // set new position to 1
                state[loc[0]+1][loc[1]-1] = 0; // set jumped position to 0      
                if(loc[0]+2==7){
                    state[loc[0]+2][loc[1]-2] = 3;
                }                    
                board.setState(Checkers.clone2D(state));
                board.updateGUI();
                board.setTurn(Board.Team.WHITE);
                }
            });
        }
        
        
        
        
        
        // If white pawn clicked and up and right move available
        if((loc[0]!=0&&loc[1]!=7)&&state[loc[0]-1][loc[1]+1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]+1].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    state[loc[0]-1][loc[1]+1] = 2;
                    state[loc[0]][loc[1]]=0;                  
                    board.setState(Checkers.clone2D(state));
                    board.updateGUI();             
                    board.setTurn(Board.Team.BLACK);                    
                    }
            });
        }     
        
        // If white pawn clicked and up and left move avaliable
        if((loc[0]!=0&&loc[1]!=0)&&state[loc[0]-1][loc[1]-1]==0&&piece ==2){
            panels[loc[0]-1][loc[1]-1].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    state[loc[0]-1][loc[1]-1] = 2;
                    state[loc[0]][loc[1]]=0;
                    board.setState(Checkers.clone2D(state));
                    board.updateGUI();
                    board.setTurn(Board.Team.BLACK);                    
                    }
            });
        }
        
        // if white pawn clicked and right take available
        if((loc[0]>1&&loc[1]<6)&&(state[loc[0]-1][loc[1]+1]==1|state[loc[0]-1][loc[1]+1]==3)&&state[loc[0]-2][loc[1]+2]==0&&piece ==2){
            panels[loc[0]-2][loc[1]+2].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                //upadate game state
                state[loc[0]][loc[1]]=0; // set original position to 0
                state[loc[0]-2][loc[1]+2]=2; // set new position to 1
                state[loc[0]-1][loc[1]+1] = 0; // set jumped position to 0      
                    
                board.setState(Checkers.clone2D(state));
                board.updateGUI();
                board.setTurn(Board.Team.BLACK);
                }
            });
        }
        // if white pawn clicked and left take available
        if((loc[0]>1&&loc[1]>1)&&(state[loc[0]-1][loc[1]-1]==1|state[loc[0]-1][loc[1]-1]==3)&&state[loc[0]-2][loc[1]-2]==0&&piece ==2){
            panels[loc[0]-2][loc[1]-2].addMouseListener(new SecondListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                //upadate game state
                state[loc[0]][loc[1]]=0; // set original position to 0
                state[loc[0]-2][loc[1]-2]=2; // set new position to 1
                state[loc[0]-1][loc[1]-1] = 0; // set jumped position to 0      
                    
                board.setState(Checkers.clone2D(state));
                board.updateGUI();
                board.setTurn(Board.Team.BLACK);
                }
            });
        }
    }    

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    
    
    
    private class SecondListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
 
        }
      
    }
}
