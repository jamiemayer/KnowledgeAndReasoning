package checkers;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 *
 * @author Jamie
 */


public class Board {
    private JFrame window;
    protected String playerName;
    public enum Team{BLACK, WHITE};
    private Team playerColour = null;
    private int[][] state;
    private int blackPawns;
    private int blackKings;
    private int whitePawns;
    private int whiteKings;
    private Team currentTurn;
    private boolean gameOver;
    private boolean ready = false;
    JPanel[][] panels = new JPanel[8][8];
        
    Board(){        
        setupGame();
        createWindow();
        blackPawns = 12;
        blackKings = 0;
        whitePawns = 12;
        whiteKings = 0;
        currentTurn = Team.BLACK;
        gameOver = false;
    }
    
    private void createWindow(){
        window = new JFrame("Checkers 0.01");
        window.setResizable(false);
        Container contentPane = window.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.setBackground(Color.WHITE);
        JLabel label = new JLabel("Please enter your Name and Press the Play Button");
        contentPane.add(label);
        JTextField nameField = new JTextField(20);
        contentPane.add(nameField);
        JButton goButton = new JButton ("Go!");
        goButton.setOpaque(true);
        goButton.setBorderPainted(false);
        goButton.setBackground(new Color(59,89,182));
        goButton.setForeground(Color.WHITE);
        goButton.setFont(new Font("Tahoma", Font.BOLD,12));
        contentPane.add(goButton);
        
        goButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playerName = nameField.getText();
                contentPane.removeAll();
                playOrder();
            }
        });
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    protected void playOrder(){
        Container cp = window.getContentPane();

        //Prompt for player to choose a colour
        JLabel choose = new JLabel(String.format("Hi %s, which colour would you like to be?", playerName));
        cp.add(choose);
        
        
        //Add Button for Black
        JButton black = new JButton("Black");
        black.setOpaque(true);
        black.setBorderPainted(false);
        black.setBackground(Color.BLACK);
        black.setForeground(Color.WHITE);
        black.setFont(new Font("Tahoma",Font.BOLD,12));
        black.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playerColour = Team.BLACK;
                cp.removeAll();
                drawGrid();
            }
        });
        cp.add(black);
        
        //Add Button for White
        JButton white = new JButton("White");
        white.setOpaque(true);
        white.setBorderPainted(false);
        white.setBackground(new Color(250,250,250));
        white.setFont(new Font("Tahoma",Font.BOLD,12));
        white.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playerColour = Team.WHITE;
                cp.removeAll();
                drawGrid();
            }
        });
        
        
        cp.add(white);
        
        
        window.pack();
        //window.setVisible(true);
        
    }
    
    private void drawGrid(){
        Container cp = window.getContentPane();
        cp.setPreferredSize(new Dimension(900,900));
        cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));
        JPanel surface = new JPanel(new GridLayout(8,8));
        //JPanel[][] panels = new JPanel[8][8];
        surface.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        for(int i = 0; i<8; i++){
            for (int j = 0; j<8;j++){
                JPanel cell = new JPanel();

                if(i % 2 == 0){
                    if(j % 2 == 0){
                        cell.setBackground(Color.BLACK);
                        
                    }
                    else{
                        cell.setBackground(Color.WHITE);
                        
                    }
                }
                else{
                    if(j % 2 == 0){
                        cell.setBackground(Color.WHITE);
                    }
                    else{
                        cell.setBackground(Color.BLACK);
                    }
                }                
                cell.setPreferredSize(new Dimension(80,80));
                cell.setLayout(new GridBagLayout());
                this.panels[i][j]=cell;                
            }
        }
        
        setupPieces();
        
        for(int i=0; i<8; i++){
            for(int j = 0; j<8; j++){
                surface.add(panels[i][j]);
            }
        }
        
        
        JButton exit = new JButton("Exit");
        exit.setOpaque(true);
        exit.setBorderPainted(false);
        exit.setBackground(new Color(59,89,182));
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Tahoma", Font.BOLD,12));
        exit.setVisible(true);
        cp.add(surface);
        cp.add(exit);
        window.pack();
        
    }
    
    public Team getPlayerTeam(){
        return playerColour;
    }
    
    private void setupGame(){
        state = new int[8][8];
        for(int i = 0; i <8;i++){
            for (int j =0; j<8; j++){
                if(j%2 ==0 && i%2==0 && i<3){
                    state[i][j]=1;
                }
                else{
                    if(j%2 >0 && i%2 >0 && i<3){
                        state[i][j] = 1;
                    }
                    else{
                        if(j%2==0 && i%2==0 &&i >4){
                            state[i][j]=2;
                        }
                        else if(j%2>0 && i%2>0 && i>4){
                            state[i][j]=2;
                        }
                    }
                }
            }
        }     
    }
    
    public void setupPieces(){
        ImageIcon blackPawn = new ImageIcon("BlackPawn.png");
        ImageIcon whitePawn = new ImageIcon("WhitePawn.png");
        ImageIcon blackKing = new ImageIcon("BlackKing.png");
        ImageIcon whiteKing = new ImageIcon("WhiteKing.png");
        
        for (int i = 0; i<8; i++){
            for(int j =0; j<8;j++){
                if(state[i][j]==1){
                    JLabel piece = new JLabel(blackPawn);
                    if(playerColour == Team.BLACK){
                        piece.addMouseListener(new MoveListener(new int[] {i,j}, Checkers.clone2D(state),1, panels,this));                     
                        
                    }
                    panels[i][j].removeAll();
                    panels[i][j].add(piece);
                    panels[i][j].revalidate();
                    panels[i][j].repaint();
                }
                if(state[i][j]==2){
                    JLabel piece = new JLabel(whitePawn);
                    if(playerColour == Team.WHITE){
                        piece.addMouseListener(new MoveListener(new int[] {i,j}, Checkers.clone2D(state),2, panels,this));                     
                        
                    }
                    panels[i][j].removeAll();
                    panels[i][j].add(piece);
                    panels[i][j].revalidate();
                    panels[i][j].repaint();
                }
                
                if(state[i][j]==3){
                    JLabel piece = new JLabel(blackKing);
                    if(playerColour == Team.BLACK){
                        piece.addMouseListener(new MoveListener(new int[] {i,j}, Checkers.clone2D(state),3, panels,this));
                        
                    }                    
                    panels[i][j].removeAll();
                    panels[i][j].add(piece);
                    panels[i][j].revalidate();
                    panels[i][j].repaint();
                }
                
                if(state[i][j]==4){
                    JLabel piece = new JLabel(whiteKing);
                    if(playerColour == Team.WHITE){
                        piece.addMouseListener(new MoveListener(new int[] {i,j}, Checkers.clone2D(state),4, panels,this));                     
                        
                    }
                    panels[i][j].removeAll();
                    panels[i][j].add(piece);
                    panels[i][j].revalidate();
                    panels[i][j].repaint();                   
                }
                
                if(state[i][j]==0){
                    panels[i][j].removeAll();
                    panels[i][j].revalidate();
                    panels[i][j].repaint();
                }
                
                    
            }
        }        
    }
    
    public void updateGUI(){
        setupPieces();
        window.pack();
    }
    
    
    public void setState(int[][] state){
        this.state = state;
    }
    
    public int[][] getState(){
        return state;
    }
    
    public Team getCurrentTurn(){
        return currentTurn;
    }
    
    public void setTurn(Team team){
        currentTurn = team;
    }
    
    public boolean isGameOver(){
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver){
        this.gameOver =gameOver;
    }
    
    public boolean isReady(){
        return ready;
    }
    
    public void processPayerTurn(){
        
    }
    
}
