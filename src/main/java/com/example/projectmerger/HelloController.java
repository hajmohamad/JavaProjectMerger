package com.example.projectmerger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button bt_fileChoser;


    @FXML
    private Button bt_findJavaClass;

    @FXML
    private Label lb_filePath;

    @FXML
    private VBox vbox_javaClasses;
    String filePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_fileChoser.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            File sourceFile = fileChooser.showOpenDialog(HelloApplication.BaseStage);
            if (sourceFile != null) {
                lb_filePath.setText(sourceFile.getAbsolutePath());
                filePath = sourceFile.getAbsolutePath();
                bt_findJavaClass.setVisible(true);
            }
        });
        bt_findJavaClass.setOnMouseClicked(event -> {
            JavaFileMerger.findJavaFiles(filePath);

        });

    }
}