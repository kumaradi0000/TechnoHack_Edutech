import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Tic_Tac_Toe extends JFrame implements ActionListener {

  private int xScore, oScore, moveCounter;

  private boolean isPlayerOne;

  private JLabel turnLabel, scoreLabel, resultLabel;
  private JButton[][] board;
  private JDialog resultDialog;

  public Tic_Tac_Toe() {
    super("Tic Tac Toe (Java Swing)");
    setSize(CommonConstants.FRAME_SIZE);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    setLayout(null);
    getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

    createResultDialog();

    board = new JButton[3][3];
    isPlayerOne = true;
    addGuiComponent();
  }

  private void addGuiComponent() {
    JLabel barLabel = new JLabel();
    barLabel.setOpaque(true);
    barLabel.setBackground(CommonConstants.BAR_COLOR);
    barLabel.setBounds(0, 0, CommonConstants.FRAME_SIZE.width, 25);

    turnLabel = new JLabel(CommonConstants.X_LABEL);
    turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

    turnLabel.setFont(new Font("Dialog", Font.PLAIN, 40));
    turnLabel.setPreferredSize(new Dimension(100, turnLabel.getPreferredSize().height));
    turnLabel.setOpaque(true);
    turnLabel.setForeground(CommonConstants.BOARD_COLOR);
    turnLabel.setBackground(CommonConstants.X_COLOR);
    turnLabel.setBounds(
        (CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width) / 2,
        0,
        turnLabel.getPreferredSize().width,
        turnLabel.getPreferredSize().height);

    scoreLabel = new JLabel(CommonConstants.SCORE_LABEL);
    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
    scoreLabel.setForeground(CommonConstants.BOARD_COLOR);
    scoreLabel.setBounds(0, turnLabel.getY() + turnLabel.getPreferredSize().height + 25,
        CommonConstants.FRAME_SIZE.width, scoreLabel.getPreferredSize().height);

    GridLayout gridLayout = new GridLayout(3, 3);
    JPanel boardPanel = new JPanel(gridLayout);
    boardPanel.setBounds(0,
        scoreLabel.getY() + scoreLabel.getPreferredSize().height + 35,
        CommonConstants.BOARD_SIZE.width,
        CommonConstants.BOARD_SIZE.height);

    // create board

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        JButton button = new JButton();
        button.setFont(new Font("Dialog", Font.PLAIN, 180));
        button.setPreferredSize(CommonConstants.BUTTON_SIZE);
        button.setBackground(CommonConstants.BACKGROUND_COLOR);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createLineBorder(CommonConstants.BOARD_COLOR));

        board[i][j] = button;
        boardPanel.add(board[i][j]);
      }
    }
    // Reset Button

    JButton resetButton = new JButton("Reset");
    resetButton.setFont(new Font("Dialog", Font.PLAIN, 24));
    resetButton.addActionListener(this);
    resetButton.setBackground(CommonConstants.BOARD_COLOR);
    resetButton.setBounds(
        (CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width) / 2,
        CommonConstants.FRAME_SIZE.height - 100,
        resetButton.getPreferredSize().width,
        resetButton.getPreferredSize().height);

    getContentPane().add(turnLabel);
    getContentPane().add(barLabel);
    getContentPane().add(scoreLabel);
    getContentPane().add(boardPanel);
    getContentPane().add(resetButton);
  }

  private void createResultDialog() {
    resultDialog = new JDialog();
    resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
    resultDialog.setResizable(false);
    resultDialog.setTitle("Result");
    resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
    resultDialog.setLocationRelativeTo(this);
    resultDialog.setModal(true);
    resultDialog.setLayout(new GridLayout(2, 1));
    resultDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        resetGame();
      }
    });
    resultLabel = new JLabel();
    resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
    resultLabel.setForeground(CommonConstants.BOARD_COLOR);
    resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JButton restartButton = new JButton("Play Again");
    restartButton.setBackground(CommonConstants.BOARD_COLOR);
    restartButton.addActionListener(this);

    resultDialog.add(resultLabel);
    resultDialog.add(restartButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("Reset") || command.equals("Play Again")) {
      resetGame();

      if (command.equals("Reset"))
        xScore = oScore = 0;

      if (command.equals("Play Again"))
        resultDialog.setVisible(false);
    } else {
      JButton button = (JButton) e.getSource();
      if (button.getText().equals("")) {
        moveCounter++;

        if (isPlayerOne) {
          button.setText(CommonConstants.X_LABEL);
          button.setForeground(CommonConstants.X_COLOR);

          turnLabel.setText(CommonConstants.O_LABEL);
          turnLabel.setBackground(CommonConstants.O_COLOR);

          isPlayerOne = false;
        } else {
          button.setText(CommonConstants.O_LABEL);
          button.setForeground(CommonConstants.O_COLOR);

          turnLabel.setText(CommonConstants.X_LABEL);
          turnLabel.setBackground(CommonConstants.X_COLOR);

          isPlayerOne = true;
        }
        if (isPlayerOne) {
          checkOWin();
        }
        {
          checkXWin();
        }

        checkDraw();
        scoreLabel.setText("X:" + xScore + "| O: " + oScore);
      }

      repaint();
      revalidate();
    }
  }

  private void checkXWin() {
    resultLabel.setText("X Wins!");
    for (int row = 0; row < board.length; row++) {
      if (board[row][0].getText().equals("X") && board[row][1].getText().equals("X")
          && board[row][2].getText().equals("X")) {
        resultDialog.setVisible(true);
        xScore++;
      }
    }
    for (int col = 0; col < board.length; col++) {
      if (board[0][col].getText().equals("X") && board[1][col].getText().equals("X")
          && board[2][col].getText().equals("X")) {
        resultDialog.setVisible(true);
        xScore++;
      }
    }
    if (board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")) {
      resultDialog.setVisible(true);
      xScore++;
    }

    if (board[2][0].getText().equals("X") && board[1][1].getText().equals("X") && board[0][2].getText().equals("X")) {
      resultDialog.setVisible(true);
      xScore++;
    }
  }

  private void checkOWin() {
    resultLabel.setText("O Wins!");

    for (int row = 0; row < board.length; row++) {
      if (board[row][0].getText().equals("O") && board[row][1].getText().equals("O")
          && board[row][2].getText().equals("O")) {
        resultDialog.setVisible(true);
        oScore++;
      }
    }
    for (int col = 0; col < board.length; col++) {
      if (board[0][col].getText().equals("O") && board[1][col].getText().equals("O")
          && board[2][col].getText().equals("O")) {
        resultDialog.setVisible(true);
        oScore++;
      }
    }
    if (board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")) {
      resultDialog.setVisible(true);
      oScore++;
    }

    if (board[2][0].getText().equals("O") && board[1][1].getText().equals("O") && board[0][2].getText().equals("O")) {
      resultDialog.setVisible(true);
      oScore++;
    }
  }

  private void checkDraw() {
    if (moveCounter >= 9) {
      resultLabel.setText("Draw!");
      resultDialog.setVisible(true);
    }
  }

  private void resetGame() {
    // reset player back to x_player
    isPlayerOne = true;
    turnLabel.setText(CommonConstants.X_LABEL);
    turnLabel.setBackground(CommonConstants.X_COLOR);

    scoreLabel.setText(CommonConstants.SCORE_LABEL);

    moveCounter = 0;

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j].setText("");
      }
    }
  }
}
