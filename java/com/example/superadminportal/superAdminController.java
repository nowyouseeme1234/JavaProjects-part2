package com.example.superadminportal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.example.superadminportal.databaseConnection;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

public class superAdminController {
    String filePath = "C:\\Users\\hp\\IdeaProjects\\superAdminPortal\\src\\main\\resources\\logs.txt";
    @FXML
    private Button cancelBtn,removeBtn,addBtn,adminBtn,addEmployeeBtn,addCustomerBtn,addAdminBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane addAnchorContainer,contentContainer,inputContainer,removeAnchorContainer;
    @FXML
    private Label label1,label2,inputContainerLabel,nameLbl,passwordLbl,errorMsg;
    @FXML
    private Button backBtn,logsBtn,messageBtn,notifyBtn,removeAdminBtn,removeCustomerBtn,removeEmployeeBtn,reportBtn,serviceBtn,showTable;
    @FXML
    private TextField userNameInput,nameInput;
    @FXML
    private TextArea logsTextArea;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ChoiceBox<String> roleInput;
    @FXML
    private TableView<?> contentTable;
    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnPassword;

    @FXML
    private TableColumn<?, ?> columnRole;

    @FXML
    private TableColumn<?, ?> columnUsername;
    @FXML
    private void initialize() {
        contentTable.setVisible(false);
        roleInput.setItems(FXCollections.observableArrayList("Admin","Employee","Customer"));
        contentContainer.setVisible(false);
        inputContainer.setVisible(false);
        logsTextArea.setVisible(false);
        adminBtn.setOnAction(actionEvent -> {
            logsTextArea.setVisible(false);
            contentTable.setVisible(false);
            inputContainerLabel.setText("Insert");
            addBtn.setText("Add");
            roleInput.setValue("");
            contentContainer.setVisible(true);
            removeAnchorContainer.setVisible(false);
            addAnchorContainer.setVisible(true);
        });
        removeBtn.setOnAction(actionEvent -> {
            logsTextArea.setVisible(false);
            contentTable.setVisible(false);
            inputContainerLabel.setText("Remove");
            addBtn.setText("Delete");
            roleInput.setValue("");
            contentContainer.setVisible(true);
            addAnchorContainer.setVisible(false);
            removeAnchorContainer.setVisible(true);
        });
        addAdminBtn.setOnAction(actionEvent -> {
           addBtn.setText("Add");
           inputContainerLabel.setText("Add");
           roleInput.setValue("Admin");
            inputContainer.setVisible(true);
        });
        addCustomerBtn.setOnAction(actionEvent -> {
            addBtn.setText("Add");
            inputContainerLabel.setText("Add");
            roleInput.setValue("Customer");
            inputContainer.setVisible(true);
        });
        addEmployeeBtn.setOnAction(actionEvent -> {
            addBtn.setText("Add");
            inputContainerLabel.setText("Add");
            roleInput.setValue("Employee");
            inputContainer.setVisible(true);
        });
        removeAdminBtn.setOnAction(actionEvent -> {
            roleInput.setValue("Admin");
            inputContainer.setVisible(true);
        });
        removeCustomerBtn.setOnAction(actionEvent -> {
            roleInput.setValue("Customer");
            inputContainer.setVisible(true);
        });
        removeEmployeeBtn.setOnAction(actionEvent -> {
            roleInput.setValue("Employee");
            inputContainer.setVisible(true);
        });
        addBtn.setOnAction(actionEvent -> {
            String btnText = addBtn.getText();
            System.out.println(btnText);
            if(btnText.equals("Delete") && !nameInput.getText().isEmpty() && !userNameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()){
                errorMsg.setText("");
                passwordLbl.setText("Password");
                String Username = userNameInput.getText();
                String Role = roleInput.getValue();
                String Name = nameInput.getText();
                String Password = passwordInput.getText();
                int id = 0;
                    //database connection starts
                try (Connection connection = databaseConnection.getConnection()) {
                    String deleteSql2 = "DELETE t1,t2 FROM Account AS t1 JOIN User AS t2 ON t1.Foreign_ID = t2.ID WHERE t2.Name = ? AND t1.Role = ? AND t1.Username = ?";

                    //===========================prepared statments

                    PreparedStatement preparedStatement = connection.prepareStatement(deleteSql2);

                    String rowUsernameToDelete = Username;
                    String rowRoleToDelete = Role;
                    String nameToBeDeleted = Name;

                    String superAdminPass = "select Password from Account where Role = 'Super Admin'";
                    PreparedStatement selectPass = connection.prepareStatement(superAdminPass);
                    ResultSet resultSet = selectPass.executeQuery(superAdminPass);
                    while (resultSet.next()) {
                        String pass = resultSet.getString("Password");
                        if (!pass.equals(Password) ){
                            errorMsg.setText("You are not Authenticated");
                            return;
                        }
                    }
                    //=============================first table row deletion

                    preparedStatement.setString(1, nameToBeDeleted);
                    preparedStatement.setString(2, rowRoleToDelete);
                    preparedStatement.setString(3, rowUsernameToDelete);

                    //=============================second table row deletion
//                    String selectAll = "select * from Account";
//                    PreparedStatement selectPass = connection.prepareStatement(selectAll);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        errorMsg.setText("User deleted successfully");
                        errorMsg.setStyle("-fx-text-fill:white");
                        System.out.println("Row deleted successfully.");
                    } else {
                        errorMsg.setText("User not found!");
                        System.out.println("No rows were affected. The deletion may have failed.");
                    }

                    //database connection and prepared statements end

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(btnText.equals("Add") && !nameInput.getText().isEmpty() && !userNameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()){
                String Username = userNameInput.getText();
                String Password = passwordInput.getText();
                String Role = roleInput.getValue();
                String Name = nameInput.getText();
                System.out.println(Username);
                System.out.println(Password);
                System.out.println(Role);
                errorMsg.setText("");
                try {
                    Random random = new Random();
                    int randomId = Math.abs(random.nextInt());

                    //=============================database connection starts

                    try (Connection connection = databaseConnection.getConnection()) {
//                        String sql = "INSERT INTO User (ID, Name) VALUES (?,?)";
                        String sql = "INSERT INTO User (Name) VALUES (?)";
//                        String sql2 = "INSERT INTO Account (Username, Password, Foreign_ID, Role) VALUES (?,?,?,?)";
                        String sql2 = "INSERT INTO Account (Username, Password, Role) VALUES (?,?,?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

                        String selectUsername = "select Username from Account ";
                        ResultSet resultSet = preparedStatement2.executeQuery(selectUsername);
                        while (resultSet.next()) {
                            String checkUsername = resultSet.getString("Username");
                            if (checkUsername.equals(Username) ){
                                errorMsg.setText("This Username has taken");
                                return;
                            }
                        }
                        //=======================================first table

//                        preparedStatement.setInt(1, randomId);
                        preparedStatement.setString(1, Name);

                        //=======================================second table

                        preparedStatement2.setString(1, Username);
                        preparedStatement2.setString(2, Password);
//                        preparedStatement2.setInt(3, randomId);
                        preparedStatement2.setString(3, Role);

                        int rowsAffected1 = preparedStatement.executeUpdate();
                        int rowsAffected2 = preparedStatement2.executeUpdate();

                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Data Insertion successfully.");
                        } else {
                            System.out.println("No rows were affected. The Insertion may have failed.");
                        }

                        //===================================database connection and prepared statements  ends
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //clear the input fields

            userNameInput.setText("");
            passwordInput.setText("");
            nameInput.setText("");
        });
        cancelBtn.setOnAction(actionEvent -> {
            userNameInput.setText("");
            passwordInput.setText("");
            nameInput.setText("");
        });
        showTable.setOnAction(actionEvent -> {
            logsTextArea.setVisible(false);
            contentTable.setVisible(true);
            contentContainer.setVisible(false);
            Connection connection = null;
            try {
                connection = databaseConnection.getConnection();
                String query = "select ID,Name,Username,Password,Role from User as t2 join Account as t1 where t1.Foreign_ID = t2.ID";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                ObservableList<tabelData> data = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    tabelData rowData = new tabelData();
                    rowData.setColumn1(resultSet.getInt("ID"));
                    rowData.setColumn2(resultSet.getString("Name"));
                    rowData.setColumn3(resultSet.getString("Username"));
                    rowData.setColumn4(resultSet.getString("Password"));
                    rowData.setColumn5(resultSet.getString("Role"));
                    data.add(rowData);
                }
                TableView<?> tableView = contentTable;
//                tableView.setItems(data);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
        backBtn.setOnAction(actionEvent -> {
            try {
                superAdminPortal.sceneFactory("Login");
                //==================================file writer

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();
                String content = ("Super Admin " + " has logged-Out in " + currentDate + " at "+currentTime.getHour()+ ":"+currentTime.getMinute()+":"+currentTime.getSecond());
                FileWriter writer = new FileWriter(filePath,true);
                PrintWriter out = new PrintWriter(writer);
                out.println(content);
                out.close();

                //==================================file writer ends
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logsBtn.setOnAction(actionEvent -> {
            logsTextArea.setVisible(true);
            File read = new File(filePath);
            try {
                Scanner input = new Scanner(read);
                while (input.hasNext()){
                    System.out.println(input);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public class tabelData {
       private int column1;
       private String column2,column3,column4,column5;

       private int setColumn1 (int id) {
           this.column1 = id;
           return  column1;
        }
        private String setColumn2 (String name) {
            this.column2 = name;
            return  column2;
        }
        private String setColumn3 (String username) {
            this.column3 = username;
            return  column3;
        }
        private String setColumn4 (String password) {
            this.column4 = password;
            return  column4;
        }
        private String setColumn5 (String role) {
            this.column5 = role;
            return  column5;
        }
    }


}
