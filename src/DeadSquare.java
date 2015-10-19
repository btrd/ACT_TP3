import java.util.ArrayList;

class DeadSquare {
	Integer[][][][] myDynamicArray;
	int res;

	public DeadSquare(Integer nb_col, Integer nb_row) {
		myDynamicArray = new Integer[nb_col + 1][nb_row + 1][nb_col + 1][nb_row + 1];
	}

	Integer getValue(Integer nb_col, Integer nb_row, Integer x_death, Integer y_death) {
		res = calcValue(nb_col, nb_row, x_death, y_death);
		return res;
	}

	Integer calcValue(Integer nb_col, Integer nb_row, Integer x_death, Integer y_death) {
		if (nb_col == 1 && nb_row == 1) {
			return 0;
		} else {
			ArrayList<Integer> array_value = new ArrayList<Integer>();
			boolean neg = false;
			Integer tmp;
			Integer max = 0;
			for (Integer k = 1; k < nb_col; k++) {
				if (k <= x_death) { // on coupe avant le carré de la mort
					tmp = searchValue(nb_col - k, nb_row, x_death - k, y_death);
				} else { // on coupe aprés
					tmp = searchValue(k, nb_row, x_death, y_death);
				}
				array_value.add(tmp);
				if (tmp <= 0)
					neg = true;
			}
			for (Integer l = 1; l < nb_row; l++) {
				if (l <= y_death) { // on coupe avant le carré de la mort
					tmp = searchValue(nb_col, nb_row - l, x_death, y_death - l);
				} else { // on coupe aprés
					tmp = searchValue(nb_col, l, x_death, y_death);
				}
				array_value.add(tmp);
				if (tmp <= 0)
					neg = true;
			}
			if (neg) {
				max = (-1) * (maxNeg(array_value) - 1);
			} else {
				max = (-1) * (maxPos(array_value) + 1);
			}
			return max;
		}
	}

	Integer searchValue(Integer nb_col, Integer nb_row, Integer x_death, Integer y_death) {
		int res;
		// si la valeur n'est pas dans le tableau
		Integer[] index = mirror(nb_col, nb_row, x_death, y_death);
		nb_col = index[0];
		nb_row = index[1];
		x_death = index[2];
		y_death = index[3];
		if (myDynamicArray[nb_col][nb_row][x_death][y_death] == null) {
			res = calcValue(nb_col, nb_row, x_death, y_death);
			myDynamicArray[nb_col][nb_row][x_death][y_death] = res;
		} else {
			res = myDynamicArray[nb_col][nb_row][x_death][y_death];
		}
		return res;
	}

	Integer[] mirror(Integer nb_col, Integer nb_row, Integer x_death, Integer y_death) {
		if (nb_col < nb_row) { //On fait une rotation pour que m soit toujours le max
			int nb_row_tmp = nb_row;
			nb_row = nb_col;
			nb_col = nb_row_tmp;

			int x_death_tmp = x_death;
			x_death = y_death;
			y_death = x_death_tmp; //c'est "faux" mais avec l'effet miroir c'est ok
		}

		if (x_death >= (nb_col/2)) {
			x_death = -(x_death - nb_col) - 1;
		}
		if (y_death >= (nb_row/2)) {
			y_death = -(y_death - nb_row) - 1;
		}

		Integer[] res = new Integer[4];
		res[0] = nb_col;
		res[1] = nb_row;
		res[2] = x_death;
		res[3] = y_death;
		return res;
	}

	// Return max value of the negatif element of an arrayList
	static Integer maxNeg(ArrayList<Integer> array) {
		Integer max = Integer.MIN_VALUE;
		for (Integer k = 0; k < array.size(); k++) {
			if (array.get(k) <= 0) {
				max = Math.max(array.get(k), max);
			}
		}
		return max;
	}

	// Return max value of the positif element of an arrayList
	static Integer maxPos(ArrayList<Integer> array) {
		Integer max = 0;
		for (Integer k = 0; k < array.size(); k++) {
			if (array.get(k) > 0) {
				max = Math.max(array.get(k), max);
			}
		}
		return max;
	}

	private void printArray() {
		for(int i = 0; i < myDynamicArray.length; i++) {
			for(int j = 0; j < myDynamicArray[i].length; j++) {
				for(int k = 0; k < myDynamicArray[i][j].length; k++) {
					for(int l = 0; l < myDynamicArray[i][j][k].length; l++) {
						System.out.print(myDynamicArray[i][j][k][l] + " ");
					}
					System.out.println("");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

	private void countArray() {
		int count = 0;
		for(int i = 0; i < myDynamicArray.length; i++) {
			for(int j = 0; j < myDynamicArray[i].length; j++) {
				for(int k = 0; k < myDynamicArray[i][j].length; k++) {
					for(int l = 0; l < myDynamicArray[i][j][k].length; l++) {
						if(myDynamicArray[i][j][k][l] != null)
							count++;
					}
				}
			}
		}
		System.out.println(count);
	}
}
