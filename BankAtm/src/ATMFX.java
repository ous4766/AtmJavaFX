import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class ATMFX extends Application {

Scanner sc = new Scanner(System.in);

    public Bank theBank;
    public User currentUser;
    public Account currentAccount;
    public Transaction currentTransaction;
    

     public static void main(String[] args) {
        launch(args);
    }
   
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM JavaFX");

        // Initialize the bank
        theBank = new Bank("Bank of Oussama");

        // Create a dummy user for demonstration
        User aUser = theBank.AddUser("Bob", "Smith", "1234");

        // Add a checking account for the user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        // Set up the JavaFX UI
       
       
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        root.setStyle("-fx-background-color:#86ebe9;");


        Label welcomeLabel = new Label("Welcome to " + theBank.getName());

        //apply css design 
        welcomeLabel.setStyle("-fx-font-size: 20px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #0066cc;" +
        "-fx-background-color: #86ebe9;");
        
        Label userLabel = new Label("User ID:");
        userLabel.setStyle("-fx-font-size: 12px;" +
        "-fx-font-weight: Medium;" +
        "-fx-text-fill: #000000;" +
        "-fx-background-color: #86ebe9;");
        
        TextField userIdField = new TextField();
        userIdField.setStyle(
                "-fx-background-color: #f0f0f0; " +
                "-fx-border-color: #b0b0b0 #b0b0b0 #d0d0d0 #d0d0d0; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 2; " +
                "-fx-padding: 2; " +
                "-fx-font-size: 12px;");

        Label pinLabel = new Label("PIN:");
         pinLabel.setStyle("-fx-font-size: 12px;" +
        "-fx-font-weight: Medium;" +
        "-fx-text-fill: #000000;" +
        "-fx-background-color: #86ebe9;"); 
        PasswordField pinField = new PasswordField();
        pinField.setStyle("-fx-text-fill: red;");
       
        Button loginButton = new Button("Login");

        root.getChildren().addAll(
                welcomeLabel,
                userLabel,
                userIdField,
                pinLabel,
                pinField,
                loginButton
        );

        loginButton.setOnAction(event -> {
            String userID = userIdField.getText();
        String pin = pinField.getText();
        loginUser(primaryStage, userID, pin);
        });

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loginUser(Stage primaryStage, String userID, String pin) {
        User authUser = theBank.userLogin(userID, pin);
    
        if (authUser != null) {
            currentUser = authUser;
    
            if (authUser.numAccounts() > 0) {
                // If the user has multiple accounts, prompt them to choose one
                if (authUser.numAccounts() > 1) {
                    int accountIndex = chooseAccount(authUser);
    
                    if (accountIndex != -1) {
                        Account selectedAccount = authUser.getAccount(accountIndex);
                        currentAccount = selectedAccount;
                        showMainMenu(primaryStage, authUser);
                    } else {
                        // User canceled or entered an invalid account index
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid account selection. Please try again.");
                        alert.showAndWait();
                    }
                } else {
                    // If the user has only one account, set it as the current account
                    currentAccount = authUser.getAccount(0);
                    showMainMenu(primaryStage, authUser);
                }
            } else {
                // Handle the case where the user has no accounts
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("User has no accounts.");
                alert.showAndWait();
            }
        } else {
            // Failed login
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect user ID/pin. Please try again.");
            alert.showAndWait();
        }
    }
    
    // Helper method to prompt the user to choose an account
    private int chooseAccount(User authUser) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Choose Account");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose your account:");
    
        // Populate the choice dialog with account options
        for (int i = 0; i < authUser.numAccounts(); i++) {
            dialog.getItems().add("Account " + (i + 1));
        }
    
        Optional<String> result = dialog.showAndWait();
    
        if (result.isPresent()) {
            // Extract the selected account index from the user's choice
            String choice = result.get();
            int accountIndex = Integer.parseInt(choice.substring(choice.lastIndexOf(" ") + 1)) - 1;
            return accountIndex;
        } else {
            // User canceled the dialog
            return -1;
        }
    }

    private void showMainMenu(Stage primaryStage, User authUser) {
        // Create a new stage for the main menu
        Stage mainMenuStage = new Stage();
        mainMenuStage.setTitle("Main Menu");
    
        VBox mainMenuRoot = new VBox(10);
        mainMenuRoot.setAlignment(Pos.CENTER);
        mainMenuRoot.setStyle("-fx-background-color: #f0f0f0;");
    
        Label welcomeLabel = new Label("Welcome, " + authUser.getFirstName());
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    
        Button viewBalanceButton = new Button("View Balance");
        Button viewTransactionHistoryButton = new Button("View Transaction History");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transferButton = new Button("Transfer Money");
         // New button for switching accounts
        Button switchAccountButton = new Button("Switch Account");
    
        // Event handlers for the new buttons
        viewBalanceButton.setOnAction(event -> {
            Platform.runLater(() -> {
                viewBalance(authUser);
            });
        });
    
        viewTransactionHistoryButton.setOnAction(event -> {
            Platform.runLater(() -> {
                showTransHistory(authUser);
            });
        });
    
       depositButton.setOnAction(event -> {
            Platform.runLater(() -> {
                deposit(authUser,currentAccount);
            });
        });
    
        withdrawButton.setOnAction(event -> {
            Platform.runLater(() -> {
                withdraw(authUser, currentAccount);
            });
        });
        transferButton.setOnAction(event -> {
            Platform.runLater(() -> {
                transferMoney();
            });
        });
    
        switchAccountButton.setOnAction(event -> {
            Platform.runLater(() -> {
                switchAccount(authUser);
            });
        });

        mainMenuRoot.getChildren().addAll(welcomeLabel, viewBalanceButton, viewTransactionHistoryButton, depositButton, withdrawButton, transferButton, switchAccountButton);
    
        Scene mainMenuScene = new Scene(mainMenuRoot, 300, 200);
        mainMenuStage.setScene(mainMenuScene);
    
        // Show the main menu stage
        mainMenuStage.show();
    
        // Close the login stage
        primaryStage.close();
    }
    // New method to display account balance
   
    private void switchAccount(User authUser) {
        int accountIndex = chooseAccount(authUser);
    
        if (accountIndex != -1) {
            Account selectedAccount = authUser.getAccount(accountIndex);
            currentAccount = selectedAccount;
    
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Switch");
            alert.setHeaderText(null);
            alert.setContentText("Switched to account: " + currentAccount.getUUID());
            alert.showAndWait();
        } else {
            // User canceled or entered an invalid account index
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account Switch Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid account selection. Please try again.");
            alert.showAndWait();
        }
    }
   
    private void viewBalance(User authUser) {
        System.out.println("!!! I've reached viewBalance() outside if statement !!!");

        if(currentUser == null){
            System.out.println("currentUser is null");
        }else{
            System.out.println("currentUser is not null");
        }

        if(currentAccount == null){
            System.out.println("currentAccount is null");
        }else{
            System.out.println("currentAccount is not null");
        }
       
        if (authUser  != null && currentAccount != null) {
            System.out.println("!!! I've reached viewBalance() !!!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Balance");
            alert.setHeaderText(null);
            alert.setContentText("Account Balance: £" + currentAccount.getBalance());
    
            // Apply CSS styles to the alert
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #f0f0f0;");
    
            Label contentLabel = (Label) dialogPane.lookup(".content.label");
            if (contentLabel != null) {
                contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333;");
            }
    
            // Create a custom close button
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(closeButton);
    
            // Set action for the close button
            Button closeButtonNode = (Button) dialogPane.lookupButton(closeButton);
            closeButtonNode.setOnAction(event -> alert.close());
    
            alert.showAndWait();
        }
    }
    
    

    // New method to display transaction history
    public static void showTransHistory(User theUser) {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Transaction History");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the account number (1-" + theUser.numAccounts() + "):");
    
            Optional<String> result = dialog.showAndWait();
    
            result.ifPresent(accountNumberStr -> {
                try {
                    int theAcct = Integer.parseInt(accountNumberStr) - 1;
    
                    if (theAcct >= 0 && theAcct < theUser.numAccounts()) {
                        // Valid account number
                        double accountBalance = theUser.getAcctBalance(theAcct);
                        List<Transaction> transactions = theUser.getAcctTransactions(theAcct);
    
                        // Print debug information
                        System.out.println("Account Balance: £" + accountBalance);
                        System.out.println("Number of transactions: " + transactions.size());
    
                        // Display an "OK" button if it doesn't already exist
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Transaction History");
                        alert.setHeaderText(null);
    
                        StringBuilder contentText = new StringBuilder("Account Balance: £" + accountBalance + "\n\nTransaction history for account " + (theAcct + 1) + ":\n");
    
                        for (Transaction transaction : transactions) {
                            contentText.append(transaction.getSummaryLine()).append("\n");
    
                            // Include transfer details in the transaction history
                            if (transaction instanceof TransferTransaction) {
                                TransferTransaction transferTransaction = (TransferTransaction) transaction;
                                contentText.append("  Transfer from: ").append(transferTransaction.getSrcAccount().getUUID()).append("\n");
                                contentText.append("  Transfer to: ").append(transferTransaction.getDestAccount().getUUID()).append("\n");
                            }
                        }
    
                        // Print debug information
                        System.out.println("Content Text:\n" + contentText.toString());
    
                        alert.setContentText(contentText.toString());
    
                        if (!alert.getButtonTypes().contains(ButtonType.OK)) {
                            alert.getButtonTypes().add(ButtonType.OK);
                        }
    
                        alert.showAndWait();
                    } else {
                        // Invalid account number
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid account number. Please enter a valid number.");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    // Invalid input format
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid input format. Please enter a valid number.");
                    alert.showAndWait();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    


    private void deposit(User authUser, Account selectedAccount) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter deposit amount:");

        dialog.showAndWait().ifPresent(amount -> {
            try {
                double depositAmount = Double.parseDouble(amount);
                // Ensure currentUser and currentAccount are not null
                if (currentUser != null && currentAccount != null) {
                    currentAccount.addTransaction(depositAmount, "Deposit");
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Deposit Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Deposit successful!");
                    successAlert.showAndWait();
                } else {
                    // Handle null currentUser or currentAccount
                    System.out.println("Invalid user or account");
                }
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Invalid amount. Please enter a valid number.");
                errorAlert.showAndWait();
            }
        });
    }
    private void withdraw(User authUser, Account selectedAccount) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdrawal");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter withdrawal amount:");
    
        dialog.showAndWait().ifPresent(amount -> {
            try {
                double withdrawalAmount = Double.parseDouble(amount);
                // Ensure currentUser and currentAccount are not null
                if (currentUser != null && currentAccount != null) {
                    // Validate if the withdrawal amount is non-negative
                    if (withdrawalAmount >= 0) {
                        // Make sure the user has sufficient balance before allowing withdrawal
                        if (currentAccount.getBalance() >= withdrawalAmount) {
                            System.out.println("Before withdrawal: " + currentAccount.getBalance()); // Debugging line
                            currentAccount.withdraw(withdrawalAmount, "Withdrawal");
                            System.out.println("After withdrawal: " + currentAccount.getBalance()); // Debugging line
    
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Withdrawal Success");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Withdrawal successful!");
                            successAlert.showAndWait();
                        } else {
                            // Insufficient balance
                            showErrorAlert("Error", "Insufficient balance for withdrawal.");
                        }
                    } else {
                        // Negative amount is not allowed
                        showErrorAlert("Error", "Invalid withdrawal amount. Please enter a non-negative amount.");
                    }
                } else {
                    // Handle null currentUser or currentAccount
                    showErrorAlert("Error", "Invalid user or account");
                }
            } catch (NumberFormatException e) {
                // Invalid amount format
                showErrorAlert("Error", "Invalid withdrawal amount. Please enter a valid number.");
            }
        });
    }
    // Helper method to show an error alert
    private void showErrorAlert(String title, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
    

    private void transferMoney() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Transfer Money");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the amount to transfer:");

        // Traditional way to get the response value.
        dialog.showAndWait().ifPresent(amount -> {
            try {
                double transferAmount = Double.parseDouble(amount);

                // Ensure currentUser and currentAccount are not null
                if (currentUser != null && currentAccount != null) {
                    // Prompt user to select source and destination accounts
                    Account sourceAccount = selectAccount("Select Source Account:");
                    Account destinationAccount = selectAccount("Select Destination Account:");

                    if (sourceAccount != null && destinationAccount != null) {
                        sourceAccount.transfer(destinationAccount, transferAmount);

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Transfer Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Transfer successful!");
                        successAlert.showAndWait();
                    } else {
                        // Invalid account selection
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Invalid account selection. Please try again.");
                        errorAlert.showAndWait();
                    }
                } else {
                    // Handle null currentUser or currentAccount
                    System.out.println("Invalid user or account");
                }
            } catch (NumberFormatException e) {
                // Invalid amount
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Invalid amount. Please enter a valid number.");
                errorAlert.showAndWait();
            }
        });
    }

private Account selectAccount(String prompt) {
    ChoiceDialog<Account> accountDialog = new ChoiceDialog<>(currentUser.getAccount(0), currentUser.getAccounts());
    accountDialog.setTitle("Select Account");
    accountDialog.setHeaderText(null);
    accountDialog.setContentText(prompt);

    Optional<Account> result = accountDialog.showAndWait();
    return result.orElse(null);
}

}

