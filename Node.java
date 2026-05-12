class Node {
    String head;
    Node next;

    Node(String head, Node next) {
        this.head = head;
        this.next = next;
    }

    static int lengthRec(Node l) {
        if (l == null)
            return 0;

        return (1 + lengthRec(l.next));
    }

    static int length(Node l) {

        int len = 0;

        for (Node cur = l; cur != null; cur = cur.next) {
            len++;
        }

        return len;
    }

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

    static void addLast(String s, Node l) {
        if (l == null)
            return;

        Node last = l;
        while (last.next != null) {
            last = last.next;
        }

        last.next = new Node(s, null);
    }

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

    static Node insertionSort(Node l) {
        Node sorted = null;
        for (Node cur = l; cur != null; cur = cur.next) {
            sorted = insert(cur.head, sorted); 
        }
        return sorted;
    }

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