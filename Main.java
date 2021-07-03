import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        JFrame obj = new JFrame();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Brickbreaker");
        obj.setResizable(true);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(new Gameplay());
    }
}