package com.jdc.pos.views.common;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dialog {

    @FXML
    private Label title;
    @FXML
    private Label message;
    @FXML
    private Button okBtn;
    @FXML
    private Button closeBtn;

    private Stage stage;

    private ActionListener actionListener;

    @FXML
    private void okAction() {
        if(null != actionListener) {
            cancel();
            actionListener.doAction();
        }
    }

    private void attachEvents() {
        closeBtn.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                if(closeBtn.isFocused()) {
                    cancel();
                }

                if(okBtn.isFocused()) {
                    okAction();
                }
            }
        });
    }
    @FXML
    private void cancel() {
        okBtn.getScene().getWindow().hide();
    }

    public void show() {
        stage.show();
    }

    public static class DialogBuilder {

        private String title;
        private String message;

        private ActionListener okActionListener;

        private DialogBuilder() {
        }

        public DialogBuilder okActionListener(ActionListener okActionListener) {
            this.okActionListener = okActionListener;
            return this;
        }

        public DialogBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Dialog build() {

            try {

                Stage stage = new Stage(StageStyle.UNDECORATED);
                FXMLLoader loader = new FXMLLoader(Dialog.class.getResource("Dialog.fxml"));
                Parent view = loader.load();
                stage.setScene(new Scene(view));
                stage.initModality(Modality.APPLICATION_MODAL);

                Dialog controller = loader.getController();
                controller.stage = stage;

                controller.title.setText(this.title);
                controller.message.setText(this.message);
                controller.actionListener = okActionListener;

                if(null == okActionListener) {
                    controller.okBtn.setVisible(false);
                    controller.closeBtn.setText("CLOSE");
                }

                controller.attachEvents();

                return controller;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static DialogBuilder builder() {
            return new DialogBuilder();
        }
    }

    public static interface ActionListener {
        void doAction();
    }
}
