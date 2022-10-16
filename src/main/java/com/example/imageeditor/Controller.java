package com.example.imageeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;

public class Controller {

    @FXML
    ImageView myImageView;
    Button FilterImage;

    public void MakeImageBlackAndWhite(ActionEvent e){

        File input = new File("C:\\Users\\acer\\Desktop\\Yash\\Coding\\Java_and_stuff\\ImageEditor\\src\\main\\resources\\com\\example\\imageeditor\\CatMan.png");
        File output = new File("C:\\Users\\acer\\Desktop\\Yash\\trash\\exampleImages\\bnwexample.png");

        Filter.toGrayscale(input,output);
//        Image editedImage = new Image(getClass().getResourceAsStream("C:\\Users\\acer\\Desktop\\Yash\\trash\\exampleImages\\eww.png"));
//        myImageView.setImage(editedImage);
        System.out.println("Completed");

    }
}

