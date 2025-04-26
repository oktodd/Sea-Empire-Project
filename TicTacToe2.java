import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe2 implements ActionListener { // ActionListener itu abstrak (kosong isinya)
    private char currentPlayer;
    JFrame frame = new JFrame("TICTACTOE GAME");
    JPanel titlePanel = new JPanel();
    JPanel gamePanel = new JPanel(); // cari javax.swing di internet
    JLabel titleLabel = new JLabel();
    JLabel currentPlayerLabel = new JLabel();
    JButton[] button = new JButton[9];

    public TicTacToe2() { // constructor
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // cari JFrame layouts di internet
        frame.setResizable(true);
        frame.setVisible(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 200);

        titleLabel.setText("TICTACTOE GAME");
        titleLabel.setBackground(new Color(25, 25, 25));
        titleLabel.setForeground(Color.blue);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 50)); // masih belum
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // masih belum
        titleLabel.setOpaque(true);

        currentPlayerLabel.setText(("Current PLayer: "));
        currentPlayerLabel.setBackground(Color.black);
        currentPlayerLabel.setForeground(Color.white);
        currentPlayerLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));
        currentPlayerLabel.setHorizontalAlignment(JLabel.LEFT);
        currentPlayerLabel.setOpaque(true);

        gamePanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            button[i] = new JButton();
            gamePanel.add(button[i]);
            button[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            button[i].setFocusable(false);
            button[i].addActionListener(this);
        }

        titlePanel.add(titleLabel, BorderLayout.NORTH); // untuk masukkin dalam frame
        titlePanel.add(currentPlayerLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(gamePanel);
        startGame();
    }

    public void startGame() {
        currentPlayer = 'X';
        currentPlayerLabel.setText("Current Player: " + currentPlayer);
    }

    public void checkWin() {
        // Array dengan semua kemungkinan kombinasi kemenangan
        int[][] winPatterns = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Baris
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Kolom
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonal
        };

        for (int[] pattern : winPatterns) {
            if (!button[pattern[0]].getText().isEmpty() &&
                    button[pattern[0]].getText().equals(button[pattern[1]].getText()) &&
                    button[pattern[1]].getText().equals(button[pattern[2]].getText())) {

                // Menandai pemenang
                char winner = button[pattern[0]].getText().charAt(0);
                gameOver(winner);
                return;
            }
        }

        // Periksa apakah permainan seri (tidak ada tombol yang kosong)
        boolean fullBoard = true;
        for (JButton btn : button) {
            if (btn.getText().isEmpty()) {
                fullBoard = false;
                break;
            }
        }

        if (fullBoard) {
            gameOver('D'); // 'D' untuk Draw (seri)
        }
    }

    // Metode gameOver untuk menangani akhir permainan
    public void gameOver(char winner) {
        String message;

        if (winner == 'D') {
            message = "Permainan berakhir seri!";
        } else {
            message = "Pemain " + winner + " menang!";
        }

        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Menonaktifkan tombol setelah menang
        for (JButton btn : button) {
            btn.setEnabled(false);
        }

        int response = JOptionPane.showConfirmDialog(frame, "Mulai ulang permainan?", "Restart",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }

    // Metode resetGame untuk mengatur ulang permainan
    public void resetGame() {
        for (JButton btn : button) {
            btn.setText(""); // Kosongkan semua tombol
            btn.setEnabled(true); // Aktifkan kembali tombol
        }
        currentPlayer = 'X'; // Setel ulang pemain ke 'X'
        currentPlayerLabel.setText("Current Player: " + currentPlayer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == button[i]) {
                if (currentPlayer == 'X') {
                    if (button[i].getText() == "") {
                        button[i].setText(Character.toString(currentPlayer));
                        button[i].setForeground(Color.RED);
                        currentPlayer = 'O';
                        currentPlayerLabel.setText("Current Player: " + currentPlayer);
                        checkWin();
                    }
                } else {
                    if (button[i].getText() == "") {
                        button[i].setText(Character.toString(currentPlayer));
                        button[i].setForeground(Color.BLUE);
                        currentPlayer = 'X';
                        currentPlayerLabel.setText("current Player: " + currentPlayer);
                    }
                }
            }
        } // pake ini biar ga error
    }

}