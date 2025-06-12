
# 🧩 PuzzleMaster

**PuzzleMaster** è un videogioco a enigmi sviluppato interamente in Java con un’architettura modulare basata su design pattern, MVC e componenti riutilizzabili.  
Lo scopo del gioco è semplice: muovere i blocchi nella griglia fino a raggiungere una **configurazione obiettivo** prestabilita, rispettando vincoli di direzione, forma e interazione.

---

## 🎮 Come si gioca

All'avvio, il gioco presenta una griglia (Board) su cui sono disposti vari blocchi di colore e forma diversi.  
L'utente può:
- cliccare e trascinare i blocchi in orizzontale o verticale,
- muovere un solo blocco alla volta,
- ottenere un suggerimento automatico (IA),
- salvare o ripristinare la partita,
- annullare l’ultima mossa,
- passare al livello successivo.

L'interfaccia è completamente interattiva e ogni azione è accompagnata da **feedback visivi e animazioni fluide**.

---

## 🎯 Logica di gioco

Ogni blocco è caratterizzato da:
- **Colore**: rappresenta la classe o funzione del blocco (es. obiettivo, ostacolo, libero).
- **Forma**: può essere rettangolare, quadrato, ecc.
- **Direzione di movimento**: alcuni si muovono solo in orizzontale, altri in verticale, altri sono liberi.

La partita termina quando tutti i blocchi **obiettivo** raggiungono la loro posizione finale nella griglia.  
Il gioco verifica la vittoria automaticamente usando la posizione e il colore dei blocchi rispetto alla configurazione finale.

---

## 🖥️ Interfaccia grafica

La schermata di gioco è composta da:
- **Area di gioco grigia**: rappresenta la griglia su cui sono disposti i blocchi.
- **Blocchi colorati**:
  - Rosso: blocco obiettivo
  - Blu, Giallo, Verde: blocchi mobili secondari
- **Pulsanti funzione**: per tornare alla home, resettare, annullare mossa, passare al prossimo livello, ricevere suggerimento.

---

## 🗂️ Struttura del progetto

Il codice è organizzato in package logici:

### `model/`  
Contiene la logica del gioco, lo stato della partita, i salvataggi, e il motore di validazione.

### `view/`  
Gestisce tutta l’interfaccia grafica: pannelli, animazioni, colori, disegno dei blocchi e aggiornamenti della GUI.

### `controller/`  
Contiene tutti i listener di eventi (mouse) e comandi eseguibili. Funziona da ponte tra model e view.

### `composite/`  
Implementa il **Pattern Composite** per `Block`, `Board`, e `GameComponent`, facilitando la gestione gerarchica degli oggetti nella griglia.

### `strategy/`  
Implementa il **Pattern Strategy** per logiche automatiche (`EasyStrategy`, `RandomStrategy`, `TargetedStrategy`) che suggeriscono o applicano mosse.

### `adapter/`  
Contiene l’adapter per salvare e convertire il modello in rappresentazioni esterne (`SavedGameAdapter`).

### `util/`  
Contiene classi di supporto come `GameLogger`, `PasswordUtil`, `DBConnection`, con Singleton e configurazioni centralizzate.

### `future_features/`  
Contiene classi **non ancora collegate** alla logica del gioco ma progettate per **espansioni future**.  
Lo scopo di questa cartella è isolare componenti sperimentali o avanzati mantenendo il codice stabile e testato separato.

---

## 📌 Classi principali

- **`PuzzlemasterModel`**: mantiene lo stato logico del gioco.
- **`PuzzlemasterUI`**: rappresenta la vista principale del gioco.
- **`Controller`**: inizializza gli handler e collega UI ↔ Model.
- **`Block` / `Board`**: gestiscono i blocchi visivi e la griglia.
- **`Move` / `MoveFactory`**: rappresentano e generano le mosse.
- **`Command`**: ogni azione dell’utente (reset, salva, undo...) è un comando eseguibile.
- **`SavedGame`**: struttura dati dei salvataggi.

---

## 🔭 Sviluppi futuri

La cartella `it.future_features` è stata creata per **mantenere separate** le classi avanzate progettate ma non ancora integrate.  
Queste includono:

- `AutoMove`: IA che gioca in autonomia (demo o auto-play).
- `HintMove`: suggerimento visivo di una mossa valida.
- `UserMove`: estensione astratta per tipi personalizzati di mosse.
- `BlockConfig`: configurazioni dinamiche per livelli generati da file o editor.
- `BorderDecorator`, `SelectableBlockDecorator`: implementazioni del **Pattern Decorator** per arricchire l'interfaccia dei blocchi con bordi, selettori, effetti.
- `SavedGameJsonUtil`: serializzazione alternativa in formato JSON.

Questa divisione garantisce che il core del gioco rimanga stabile, mentre lo sviluppo prosegue in modo parallelo ed estendibile.

---

## 🧵 Multithreading

Il progetto include un esempio strutturale di multithreading tramite la classe:

- `HintMoveThread` (in `it.future_features`)  
  Questa classe calcola in background la mossa suggerita senza bloccare l'interfaccia utente, utilizzando `Thread` nativo e scrivendo l'output nel `GameLogger`.

Questo componente dimostra la capacità del sistema di gestire calcoli asincroni ed è pronto per eventuale estensione con thread multipli o animazioni non bloccanti.

---

## 🗄️ Database (MySQL con XAMPP)

Il gioco utilizza un database MySQL gestito tramite **XAMPP** per salvare partite, login e dati utente.

### 📋 Configurazione

- **Host:** `localhost`  
- **Porta:** `3306`  
- **User:** `root`  
- **Password:** *(vuota di default)*  
- **Database:** `puzzlemaster`

### 📁 Script SQL

- Si trova in `database/puzzlemaster.sql`  
- Contiene la definizione delle tabelle necessarie

### ⚙️ Classe di accesso

- `DBConnection.java` gestisce la connessione e utilizza `DriverManager` con parametri configurabili.
- Il progetto è predisposto anche per essere convertito a **SQLite**, modificando la stringa di connessione JDBC.

⚠️ Assicurarsi che **MySQL sia avviato da XAMPP** prima dell'esecuzione dell'app.

---

## 📐 Diagramma UML

Il diagramma UML aggiornato è disponibile nel file:  
`docs/uml_final_definitivo_rebuild.png`

![UML](docs/uml_final_definitivo_rebuild.png)

Mostra:
- Classi e pacchetti
- Attributi e metodi principali
- Relazioni `extends` / `implements`
- Struttura a componenti del gioco

---

## ⚙️ Requisiti

- Java 17+
- Maven 3.8+
- Nessuna libreria esterna necessaria
- Compatibile con IDE: IntelliJ, VSCode, Eclipse

---

## 👨‍💻 Autore

Sviluppato da Bruno Checchi, con cura e attenzione all'architettura, espandibilità e qualità del codice.  
Ogni parte del progetto è pensata per essere manutenibile, testabile e facilmente estendibile.
