# Madame Bovary — Markov Chain Text Generator

A Java implementation of a Markov chain–based text generator, trained on the 35 chapters of Gustave Flaubert's _Madame Bovary_. The program produces a plausible "36th chapter" by learning statistical word sequences from the source text.


> **Note:** The text of _Madame Bovary_ is available under [this licence](./TextLicence.txt) (TextLicence.txt)

---

## Concept

Given an integer `n ≥ 1`, the generator ensures that every subsequence of `n+1` consecutive words in the output also appears in the original novel. The larger `n` is, the closer the output reads to Flaubert — and the stranger the transitions become.

---

## Algorithm

**Phase 1 — Build the associative table**

The program reads all 35 chapters and constructs a hash map associating each _prefix_ (a sequence of `n` words) to the multiset of words that follow it in the text. Special tokens `<START>` and `<END>` delimit chapter boundaries.

**Phase 2 — Generate text**

Starting from the initial prefix `<START>, ..., <START>`, the generator repeatedly picks a random successor word from the associated multiset, prints it, and slides the prefix forward — until `<END>` is drawn.

---

## Data Structures

| Class        | Role                                                     |
| ------------ | -------------------------------------------------------- |
| `Node`       | Singly linked list node (String)                         |
| `WordList`   | Wrapper around `Node` chains; represents word multisets  |
| `Prefix`     | Fixed-size array of strings used as hash map keys        |
| `Entry`      | Key–value pair `(Prefix, WordList)`                      |
| `EntryList`  | Linked list of `Entry` objects (hash collision chaining) |
| `HMap`       | Hash map with dynamic resizing (rehash at 75% load)      |
| `WordReader` | Reads words token by token from a UTF-8 text file        |
| `Bovary`     | Entry point: builds the table and runs the generator     |

---

## Project Structure

```
.
├── bovary/
│   ├── 01.txt
│   ├── 02.txt
│   └── ... (35 chapters)
├── Bovary.java
├── Entry.java
├── EntryList.java
├── HMap.java
├── Node.java
├── Prefix.java
├── WordList.java
└── WordReader.java
```

---

## Usage

### Compile

```bash
javac -d bin *.java 
```

### Run

```bash
java -cp bin Bovary 
# or 
java -cp bin Bovary 3
```
Where you remplace `3` by the length you want for the prefixes.
That prefix length is set to `3` by default in `Bovary.java` if you don't give it.  

### Example output (`n = 10`) 
**Command :**
```bash
javac -d bin *.java && java -cp bin Bovary 10
```
**Result :**
```
Yonville-l'Abbaye (ainsi nommé à cause d'une ancienne abbaye de Capucins dont les ruines n'existent même plus) est
un bourg à huit lieues de Rouen, entre la route d'Abbeville et celle de Beauvais, au fond d'une vallée qu'arrose la Rieule, 
petite rivière qui se jette dans l'Andelle, après avoir fait tourner trois moulins vers son embouchure, et où il y a quelques truites, 
que les garçons, le dimanche, s'amusent à pécher à la ligne.
On quitte la grande route à la Boissière et l'on continue à plat jusqu'au haut de la côte des Leux, d'où l'on découvre la vallée. 
La rivière qui la traverse en fait comme deux régions de physionomie distincte : tout ce qui est à gauche est en herbage,
tout ce qui est à droite est en labour. La prairie s'allonge sous un bourrelet de collines basses pour se rattacher 
par-derrière aux pâturages du pays de Bray, tandis que, du côté de l'est, la plaine, montant doucement, va s'élargissant 
et étale à perte de vue ses blondes pièces de blé. L'eau qui court au bord de l'herbe sépare d'une raie blanche la couleur des prés 
et celle des sillons, et la campagne ainsi ressemble à un grand manteau déplié qui a un collet de velours vert bordé d'un galon d'argent.
[...]
```

> _Output is non-deterministic — each run produces a different text._

---

## Requirements

- Java 8 or later
- The `bovary/` directory containing the 35 chapter files must be present at runtime

---

## Context

Developed as part of the **INF371** course at École polytechnique.  
