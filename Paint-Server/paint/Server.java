/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Thanh
 */
public class Server {

    private int port;
    Socket socket = null;
    ServerSocket ss;
    Thread thread;
    ArrayList<Point> list;
    Index2 index;
    ArrayList<User> userList;

    public Server(int port, ArrayList<Point> list, Index2 index) {
        this.port = port;
        this.list = list;
        this.index = index;
    }

    public void RunServer(Canvas canvas) throws IOException, ClassNotFoundException {
        ConnectionThread connectionThread = new ConnectionThread(canvas, list, index);
        connectionThread.setDaemon(true);
        connectionThread.start();
        // print out the text of every message
    }

    private class ConnectionThread extends Thread {

        private Socket socket;
        Canvas canvas;
        ObjectInputStream inp;
        ObjectOutputStream out;
        ArrayList<Point> list;
        Index2 index;

        public ConnectionThread(Canvas canvas, ArrayList<Point> list, Index2 index) {
            this.canvas = canvas;
            this.list = list;
            this.index = index;
        }

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(4444);
                while (true) {
                    try {
                        socket = server.accept();
                        socket.setTcpNoDelay(true);
                        System.out.println("Connect from " + socket);
                    } catch (IOException e) {
                        System.out.println("I/O error: " + e);
                    }
                    // new thread for a client
                    new EchoThread(socket, out, inp, canvas, list, index).start();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }
    }

    public class EchoThread extends Thread {

        protected Socket socket;
        ObjectInputStream inp;
        ObjectOutputStream out;
        Canvas canvas;
        ArrayList<Point> list;
        Index2 index;
        GraphicsContext gc;
        Tooltip tooltips;

        public EchoThread(Socket socket, ObjectOutputStream out, ObjectInputStream inp, Canvas canvas, ArrayList<Point> list, Index2 index) {
            this.socket = socket;
            this.inp = inp;
            this.out = out;
            this.canvas = canvas;
            this.list = list;
            this.index = index;
        }

        @Override
        public void run() {
            try {
                gc = canvas.getGraphicsContext2D();
                out = new ObjectOutputStream(socket.getOutputStream());
                inp = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    Point p = (Point) inp.readObject();
                    switch (p.Type) {
                        case "brush":
                            gc.setFill(Color.web(p.Color));
                            gc.fillRoundRect(p.X, p.Y, p.Size, p.Size, p.Size, p.Size);
                            list.add(p);
                            break;
                        case "pencil":
                            gc.setFill(Color.web(p.Color));
                            gc.fillRect(p.X, p.Y, p.Size, p.Size);
                            list.add(p);
                            break;
                        case "eraser":
                            gc.clearRect(p.X, p.Y, p.Size, p.Size);
                            break;
                        case "circle":
                            Shape shape = (Shape) inp.readObject();
                            Circles circle = new Circles(shape.start, shape.end, shape.shapename, shape.color, shape.size);
                            circle.autodraw(canvas, circle.start, circle.end, circle.color, circle.size);
                            list.add(shape.start);
                            list.add(shape.end);
                            break;
                        case "line":
                            Shape shape1 = (Shape) inp.readObject();
                            Lines line = new Lines(shape1.start, shape1.end, shape1.shapename, shape1.color, shape1.size);
                            line.autodraw(canvas, line.start, line.end, line.color, line.size);
                            list.add(line.start);
                            list.add(line.end);
                            break;
                        case "rectangle":
                            Shape shape2 = (Shape) inp.readObject();
                            Rectangles rect = new Rectangles(shape2.start, shape2.end, shape2.shapename, shape2.color, shape2.size);
                            rect.autodraw(canvas, rect.start, rect.end, rect.color, rect.size);
                            list.add(shape2.start);
                            list.add(shape2.end);
                            break;
                        case "ellipse":
                            Shape shape3 = (Shape) inp.readObject();
                            Ellipses ellipse = new Ellipses(shape3.start, shape3.end, shape3.shapename, shape3.color, shape3.size);
                            ellipse.autodraw(canvas, ellipse.start, ellipse.end, ellipse.color, ellipse.size);
                            list.add(shape3.start);
                            list.add(shape3.end);
                            break;
                        case "disconnect":{
                            inp.close();
                            socket.close();
                            break;
                        }
                        default: {
                            gc.setFill(Color.web(p.Color));
                            gc.setFont(Font.font("Time New Roman", p.Size));
                            gc.fillText(p.Type, p.X, p.Y);
                            list.add(p);
                        }
                    }
                    if ("disconnect".equals(p.Type)) {
                        break;
                    }      
                }
                canvas.setOnMouseClicked(e -> {
                for (int i = 0; i < list.size(); i++) {
                    if (Math.abs(e.getX() - list.get(i).X) >= list.get(i).Size/1.7 && Math.abs(e.getY() - list.get(i).Y) >= list.get(i).Size/1.7){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông tin: ");
                        alert.setHeaderText("Người vẽ");
                        alert.setContentText(list.get(i).Author);
                        alert.showAndWait();
                    }
                }
                });
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
