import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ATMFX1 extends Application {

    private Bank theBank;
    private User curUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize the bank
        theBank = new Bank("Bank of Oussama");

        primaryStage.setTitle("ATM Application");

        // Create a login scene
        Scene loginScene = createLoginScene(primaryStage);
        primaryStage.setScene(loginScene);

        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label titleLabel = new Label("Welcome to " + theBank.getName());
        GridPane.setConstraints(titleLabel, 0, 0);

        TextField userIDInput = new TextField();
        userIDInput.setPromptText("User ID");
        GridPane.setConstraints(userIDInput, 0, 1);

        PasswordField pinInput = new PasswordField();
        pinInput.setPromptText("PIN");
        GridPane.setConstraints(pinInput, 0, 2);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 0, 3);

        grid.getChildren().addAll(titleLabel, userIDInput, pinInput, loginButton);

        loginButton.setOnAction(e -> {
            String userID = userIDInput.getText();
            String pin = pinInput.getText();
            curUser = theBank.userLogin(userID, pin);

            if (curUser != null) {
                primaryStage.setScene(createMainMenuScene(primaryStage));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Incorrect user ID/pin. Please try again.");
                alert.showAndWait();
            }
        });

        return new Scene(grid, 300, 200);
    }

    private Scene createMainMenuScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label welcomeLabel = new Label("Welcome, " + curUser.getFirstName() + "!");
        GridPane.setConstraints(welcomeLabel, 0, 0);

        Button showHistoryButton = new Button("Show Account Transaction History");
        GridPane.setConstraints(showHistoryButton, 0, 1);

        Button withdrawalButton = new Button("Withdrawal");
        GridPane.setConstraints(withdrawalButton, 0, 2);

        Button depositButton = new Button("Deposit");
        GridPane.setConstraints(depositButton, 0, 3);

        Button transferButton = new Button("Transfer");
        GridPane.setConstraints(transferButton, 0, 4);

        Button quitButton = new Button("Quit");
        GridPane.setConstraints(quitButton, 0, 5);

        grid.getChildren().addAll(welcomeLabel, showHistoryButton, withdrawalButton, depositButton, transferButton, quitButton);

        showHistoryButton.setOnAction(e -> showTransactionHistory(primaryStage));
        withdrawalButton.setOnAction(e -> withdrawalFunds(primaryStage));
        depositButton.setOnAction(e -> depositFunds(primaryStage));
        transferButton.setOnAction(e -> transferFunds(primaryStage));
        quitButton.setOnAction(e -> primaryStage.close());

        return new Scene(grid, 300, 300);
    }
    private void showTransactionHistory(Stage primaryStage) {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    Label titleLabel = new Label("Transaction History");
    GridPane.setConstraints(titleLabel, 0, 0);

    // Add relevant UI components to display transaction history
    // For example, a TextArea to display the history
    TextArea historyTextArea = new TextArea();
    historyTextArea.setEditable(false);
    // Populate the text area with transaction history from theUser
    historyTextArea.setText(currentUser.getTransactionsSummary());

    GridPane.setConstraints(historyTextArea, 0, 1);

    grid.getChildren().addAll(titleLabel, historyTextArea);

    Scene historyScene = new Scene(grid, 300, 200);
    Stage historyStage = new Stage();
    historyStage.initModality(Modality.APPLICATION_MODAL);
    historyStage.setTitle("Transaction History");
    historyStage.setScene(historyScene);
    historyStage.showAndWait();
}

