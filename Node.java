/**
 * Node — maillon d'une liste chaînée de chaînes de caractères.
 *
 * Structure de base utilisée par WordList pour représenter les multiensembles
 * de mots associés à chaque préfixe dans la table de hachage.
 *
 * Une liste est représentée par une référence vers son premier maillon ;
 * le dernier maillon a next == null.
 */
class Node {
    String head; // valeur stockée dans ce maillon
    Node next;   // maillon suivant, ou null si fin de chaîne

    Node(String head, Node next) {
        this.head = head;
        this.next = next;
    }

    /**
     * Calcule la longueur de la chaîne de manière récursive.
     * À éviter sur de longues listes (risque de StackOverflowError) ;
     * préférer length() dans ce cas.
     */
    static int lengthRec(Node l) {
        if (l == null)
            return 0;

        return (1 + lengthRec(l.next));
    }

    /**
     * Calcule la longueur de la chaîne de manière itérative.
     * Version sûre, sans risque de débordement de pile.
     */
    static int length(Node l) {

        int len = 0;

        for (Node cur = l; cur != null; cur = cur.next) {
            len++;
        }

        return len;
    }

    /**
     * Construit une représentation lisible de la chaîne.
     * Format : "[foo, bar, baz]"
     */
    static String makeString(Node l) {
        String res = new String("[");

        for (Node cur = l; cur != null; cur = cur.next) {
            if (cur.next == null)
                res += cur.head;
            else
                res += cur.head + ", ";
        }

        res += "]";

        return res;
    }

    /**
     * Ajoute la chaîne s en fin de chaîne l.
     * Précondition : l != null (ne gère pas l'insertion dans une chaîne vide,
     * car on ne peut pas modifier la référence passée en argument).
     * Utiliser WordList.addLast() pour le cas général.
     */
    static void addLast(String s, Node l) {
        if (l == null)
            return;

        Node last = l;
        while (last.next != null) {
            last = last.next;
        }

        last.next = new Node(s, null);
    }

    /**
     * Retourne une copie indépendante de la chaîne l.
     * Complexité linéaire (pas d'appel à addLast pour éviter le quadratique).
     */
    static Node copy(Node l) {
        if (l == null)
            return null;

        Node res = new Node(l.head, null);

        Node before = res;
        for (Node cur = l.next; cur != null; cur = cur.next) {
            Node newNode = new Node(cur.head, null);
            before.next = newNode;
            before = newNode;
        }
        return res;
    }

    /**
     * Insère s dans la chaîne triée l en conservant l'ordre lexicographique.
     * Retourne la nouvelle tête de chaîne (qui peut changer si s est inséré en tête).
     */
    static Node insert(String s, Node l) {

        if (l == null)
            return new Node(s, null);
        if (s.compareTo(l.head) <= 0)
            return new Node(s, l);

        Node before = l;
        Node cur = l.next;
        while (cur != null && s.compareTo(cur.head) > 0) {
            before = cur;
            cur = cur.next;
        }
        before.next = new Node(s, cur);
        return l;
    }

    /**
     * Trie la chaîne l par ordre lexicographique via un tri par insertion.
     * Complexité quadratique — acceptable pour de petites listes.
     * Retourne la tête de la nouvelle chaîne triée.
     */
    static Node insertionSort(Node l) {
        Node sorted = null;
        for (Node cur = l; cur != null; cur = cur.next) {
            sorted = insert(cur.head, sorted); 
        }
        return sorted;
    }

    /**
     * Fusionne deux chaînes triées l1 et l2 en une seule chaîne.
     * Attention : modifie l1 en place (enchaîne l2 à la fin de l1).
     * Retourne la tête de la chaîne fusionnée.
     */
    static Node merge(Node l1, Node l2) {

        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        Node end1 = l1;
        while (end1.next != null)
            end1 = end1.next;

        end1.next = l2;

        return l1;

    }

    public static void main(String[] args) {

        // Node foobar = new Node("bar", new Node("baz", new Node("foot", null)));
        // System.out.println(makeString(foobar));
        // System.out.println(makeString(insert("alex", foobar)));

        // Node foobar = new Node("foot", new Node("bar", new Node("baz", null)));

        // System.out.println(makeString(foobar));
        // System.out.println(makeString(insertionSort(foobar)));

        // Node f1 = new Node("foot1", new Node("bar1", new Node("baz1", null)));
        // Node f2 = new Node("foot2", new Node("bar2", new Node("baz2", null)));

        // System.out.println(makeString(merge(f1, f2)));
    }

}
