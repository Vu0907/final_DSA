package gameFunctionality;

import gameFunctionality.Ship.Direction;
import guiPackage.Connect;
import guiPackage.NewGame;

public class Computer extends Player {

	private int difficulty;
	private boolean isShipDetected;
	private int[] lastHit;
	private int[] enemyShip;

	public Computer(String name, int d) {
		super(name);
		difficulty = d;
		this.isShipDetected = false;
		this.lastHit = new int[2];
		enemyShip = new int[6];
		enemyShip[0] = 2;
		enemyShip[1] = 2;
		enemyShip[2] = 3;
		enemyShip[3] = 3;
		enemyShip[4] = 4;
		enemyShip[5] = 5;

	}

	private int[] hitNormal(Table table) {
		int[] ca = new int[2];
		int[][] pos = new int[2][4];
		int p1 = 1;
		int p2 = 1;
		int count = 4;
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {

				pos[0][i] = lastHit[0] + p1;
				pos[1][i] = lastHit[1];
				p1 = p1 * (-1);

			} else {

				pos[0][i] = lastHit[0];
				pos[1][i] = lastHit[1] + p2;
				p2 = p2 * (-1);
			}
			if (pos[0][i] < 0 || pos[0][i] > 9 || pos[1][i] < 0 || pos[1][i] > 9) {

				pos[1][i] = -1;
				pos[0][i] = -1;

			}
		}

