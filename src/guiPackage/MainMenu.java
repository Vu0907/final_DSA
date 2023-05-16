package guiPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainMenu {

	private JFrame frame;
	private Clip clip;
	private boolean soundEffects, soundtrack;

	public MainMenu(JFrame f, boolean sEffects, boolean sTrack) {
		frame = new JFrame();

		if (f != null) {
			frame = f;
		}

		try {
			frame.setContentPane(
					new JLabel(new ImageIcon(ImageIO.read(ResourceLoader.load("img/background-menu.jpg")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setSoundtrack(sTrack);
		setSoundEfffects(sEffects);

		if (getSoundtrack()) {
			enableMusic("sound/MenuSong.wav");
		}
		JButton startButton = new JButton();
		try {
			startButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/button-start.jpg"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		startButton.setBounds(403, 230, 313, 140);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ChooseGameOptionMenu(frame, getSoundEfffects(), getSoundtrack(), clip);
			}
		});

		JButton exitButton = new JButton();
		try {
			exitButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/button-exit.jpg"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		exitButton.setBounds(485, 480, 150, 85);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "CLOSE GAME",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		JButton soundEffectsButton = new JButton();
		soundEffectsButton.setBounds(53, 274, 89, 82);
		soundEffectsButton.setOpaque(false);
		soundEffectsButton.setContentAreaFilled(false);
		soundEffectsButton.setBorderPainted(false);
		soundEffectsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getSoundEfffects()) {
					try {
						soundEffectsButton
								.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/sound_effects_off.png"))));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					setSoundEfffects(false);
				} else {
					try {
						soundEffectsButton
								.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/sound_effects_on.png"))));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					setSoundEfffects(true);
				}
			}
		});

		if (getSoundEfffects()) {
			try {
				soundEffectsButton
						.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/sound_effects_on.png"))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				soundEffectsButton
						.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/sound_effects_off.png"))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JButton soundtrackButton = new JButton();
		soundtrackButton.setBounds(945, 274, 89, 69);
		soundtrackButton.setOpaque(false);
		soundtrackButton.setContentAreaFilled(false);
		soundtrackButton.setBorderPainted(false);
		soundtrackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getSoundtrack()) {
					try {
						soundtrackButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/music_off.png"))));
					} catch (IOException e) {
						e.printStackTrace();
					}
					clip.stop();
					setSoundtrack(false);
				} else {
					try {
						soundtrackButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/music_on.png"))));
					} catch (IOException e) {
						e.printStackTrace();
					}
					enableMusic("sound/MenuSong.wav");
					setSoundtrack(true);
				}

			}
		});

		if (getSoundtrack()) {
			try {
				soundtrackButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/music_on.png"))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				soundtrackButton.setIcon(new ImageIcon(ImageIO.read(ResourceLoader.load("img/music_off.png"))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		frame.setBounds(100, 100, 1100, 610);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(startButton);

		frame.getContentPane().add(exitButton);
		frame.getContentPane().add(soundEffectsButton);
		frame.getContentPane().add(soundtrackButton);
		frame.validate();
	}

	public void enableMusic(String path) {
		try {
			java.net.URL soundurl = getClass().getResource("/" + path);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(soundurl));

			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getSoundEfffects() {
		return soundEffects;
	}

	public void setSoundEfffects(boolean soundEffects) {
		this.soundEffects = soundEffects;
	}

	public boolean getSoundtrack() {
		return soundtrack;
	}

	public void setSoundtrack(boolean soundtrack) {
		this.soundtrack = soundtrack;
	}

	public JFrame getFrame() {
		return frame;
	}

}
