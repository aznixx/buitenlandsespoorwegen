package nl.ovchipkaart.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import nl.ovchipkaart.model.Account;

public class LoginController {

    private static final String ACCOUNTS_FOLDER = "ovchipkaart_accounts";

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox personalCheckBox;
    @FXML private Label messageLabel;
    @FXML private Label subtitleLabel;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Button langButton;
    @FXML private Button darkModeButton;

    @FXML
    public void initialize() {
        darkModeButton.setText(OVChipkaartApp.isDarkMode() ? "Light" : "Dark");
        langButton.setText(OVChipkaartApp.isDutch() ? "EN" : "NL");
        refreshLanguage();
    }

    @FXML
    private void onLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Fill in your email and password.");
            return;
        }

        Account account = Account.loadFromFile(ACCOUNTS_FOLDER, email);
        if (account == null) {
            showMessage("Account not found. Register first.");
            return;
        }

        if (!account.checkPassword(password)) {
            showMessage("Wrong password.");
            return;
        }

        openMainScreen(account);
    }

    @FXML
    private void onRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showMessage("Fill in all fields.");
            return;
        }

        if (Account.exists(ACCOUNTS_FOLDER, email)) {
            showMessage("Account already exists. Just log in.");
            return;
        }

        boolean personal = personalCheckBox.isSelected();
        Account account = new Account(name, email, password, personal);
        account.getCard(0).topUp(50, "machine");
        account.saveToFile(ACCOUNTS_FOLDER);

        showMessage("Account created! You can now log in.");
    }

    @FXML
    private void onToggleDarkMode() {
        OVChipkaartApp.setDarkMode(!OVChipkaartApp.isDarkMode());
        applyTheme();
    }

    @FXML
    private void onToggleLanguage() {
        OVChipkaartApp.setDutch(!OVChipkaartApp.isDutch());
        refreshLanguage();
    }

    private void applyTheme() {
        Scene scene = messageLabel.getScene();
        scene.getStylesheets().clear();
        String css = OVChipkaartApp.isDarkMode() ? "style-dark.css" : "style.css";
        scene.getStylesheets().add(OVChipkaartApp.class.getResource(css).toExternalForm());
        darkModeButton.setText(OVChipkaartApp.isDarkMode() ? "Light" : "Dark");
    }

    private void refreshLanguage() {
        boolean nl = OVChipkaartApp.isDutch();
        subtitleLabel.setText(nl ? "Inloggen of account aanmaken" : "Log in or create an account");
        nameLabel.setText(nl ? "Naam" : "Name");
        emailLabel.setText(nl ? "E-mail" : "Email");
        passwordLabel.setText(nl ? "Wachtwoord" : "Password");
        personalCheckBox.setText(nl ? "Persoonlijke kaart" : "Personal card");
        loginButton.setText(nl ? "Inloggen" : "Login");
        registerButton.setText(nl ? "Registreren" : "Register");
        langButton.setText(nl ? "EN" : "NL");
    }

    private void openMainScreen(Account account) {
        try {
            OVChipkaartApp.showMainScreen(account);
        } catch (Exception e) {
            showMessage("Could not open main screen: " + e.getMessage());
        }
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
    }
}