		int i = 0;
		while (pos[1][i] == -1) {

			i++;
		}
		while (i < count) {
			ca[0] = pos[0][i];
			ca[1] = pos[1][i];
			switch (i) {
				case 0:
					p1 = 1;
					p2 = 0;
					break;
				case 1:
					p1 = 0;
					p2 = 1;
					break;
				case 2:
					p1 = -1;
					p2 = 0;
					break;
				case 3:
					p1 = 0;
					p2 = -1;
					break;
			}

			while (table.getCoordinate(ca[0], ca[1]) == -1) {
				ca[0] = ca[0] + p1;
				ca[1] = ca[1] + p2;

				if (!(ca[0] < 10 && ca[0] >= 0 && ca[1] >= 0 && ca[1] < 10)) {
					ca[0] = lastHit[0];
					ca[1] = lastHit[1];
					p1 = p1 * (-1);
					p2 = p2 * (-1);
					pos[1][i] = -1;
					pos[0][i] = -1;
				}

				if (table.getCoordinate(ca[0], ca[1]) == 1) {
					ca[0] = lastHit[0];
					ca[1] = lastHit[1];
					p1 = p1 * (-1);
					p2 = p2 * (-1);
					pos[1][i] = -1;
					pos[0][i] = -1;
				} else if (table.getCoordinate(ca[0], ca[1]) != -1) {
					return ca;
				}
			}
			do {
				i++;
			} while (i < count && pos[1][i] == -1);
		}
		for (i = 0; i < count; i++) {
			if (pos[1][i] != -1 && table.getCoordinate(pos[0][i], pos[1][i]) != 1) {
				ca[0] = pos[0][i];
				ca[1] = pos[1][i];
				return ca;
			}
		}
		return null;
	}

	private int[] hitHard(Table table) {
		int[][] proTable = probabilityTable(table);

		int max = 0;
		int[] ca = new int[2];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (proTable[i][j] > max) {
					max = proTable[i][j];
					ca[0] = i;
					ca[1] = j;
				}
			}
		}
		return ca;
	}

	private int[][] probabilityTable(Table enemyTable) {
		int table[][] = new int[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				table[i][j] = 0;
		int max = 0;
		for (int i = 0; i < 6; i++) {
			if (enemyShip[i] > max) {
				max = enemyShip[i];
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if ((j + max - 1) < 10) {
					boolean flag = true;
					for (int k = j; k < j + max; k++) {
						if (enemyTable.getCoordinate(i, k) != 0 && enemyTable.getCoordinate(i, k) != 2) {
							flag = false;
						}
					}
					if (flag) {
						for (int k = j; k < j + max; k++)
							table[i][k]++;
					}
				} else
					break;
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if ((j + max - 1) < 10) {
					boolean flag = true;
					for (int k = j; k < j + max; k++) {
						if (enemyTable.getCoordinate(k, i) != 0 && enemyTable.getCoordinate(k, i) != 2) {
							flag = false;
						}
					}
					if (flag) {
						for (int k = j; k < j + max; k++)
							table[k][i]++;
					}
				} else
					break;
			}
		}
		return table;
	}

	private void setEnemyShips(int size) {
		for (int i = 0; i < 6; i++) {
			if (enemyShip[i] == size) {
				enemyShip[i] = -1;
				break;
			}
		}
	}

	public int[] hit(Player p) {

		int[] hitCoordinates = new int[2];
		boolean hited = false;
		Table table = p.getTab();

		while (!hited) {
			switch (difficulty) {
				case 0:
					hitCoordinates[0] = (int) (Math.random() * 10);
					hitCoordinates[1] = (int) (Math.random() * 10);
					break;
				case 1:
					if (isShipDetected) {
						hitCoordinates = hitNormal(table);
					} else {
						hitCoordinates[0] = (int) (Math.random() * 10);
						hitCoordinates[1] = (int) (Math.random() * 10);

					}
					break;
				case 2:
					if (isShipDetected) {
						hitCoordinates = hitNormal(table);
					} else {
						hitCoordinates = hitHard(table);
					}

					break;
			}
			hited = table.check(hitCoordinates[0], hitCoordinates[1], p.getShip());
			if (table.getCoordinate(hitCoordinates[0], hitCoordinates[1]) == -1)
				this.isShipDetected = true;
			else if (table.getCoordinate(hitCoordinates[0], hitCoordinates[1]) == 3) {
				this.isShipDetected = false;
				int size = 1;
				int i = 1;
				int flag = 0;
				int p1, p2;
				if (hitCoordinates[0] != lastHit[0]) {
					p1 = 1;
					p2 = 0;
				} else {
					p1 = 0;
					p2 = 1;
				}
				while (true) {
					boolean A = hitCoordinates[0] + i * p1 >= 0 && hitCoordinates[0] + i * p1 < 10
							&& hitCoordinates[1] + i * p2 >= 0 && hitCoordinates[1] + i * p2 < 10;
					if (A && table.getCoordinate(hitCoordinates[0] + i * p1, hitCoordinates[1] + i * p2) == 3) {
						size++;
						i++;
					} else {
						i = 1;
						p1 = p1 * (-1);
						p2 = p2 * (-1);
						flag++;
					}

					if (flag == 2)
						break;
				}
				this.setEnemyShips(size);
			}
		}
		if (table.getCoordinate(lastHit[0], lastHit[1]) == -1) {
			if (table.getCoordinate(hitCoordinates[0], hitCoordinates[1]) == -1) {
				lastHit[0] = hitCoordinates[0];
				lastHit[1] = hitCoordinates[1];
			}
		} else {
			lastHit[0] = hitCoordinates[0];
			lastHit[1] = hitCoordinates[1];
		}

		NewGame.co.setPcHit(hitCoordinates[0], hitCoordinates[1]);
		Connect.setStatusHit(table.getCoordinate(hitCoordinates[0], hitCoordinates[1]));
		checkStatus(p);
		return hitCoordinates;

	}

	public void setPosition() {
		setTab(new Table());
		for (int i = 0; i < 6; i++) {
			int[] coor = new int[2];
			boolean flag = false;
			while (!flag) {
				coor[0] = (int) (Math.random() * 10);
				coor[1] = (int) (Math.random() * 10);
				double d = (Math.random());
				getShip(i).setCoordinates(coor);
				if (d < 0.5) {
					getShip(i).setDirection(Direction.HORIZONTAL);
				} else {
					getShip(i).setDirection(Direction.VERTICAL);
				}
				flag = getTab().plaseShip(getShip(i));
			}
		}
	}

}