package it.model;

import java.awt.*;

// ✅ Sottoclasse concreta di Move
public class StandardMove extends Move {
    public StandardMove(String type, Point from, Point to) {
        super(type, from, to);
    }
}

