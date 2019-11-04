/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Thanh
 */
public class Lines extends Shape implements Draw{
    double x1;
    double y1;
    double x2;
    double y2;
    String name;
    String color;
    GraphicsContext gc;
    Line line = new Line();
    public Lines(Point start, Point end,String name,String color,double ssize) {
        super(start, end,name,color,ssize);
        this.x1 = start.X;
        this.y1 = start.Y;
        this.x2 = end.X;
        this.y2 = end.Y;
        this.name = name;
        this.color = color;
        this.size = ssize;  
    }
    public Lines(){
        
    }
    @Override
    public void autodraw(Canvas canvas,Point start, Point end,String color,double size)
    {
        gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.web(color));
        gc.setLineWidth(size);
        line.setStartX(start.X);
        line.setStartY(start.Y);

        line.setEndX(end.X);
        line.setEndY(end.Y);
        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }
    double size;
    @Override
    public void freedraw(Canvas canvas, Slider tsize, ColorPicker color) {
        canvas.setCursor(Cursor.DEFAULT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
           gc.setStroke(color.getValue());
           gc.setLineWidth(tsize.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());
        });
        canvas.setOnMouseDragged(e -> {
            size =tsize.getValue();
        });
        canvas.setOnMouseReleased(e -> {
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        });
    }

    @Override
    public void stopdraw(Canvas canvas) {
        gc = null;
        canvas.setOnMousePressed(e -> {
        });
        canvas.setOnMouseReleased(e -> {
        });
    }

}
