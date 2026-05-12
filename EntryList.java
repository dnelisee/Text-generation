public class EntryList {
    Entry head; 
    EntryList next; 

    EntryList(Entry head, EntryList next) {
        this.head = head; 
        this.next = next; 
    }

   static void addLast(Entry e, EntryList list) {
        if (list == null)
            return;

        EntryList last = list;
        while (last.next != null) {
            last = last.next;
        }

        last.next = new EntryList(e, null);
    }

    static void display(EntryList list) {
        for(EntryList e = list; e!= null; e = e.next) {
            e.head.key.display();
            System.out.print(" --> ");
        } 
    }
}
