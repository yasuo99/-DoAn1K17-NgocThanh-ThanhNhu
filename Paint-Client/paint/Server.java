/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Thanh
 */
public class Server {

    Socket socket;
    ServerSocket ss;
    Thread thread;
    public void StartServer() throws IOException{
        ss = new ServerSocket(8888);
        socket = ss.accept();
    }
    public void RunServer(GraphicsContext gc, Canvas canvas, boolean stop) throws IOException, ClassNotFoundException {
         // blocking call, this will wait until a connection is attempted on this port.
        System.out.println("Connection from " + socket + "!");
        // get the input stream from the connected socket
        while(stop)
        {
        InputStream inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        // read the list of messages from the socket
        Point p = (Point) objectInputStream.readObject();
        System.out.println("Receiving "+p.X +" "+p.Y);
        draw(gc, canvas, p);
        }
        socket.close();
        // print out the text of every message
    }

    public void draw(GraphicsContext gc, Canvas canvas, Point p) {
        gc = canvas.getGraphicsContext2D();
        gc.fillRoundRect(p.X, p.Y, p.Size, p.Size, p.Size, p.Size);
    }
}
