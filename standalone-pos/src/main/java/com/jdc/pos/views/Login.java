package com.jdc.pos.views;

import com.jdc.pos.PosApplication;
import com.jdc.pos.model.PosException;
import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class Login {

    private static Account loginUser;

    @Autowired
    private LoginService service;

    @FXML
    private Label message;

    @FXML
    private TextField loginId;

    @FXML
    private PasswordField password;

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    private void attachEvent() {

        loginId.getScene().setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER) {

                if(closeBtn.isFocused()) {
                    close();
                }

                if(loginBtn.isFocused()) {
                    login();
                }
            }
        });
    }

    @FXML
    private void close() {
        loginBtn.getScene().getWindow().hide();
    }

    @FXML
    private void login() {
        try {
            loginUser = service.login(loginId.getText(), password.getText());

            // open application
            MainFrame.show();

            // close login view
            close();

        } catch (PosException e) {
            message.setText(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public static void loadView(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(Login.class.getResource("Login.fxml"));
            loader.setControllerFactory(PosApplication.getApplicationContext()::getBean);

            Parent view = loader.load();
            stage.setScene(new Scene(view));

            stage.initStyle(StageStyle.UNDECORATED);

            Login controller = loader.getController();
            controller.attachEvent();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
