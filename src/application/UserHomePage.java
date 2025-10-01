package application;

import databasePart1.DatabaseHelper;
import databasePart1.DatabaseHelper.Question;
import databasePart1.DatabaseHelper.Answer;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class UserHomePage {

    private final DatabaseHelper dbHelper;
    private final String studentName;

    public UserHomePage(DatabaseHelper dbHelper, String studentName) {
        this.dbHelper = dbHelper;
        this.studentName = studentName;
    }

    public void show(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-alignment: top-center; -fx-padding: 20;");

        Label userLabel = new Label("Hello, " + studentName + "!");
        userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox questionContainer = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(questionContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        TextField titleInput = new TextField();
        titleInput.setPromptText("Question title");

        TextArea descInput = new TextArea();
        descInput.setPromptText("Question description");
        descInput.setWrapText(true);
        descInput.setPrefRowCount(3);

        Button postButton = new Button("Post Question");
        postButton.setOnAction(e -> {
            String title = titleInput.getText().trim();
            String desc = descInput.getText().trim();
            try {
                int id = dbHelper.createQuestion(studentName, title, desc);
                if (id > 0) {
                    refreshQuestions(questionContainer);
                    titleInput.clear();
                    descInput.clear();
                }
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        VBox newQuestionBox = new VBox(10, titleInput, descInput, postButton);
        newQuestionBox.setStyle("-fx-padding: 10; -fx-background-color: #eaeaea; -fx-background-radius: 5;");

        layout.getChildren().addAll(userLabel, scrollPane, newQuestionBox);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Home Page");

        refreshQuestions(questionContainer);
        primaryStage.show();
    }

    private void refreshQuestions(VBox container) {
        container.getChildren().clear();
        List<Question> questions = dbHelper.getAllQuestions();
        for (Question q : questions) {
            VBox qBox = new VBox(5);
            qBox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-background-radius: 5;");

            Label titleLabel = new Label("Title: " + q.getTitle());
            Label descLabel = new Label("Description: " + q.getDescription());

            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> showEditQuestionDialog(q));

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                dbHelper.deleteQuestion(q.getId());
                refreshQuestions(container);
            });

            Button viewAnswersButton = new Button("View Answers");
            viewAnswersButton.setOnAction(e -> showAnswersDialog(q));

            HBox buttonBox = new HBox(10, editButton, deleteButton, viewAnswersButton);
            qBox.getChildren().addAll(titleLabel, descLabel, buttonBox);

            container.getChildren().add(qBox);
        }
    }

    private void showEditQuestionDialog(Question q) {
        TextField titleField = new TextField(q.getTitle());
        TextArea descField = new TextArea(q.getDescription());
        descField.setWrapText(true);
        descField.setPrefRowCount(3);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                dbHelper.updateQuestion(q.getId(), titleField.getText(), descField.getText());
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        VBox box = new VBox(10, titleField, descField, saveButton);
        box.setStyle("-fx-padding: 20;");
        Stage stage = new Stage();
        stage.setScene(new Scene(box, 400, 250));
        stage.setTitle("Edit Question");
        stage.show();
    }

    private void showAnswersDialog(Question q) {
        VBox container = new VBox(10);
        List<Answer> answers = dbHelper.getAnswersForQuestion(q.getId());

        for (Answer a : answers) {
            HBox aBox = new HBox(10);
            Label content = new Label(a.getContent());

            Button editBtn = new Button("Edit");
            editBtn.setOnAction(e -> showEditAnswerDialog(a, container));

            Button delBtn = new Button("Delete");
            delBtn.setOnAction(e -> {
                dbHelper.deleteAnswer(a.getId());
                showAnswersDialog(q); // refresh
            });

            aBox.getChildren().addAll(content, editBtn, delBtn);
            container.getChildren().add(aBox);
        }

        TextArea newAnswerArea = new TextArea();
        newAnswerArea.setPromptText("Write your answer...");
        newAnswerArea.setPrefRowCount(3);

        Button postAnswer = new Button("Post Answer");
        postAnswer.setOnAction(e -> {
            try {
                dbHelper.createAnswer(q.getId(), newAnswerArea.getText());
                showAnswersDialog(q);
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        VBox box = new VBox(10, container, newAnswerArea, postAnswer);
        box.setStyle("-fx-padding: 20;");
        Stage stage = new Stage();
        stage.setScene(new Scene(box, 500, 400));
        stage.setTitle("Answers for: " + q.getTitle());
        stage.show();
    }

    private void showEditAnswerDialog(Answer a, VBox container) {
        TextArea content = new TextArea(a.getContent());
        content.setWrapText(true);
        content.setPrefRowCount(3);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            try {
                dbHelper.updateAnswer(a.getId(), content.getText());
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        VBox box = new VBox(10, content, saveBtn);
        box.setStyle("-fx-padding: 20;");
        Stage stage = new Stage();
        stage.setScene(new Scene(box, 400, 250));
        stage.setTitle("Edit Answer");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
