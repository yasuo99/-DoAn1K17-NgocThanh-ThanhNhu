/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

/**
 *
 * @author Thanh
 */
public class Client {

    private String ip;
    private int port;
    Socket socket;
    boolean isConnected = false;
    GraphicsContext gc;
    Canvas canvas;
    ObjectOutputStream out;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void Connect() throws IOException {
        try {
            socket = new Socket(ip, port);
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Server hiện không sẵn sàng");
            alert.setContentText("Vui lòng thử lại!");
            alert.showAndWait();
        }
        socket.setTcpNoDelay(true);
             out = new ObjectOutputStream(socket.getOutputStream());
             isConnected = true;
    }

    public void Disconnect() throws IOException {
        out.close();
        isConnected = false;
    }

    public void SendObject(Point p) throws IOException {
        out.writeObject(p);
    }

    public void SendObject2(Shape s) throws IOException {
        out.writeObject(s);
    }
}
