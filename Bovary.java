public class Bovary {
    
    static HMap buildTable(String[] files, int n) {
        HMap table = new HMap();
        for (String file : files) {
            WordReader wr = new WordReader(file); 
            Prefix prefix = new Prefix(n);

            for (String w = wr.read(); w != null; w = wr.read()) {
                table.add(prefix, w);
                prefix = prefix.addShift(w); 
            }
            table.add(prefix, Prefix.end);
        }

        return table; 
    }

    static void generate(HMap t, int n) {
        Prefix prefix = new Prefix(n); 
        String w = Prefix.end; 
        WordList wl;
        while(true) {
            wl = t.find(prefix); 

            if(wl == null) {
                break;  
            } else {
                int i = (int) (wl.length() * Math.random());
                w = wl.toArray()[i]; 
            }

            if (w.equals(Prefix.end)) {
                System.out.println();
                break; 
            } 
            if (w.equals(Prefix.par)) 
                System.out.println();
            else {
                System.out.print(w + " ");
            }

            prefix = prefix.addShift(w); 
        }
    }

    public static void main(String[] args) {
        String[] files = new String[35]; 

        for (int i = 1; i < 10; i++) {
            files[i - 1] = "bovary/0" + i + ".txt"; 
        }
        for (int i = 10; i <= files.length; i++) {
            files[i - 1] = "bovary/" + i + ".txt"; 
        }

        HMap table = buildTable(files, 3); 
        generate(table, 3);
    }
}
