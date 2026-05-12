public class HMap {

    static int defaultLength = 20;

    EntryList[] t;
    int nbEntries;

    HMap() {
        this(defaultLength);
    }

    HMap(int n) {
        nbEntries = 0;
        t = new EntryList[n];
    }

    WordList find(Prefix key) {
        int h = key.hashCode(t.length);
        for (EntryList e = t[h]; e != null; e = e.next) {
            if (Prefix.eq(e.head.key, key)) {
                return e.head.value;
            }
        }
        return null;
    }

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

    void add(Prefix key, String w) {
        if (nbEntries >= (int) (0.75 * t.length)) {
            rehash(2 * t.length);
        }

        addSimple(key, w);
    }

    void print() {
        for (int i = 0; i < t.length; i++) {
            EntryList.display(t[i]);
            System.out.println();
        }
    }

    public static void main(String[] args) {

    }

}
