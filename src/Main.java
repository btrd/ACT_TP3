public class Main {
	public static void main(String[] args){
    int m = 127;
    int n = 127;
    Integer[][][][] myDynamicArray = new Integer[m + 1][n + 1][m + 1][n + 1];
    // DeadSquare myExo = new DeadSquare(m, n, 48, 52, myDynamicArray);
    // System.out.println(myExo.res);
    // DeadSquare myExo2 = new DeadSquare(m, n, 48, 52, myExo.myDynamicArray);
    // System.out.println(myExo2.res);
    DeadSquare myExo;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        myExo = new DeadSquare(m, n, i, j, myDynamicArray);
        if (myExo.res == 127) {
          System.out.println("FOOOOOOUUUUUUND i:" + i + "; j:" + j);
        } else {
          System.out.println("i:" + i + "; j:" + j);
        }
        myDynamicArray = myExo.myDynamicArray;
      }
    }
	}
}
