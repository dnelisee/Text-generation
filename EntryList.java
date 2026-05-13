/**
 * EntryList — liste chaînée d'objets Entry.
 *
 * Utilisée pour gérer les collisions dans la table de hachage HMap :
 * chaque case du tableau t[] de HMap pointe vers une EntryList,
 * qui contient toutes les entrées dont le préfixe a le même indice de hachage.
 * La recherche d'une entrée se fait en parcourant cette liste et en comparant
 * les préfixes avec Prefix.eq().
 */
public class EntryList {
    Entry head;     // entrée stockée dans ce maillon
    EntryList next; // maillon suivant, ou null si fin de chaîne

    EntryList(Entry head, EntryList next) {
        this.head = head; 
        this.next = next; 
    }

    /**
     * Ajoute une entrée e en queue de la liste list.
     * Précondition : list != null.
     * Utilisé par HMap pour insérer une nouvelle entrée dans une case déjà occupée.
     */
   static void addLast(Entry e, EntryList list) {
        if (list == null)
            return;

        EntryList last = list;
        while (last.next != null) {
            last = last.next;
        }

        last.next = new EntryList(e, null);
    }

    /**
     * Affiche les clés (préfixes) de toutes les entrées de la liste.
     * Utilisé par HMap.print() pour le débogage.
     */
    static void display(EntryList list) {
        for(EntryList e = list; e!= null; e = e.next) {
            e.head.key.display();
            System.out.print(" --> ");
        } 
    }
}
