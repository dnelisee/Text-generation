/**
 * Prefix — clé de la table de hachage HMap.
 *
 * Représente une séquence de n mots consécutifs du texte.
 * Lors de la construction de la table, le préfixe "glisse" sur le texte :
 * à chaque nouveau mot lu, le premier mot du préfixe est éjecté et le nouveau
 * mot est ajouté en queue (voir addShift).
 *
 * Tokens spéciaux :
 *   <START> : remplace les mots manquants en début de chapitre (préfixe initial)
 *   <END>   : marque la fin d'un chapitre
 *   <PAR>   : marque un saut de paragraphe (traité comme un mot ordinaire)
 */
public class Prefix {
    String[] t; // tableau de n mots constituant le préfixe, dans l'ordre

    final static String start = "<START>", end = "<END>", par = "<PAR>";

    /**
     * Construit le préfixe initial de taille n, entièrement rempli de <START>.
     * C'est le point de départ de la construction de la table et de la génération.
     * Si n <= 0, crée un préfixe vide.
     */
    Prefix(int n) {
        n = n < 0 ? 0 : n;
        t = new String[n];
        for (int i = 0; i < n; i++)
            t[i] = start;
    }

    /** Construit un préfixe à partir d'un tableau de mots existant. */
    Prefix(String[] t) {
        this.t = t;
    }

    /**
     * Compare deux préfixes mot à mot (égalité sémantique, pas de référence).
     * Utilisé par HMap.find() pour identifier la bonne entrée dans une liste de collision.
     */
    static boolean eq(Prefix p1, Prefix p2) {
        if (p1.t.length != p2.t.length)
            return false;

        for (int i = 0; i < p1.t.length; i++) {
            if (!p1.t[i].equals(p2.t[i]))
                return false;
        }
        return true;
    }

    /**
     * Retourne un nouveau préfixe obtenu en décalant d'un cran vers la gauche
     * et en ajoutant w en dernière position.
     * Ex. : [w1, w2, w3].addShift("w4") → [w2, w3, w4]
     *
     * Ne modifie pas this (immuabilité intentionnelle).
     */
    Prefix addShift(String w) {
        String[] tab;

        if (t == null || t.length == 0) {
            tab = new String[] {w}; 
        } else {
            tab = new String[t.length]; 
            for (int i = 1; i < t.length; i++) {
                tab[i - 1] = t[i];
            }
            tab[t.length - 1] = w;
        }
        return new Prefix(tab);
    }

    /**
     * Calcule la valeur de hachage du préfixe en combinant les hashcodes
     * de chaque mot via la formule : h = 37 * h + mot.hashCode()
     * Surcharge de Object.hashCode() — requise pour la cohérence avec equals.
     */
    public int hashCode() {
        int h = 0; 
        for (int i = 0; i < t.length; i++) {
            h = 37 * h + t[i].hashCode(); 
        }
        return h; 
    }
    
    /**
     * Ramène la valeur de hachage dans l'intervalle [0, n-1].
     * Gère le cas des valeurs négatives (% en Java peut retourner un négatif).
     */
    int hashCode(int n) {
        int h = this.hashCode(); 

        h = h % n; 
        while(h < 0) h += n; 
        
        return h; 
    }

    /** Affiche les mots du préfixe séparés par des espaces (pour le débogage). */
    void display() {
        for (String s : t)
            System.out.print(s + " ");
    }

    public static void main(String[] args) {
        String[] tab = {"w2", "w"};
        Prefix p1 = new Prefix(tab);
        System.out.println(p1.hashCode());
    }
}
