/**
 * Entry — paire (clé, valeur) stockée dans la table de hachage HMap.
 *
 * Chaque entrée associe un préfixe (séquence de n mots) au multiensemble
 * des mots qui le suivent dans le texte de Madame Bovary.
 * Plusieurs entrées peuvent cohabiter dans la même case du tableau de HMap
 * en cas de collision de hachage ; elles sont alors chaînées via EntryList.
 */
public class Entry {
    Prefix key;    // préfixe de n mots, sert de clé de recherche
    WordList value; // multiensemble des mots suivant ce préfixe dans le texte

    Entry(Prefix key, WordList value) {
        this.key = key; 
        this.value = value; 
    }
}
