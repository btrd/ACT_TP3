import java.util.ArrayList;

class DeadSquare {
	private Integer[][][][] myDynamicArray;

	public DeadSquare(Integer m, Integer n, Integer i, Integer j) {
		this.myDynamicArray = new Integer[m + 1][n + 1][m + 1][n + 1];
		System.out.println(this.calcValue(m, n, i, j));
	}

	Integer calcValue(Integer m, Integer n, Integer i, Integer j) {
		if (m == 1 && n == 1) {
			return 0;
		} else {
			ArrayList<Integer> array_value = new ArrayList<Integer>();
			boolean neg = false;
			Integer tmp;
			Integer max = 0;
			for (Integer k = 1; k < m; k++) {
				if (k <= i) { // on coupe avant le carré de la mort
					if (this.myDynamicArray[m - k][n][i - k][j] == null) {
						tmp = calcValue(m - k, n, i - k, j);
						this.myDynamicArray[m - k][n][i - k][j] = tmp;
					} else {
						tmp = this.myDynamicArray[m - k][n][i - k][j];
					}
				} else { // on coupe aprés
					if (this.myDynamicArray[k][n][i][j] == null) {
						tmp = calcValue(k, n, i, j);
						this.myDynamicArray[k][n][i][j] = tmp;
					} else {
						tmp = this.myDynamicArray[k][n][i][j];
					}
				}
				array_value.add(tmp);
				if (tmp <= 0)
					neg = true;
			}
			for (Integer l = 1; l < n; l++) {
				if (l <= j) { // on coupe avant le carré de la mort
					tmp = calcValue(m, n - l, i, j - l);
					if (this.myDynamicArray[m][n - l][i][j - l] == null) {
						tmp = calcValue(m, n - l, i, j - l);
						this.myDynamicArray[m][n - l][i][j - l] = tmp;
					} else {
						tmp = this.myDynamicArray[m][n - l][i][j - l];
					}
				} else { // on coupe aprés
					if (this.myDynamicArray[m][l][i][j] == null) {
						tmp = calcValue(m, l, i, j);
						this.myDynamicArray[m][l][i][j] = tmp;
					} else {
						tmp = this.myDynamicArray[m][l][i][j];
					}
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

	// Return max value of the negatif element of an arrayList
	static Integer maxNeg(ArrayList<Integer> array) {
		Integer max = Integer.MIN_VALUE;
		for (Integer i = 0; i < array.size(); i++) {
			if (array.get(i) <= 0) {
				max = Math.max(array.get(i), max);
			}
		}
		return max;
	}

	// Return max value of the positif element of an arrayList
	static Integer maxPos(ArrayList<Integer> array) {
		Integer max = 0;
		for (Integer i = 0; i < array.size(); i++) {
			if (array.get(i) > 0) {
				max = Math.max(array.get(i), max);
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
