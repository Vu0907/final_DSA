package gameFunctionality;

import guiPackage.Connect;

public class Game extends Thread {

	private static Player p1;
	private static Player p2;
	private static Player winner;

	private static void print(Player p) {
		System.out.print("The table of player " + p.getName() + " \n");
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				System.out.print(p.getTab().getCoordinate(i, j) + " ");
			}
			System.out.print("\n");
		}
	}

	public static void print(Table table) {
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				System.out.print(table.getCoordinate(i, j) + " ");
			}
			System.out.print("\n");
		}
	}

	private static Player TheGame(Player p1, Player p2) {
		Player winner;
		if (p1.Alive() && p2.Alive()) {
			int[] coordinates = p1.hit(p2);
			if (p2.getTab().getCoordinate(coordinates[0], coordinates[1]) == 1) {
				winner = TheGame(p2, p1);
			} else {
				winner = TheGame(p1, p2);
			}
		} else {
			if (!p1.Alive()) {
				return p2;
			} else
				return p1;
		}

		return winner;

	}

	public static void createPlayers() {
		p1 = new Player("Player1");
		if (Connect.getMode() == 0) {
			p2 = new Player("Player2");
		} else {
			p2 = new Computer("Computer", Connect.getLevelPC());
		}
	}

	public static void setPositions(int m) {
		if (Connect.getMode() == 0) {
			switch (m) {
				case 0:
					p1.setPosition();
					break;
				case 1:
					p2.setPosition();
					break;
			}
		} else {
			p1.setPosition();
			p2.setPosition();
		}
	}

	public void run() {
		Connect.setGameStatus(true);
		winner = TheGame(p1, p2);
		Connect.setWinner(winner.getName());
		Connect.setGameStatus(false);
	}
}