private void withdrawalFunds(Stage primaryStage) {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    Label titleLabel = new Label("Withdrawal");
    GridPane.setConstraints(titleLabel, 0, 0);

    TextField amountInput = new TextField();
    amountInput.setPromptText("Enter amount");
    GridPane.setConstraints(amountInput, 0, 1);

    TextField memoInput = new TextField();
    memoInput.setPromptText("Enter memo");
    GridPane.setConstraints(memoInput, 0, 2);

    Button withdrawButton = new Button("Withdraw");
    GridPane.setConstraints(withdrawButton, 0, 3);

    grid.getChildren().addAll(titleLabel, amountInput, memoInput, withdrawButton);

    withdrawButton.setOnAction(e -> {
        double amount = Double.parseDouble(amountInput.getText());
        String memo = memoInput.getText();
        theUser.addAcctTransaction(selectedAccount, -1 * amount, memo);
        // Update UI or show a confirmation dialog if needed
    });

    Scene withdrawalScene = new Scene(grid, 300, 200);
    Stage withdrawalStage = new Stage();
    withdrawalStage.initModality(Modality.APPLICATION_MODAL);
    withdrawalStage.setTitle("Withdrawal");
    withdrawalStage.setScene(withdrawalScene);
    withdrawalStage.showAndWait();
}
private void depositFunds(Stage primaryStage) {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    Label titleLabel = new Label("Deposit Funds");
    GridPane.setConstraints(titleLabel, 0, 0);

    TextField amountInput = new TextField();
    amountInput.setPromptText("Enter amount");
    GridPane.setConstraints(amountInput, 0, 1);

    TextField memoInput = new TextField();
    memoInput.setPromptText("Enter memo");
    GridPane.setConstraints(memoInput, 0, 2);

    Button depositButton = new Button("Deposit");
    GridPane.setConstraints(depositButton, 0, 3);

    grid.getChildren().addAll(titleLabel, amountInput, memoInput, depositButton);

    depositButton.setOnAction(e -> {
        double amount = Double.parseDouble(amountInput.getText());
        String memo = memoInput.getText();
        theUser.addAcctTransaction(selectedAccount, amount, memo);
        // Update UI or show a confirmation dialog if needed
    });

    Scene depositScene = new Scene(grid, 300, 200);
    Stage depositStage = new Stage();
    depositStage.initModality(Modality.APPLICATION_MODAL);
    depositStage.setTitle("Deposit Funds");
    depositStage.setScene(depositScene);
    depositStage.showAndWait();
}

private void transferFunds(Stage primaryStage) {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(8);
    grid.setHgap(10);

    Label titleLabel = new Label("Transfer Funds");
    GridPane.setConstraints(titleLabel, 0, 0);

    TextField amountInput = new TextField();
    amountInput.setPromptText("Enter amount");
    GridPane.setConstraints(amountInput, 0, 1);

    TextField memoInput = new TextField();
    memoInput.setPromptText("Enter memo");
    GridPane.setConstraints(memoInput, 0, 2);

    TextField toAccountInput = new TextField();
    toAccountInput.setPromptText("Enter destination account");
    GridPane.setConstraints(toAccountInput, 0, 3);

    Button transferButton = new Button("Transfer");
    GridPane.setConstraints(transferButton, 0, 4);

    grid.getChildren().addAll(titleLabel, amountInput, memoInput, toAccountInput, transferButton);

    transferButton.setOnAction(e -> {
        double amount = Double.parseDouble(amountInput.getText());
        String memo = memoInput.getText();
        int toAccount = Integer.parseInt(toAccountInput.getText()) - 1;

        // Validate the destination account
        if (toAccount < 0 || toAccount >= theUser.numAccounts()) {
            // Show an error message or dialog
            return;
        }

        theUser.addAcctTransaction(selectedAccount, -1 * amount, "Transfer to account " + theUser.getAcctUUID(toAccount));
        theUser.addAcctTransaction(toAccount, amount, "Transfer from account " + theUser.getAcctUUID(selectedAccount));
        // Update UI or show a confirmation dialog if needed
    });

    Scene transferScene = new Scene(grid, 300, 250);
    Stage transferStage = new Stage();
    transferStage.initModality(Modality.APPLICATION_MODAL);
    transferStage.setTitle("Transfer Funds");
    transferStage.setScene(transferScene);
    transferStage.showAndWait();
    }
}
