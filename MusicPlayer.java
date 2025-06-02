import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;


 public class MusicPlayer extends JFrame implements ActionListener {
   
    private JTextField filePathField;
    private JButton playButton;
    private JButton playButton2;
    private JButton pauseButton;
    private JButton chooseButton;
    private JButton loopButton;
    private boolean isPaused;
    private boolean isLooping;
    private JFileChooser fileChooser;
    private Clip clip;

    MusicPlayer(){
        super("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for file path and file chooser
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        playButton = new JButton(new ImageIcon("cnTower.png"));
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setContentAreaFilled(false);

        playButton2 = new JButton(new ImageIcon("spongebob.jpg"));
        playButton2.setBorder(BorderFactory.createEmptyBorder());
        playButton2.setContentAreaFilled(false);

        topPanel.add(playButton);
        topPanel.add(playButton2);
        // Bottom panel for control buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        pauseButton = new JButton("Pause");
        loopButton = new JButton("Loop");

        bottomPanel.add(pauseButton);
        bottomPanel.add(loopButton);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize variables
        isPaused = false;
        isLooping = false;

        // Add action listeners
        playButton.addActionListener(this);
        playButton2.addActionListener(this);
        pauseButton.addActionListener(this);
        loopButton.addActionListener(this);

        // Set frame size and visibility
        setSize(700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playButton){
            playMusic(1);
        } else if(e.getSource() == playButton2){
            playMusic(2);
        } else if(e.getSource() == pauseButton){
            pauseMusic();
        } else if(e.getSource() == loopButton){
            toggleLoop();
        }
    }

    private void playMusic(int songNumber) {
        File file = null;

        isPaused = false;
        pauseButton.setText("Pause");

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            if(songNumber == 1) {
                file = new File("PARTYNEXTDOOR-CN-TOWER-Ft-DRAKE-(HipHopKit.com).wav");
            } else if(songNumber == 2) {
                file = new File("Spongebob Squarepants.wav");
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
            pauseButton.setText("Resume");
        } else if (clip != null && isPaused) {
            clip.start();
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            isPaused = false;
            pauseButton.setText("Pause");
        }
    }

    private void toggleLoop(){
        isLooping = !isLooping;
        if(isLooping){
            loopButton.setText("Stop Loop");
            if(clip.isRunning()){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            loopButton.setText("Loop");
            if(clip.isRunning()){
                clip.loop(0);
            }
        }
        
    }

    public static void main(String[] args) {
        new MusicPlayer();
    }

}