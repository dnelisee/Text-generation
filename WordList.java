public class WordList {
    public static WordList foobar = new WordList("foo", new WordList("bar", new WordList("baz", new WordList())));

    Node content;

    WordList() {
        content = null;
    }

    WordList(String head, WordList next) {
        content = new Node(head, next.content);
    }

    WordList(String[] t) {

        content = null;
        if (t == null) return; 
        for (String s : t) {
            this.addLast(s);
        }
    }

    int length() {
        if (content == null)
            return 0;
        else
            return Node.length(content);
    }

    public String toString() {
        return Node.makeString(content);
    }

    void addFirst(String w) {
        if (this.content == null) {
            this.content = new Node(w, null);
        } else
            this.content = new Node(w, this.content);
    }

    void addLast(String w) {
        if (this.content == null) {
            this.content = new Node(w, null);
        } else
            Node.addLast(w, this.content);
    }

    String removeFirst() {
        if (content == null)
            return null;

        String res = content.head;
        content = content.next;

        return res;
    }

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

    void insert(String s) {
        content = Node.insert(s, content);
    }

    void insertionSort() {
        content = Node.insertionSort(content);
    }

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
