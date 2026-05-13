import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * WordReader — lecteur de mots à partir d'un fichier texte.
 *
 * Fournit une interface simple pour lire un fichier mot par mot (token par token).
 * Utilisé dans Bovary.buildTable() pour parcourir chacun des 35 chapitres
 * de Madame Bovary. Le fichier est attendu en UTF-8.
 *
 * Les sauts de paragraphe sont déjà encodés comme le token "<PAR>" dans les
 * fichiers sources ; WordReader les lit comme n'importe quel autre mot.
 */
class WordReader {
  private Scanner scanner; // scanner Java sous-jacent, lit le fichier token par token

  /**
   * Ouvre le fichier filename en lecture UTF-8.
   * Lève une RuntimeException si le fichier est introuvable.
   */
  public WordReader(String filename) {
    try {
      this.scanner = new Scanner(new File(filename), "UTF-8");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /** Constructeur alternatif pour lire depuis n'importe quelle source Readable (ex. System.in). */
  public WordReader(Readable in) {
    this.scanner = new Scanner(in);
  }

  /**
   * Lit et retourne le prochain mot du fichier.
   * Retourne null si le fichier a été entièrement lu ou si le scanner est fermé.
   */
  public String read() {
    if (this.scanner == null)
      return null;

    if (this.scanner.hasNext())
      return scanner.next();

    return null;
  }

  /**
   * Point d'entrée de test : affiche tous les mots du fichier passé en argument,
   * encadrés de crochets, ainsi que le nombre total de mots.
   * Usage : java WordReader bovary/01.txt
   */
  public static void main(String[] arg) {
    final WordReader wr = new WordReader(arg[0]);
    int counter = 0;

    for (String w = wr.read(); w != null; w = wr.read()) {
      System.out.println("[" + w + "]");
      counter++;
    }
    System.out.println(counter);
  }
}
