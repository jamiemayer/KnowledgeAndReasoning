/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

/**
 *
 * @author Jamie
 */
public class Move {
    int[] origPos;
    int [] newPos;
    int piece;
    
    Move(int[] origPos, int[]newPos, int piece){
        this.origPos = origPos;
        this.newPos = newPos;
        this.piece = piece;
    }
    
}
