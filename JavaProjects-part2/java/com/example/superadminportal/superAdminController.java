package com.example.superadminportal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

public class superAdminController {
    String filePath = "C:\\Users\\hp\\IdeaProjects\\superAdminPortal2\\src\\main\\resources\\logs.txt";
    @FXML
    private Button cancelBtn,removeBtn,addBtn,adminBtn,addEmployeeBtn,addCustomerBtn,addAdminBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane addAnchorContainer,contentContainer,inputContainer,removeAnchorContainer, employeeInputContainer;
    @FXML
    private Label label1,label2,inputContainerLabel,nameLbl,passwordLbl,errorMsg;
    @FXML
    private Button backBtn,logsBtn,messageBtn,notifyBtn,removeAdminBtn,removeCustomerBtn,removeEmployeeBtn,reportBtn,serviceBtn,showTable;
    @FXML
    private TextField userNameInput,nameInput, ageInput, deptInput, salaryInput, shiftInput;
    @FXML
    private TextArea logsTextArea;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ChoiceBox<String> roleInput;

    @FXML
    private TableView<tabelData> contentTable;

    @FXML
    private TableColumn<tabelData, Integer> columnID;

    @FXML
    private TableColumn<tabelData, String> columnName;

    @FXML
    private TableColumn<tabelData, String> columnPassword;

    @FXML
    private TableColumn<tabelData, String> columnRole;

    @FXML
    private TableColumn<tabelData, String> columnUsername;

