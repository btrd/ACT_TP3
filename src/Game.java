import java.util.Scanner;
import java.util.Random;

class Game {
  Scanner scan;
  Random r;
  Integer nb_row, nb_col, x_death, y_death;
  Boolean computer_turn;
  DeadSquare deadSquare;

  public Game() {
    scan = new Scanner(System.in);
    r = new Random();
    initialisation();

    deadSquare = new DeadSquare(nb_row, nb_col);
    
    loop_game();

    showChocolate();
    
    if (computer_turn) {
      System.out.println("VOUS AVEZ GAGNÉ :)");
    } else {
      System.out.println("Vous avez perdu :(");
    }
  }

  void initialisation() {
    System.out.println("Taille de la tablette de chocolat");

    nb_col = 0;
    while (nb_col < 1) {
      System.out.print("\t\tNombre de colonne: ");
      nb_col = scan.nextInt();
    }

    nb_row = 0;
    while (nb_row < 1) {
      System.out.print("\t\tNombre de ligne: ");
      nb_row = scan.nextInt();
    }
    
    showChocolate();
    
    System.out.print("Emplacement du carré de la mort:\n\t\tAléatoire (O)ui/(N)on: ");
    String alea = scan.next();

    if (alea.toLowerCase().equals("n") || alea.toLowerCase().equals("non")) {
      x_death = -1;
      while (x_death < 0 || x_death >= nb_col) {
        System.out.print("\t\tColonne numéro: ");
        x_death = scan.nextInt();
      }

      y_death = -1;
      while (y_death < 0 || y_death >= nb_row) {
        System.out.print("\t\tLigne numéro: ");
        y_death = scan.nextInt();
      }
    } else {
      x_death = r.nextInt(nb_col);
      y_death = r.nextInt(nb_row);
      System.out.println("\t\tColonne numéro: " + x_death);
      System.out.println("\t\tLigne numéro: " + y_death);
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
    while (nb_row > 1 || nb_col > 1) {
      showChocolate();
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

    Integer tmp_max;
    Integer tmp_nb_row = nb_row;
    Integer tmp_nb_col = nb_col;
    Integer tmp_x_death = x_death;
    Integer tmp_y_death = y_death;
    Boolean coupe_vert = false;
    Integer coupe_value = 0;
    Integer max = Integer.MIN_VALUE;

    for (Integer k = 1; k < nb_col; k++) {
      if (k <= x_death) {
        tmp_max = deadSquare.calcValue(nb_col - k, nb_row, x_death - k, y_death);
        if (tmp_max > max) {
          coupe_vert = true;
          coupe_value = k;
          max = tmp_max;
          tmp_nb_col = nb_col - k;
          tmp_nb_row = nb_row;
          tmp_x_death = x_death - k;
          tmp_y_death = y_death;
        }
      } else {
        tmp_max = deadSquare.calcValue(k, nb_row, x_death, y_death);
        if (tmp_max > max) {
          coupe_vert = true;
          coupe_value = k;
          max = tmp_max;
          tmp_nb_col = k;
          tmp_nb_row = nb_row;
          tmp_x_death = x_death;
          tmp_y_death = y_death;
        }
      }
    }
    for (Integer l = 1; l < nb_row; l++) {
      if (l <= y_death) {
        tmp_max = deadSquare.calcValue(nb_col, nb_row - l, x_death, y_death - l);
        if (tmp_max > max) {
          coupe_vert = false;
          coupe_value = l;
          max = tmp_max;
          tmp_nb_col = nb_col;
          tmp_nb_row = nb_row - l;
          tmp_x_death = x_death;
          tmp_y_death = y_death - l;
        }
      } else {
        tmp_max = deadSquare.calcValue(nb_col, l, x_death, y_death);
        if (tmp_max > max) {
          coupe_vert = false;
          coupe_value = l;
          max = tmp_max;
          tmp_nb_col = nb_col;
          tmp_nb_row = l;
          tmp_x_death = x_death;
          tmp_y_death = y_death;
        }
      }
    }
    System.out.println("");
    if (coupe_vert) {
      System.out.println("Ordinateur coupe verticalement en " + coupe_value);
    } else {
      System.out.println("Ordinateur coupe horizontalement en " + coupe_value);
    }
    nb_col = tmp_nb_col;
    nb_row = tmp_nb_row;
    x_death = tmp_x_death;
    y_death = tmp_y_death;
  }

  void playerChoice() {
    Boolean coupe_vert;

    System.out.println("Début de votre tour:");
    if (nb_row < 2) {
      System.out.println("\t\tCoupe vertical");
      coupe_vert = true;
    } else if (nb_col < 2) {
      System.out.println("\t\tCoupe horizontale");
      coupe_vert = false;
    } else {
      System.out.print("\t\tCoupe (h)orizontale ou (v)ertical ? ");
      String alea = scan.next();
      if (alea.toLowerCase().equals("v") || alea.toLowerCase().equals("vertical")) {
        coupe_vert = true;
      } else {
        coupe_vert = false;
      }
    }

    Integer coupe_value = 0;
    if (coupe_vert) {
      while (coupe_value < 1 || coupe_value >= nb_col) {
        System.out.print("\t\tColonne numéro: ");
        coupe_value = scan.nextInt();
      }
    } else {
      while (coupe_value < 1 || coupe_value >= nb_row) {
        System.out.print("\t\tLigne numéro: ");
        coupe_value = scan.nextInt();
      }
    }

    if (coupe_vert) {
      if (coupe_value <= x_death) {
        nb_col = nb_col - coupe_value;
        x_death = x_death - coupe_value;
      } else {
        nb_col = coupe_value;
      }
    } else {
      if (coupe_value <= y_death) {
        nb_row = nb_row - coupe_value;
        y_death = y_death - coupe_value;
      } else {
        nb_row = coupe_value;
      }
    }
  }

  void showChocolate() {
    System.out.println("");
    System.out.print("\t  ");
    for (Integer k = 0; k < nb_col; k++ ) {
      if (k <= 10) {
        System.out.print(" ");
      }
      System.out.print(" " + k + " ");
    }
    System.out.println("");
    for (Integer k = 0; k < nb_row; k++ ) {
      System.out.print("\t" + k + " ");
      if (k < 10)
        System.out.print(" ");

      for (Integer l = 0; l < nb_col; l++ ) {
        if (l == x_death && k == y_death) {
          System.out.print("[X]");
        } else {
          System.out.print("[ ]");
        }
        System.out.print(" ");
      }
      System.out.println("");
    }
    System.out.println("");
  }
}
