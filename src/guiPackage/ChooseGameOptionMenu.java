package guiPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import gameFunctionality.Game;

public class ChooseGameOptionMenu {

	private JFrame frame;
	private JCheckBox checkBoxPvP, checkBoxPvPC;
	private JList<String> difficultyList = new JList<String>();
	private String gameMode;
	private int numberOfPlayers;

	public ChooseGameOptionMenu(JFrame f, boolean sEffects, boolean sTrack, Clip clip) {
		frame = f;
		frame.getContentPane().removeAll();
		frame.repaint();

		checkBoxPvP = new JCheckBox("PLAYER1 VS PLAYER2");
		checkBoxPvP.setFont(new Font("Tempus Sans ITC", Font.BOLD, 40));
		checkBoxPvP.setForeground(Color.BLACK);
		checkBoxPvP.setOpaque(false);
		checkBoxPvP.setBounds(587, 55, 477, 99);
		checkBoxPvP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBoxPvPC.isSelected()) {
					checkBoxPvPC.setSelected(false);
					frame.remove(difficultyList);
				}
				frame.repaint();
			}
		});

		checkBoxPvPC = new JCheckBox("PLAYER VS PC");
		checkBoxPvPC.setBackground(Color.LIGHT_GRAY);
		checkBoxPvPC.setFont(new Font("Tempus Sans ITC", Font.BOLD, 40));
		checkBoxPvPC.setBounds(24, 55, 330, 99);
		checkBoxPvPC.setForeground(Color.BLACK);
		checkBoxPvPC.setOpaque(false);
		checkBoxPvPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkBoxPvP.isSelected()) {
					checkBoxPvP.setSelected(false);
				}

				if (checkBoxPvPC.isSelected()) {
					difficultyList = new JList<String>();
					difficultyList.setForeground(Color.PINK);
					difficultyList.setFont(new Font("Tempus Sans ITC", Font.BOLD, 44));
					difficultyList.setBackground(Color.WHITE);
					difficultyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					difficultyList.setBounds(48, 201, 177, 130);
					difficultyList.setModel(new AbstractListModel<String>() {
						private static final long serialVersionUID = 1L;

						String[] values = new String[] { "medium", "hard" };

						public int getSize() {
							return values.length;
						}

						public String getElementAt(int index) {
							return values[index];
						}
					});

					frame.getContentPane().add(difficultyList);
				} else {
					frame.remove(difficultyList);
				}

				frame.repaint();
			}
		});

		JButton backToMainMenuButton = new JButton();
		try {
			backToMainMenuButton
					.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/button-back_to_main_menu.jpg"))));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		backToMainMenuButton.setBounds(473, 498, 143, 65);
		backToMainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.getContentPane().removeAll();
				frame.repaint();

				if (sTrack) {
					clip.stop();
					clip.close();
				}

				new MainMenu(frame, sEffects, sTrack);
			}
		});

		JButton startGameButton = new JButton();
		try {
			startGameButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/button-ready.jpg"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		startGameButton.setBounds(360, 250, 365, 160);
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!checkBoxPvPC.isSelected() && !checkBoxPvP.isSelected()) {
					JOptionPane.showMessageDialog(new JFrame(), "CHOOSE HOW YOU WANT TO PLAY\n"
							+ "(PLAYER VS PC OR PLAYER1 VS PLAYER 2)", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (checkBoxPvPC.isSelected() && difficultyList.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "SELECT DIFFICULTY", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (checkBoxPvP.isSelected()) {
						gameMode = "pvp";
						numberOfPlayers = 1;
					} else {
						gameMode = difficultyList.getSelectedValue().toString();
						numberOfPlayers = 0;
					}

					Connect.setGameMode(gameMode);
					Game.createPlayers();
					new ShipPlacementMenu(frame, sEffects, sTrack, gameMode, clip, numberOfPlayers);
				}
			}
		});

		frame.getContentPane().add(checkBoxPvPC);
		frame.getContentPane().add(checkBoxPvP);
		frame.getContentPane().add(backToMainMenuButton);
		frame.getContentPane().add(startGameButton);
	}

}
