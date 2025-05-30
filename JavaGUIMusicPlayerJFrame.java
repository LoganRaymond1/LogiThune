import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;


 public class JavaGUIMusicPlayerJFrame extends JFrame implements ActionListener {
   
    private JTextField filePathField;
    private JButton playButton;
    private JButton pauseButton;
    private JButton chooseButton;
    private JButton loopButton;
    private boolean isPaused;
    private boolean isLooping;
    private JFileChooser fileChooser;
    private Clip clip;

    JavaGUIMusicPlayerJFrame(){
        super("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for file path and file chooser
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filePathField = new JTextField(25);
        chooseButton = new JButton("Choose File");
        topPanel.add(filePathField);
        topPanel.add(chooseButton);

        // Bottom panel for control buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        loopButton = new JButton("Loop");
        bottomPanel.add(playButton);
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
        pauseButton.addActionListener(this);
        chooseButton.addActionListener(this);
        loopButton.addActionListener(this);

        // File chooser setup
        fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));

        // Set frame size and visibility
        setSize(600, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playButton){
            playMusic();
        } else if(e.getSource() == pauseButton){
            pauseMusic();
        } else if(e.getSource() == chooseButton){
            chooseFile();
        } else if(e.getSource() == loopButton){
            toggleLoop();
        }
    }

    private void playMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            File file = new File(filePathField.getText());
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

    private void chooseFile(){
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
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
        new JavaGUIMusicPlayerJFrame();
    }

}