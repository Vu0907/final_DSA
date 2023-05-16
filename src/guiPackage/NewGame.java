package guiPackage;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NewGame {

	public static Connect co;
	private static JFrame frame = new JFrame();

	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setTitle("Battleship");
				java.net.URL imgURL2 = getClass().getResource("/img/icon.png");
				Image im = Toolkit.getDefaultToolkit().getImage(imgURL2);
				frame.setIconImage(im);

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						String ObjButtons[] = { "Yes", "No" };
						int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
								"EXIT GAME",
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
								ObjButtons[1]);

						if (PromptResult == JOptionPane.YES_OPTION) {
							System.exit(0);
						} else {
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
				});

				try {
					MainMenu window = new MainMenu(frame, true, true);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
