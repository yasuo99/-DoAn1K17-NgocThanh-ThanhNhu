/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Stack;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
    public void ToolFunction(Canvas canvas, Slider tsize, List<WritableImage> undo, Index i) {

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
                    gc.setFont(Font.font("Time New Roman", tsize.getValue()));
                    gc.fillText(textField.getText(), e.getX(), e.getY());

                });
                canvas.setOnMouseReleased(e -> {
                    if (tsize.getValue() > 0 && textField.getText().isEmpty() == false) {
                        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                        canvas.snapshot(new SnapshotParameters(), writableImage);
                        undo.add(writableImage);
                        i.i = i.i + 1;
                        System.out.println(i.i);
                    }
                });
                canvas.setOnMouseDragged(e -> {
                });
            }

        });

    }
}
