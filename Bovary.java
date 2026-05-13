/**
 * Bovary — point d'entrée du générateur de texte par chaînes de Markov.
 *
 * Ce programme génère un pseudo "36e chapitre" de Madame Bovary en deux phases :
 *
 *   1. buildTable : lit les 35 chapitres et construit une table de hachage
 *      associant chaque préfixe de n mots au multiensemble des mots qui le suivent.
 *
 *   2. generate : à partir du préfixe initial <START>×n, choisit aléatoirement
 *      un mot successeur, l'affiche, fait glisser le préfixe, et recommence
 *      jusqu'à tirer le token <END>.
 *
 * Paramètre clé : n (longueur des préfixes). Plus n est grand, plus le texte
 * ressemble à Flaubert ; plus n est petit, plus il est incohérent.
 */
public class Bovary {
    static int n = 3; // valeur par défaut de la taille des préfixes pour l'éxécution 
    
    /**
     * Construit la table de hachage à partir d'un ensemble de fichiers texte.
     *
     * Pour chaque fichier (chapitre) :
     *   - Le préfixe est initialisé à [<START>, ..., <START>] (n fois).
     *   - Pour chaque mot w lu : w est ajouté à la liste du préfixe courant,
     *     puis le préfixe glisse d'un cran (addShift).
     *   - En fin de chapitre, <END> est ajouté au dernier préfixe.
     *
     * @param files tableau des chemins vers les 35 fichiers chapitres
     * @param n     taille des préfixes (longueur des séquences clés)
     * @return la table de hachage complète
     */
    static HMap buildTable(String[] files, int n) {
        HMap table = new HMap();
        for (String file : files) {
            WordReader wr = new WordReader(file); 
            Prefix prefix = new Prefix(n); // préfixe initial : [<START>, ..., <START>]

            for (String w = wr.read(); w != null; w = wr.read()) {
                table.add(prefix, w);      // associe w au préfixe courant
                prefix = prefix.addShift(w); // fait glisser le préfixe d'un mot
            }
            table.add(prefix, Prefix.end); // marque la fin du chapitre
        }

        return table; 
    }

    /**
     * Génère et affiche un texte aléatoire à partir de la table t.
     *
     * À chaque étape :
     *   - Recherche la liste de mots associée au préfixe courant.
     *   - Tire un mot au hasard (loi uniforme sur la liste).
     *   - Si c'est <END>, termine.
     *   - Si c'est <PAR>, insère un saut de ligne (paragraphe).
     *   - Sinon, affiche le mot suivi d'un espace.
     *   - Fait glisser le préfixe.
     *
     * @param t table de hachage construite par buildTable
     * @param n taille des préfixes (doit être identique à celui utilisé pour buildTable)
     */
    static void generate(HMap t, int n) {
        Prefix prefix = new Prefix(n); // préfixe de départ : [<START>, ..., <START>]
        String w = Prefix.end; 
        WordList wl;
        while(true) {
            wl = t.find(prefix); // cherche les successeurs possibles du préfixe courant

            if(wl == null) {
                break;  // préfixe inconnu : ne devrait pas arriver avec un texte bien formé
            } else {
                int i = (int) (wl.length() * Math.random()); // index aléatoire uniforme
                w = wl.toArray()[i]; 
            }

            if (w.equals(Prefix.end)) {
                System.out.println(); // fin de texte
                break; 
            } 
            if (w.equals(Prefix.par)) 
                System.out.println(); // saut de paragraphe
            else {
                System.out.print(w + " ");
            }

            prefix = prefix.addShift(w); // fait glisser le préfixe d'un mot
        }
    }

    /**
     * Point d'entrée principal.
     * Charge les 35 chapitres (bovary/01.txt … bovary/35.txt),
     * construit la table avec des préfixes de taille 3, puis génère le texte.
     */
    public static void main(String[] args) {

        String[] files = new String[35]; 

        for (int i = 1; i < 10; i++) {
            files[i - 1] = "bovary/0" + i + ".txt"; // chapitres 01 à 09
        }
        for (int i = 10; i <= files.length; i++) {
            files[i - 1] = "bovary/" + i + ".txt"; // chapitres 10 à 35
        }

        if (args.length != 0) {
            try {
                n = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                throw new RuntimeException("Please the parameter must be an integer");
            }
        }

        HMap table = buildTable(files, n);  // préfixes de n mots
        generate(table, n);
    }
}
