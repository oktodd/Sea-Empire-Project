import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SeaEmpire implements ActionListener {
    JFrame frame;
    JLayeredPane panel;
    JLabel label;
    JButton playButton;
    JButton tutorialButton;
    JLayeredPane gamePanel;
    JLabel mainGameBG;
    JLayeredPane tutorialPanel;
    JLabel tutorialBG;

    SeaEmpire() {
        /* FRAME */
        frame = new JFrame(); // buat frame
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // buat set sizenya frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // memaksimalkan dan menyesuaikan frame dengan layar laptop
        frame.setTitle("Sea Empire"); // buat bikin titlenya frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // supaya bisa tekan keluar dari frame
        frame.setResizable(true); // ini berfungsi untuk bisa kasi lebar atau kasi panjang frame
        ImageIcon image = new ImageIcon("SEAEMPIREICON.png"); // nama file yang mau dijadikan icon
        frame.setIconImage(image.getImage()); // ubah icon

        /* PANEL */
        panel = new JLayeredPane();
        panel.setLayout(null);
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        /* LABEL (DIATAS GAMBAR) */
        ImageIcon background = new ImageIcon("SeaEmpire.png");
        label = new JLabel(background); // buat label
        label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        label.setIcon(background);
        label.setVisible(true);

        /* BUTTON */
        ImageIcon buttonImage1 = new ImageIcon("TombolPlay.jpg");
        playButton = new JButton();
        playButton.setBounds(775, 500, 330, 75);
        playButton.setIcon(buttonImage1);
        playButton.addActionListener(e -> createGamePanel());

        ImageIcon buttonImage2 = new ImageIcon("TombolTutorial.jpg");
        tutorialButton = new JButton();
        tutorialButton.setBounds(775, 600, 330, 80);
        tutorialButton.setIcon(buttonImage2);
        tutorialButton.addActionListener(e -> createTutorialPanel());

        /*-----MENCETAK DI FRAME------*/
        panel.add(label, 0);
        panel.add(playButton, 1);
        panel.add(tutorialButton, 1);
        frame.add(panel);
        frame.setVisible(true); // supaya frame dapat ditampilkan
        frame.repaint();
        frame.revalidate(); // paksa refresh UI

    }

    // --- Ganti cursor jadi ikan ---
    int lastMouseX = 0; // tambahkan ini di class kamu (global)

    public void createGamePanel() {
        gamePanel = new JLayeredPane();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.add(gamePanel);
        frame.remove(panel);
        frame.revalidate();

        ImageIcon ikanKananIcon = new ImageIcon("Fishright9.png");
        Image ikanKanan = ikanKananIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon ikanKiriIcon = new ImageIcon("Fishleft9.png");
        Image ikanKiri = ikanKiriIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        Cursor cursorKanan = Toolkit.getDefaultToolkit().createCustomCursor(ikanKanan, new Point(0, 0), "ikanKanan");
        Cursor cursorKiri = Toolkit.getDefaultToolkit().createCustomCursor(ikanKiri, new Point(0, 0), "ikanKiri");

        ImageIcon originalIcon = new ImageIcon("background.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(3840, 2160, Image.SCALE_SMOOTH);
        ImageIcon backgroundGame = new ImageIcon(scaledImage);

        mainGameBG = new JLabel(backgroundGame);
        mainGameBG.setBounds(0, 0, 3849, 2160);
        gamePanel.add(mainGameBG);

        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                /* cursor geser kiri kanan */
                int mouseX1 = e.getX();

                if (mouseX1 > lastMouseX) {
                    gamePanel.setCursor(cursorKanan); // ikan ke kanan
                } else if (mouseX1 < lastMouseX) {
                    gamePanel.setCursor(cursorKiri); // ikan ke kiri
                }

                lastMouseX = mouseX1;

                /* supaya background dapat digerakkan sesuai cursor */

                int mouseX = e.getX();
                int mouseY = e.getY();

                int frameWidth = 1920;
                int frameHeight = 1080;

                int backgroundWidth = 3840;
                int backgroundHeight = 2160;

                // Hitung persentase posisi mouse
                double percentX = (double) mouseX / frameWidth;
                double percentY = (double) mouseY / frameHeight;

                // Kurangi maksimal gerak background supaya tidak buka ujung
                double movementFactor = 0.9; // 90% saja geraknya

                // Hitung offset gerakan background
                int maxOffsetX = backgroundWidth - frameWidth;
                int maxOffsetY = backgroundHeight - frameHeight;

                int bgX = (int) (-percentX * maxOffsetX * movementFactor);
                int bgY = (int) (-percentY * maxOffsetY * movementFactor);

                int safeMarginX = 50;
                int safeMarginY = 50;

                // Set posisi background
                mainGameBG.setLocation(bgX - safeMarginX, bgY - safeMarginY);
            }
        });
    }

    public void createTutorialPanel() {

        tutorialPanel = new JLayeredPane();
        tutorialPanel.setLayout(null);
        tutorialPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        ImageIcon tutorialBackground = new ImageIcon("TutorialPanel.png"); // jangan lupa isi
        tutorialBG = new JLabel(tutorialBackground);
        tutorialBG.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        tutorialPanel.add(tutorialBG);

        JButton exitButton = new JButton();
        ImageIcon XButton = new ImageIcon("XButton");
        exitButton.setIcon(XButton);
        exitButton.setBounds(700, 300, 50, 50); // jangan lupa isi
        exitButton.addActionListener(e -> {
            frame.remove(tutorialPanel);
            frame.add(panel);
            frame.repaint();
            frame.revalidate();
        });
        tutorialPanel.add(exitButton, 2);

        frame.remove(panel);
        frame.add(tutorialPanel);
        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            createGamePanel();
        } else if (e.getSource() == tutorialButton) {
            createTutorialPanel();
        }
    }
}
