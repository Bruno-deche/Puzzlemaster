# ✅ TestResults.md — PuzzleMaster Project

Questa è la documentazione ufficiale dei test JUnit implementati per il progetto *PuzzleMaster*. Ogni test copre un aspetto critico della logica, struttura o interfaccia dell’applicazione.

---

## ✅ Comandi

- **HomeCommandTest** – Verifica che il comando Home esegua il salvataggio e il ritorno alla schermata iniziale.
- **RestartCommandTest** – Verifica che il gioco venga resettato correttamente.
- **NextCommandTest** – Verifica che venga suggerita e applicata una mossa automatica.
- **UndoCommandTest** – Verifica il ripristino dell’ultima mossa effettuata.
- **SaveCommandTest** – Verifica che venga salvato lo stato corrente nel database.

---

## ✅ Strategie (Pattern Strategy)

- **EasyStrategyTest** – Verifica che venga generata una mossa valida e prevedibile.
- **RandomStrategyTest** – Verifica che venga generata una mossa casuale ma valida.
- **TargetedStrategyTest** – Verifica che la mossa sia generata in direzione dell’obiettivo o nulla se non valida.

---

## ✅ Componenti Grafici & Composite

- **BlockTest** – Verifica spostamento, bounds, e comportamento visivo del blocco.
- **BoardTest** – Verifica aggiunta, rimozione, e collisione tra blocchi.
- **PuzzlemasterUITest** – Simula il completamento di un livello per validare `checkVictory()`.

---

## ✅ Listener

- **BoardListenerTest** – Verifica che il click attivi correttamente una mossa tra model e view.

---

## ✅ Modello e Persistenza

- **MoveFactoryTest** – Verifica che una `Move` venga creata correttamente.
- **SendGameToServerTest** – Verifica l’invio del gioco al server senza eccezioni.
- **SavedGameAdapterTest** – Verifica che il salvataggio venga correttamente riconvertito in `Board`.

---

## ✅ Copertura

| Area            | Coperta | Test |
|------------------|--------|------|
| Logica gioco     | ✅     | `Move`, `Board`, `Undo`, `Next`, `Restart` |
| UI grafica       | ✅     | `PuzzlemasterUITest` |
| AI (strategie)   | ✅     | `Easy`, `Random`, `Targeted` |
| Persistenza      | ✅     | `Save`, `Adapter`, `SendGameToServer` |
| Controller       | ✅     | `BoardListener` |
| Pattern supportati | ✅     | `Command`, `Strategy`, `Adapter`, `Composite` |

---

✔️ **Tutti i test sono passati con successo.**  
🧪 **Progetto testato e validato per una consegna completa.**
