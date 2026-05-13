/**
 * WordList — liste chaînée de mots, avec accès de haut niveau.
 *
 * Encapsule un Node pour représenter un multiensemble de mots :
 * c'est la valeur associée à chaque préfixe dans la table de hachage HMap.
 * Contrairement à Node, WordList gère correctement les cas limites (liste vide,
 * insertion/suppression en tête ou en queue) sans exposer les maillons.
 */
public class WordList {
    // Instance de test : ["foo", "bar", "baz"]
    public static WordList foobar = new WordList("foo", new WordList("bar", new WordList("baz", new WordList())));

    Node content; // tête de la chaîne de maillons, null si liste vide

    /** Construit une liste vide. */
    WordList() {
        content = null;
    }

    /**
     * Construit une liste à partir d'un premier mot et d'une liste existante.
     * Le nouveau mot est placé en tête de la chaîne de next.
     */
    WordList(String head, WordList next) {
        content = new Node(head, next.content);
    }

    /**
     * Construit une liste à partir d'un tableau de chaînes.
     * L'ordre des éléments du tableau est conservé.
     */
    WordList(String[] t) {

        content = null;
        if (t == null) return; 
        for (String s : t) {
            this.addLast(s);
        }
    }

    /** Retourne le nombre de mots dans la liste. */
    int length() {
        if (content == null)
            return 0;
        else
            return Node.length(content);
    }

    /** Retourne une représentation lisible de la liste, ex. "[foo, bar, baz]". */
    public String toString() {
        return Node.makeString(content);
    }

    /** Ajoute w en tête de liste. */
    void addFirst(String w) {
        if (this.content == null) {
            this.content = new Node(w, null);
        } else
            this.content = new Node(w, this.content);
    }

    /** Ajoute w en queue de liste. */
    void addLast(String w) {
        if (this.content == null) {
            this.content = new Node(w, null);
        } else
            Node.addLast(w, this.content);
    }

    /**
     * Retire et retourne le premier mot de la liste.
     * Retourne null si la liste est vide.
     */
    String removeFirst() {
        if (content == null)
            return null;

        String res = content.head;
        content = content.next;

        return res;
    }

    /**
     * Retire et retourne le dernier mot de la liste.
     * Retourne null si la liste est vide.
     */
    String removeLast() {
        if (content == null)
            return null;
        if (content.next == null) {
            String res = content.head;
            content = null;
            return res;
        }

        Node end = content;
        while (end.next.next != null)
            end = end.next;

        String res = end.next.head;

        end.next = null;

        return res;
    }

    /** Insère s dans la liste en conservant l'ordre lexicographique. */
    void insert(String s) {
        content = Node.insert(s, content);
    }

    /** Trie la liste par ordre lexicographique via un tri par insertion (complexité quadratique). */
    void insertionSort() {
        content = Node.insertionSort(content);
    }

    /**
     * Trie la liste via un tri rapide récursif (en place).
     * Le pivot est le premier élément ; les éléments inférieurs sont regroupés
     * dans firstPart, les autres dans secondPart, puis les deux parties sont
     * triées récursivement et fusionnées.
     */
    void mergeSort() {
        /*
         * La strategie est de diviser pour regner.
         * On va proceder comme le tri rapide et choisir un
         * pivot. Puis on met avant lui tous les éléments qui lui sont inférieurs et
         * après lui tous ceux qui lui sont supérieurs. Puis fusion des deux parties.
         */
        // on traite les cas length = 0, 1
        if (this.length() < 2)
            return;
        // on prend le pivot égal au premier élément.
        // et les deux parties sont comme suit :
        // la premiere est initialement juste le premier elt ( qui est le pivot) et la
        // seconde le reste de la liste
        String pivot = this.content.head;

        WordList firstPart = new WordList(pivot, new WordList());
        WordList secondPart = new WordList();
        secondPart.content = this.content.next;

        // on parcourt secondPart et on envoit tous ceux qui sont supérieurs au pivot
        // dans firstPart
        Node before = null;
        Node cur = secondPart.content;
        while (cur != null) {
            Node next = cur.next;

            // si cur < pivot, on le deplace dans firstPart
            if (cur.head.compareTo(pivot) < 0) {
                if (before != null)
                    before.next = cur.next;
                else
                    secondPart.content = next;

                cur.next = firstPart.content;
                firstPart.content = cur;
            } else {
                before = cur;
            }
            cur = next;
        }

        // trier maintenant firstPart et SecondPart
        firstPart.mergeSort();
        secondPart.mergeSort();

        // fusionner
        this.content = Node.merge(firstPart.content, secondPart.content);
    }

    /**
     * Convertit la liste en tableau de chaînes, dans l'ordre de la liste.
     * Utilisé dans Bovary.generate() pour piocher un mot au hasard via un index entier.
     * Retourne un tableau vide si la liste est vide.
     */
    String[] toArray() {
        if (content == null) return new String[0]; 
        String[] res = new String[this.length()]; 
        int i = 0; 
        for (Node cur = content; cur != null; cur = cur.next) {
            res[i] = cur.head; 
            i++; 
        }

        return res; 
    }

    public static void main(String[] args) {
        String[] test = foobar.toArray();
        for (int i = 0; i < 3; i++) {
            System.out.print(test[i] + " ");
        }
    }
}
