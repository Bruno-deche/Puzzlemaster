package it.composite;

import java.awt.*;

public interface GameComponent {
    void draw(Graphics g);

    void move(int dx, int dy);

    Rectangle getBounds();
}





