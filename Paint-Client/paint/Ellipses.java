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
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Thanh
 */
public class Ellipses extends Shape implements Draw {

    GraphicsContext gc;
    double x1; //toa do x cua tam eclipse
    double y1; //to do y cua tam eclipse
    double x2; //toa do x cua vi tri tha chuot
    double y2;  //toa do y cua vi tri tha chuot
    Ellipse elps = new Ellipse();
    String name;
    String color;
    String author;
    Point startpoint;
    Point endpoint;

    public Ellipses(Point start, Point end, String shapename, String color, double ssize,String Author) {
        super(start, end, shapename, color, ssize,Author);
        this.x1 = start.X;
        this.y1 = start.Y;
        this.x2 = end.X;
        this.y2 = end.Y;
        this.name = shapename;
        this.color = color;
        this.size = ssize;
        this.author = Author;
    }

    public Ellipses() {

    }

    @Override
    public void autodraw(Canvas canvas, Point start, Point end, String color, double size) {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(size);
        gc.setStroke(Color.web(color));
        elps.setCenterX(start.X);
        elps.setCenterY(start.Y);
        elps.setRadiusX(Math.abs(end.X - elps.getCenterX()));
        elps.setRadiusY(Math.abs(end.Y - elps.getCenterY()));
        if (elps.getCenterX() > end.X) {
            elps.setCenterX(end.X);
        }
        if (elps.getCenterY() > end.Y) {
            elps.setCenterY(end.Y);
        }
        gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());

    }
    double size;

    @Override
    public void freedraw(Canvas canvas, Slider tsize, ColorPicker color, Client client,String Author,boolean isConnected) {
        canvas.setCursor(Cursor.DEFAULT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
            gc.setStroke(color.getValue());
            elps.setCenterX(e.getX());
            elps.setCenterY(e.getY());
            startpoint = new Point(e.getX(), e.getY(), size, "ellipse", color.getValue().toString(),Author);
        });
        canvas.setOnMouseDragged(e -> {
            size = tsize.getValue();
        });
        canvas.setOnMouseReleased(e -> {
            elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
            elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
            endpoint = new Point(e.getX(), e.getY(), size, "ellipse", color.getValue().toString(),Author);
            if (elps.getCenterX() > e.getX()) {
                elps.setCenterX(e.getX());
            }
            if (elps.getCenterY() > e.getY()) {
                elps.setCenterY(e.getY());
            }
            gc.setLineWidth(size);
            gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
            Shape shape = new Shape(startpoint, endpoint, "ellipse", color.getValue().toString(), size,Author);
            if (size > 0 && isConnected) {
                try {
                    client.SendObject(endpoint);
                    client.SendObject2(shape);
                } catch (IOException ex) {
                    Logger.getLogger(Ellipses.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
