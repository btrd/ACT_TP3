import java.util.ArrayList;

public class Main {
	public static void main(String[] args){
    // onCase();
    // find127();

    // Jeux
    Game session = new Game();
	}
  static public void onCase() {
    Integer res = 0;

    Integer m = 127;
    Integer n = 127;

    Integer i = 40;
    Integer j = 52;

    DeadSquare myExo = new DeadSquare(m, n);

    res = myExo.getValue(m, n, i, j);
    System.out.println(res);
  }

  static public void find127() {
    Integer res = 0;

    Integer m = 127;
    Integer n = 127;

    ArrayList<Integer> res_ij = new ArrayList<Integer>();
    DeadSquare myExo = new DeadSquare(m, n);

    for (Integer i = 0; i < m; i++) {
      System.out.println("i:" + i + "; j:0");
      for (Integer j = 0; j < n; j++) {
        res = myExo.getValue(m, n, i, j);
        if (res == 127) {
          res_ij.add(i);
          res_ij.add(j);
          System.out.println("FOUND i:" + i + "; j:" + j);
        }
      }
    }
    System.out.println("");
    System.out.println("Résultats:");
    for (int k = 0; k < res_ij.size(); k++) {
      System.out.print("i: " + res_ij.get(k));
      k++;
      System.out.println(";j: " + res_ij.get(k));
    }
  }
}
