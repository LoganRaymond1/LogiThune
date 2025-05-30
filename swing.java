import javax.swing.*;

public class swing{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton button = new JButton("Gurt");
        button.addActionListener(e -> System.out.println("GURTY"));
        frame.add(button);
        
        frame.setVisible(true);
    }
}