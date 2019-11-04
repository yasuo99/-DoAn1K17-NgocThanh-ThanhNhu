/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Thanh
 */
public class Brush extends Tools {

    GraphicsContext brushTool;
    ImageCursor brushCursor;

    public Brush(String name) {
        super(name);
    }

    public Brush() {
    }

    @Override
    public void SetCursor(Canvas canvas, int brushSelectedCount) {
        if (brushSelectedCount == 1) {
            brushCursor = new ImageCursor(new Image(getClass().getResource("paint-brush.png").toExternalForm()), 24, 24);
            canvas.setCursor(brushCursor);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }

    @Override
    public void ToolFunction(Canvas canvas, Slider tsize, List<WritableImage> undo, Index index, Client client, ColorPicker color,String author,boolean isConnected) {
        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
        });
        canvas.setOnMouseDragged(e -> {
            double size = tsize.getValue();
            double x = e.getX();
            double y = e.getY();
            brushTool.setFill(color.getValue());
            brushTool.setLineWidth(tsize.getValue());
            brushTool.fillRoundRect(x, y, size, size, size, size);
            Point p = new Point(x, y, size, "brush", color.getValue().toString(),author);
            try {
                if (p.Size > 0 && isConnected) {
                    client.SendObject(p);
                    System.out.println("Sending " + p.X + " " + p.Y);
                }
            } catch (IOException ex) {
                Logger.getLogger(Brush.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        canvas.setOnMouseReleased(e -> {
            if (tsize.getValue() > 0) {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(new SnapshotParameters(), writableImage);
                undo.add(writableImage);
                index.i = index.i + 1;
            }
        });

    }

    @Override
    public void UnselectTool(Canvas canvas) {
        brushTool.setLineWidth(0.0);
        brushTool = canvas.getGraphicsContext2D();
        brushTool.setStroke(Color.BLACK);
        canvas.setOnMouseDragged(e -> {
        });
    }
}
