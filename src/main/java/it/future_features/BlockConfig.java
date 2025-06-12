package it.future_features;

import java.awt.Color;
import java.awt.Rectangle;
import it.composite.Block;

/**
 * Rappresenta la configurazione completa di un blocco,
 * per generazione dinamica nei livelli.
 */
public class BlockConfig {

    public final Color color;
    public final Block.Shape shape;
    public final Block.Direction direction; // null se movimento libero
    public final Rectangle bounds;
    public final boolean isTargetBlock;

    public BlockConfig(Color color, Block.Shape shape, Block.Direction direction, Rectangle bounds, boolean isTargetBlock) {
        this.color = color;
        this.shape = shape;
        this.direction = direction;
        this.bounds = bounds;
        this.isTargetBlock = isTargetBlock;
    }
}


