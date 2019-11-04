/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    public void ToolFunction(Canvas canvas, Slider tsize, List<WritableImage> undo, Index index) {
        brushTool = canvas.getGraphicsContext2D();
        brushTool.setStroke(Color.BLACK);
        canvas.setOnMousePressed(e -> {
        });
        canvas.setOnMouseDragged(e -> {

            double size = tsize.getValue();
            double x = e.getX() - size / 1.7;
            double y = e.getY() - size / 1.7;
            brushTool.setFill(Color.BLACK);
            brushTool.setLineWidth(tsize.getValue());
            brushTool.fillRoundRect(x, y, size, size, size, size);     
        });
        canvas.setOnMouseReleased(e -> {
            if (tsize.getValue() > 0) {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(new SnapshotParameters(), writableImage);
                undo.add(writableImage);
                index.i = index.i + 1;
            }
//            try {
//                Point p = new Point(0,0,0,"Disconnected");
//                client.SendObject(p);
//                client.Disconnect();            
//            } catch (IOException ex) {
//                Logger.getLogger(Brush.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(Brush.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });

    }

    @Override
    public void UnselectTool(Canvas canvas) {
        brushTool.setLineWidth(0.0);
        brushTool = canvas.getGraphicsContext2D();
        brushTool.setStroke(Color.BLACK);
        canvas.setOnMouseDragged(e -> {

//            double x = e.getX();
//            double y = e.getY();
//            brushTool.setFill(Color.BLACK);
//            brushTool.setLineWidth(0);
//            brushTool.fillRoundRect(x, y, 0, 0, 0, 0);
        });
    }
}
