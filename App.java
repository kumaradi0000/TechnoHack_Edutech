import javax.swing.*;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new Tic_Tac_Toe().setVisible(true);
      }
    });
  }
}
