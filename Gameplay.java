 
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.*;
public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private boolean play=false;
    private int score = 0;
    
    private int totalbricks=21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX=310;
    
    private int ballPosX=120;
    private int ballPosY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    
    Map map;
    
    public Gameplay()
    {
        map = new Map(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics g)
    {
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);
        
        //bricks
        map.draw((Graphics2D)g);
        
        //border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("DS-Digital Noraml",Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);
        
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);
        
        if (ballPosY > 570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            setFont(new Font("DS-Digital Noraml",Font.BOLD, 30));
            g.drawString("GAME OVER",190,300);
            g.drawString("Press Enter To Restart", 190, 350);
        }
        
        if(score==105)
        {
            g.drawString("YOU WON", 190, 300);
            play=false;
            g.drawString("Press Enter To Restart", 190, 350);
        }
        
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8) ))
                ballYdir=-ballYdir;
            A: for(int i=0; i<map.map.length; i++){
                    for(int j=0; j<map.map[0].length; j++)
                    {
                        if (map.map[i][j]>0)
                        {
                            int brickX=j*map.brickwidth+80;
                            int brickY=i*map.brickheight+50;
                            int brickWidth=map.brickwidth;
                            int brickHeight=map.brickheight;
                            Rectangle rect = new Rectangle (brickX, brickY, brickWidth, brickHeight);
                            Rectangle ballRect= new Rectangle(ballPosX, ballPosY, 20, 20);
                            Rectangle brickRect=rect;
                            if (ballRect.intersects(brickRect))
                            {
                                map.setBrickValue(0,i,j);
                                totalbricks--;
                                score+=5;
                                if(ballPosX+19<=brickRect.x||ballPosX+1>=brickRect.x+brickRect.width)
                                    ballXdir=-ballXdir;
                                else
                                    ballYdir=-ballYdir;
                                break A;
                            }
                        }
                    }}
                   
            ballPosX+=ballXdir;
            ballPosY+=ballYdir;
            if(ballPosX<0)
                ballXdir=-ballXdir;
            if(ballPosY<0)
                ballYdir=-ballYdir;
            if(ballPosX>670)
                ballXdir=-ballXdir;
        }
        repaint();
    }
    @Override
    public void keyTyped ( KeyEvent e ){ //not used
    }   
    @Override
    public void keyReleased ( KeyEvent e ){ //not used
    }
    @Override
    public void keyPressed ( KeyEvent e ){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX>=600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX<10)
                playerX = 10;
            else
                moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER&&!play){
            score=0;
            totalbricks=21;
            map = new Map(3,7);
            playerX=310;
            ballPosX=120;
            ballPosY=350;
            ballXdir=-1;
            ballYdir=-2;
            play=true;
        }
    }
    public void moveRight(){
        play=true;
        playerX+=20;
    }
    public void moveLeft(){
        play=true;
        playerX-=20;
    }
}