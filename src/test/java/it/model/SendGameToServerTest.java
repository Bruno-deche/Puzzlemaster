package it.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SendGameToServerTest {

    @Test
    void testSendGameToServerFakeUrl() {
        try {
            // Imposta una URL finta che non fa nulla
            System.setProperty("server.url", "http://localhost/fake");

            PuzzlemasterModel model = new PuzzlemasterModel();

            // Esegui il metodo
            model.sendGameToServer("ABC", 3, "easy");

            // Se non lancia eccezioni, il test è superato (in locale)
            assertTrue(true);
        } catch (Exception e) {
            fail("Il metodo ha lanciato un'eccezione: " + e.getMessage());
        }
    }
}

