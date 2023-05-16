package gameFunctionality;

import gameFunctionality.Ship.Direction;
import guiPackage.Connect;

public class Table {

	private int[][] table = new int[10][10];

	public Table() {

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				table[i][j] = 0;
	}

	public boolean plaseShip(Ship ship) {
		int x = ship.getCoordinates()[0];
		int y = ship.getCoordinates()[1];
		boolean flag;

		if (ship.getDirection() == Direction.HORIZONTAL) {
			if (x + ship.getSize() - 1 > 9)
				return false;
			for (int i = -1; i <= 1; i++) {
				boolean A = true;
				boolean B = true;
				if (x - 1 < 0) {
					A = false;
				} else if (!(x + ship.getSize() < 10)) {
					B = false;
				}
				if (y + i < 0 || y + i > 9) {
					A = false;
					B = false;
				}
				if (A && table[x - 1][y + i] != 0 || B && table[x + ship.getSize()][y + i] != 0)
					return false;
			}
			for (int i = x; i < x + ship.getSize(); i++) {
				boolean A = true;
				boolean B = true;
				if (y - 1 < 0) {
					A = false;
				} else if (y + 1 > 9) {
					B = false;
				}
				flag = table[i][y] != 0 || B && table[i][y + 1] != 0 || A && table[i][y - 1] != 0;
				if (flag)
					return false;
			}
			for (int i = x; i < x + ship.getSize(); i++)
				table[i][y] = 2;
			return true;
		} else {
			if (y + ship.getSize() - 1 > 9)
				return false;
			for (int i = -1; i <= 1; i++) {
				boolean A = true;
				boolean B = true;
				if (y - 1 < 0) {
					A = false;
				} else if (!(y + ship.getSize() < 10)) {
					B = false;
				}
				if (x + i < 0 || x + i > 9) {
					A = false;
					B = false;
				}
				if (A && table[x + i][y - 1] != 0 || B && table[x + i][y + ship.getSize()] != 0)
					return false;
			}

			for (int i = y; i < y + ship.getSize(); i++) {
				boolean A = true;
				boolean B = true;
				if (x - 1 < 0) {
					A = false;
				} else if (x + 1 > 9) {
					B = false;
				}
				flag = table[x][i] != 0 || B && table[x + 1][i] != 0 || A && table[x - 1][i] != 0;
				if (flag)
					return false;

			}

			for (int i = y; i < y + ship.getSize(); i++)
				table[x][i] = 2;
			return true;
		}
	}

	public boolean check(int x, int y, Ship[] ship) {

		if (table[x][y] == 0)
			table[x][y] = 1;
		else if (table[x][y] == 2) {
			table[x][y] = -1;
			int i = 0;
			boolean flag = false;
			while (!flag && i < 6) {
				flag = ship[i].updateLive(x, y);
				i++;
			}
			if (!ship[i - 1].getStatus()) {
				sankShip(ship[i - 1]);
			}
		} else
			return false;

		return true;

	}

	public void sankShip(Ship ship) {
		int x = ship.getCoordinates()[0];
		int y = ship.getCoordinates()[1];
		int d;

		if (ship.getDirection() == Direction.HORIZONTAL) {
			d = 0;
			for (int i = -1; i <= 1; i++) {
				boolean A = true;
				boolean B = true;
				if (x - 1 < 0) {
					A = false;
				} else if (!(x + ship.getSize() < 10)) {
					B = false;
				}
				if (y + i < 0 || y + i > 9) {
					A = false;
					B = false;
				}
				if (A)
					table[x - 1][y + i] = 1;
				if (B)
					table[x + ship.getSize()][y + i] = 1;
			}
			for (int i = x; i < x + ship.getSize(); i++) {
				if (y + 1 < 10)
					table[i][y + 1] = 1;
				if (y - 1 >= 0)
					table[i][y - 1] = 1;
				table[i][y] = 3;

			}
		} else {
			d = 1;
			for (int i = -1; i <= 1; i++) {
				boolean A = true;
				boolean B = true;
				if (y - 1 < 0) {
					A = false;
				}
				if (!(y + ship.getSize() < 10)) {
					B = false;
				}
				if (x + i < 0 || x + i > 9) {
					A = false;
					B = false;
				}
				if (A)
					table[x + i][y - 1] = 1;
				if (B)
					table[x + i][y + ship.getSize()] = 1;
			}
			for (int i = y; i < y + ship.getSize(); i++) {
				if (x + 1 < 10)
					table[x + 1][i] = 1;
				if (x - 1 >= 0)
					table[x - 1][i] = 1;
				table[x][i] = 3;

			}
		}

		Connect.setSunkShip(x, y, ship.getSize(), d);
	}

	public int getCoordinate(int i, int j) {
		return table[i][j];
	}

}
