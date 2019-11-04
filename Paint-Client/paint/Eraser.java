/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
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
public class Eraser extends Tools{
    GraphicsContext EraserTool;
    ImageCursor EraserCursor;
    
    public Eraser(String name){
        super(name);
    }
    
    public Eraser(){
        
    }
    
    @Override
    public void SetCursor(Canvas canvas, int eraserSelectedCount) {
        if (eraserSelectedCount == 1) {
            EraserCursor = new ImageCursor(new Image(getClass().getResource("eraser.png").toExternalForm()), 24, 24);
            canvas.setCursor(EraserCursor);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }
    
    @Override
    public void UnselectTool(Canvas canvas) {
        EraserTool.setLineWidth(0.0);
        EraserTool = canvas.getGraphicsContext2D();
        EraserTool.setStroke(Color.BLACK);
        canvas.setOnMouseDragged(e -> {
//            double x = e.getX();
//            double y = e.getY();
//            EraserTool.setLineWidth(0);
//            EraserTool.fillRoundRect(x, y, 0, 0, 0, 0);
        });
    }
    
    @Override
    public void ToolFunction(Canvas canvas, Slider tsize,List<WritableImage> undo,Index i,Client client,ColorPicker color,String author,boolean isConnected)
    {
        EraserTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e ->{
            double size = tsize.getValue();
            double x = e.getX();
            double y = e.getY();
            EraserTool.setLineWidth(tsize.getValue());
            EraserTool.clearRect(x, y, size, size);         
            Point p = new Point(x, y, size, "eraser",color.getValue().toString(),author);
            try {
                if(p.Size > 0 && isConnected)
                {
                client.SendObject(p);
                System.out.println("Sending "+p.X+ " "+ p.Y);
                }
            } catch (IOException ex) {
                Logger.getLogger(Brush.class.getName()).log(Level.SEVERE, null, ex);
            }
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
