/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.List;
import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Thanh
 */
public class Pencil extends Tools {

    GraphicsContext pencilTool;
    ImageCursor pencilCursor;

    public Pencil(String name) {
        super(name);
    }

    public Pencil() {
    }

    @Override
    public void SetCursor(Canvas canvas, int pencilSelectedCount) {
        if (pencilSelectedCount == 1) {
            pencilCursor = new ImageCursor(new Image(getClass().getResource("pencil.png").toExternalForm()), 24, 24);
            canvas.setCursor(pencilCursor);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }

    @Override
    public void UnselectTool(Canvas canvas) {
        pencilTool.setLineWidth(0.0);
        pencilTool = canvas.getGraphicsContext2D();
        pencilTool.setStroke(Color.BLACK);
        canvas.setOnMouseDragged(e -> {
//            double x = e.getX();
//            double y = e.getY();
//            pencilTool.setFill(Color.BLACK);
//            pencilTool.setLineWidth(0);
//            pencilTool.fillRoundRect(x, y, 0, 0, 0, 0);
        });
    }

    @Override
    public void ToolFunction(Canvas canvas, Slider tsize, List<WritableImage> undo,Index i) {
        pencilTool = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(e -> {
            double size = tsize.getValue();
            double x = e.getX() - size / 1.7;
            double y = e.getY() - size / 1.7;
            pencilTool.setFill(Color.BLACK);
            pencilTool.setLineWidth(tsize.getValue());
            pencilTool.fillRect(x, y, size, size);
        });
        canvas.setOnMouseReleased(e -> {
            if(tsize.getValue() > 0)
                {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(new SnapshotParameters(), writableImage);
            undo.add(writableImage);
            i.i = i.i +1;
            System.out.println(i.i);
                }
        });
        canvas.setOnMousePressed(e -> {
        });
    }
}
