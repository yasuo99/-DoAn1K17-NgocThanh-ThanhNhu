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
import javafx.scene.shape.Circle;

/**
 *
 * @author Thanh
 */
public class Circles extends Shape implements Draw {

    Circle cir = new Circle();
    GraphicsContext gc;
    double x1; //toa do x cua tam eclipse
    double y1; //to do y cua tam eclipse
    double x2; //toa do x cua vi tri tha chuot
    double y2;  //toa do y cua vi tri tha chuot
    String name;
    String color;
    double size;
    public Circles(Point start, Point end,String shapename,String color,double ssize) {
        super(start, end,shapename,color,ssize);
        this.name = shapename;
        this.color = color;
        this.size = ssize;
    }
    public Circles(){
        
    }
    @Override
    public void freedraw(Canvas canvas, Slider tsize, ColorPicker color) {
        canvas.setCursor(Cursor.DEFAULT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            gc.setStroke(color.getValue());
            gc.setFill(color.getValue());
            gc.setLineWidth(tsize.getValue());
            cir.setCenterX(e.getX());
            cir.setCenterY(e.getY());
        });
        canvas.setOnMouseDragged(e ->{
            size = tsize.getValue();
        });
        canvas.setOnMouseReleased(e -> {
            cir.setRadius((Math.abs(e.getX()- cir.getCenterX()) + Math.abs(e.getY() - cir.getCenterY())) / 2);
            if (cir.getCenterX() > e.getX()) {
                cir.setCenterX(e.getX());
            }
            if (cir.getCenterY() > e.getY()) {
                cir.setCenterY(e.getY());
            }
            
            gc.setLineWidth(size); 
            gc.strokeOval(cir.getCenterX(), cir.getCenterY(), cir.getRadius(), cir.getRadius());
        });
    }

    @Override
    public void autodraw(Canvas canvas, Point start, Point end, String color, double size) {
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.web(color));
        gc.setLineWidth(size);
        cir.setCenterX(start.X);
        cir.setCenterY(start.Y);
        cir.setRadius((Math.abs(end.X - cir.getCenterX()) + Math.abs(end.Y - cir.getCenterY())) / 2);

        if (cir.getCenterX() > end.X) {
            cir.setCenterX(end.X);
        }
        if (cir.getCenterY() > end.Y) {
            cir.setCenterY(end.Y);
        }
        gc.strokeOval(cir.getCenterX(), cir.getCenterY(), cir.getRadius(), cir.getRadius());

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
