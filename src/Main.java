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

    Integer nb_col = 100;
    Integer nb_row = 100;

    Integer i = 48;
    Integer j = 52;

    DeadSquare myExo = new DeadSquare(nb_col, nb_row);

    res = myExo.getValue(nb_col, nb_row, i, j);
    System.out.println(res);
  }

  static public void find127() {
    Integer res = 0;

    Integer nb_col = 127;
    Integer nb_row = 127;

    ArrayList<Integer> res_ij = new ArrayList<Integer>();
    DeadSquare myExo = new DeadSquare(nb_col, nb_row);

    for (Integer i = 0; i < nb_col; i++) {
      System.out.println("i:" + i + "; j:0");
      for (Integer j = 0; j < nb_row; j++) {
        res = myExo.getValue(nb_col, nb_row, i, j);
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
