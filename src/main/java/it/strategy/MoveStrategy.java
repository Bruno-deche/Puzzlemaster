package it.strategy;

import it.model.Move;

import java.awt.*;

public interface MoveStrategy {
    Move nextMove(Rectangle[] currentPositions, Point selectedPiece);
}



