package it.future_features;

import it.model.Move;

import java.awt.*;

public class UserMove extends Move {
    public UserMove(Point from, Point to) {
        super("user", from, to);
    }
}


