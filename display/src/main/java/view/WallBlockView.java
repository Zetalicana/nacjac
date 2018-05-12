package view;

import game.WallBlock;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WallBlockView {
    private Color color = Color.PURPLE;
    private List<WallBlock> wallBlockList;

    public WallBlockView(List<WallBlock> wallBlockList) {
        this.wallBlockList = wallBlockList;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setFill(color);
        for (WallBlock wallBlock : wallBlockList) {
            graphicsContext.strokeRect(getWallBlockX(wallBlock), getWallBlockY(wallBlock), wallBlock.BLOCKSIZE, WallBlock.BLOCKSIZE);
        }
        /*graphicsContext.setGlobalBlendMode(BlendMode.OVERLAY);
        graphicsContext.setStroke(Color.RED);
        for (int i = 0; i < 31; i++) {
            graphicsContext.strokeLine(0, i * 20, 31 * 20, i * 20);
        }
        for (int i = 0; i < 31; i++) {
            graphicsContext.strokeLine(i * 20, 0, i * 20, 31 * 20);
        }

        graphicsContext.setGlobalBlendMode(BlendMode.SRC_ATOP);*/
    }

    public double getWallBlockX(WallBlock wallBlock) {
        return wallBlock.getCol() * WallBlock.BLOCKSIZE - (4 * WallBlock.BLOCKSIZE);
    }

    public double getWallBlockY(WallBlock wallBlock) {
        return wallBlock.getRow() * WallBlock.BLOCKSIZE;
    }

}
