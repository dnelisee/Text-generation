/**
 * HMap — table de hachage associant des préfixes à des listes de mots.
 *
 * Structure centrale du programme : stocke, pour chaque préfixe de n mots
 * rencontré dans Madame Bovary, le multiensemble des mots qui le suivent.
 *
 * Implémentation par chaînage externe : le tableau t[] contient des listes
 * d'entrées (EntryList). En cas de collision (deux préfixes avec le même
 * indice de hachage), les entrées cohabitent dans la même liste chaînée.
 *
 * Redimensionnement automatique : quand le taux de remplissage dépasse 75%,
 * le tableau est doublé et toutes les entrées sont redistribuées (rehash).
 */
public class HMap {

    static int defaultLength = 20; // taille initiale par défaut du tableau

    EntryList[] t;  // tableau de listes d'entrées (une liste par case de hachage)
    int nbEntries;  // nombre total d'entrées distinctes stockées dans la table

    /** Construit une table avec la taille par défaut (20 cases). */
    HMap() {
        this(defaultLength);
    }

    /** Construit une table avec n cases. */
    HMap(int n) {
        nbEntries = 0;
        t = new EntryList[n];
    }

    /**
     * Recherche et retourne la liste de mots associée au préfixe key.
     * Calcule l'indice de hachage, puis parcourt la liste de collisions
     * pour trouver l'entrée dont la clé est égale à key (via Prefix.eq).
     * Retourne null si key n'est pas dans la table.
     */
    WordList find(Prefix key) {
        int h = key.hashCode(t.length);
        for (EntryList e = t[h]; e != null; e = e.next) {
            if (Prefix.eq(e.head.key, key)) {
                return e.head.value;
            }
        }
        return null;
    }

    /**
     * Ajoute le mot w à la liste associée à key.
     * - Si key existe déjà : ajoute w à sa WordList existante.
     * - Sinon : crée une nouvelle entrée avec une WordList contenant w,
     *   et l'insère dans la case de hachage correspondante.
     * Ne déclenche pas de rehachage (voir add).
     */
    void addSimple(Prefix key, String w) {
        WordList wordList = find(key);
        if (wordList != null) {
            wordList.addLast(w);
        } else {
            int h = key.hashCode(t.length);

            Entry entry = new Entry(key, new WordList(w, new WordList()));
            if (t[h] == null) {
                t[h] = new EntryList(entry, null);
            } else {
                EntryList.addLast(entry, t[h]);
            }
            nbEntries += 1;
        }
    }

    /**
     * Redistribue toutes les entrées existantes dans un nouveau tableau de taille n.
     * Chaque entrée est réinsérée à son nouvel indice de hachage (hashCode(n)).
     * Appelé automatiquement par add() quand le taux de remplissage dépasse 75%.
     */
    void rehash(int n) {
        EntryList[] tab = new EntryList[n];

        for (int i = 0; i < t.length; i++) {
            for (EntryList e = t[i]; e != null; e = e.next) {

                Entry entry = e.head;
                int h = entry.key.hashCode(n);
                if (tab[h] == null) {
                    tab[h] = new EntryList(entry, null);
                } else {
                    EntryList.addLast(entry, tab[h]);
                }

            }
        }

        t = tab;
    }

    /**
     * Version améliorée de addSimple : déclenche un rehachage si nécessaire.
     * Si nbEntries >= 75% de la taille du tableau, le tableau est doublé
     * avant l'insertion pour maintenir des performances de recherche en O(1) amorti.
     */
    void add(Prefix key, String w) {
        if (nbEntries >= (int) (0.75 * t.length)) {
            rehash(2 * t.length);
        }

        addSimple(key, w);
    }

    /** Affiche le contenu de toutes les cases de la table (pour le débogage). */
    void print() {
        for (int i = 0; i < t.length; i++) {
            EntryList.display(t[i]);
            System.out.println();
        }
    }

    public static void main(String[] args) {

    }

}