    @FXML
    private void initialize() {
        employeeInputContainer.setVisible(false);
        contentTable.setVisible(false);
        roleInput.setItems(FXCollections.observableArrayList("Admin","Employee","Customer"));
        contentContainer.setVisible(false);
        inputContainer.setVisible(false);
        logsTextArea.setVisible(false);
        messageBtn.setVisible(false);
        serviceBtn.setVisible(false);
        notifyBtn.setVisible(false);
        reportBtn.setVisible(false);
        adminBtn.setOnAction(actionEvent -> {
            employeeInputContainer.setVisible(false);
            logsTextArea.setVisible(false);
            contentTable.setVisible(false);
            inputContainerLabel.setText("Insert");
            addBtn.setText("Add");
            roleInput.setValue("");
            contentContainer.setVisible(true);
            removeAnchorContainer.setVisible(false);
            addAnchorContainer.setVisible(true);
            errorMsg.setText("");
        });
        removeBtn.setOnAction(actionEvent -> {
            employeeInputContainer.setVisible(false);
            logsTextArea.setVisible(false);
            contentTable.setVisible(false);
            inputContainerLabel.setText("Remove");
            addBtn.setText("Delete");
            roleInput.setValue("");
            contentContainer.setVisible(true);
            addAnchorContainer.setVisible(false);
            removeAnchorContainer.setVisible(true);
            errorMsg.setText("");
        });
        addAdminBtn.setOnAction(actionEvent -> {
           addBtn.setText("Add");
           inputContainerLabel.setText("Add");
           roleInput.setValue("Admin");
           inputContainer.setVisible(true);
            employeeInputContainer.setVisible(false);
        });
        addCustomerBtn.setOnAction(actionEvent -> {
            addBtn.setText("Add");
            inputContainerLabel.setText("Add");
            roleInput.setValue("Customer");
            inputContainer.setVisible(true);
            employeeInputContainer.setVisible(false);
        });
        addEmployeeBtn.setOnAction(actionEvent -> {
            addBtn.setText("Add");
            inputContainerLabel.setText("Add");
            roleInput.setValue("Employee");
            inputContainer.setVisible(true);
            employeeInputContainer.setVisible(true);
        });
        removeAdminBtn.setOnAction(actionEvent -> {
            employeeInputContainer.setVisible(false);
            roleInput.setValue("Admin");
            inputContainer.setVisible(true);
        });
        removeCustomerBtn.setOnAction(actionEvent -> {
            employeeInputContainer.setVisible(false);
            roleInput.setValue("Customer");
            inputContainer.setVisible(true);
        });
        removeEmployeeBtn.setOnAction(actionEvent -> {
            employeeInputContainer.setVisible(false);
            roleInput.setValue("Employee");
            inputContainer.setVisible(true);
        });
        addBtn.setOnAction(actionEvent -> {
            String btnText = addBtn.getText();
            String Age = ageInput.getText();
            String Salary = salaryInput.getText();
            String Dept = deptInput.getText();
            String Shift = shiftInput.getText();
            String Username = userNameInput.getText();
            String Role = roleInput.getValue();
            String Name = nameInput.getText();
            String Password = passwordInput.getText();
            System.out.println(btnText);
            if(btnText.equals("Delete") && !Name.isEmpty() && !Username.isEmpty() && !Password.isEmpty()){
                errorMsg.setText("");
                passwordLbl.setText("Super Admin Password");

                int id = 0;
                    //database connection starts
                try (Connection connection = databaseConnection.getConnection()) {
                    String deleteSql2 = "DELETE t1,t2 FROM Account AS t1 JOIN User AS t2 ON t1.Foreign_ID = t2.ID WHERE t2.Name = ? AND t1.Role = ? AND t1.Username = ?";

                    //===========================prepared statments

                    String rowUsernameToDelete = Username;
                    String rowRoleToDelete = Role;
                    String nameToBeDeleted = Name;
                        if(rowRoleToDelete.equals("Employee")){
                            deleteSql2 = "DELETE t1,t2 ,t3 FROM Account AS t1 JOIN User AS t2 ON t1.Foreign_ID = t2.ID JOIN employee_info AS t3 on t3.ID = t2.ID WHERE t2.Name = ? AND t1.Role = ? AND t1.Username = ?";
                        }
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteSql2);

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
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        superAdminPortal.setDelete_usernameName(rowUsernameToDelete,rowRoleToDelete);
                        superAdminPortal.getDelete();
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
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(btnText.equals("Add") && !Name.isEmpty() && !Username.isEmpty() && !Password.isEmpty()){
                System.out.println(Username);
                System.out.println(Password);
                System.out.println(Role);
                System.out.println(Age);
                errorMsg.setText("");

                //=============================database connection starts

                try  {
                    ResultSet resultSet = superAdminPortal.selectUserName();
                    while (resultSet.next()) {
                        String checkUsername = resultSet.getString("Username");
                        if (checkUsername.equals(Username) ){
                            errorMsg.setText("This username is already in use");
                            return;
                        }

                    }
                        if (Role.equals("Admin") || Role.equals("Customer"))
                            superAdminPortal.insertIntoUserAndAccountTable(Name, Username, Password, Role);
                        else if (employeeInputContainer.isVisible() && !Age.isEmpty() && !Salary.isEmpty() && !Dept.isEmpty() && !Shift.isEmpty())
                            superAdminPortal.createEmployeeAccount(Name, Username, Password, Age, Shift, Dept, Salary);
                        else
                            errorMsg.setText("Please Insert all the inputs");



                    //===================================database connection and prepared statements  ends
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                errorMsg.setText("Please Insert all the inputs");
            }
            //clear the input fields

            userNameInput.setText("");
            passwordInput.setText("");
            nameInput.setText("");
            superAdminPortal.clearFields(shiftInput, deptInput, salaryInput, ageInput);
        });
        cancelBtn.setOnAction(actionEvent -> {
            userNameInput.setText("");
            passwordInput.setText("");
            nameInput.setText("");
            errorMsg.setText("");

        });
        showTable.setOnAction(actionEvent -> {
            logsTextArea.setVisible(false);
            contentTable.setVisible(true);
            contentContainer.setVisible(false);
            try {
                Connection connection  = databaseConnection.getConnection();
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
                columnID.setCellValueFactory(new PropertyValueFactory<>("column1"));
                columnName.setCellValueFactory(new PropertyValueFactory<>("column2"));
                columnUsername.setCellValueFactory(new PropertyValueFactory<>("column3"));
                columnPassword.setCellValueFactory(new PropertyValueFactory<>("column4"));
                columnRole.setCellValueFactory(new PropertyValueFactory<>("column5"));

                contentTable.setItems(data);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        backBtn.setOnAction(actionEvent -> {
            try {
                superAdminPortal.sceneFactory("Login");
                //==================================file writer

                superAdminPortal.getLogOut();

                //==================================file writer ends
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logsBtn.setOnAction(actionEvent -> {
            logsTextArea.setVisible(true);
            logsTextArea.setText("");
            File read = new File(filePath);
            try {
                Scanner input = new Scanner(read);
                while (input.hasNext()) {
                    String line = input.nextLine();
                    if (!logsTextArea.getText().equals(line)){
                        logsTextArea.appendText(line + "\n" + "\n");
                    }
                }
                input.close(); // Remember to close the Scanner
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }
public static class tabelData {
    private int column1;
    private String column2, column3, column4, column5;

    public int getColumn1() {
        return column1;
    }
    public String getColumn2() {
        return column2;
    }
    public String getColumn3() {
        return column3;
    }
    public String getColumn4() {
        return column4;
    }
    public String getColumn5() {
        return column5;
    }

    public void setColumn1(int id) {
        this.column1 = id;
    }
    public void setColumn2(String name) {
        this.column2 = name;
    }
    public void setColumn3(String username) {
        this.column3 = username;
    }
    public void setColumn4(String password) {
        this.column4 = password;
    }
    public void setColumn5(String role) {
        this.column5 = role;
    }
}
}
