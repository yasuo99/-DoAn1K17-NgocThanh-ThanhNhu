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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Thanh
 */
public class AddText extends Tools {

    GraphicsContext gc;
    ImageCursor addTextCursor;

    public AddText(String name) {
        super(name);
    }

    public AddText() {

    }

    @Override
    public void SetCursor(Canvas canvas, int eraserSelectedCount) {
        if (eraserSelectedCount == 1) {
            addTextCursor = new ImageCursor(new Image(getClass().getResource("tumblr-logo.png").toExternalForm()), 24, 24);
            canvas.setCursor(addTextCursor);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }

    @Override
    public void UnselectTool(Canvas canvas) {
        gc.setLineWidth(0.0);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> {
        });
        canvas.setOnMouseDragged(e -> {
        });
    }

    @Override
    public void ToolFunction(Canvas canvas, Slider tsize, List<WritableImage> undo, Index i, Client client,ColorPicker color,String author,boolean isConnected) {

        gc = canvas.getGraphicsContext2D();
        Stage stage = new Stage();
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.CENTER);
        Button ok = new Button();
        ok.setText("OK");
        TextField textField = new TextField();
        hbox.getChildren().addAll(textField, ok);
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefHeight(50);
        root.getChildren().add(hbox);
        Scene canvasScene = new Scene(root);
        stage.setTitle("Add Text");
        stage.setScene(canvasScene);
        stage.show();
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.close();
                canvas.setOnMousePressed(e -> {
                    gc.setFill(color.getValue());
                    gc.setFont(Font.font("Time New Roman", tsize.getValue()));
                    gc.fillText(textField.getText(), e.getX(), e.getY());
                    Point p = new Point(e.getX(), e.getY(), tsize.getValue(), textField.getText(),color.getValue().toString(),author);
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
                    if (tsize.getValue() > 0 && textField.getText().isEmpty() == false) {
                        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(new SnapshotParameters(), writableImage);
                        undo.add(writableImage);
                        i.i = i.i + 1;
                    }
                });
                canvas.setOnMouseDragged(e -> {
                });
            }

        });

    }
}
