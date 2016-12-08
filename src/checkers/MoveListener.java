/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Jamie
 */
public class MoveListener implements MouseListener {
    
    int[] location;
    int[][] state;
    int piece;
    JPanel[][] panels;
    
    MoveListener(int[] location, int[][] state,int piece, JPanel[][] panels){
        this.location = location;
        this.state = state;
        this.piece = piece;
        this.panels = panels;
    }
    
    @Override
    public void mouseExited(MouseEvent e){
        // When black pawn no longer hovered over. Return the JPanel to black.
        if((location[0]!=7&&location[1]!=7)&&state[location[0]+1][location[1]+1]==0&&piece ==1){
            panels[location[0]+1][location[1]+1].setBackground(Color.black);
        }
        if((location[0]!=7&&location[1]!=0&&state[location[0]+1][location[1]-1]==0&&piece ==1)){
            panels[location[0]+1][location[1]-1].setBackground(Color.black);
        }
        // When white pawn no longer hovered over. Return the JPanel to black.
        if((location[0]!=0&&location[1]!=7)&&state[location[0]-1][location[1]+1]==0&&piece ==2){
            panels[location[0]-1][location[1]+1].setBackground(Color.black);
        }
        if((location[0]!=0&&location[1]!=0)&&state[location[0]-1][location[1]-1]==0&&piece ==2){
            panels[location[0]-1][location[1]-1].setBackground(Color.black);
        }
    }
    
    @Override 
    public void mouseEntered(MouseEvent e){
        // If black pawn is hovered over. Highlight next possible moves.
        if((location[0]!=7&&location[1]!=7)&&state[location[0]+1][location[1]+1]==0&&piece ==1){
            panels[location[0]+1][location[1]+1].setBackground(Color.blue);
        }
        if((location[0]!=7&&location[1]!=0)&&state[location[0]+1][location[1]-1]==0&&piece ==1){
            panels[location[0]+1][location[1]-1].setBackground(Color.blue);
        }
        
        // if white pawn is hovered over. Highlight possible moves.
        if((location[0]!=0&&location[1]!=7)&&state[location[0]-1][location[1]+1]==0&&piece ==2){
            panels[location[0]-1][location[1]+1].setBackground(Color.blue);
        }
        if((location[0]!=0&&location[1]!=0)&&state[location[0]-1][location[1]-1]==0&&piece ==2){
            panels[location[0]-1][location[1]-1].setBackground(Color.blue);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }
}
