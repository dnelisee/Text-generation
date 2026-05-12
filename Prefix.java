public class Prefix {
    String[] t;

    final static String start = "<START>", end = "<END>", par = "<PAR>";

    Prefix(int n) {
        n = n < 0 ? 0 : n;
        t = new String[n];
        for (int i = 0; i < n; i++)
            t[i] = start;
    }

    Prefix(String[] t) {
        this.t = t;
    }

    static boolean eq(Prefix p1, Prefix p2) {
        if (p1.t.length != p2.t.length)
            return false;

        for (int i = 0; i < p1.t.length; i++) {
            if (!p1.t[i].equals(p2.t[i]))
                return false;
        }
        return true;
    }

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

    public int hashCode() {
        int h = 0; 
        for (int i = 0; i < t.length; i++) {
            h = 37 * h + t[i].hashCode(); 
        }
        return h; 
    }
    
    int hashCode(int n) {
        int h = this.hashCode(); 

        h = h % n; 
        while(h < 0) h += n; 
        
        return h; 
    }

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
