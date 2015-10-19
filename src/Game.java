import java.util.Scanner;
import java.util.Random;

class Game {
  Scanner scan;
  Random r;
  int m, n, i, j;
  Boolean computer_turn;
  DeadSquare deadSquare;

  public Game() {
    scan = new Scanner(System.in);
    r = new Random();
    initialisation();

    deadSquare = new DeadSquare(m, n);
    
    loop_game();

    if (computer_turn) {
      System.out.println("VOUS AVEZ GAGNÉ :)");
    } else {
      System.out.println("Vous avez perdu :(");
    }
  }

  void initialisation() {
    System.out.println("Taille de la tablette:");
    System.out.print("\t\tm: ");
    m = scan.nextInt();
    System.out.print("\t\tn: ");
    n = scan.nextInt();
    System.out.print("Emplacement du carré de la mort:\n\t\tAléatoire (O)ui/(N)on: ");
    String alea = scan.next();

    if (alea.toLowerCase().equals("n") || alea.toLowerCase().equals("non")) {
      System.out.print("\t\ti: ");
      i = scan.nextInt();
      System.out.print("\t\tj: ");
      j = scan.nextInt();
    } else {
      i = r.nextInt(m);
      j = r.nextInt(n);
      System.out.println("\t\ti: "+i);
      System.out.println("\t\tj: "+j);
    }
    System.out.println("");
    if(r.nextInt(2) == 0) {
      System.out.println("L'ordinateur commence");
      computer_turn = true;
    } else {
      System.out.println("Vous commencez, bonne chance !");
      computer_turn = false;
    }
  }

  void loop_game() {
    while (m > 1 && n > 1) {
      System.out.println("____________________________________\n");
      System.out.println("Informations du tour");
      System.out.println("\t\t\tm: " + m + "; n: " + n);
      System.out.println("\t\t\ti: " + i + "; j: " + j);
      System.out.println("");
      if (computer_turn) {
        computerIA();
      } else {
        playerChoice();
      }
      computer_turn = !computer_turn;
    }
  }

  void computerIA() {
    System.out.println("Début du tour de l'ordinateur");

    int tmp_m, tmp_n, tmp_i, tmp_j, tmp_max;
    tmp_m = m;
    tmp_n = n;
    tmp_i = i;
    tmp_j = j;
    Boolean coupe_hori = false;
    int coupe_value = 0;
    int max = Integer.MIN_VALUE;

    for (Integer k = 1; k < m; k++) {
      System.out.print("."); //Montre que le prog est pas planté
      if (k <= i) { // on coupe avant le carré de la mort
        tmp_max = deadSquare.calcValue(m - k, n, i - k, j);
        if (tmp_max > max) {
          coupe_hori = false;
          coupe_value = k;
          max = tmp_max;
          tmp_m = m-k;
          tmp_n = n;
          tmp_i = i-k;
          tmp_j = j;
        }
      } else { // on coupe aprés
        tmp_max = deadSquare.calcValue(k, n, i, j);
        if (tmp_max > max) {
          coupe_hori = false;
          coupe_value = k;
          max = tmp_max;
          tmp_m = k;
          tmp_n = n;
          tmp_i = i;
          tmp_j = j;
        }
      }
    }
    for (Integer l = 1; l < n; l++) {
      System.out.print("."); //Montre que le prog est pas planté
      if (l <= j) { // on coupe avant le carré de la mort
        tmp_max = deadSquare.calcValue(m, n - l, i, j - l);
        if (tmp_max > max) {
          coupe_hori = true;
          coupe_value = l;
          max = tmp_max;
          tmp_m = m;
          tmp_n = n-l;
          tmp_i = i;
          tmp_j = j-l;
        }
      } else { // on coupe aprés
        tmp_max = deadSquare.calcValue(m, l, i, j);
        if (tmp_max > max) {
          coupe_hori = true;
          coupe_value = l;
          max = tmp_max;
          tmp_m = m;
          tmp_n = l;
          tmp_i = i;
          tmp_j = j;
        }
      }
    }
    System.out.println("");
    if (coupe_hori) {
      System.out.println("Ordinateur coupe horizontalement en " + coupe_value);
    } else {
      System.out.println("Ordinateur coupe verticalement en " + coupe_value);
    }
    m = tmp_m;
    n = tmp_n;
    i = tmp_i;
    j = tmp_j;
  }

  void playerChoice() {
    System.out.println("Début de votre tour:");

    System.out.print("\t\tCoupe (h)orizontale ou (v)ertical ? ");
    String alea = scan.next();
    Boolean coupe_hori;
    if (alea.toLowerCase().equals("h") || alea.toLowerCase().equals("horizontale")) {
      coupe_hori = true;
    } else {
      coupe_hori = false;
    }
    System.out.print("\t\tValeur (max " + (m-1) + ") ? ");
    int coupe_value = scan.nextInt();
    if (coupe_hori) {
      if (coupe_value <= i) {
        m = m - coupe_value;
        i = i - coupe_value;
      } else {
        m = coupe_value;
      }
    } else {
      if (coupe_value <= j) {
        n = n - coupe_value;
        j = j - coupe_value;
      } else {
        n = coupe_value;
      }
    }
  }
}