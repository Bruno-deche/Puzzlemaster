package it.controller;

import it.model.PuzzlemasterModel;
import it.view.PuzzlemasterUI;

import java.awt.event.MouseListener;

public class CommandFactory {

    public static MouseListener createCommand(String commandName, PuzzlemasterModel model, PuzzlemasterUI ui, SaveCommand saveCommand) {
        switch (commandName.toLowerCase()) {
            case "restart":
                return new RestartCommand(model, ui);
            case "save":
                return saveCommand; // già creato e passato
            default:
                throw new IllegalArgumentException("❌ Comando sconosciuto: " + commandName);
        }
    }
}




