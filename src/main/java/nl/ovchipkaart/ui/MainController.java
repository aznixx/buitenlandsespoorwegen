package nl.ovchipkaart.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import nl.ovchipkaart.model.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private static final String ACCOUNTS_FOLDER = "ovchipkaart_accounts";

    @FXML private Label welcomeLabel;
    @FXML private ComboBox<String> cardSelectorComboBox;
    @FXML private Label balanceLabel;
    @FXML private Label statusLabel;
    @FXML private Label cardTypeLabel;
    @FXML private Label warningLabel;
    @FXML private Label dayPassLabel;
    @FXML private ComboBox<String> stationComboBox;
    @FXML private ComboBox<String> transportComboBox;
    @FXML private ComboBox<String> travelClassComboBox;
    @FXML private ComboBox<String> subscriptionComboBox;
    @FXML private Button tapButton;
    @FXML private TextField topUpAmountField;
    @FXML private CheckBox autoTopUpCheckBox;
    @FXML private TextField autoTopUpThresholdField;
    @FXML private TextField autoTopUpAmountField;
    @FXML private ListView<String> transactionList;
    @FXML private Label messageLabel;
    @FXML private Label monthSummaryLabel;
    @FXML private Label statsLabel;
    @FXML private Label cardInfoLabel;
    @FXML private TabPane tabPane;

    @FXML private ComboBox<String> fromStationComboBox;
    @FXML private ComboBox<String> toStationComboBox;
    @FXML private Label fareEstimateLabel;
    @FXML private Label bikeStatusLabel;
    @FXML private ComboBox<String> transferCardComboBox;
    @FXML private TextField transferAmountField;
    @FXML private Label loyaltyLabel;
    @FXML private TextField redeemPointsField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private CheckBox balanceProtectionCheckBox;

    @FXML private ImageView profileImageView;
    @FXML private ImageView profileImageLarge;
    @FXML private TextField editNameField;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private Button langButton;
    @FXML private Button darkModeButton;
    @FXML private Button logoutButton;

    @FXML private Tab travelTab;
    @FXML private Tab topUpTab;
    @FXML private Tab transactionsTab;
    @FXML private Tab accountTab;

    @FXML private Label stationLabel;
    @FXML private Label transportLabel;
    @FXML private Label classLabel;
    @FXML private Label subscriptionLabel;
    @FXML private Button dayPassButton;
    @FXML private Button missedCheckoutButton;
    @FXML private Label fareEstimatorTitle;
    @FXML private Label fromLabel;
    @FXML private Label toLabel;
    @FXML private Button estimateButton;
    @FXML private Label ovFietsTitle;
    @FXML private Button rentBikeButton;
    @FXML private Button returnBikeButton;
    @FXML private Label manualTopUpTitle;
    @FXML private Button machineButton;
    @FXML private Button onlineButton;
    @FXML private Label autoTopUpTitle;
    @FXML private Button applyButton;
    @FXML private Label transferTitle;
    @FXML private Button transferButton;
    @FXML private Label loyaltyTitle;
    @FXML private Button redeemButton;
    @FXML private Button refundButton;
    @FXML private Button exportButton;
    @FXML private Label myCardsTitle;
    @FXML private Button addPersonalButton;
    @FXML private Button addAnonButton;
    @FXML private Button removeCardButton;
    @FXML private Button blockButton;
    @FXML private Button unblockButton;
    @FXML private Button groupDayPassButton;
    @FXML private Label statsTitle;
    @FXML private Label cardInfoTitle;
    @FXML private Label profileTitle;
    @FXML private Button changeNameButton;
    @FXML private Button changePasswordButton;
    @FXML private Button uploadPhotoButton;

    private Account account;
    private OVCard card;
    private int selectedCardIndex;
    private List<Station> stations;

    @FXML
    public void initialize() {
        stations = List.of(
                new Station("Amsterdam Centraal", 0.0,
                        List.of(TransportType.TRAIN, TransportType.METRO, TransportType.TRAM)),
                new Station("Haarlem", 20.0,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Schiphol Airport", 15.2,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Leiden Centraal", 36.5,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Delft", 50.0,
                        List.of(TransportType.TRAIN, TransportType.TRAM, TransportType.BUS)),
                new Station("Den Haag Centraal", 56.4,
                        List.of(TransportType.TRAIN, TransportType.TRAM, TransportType.BUS)),
                new Station("Rotterdam Centraal", 78.1,
                        List.of(TransportType.TRAIN, TransportType.METRO, TransportType.TRAM, TransportType.BUS)),
                new Station("Utrecht Centraal", 52.0,
                        List.of(TransportType.TRAIN, TransportType.BUS, TransportType.TRAM)),
                new Station("Amersfoort", 65.0,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Arnhem Centraal", 99.0,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Breda", 105.0,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Eindhoven", 125.0,
                        List.of(TransportType.TRAIN, TransportType.BUS)),
                new Station("Groningen", 190.0,
                        List.of(TransportType.TRAIN, TransportType.BUS))
        );

        List<String> stationNames = stations.stream().map(Station::getName).toList();
        stationComboBox.setItems(FXCollections.observableArrayList(stationNames));
        stationComboBox.getSelectionModel().selectFirst();

        fromStationComboBox.setItems(FXCollections.observableArrayList(stationNames));
        fromStationComboBox.getSelectionModel().selectFirst();
        toStationComboBox.setItems(FXCollections.observableArrayList(stationNames));
        toStationComboBox.getSelectionModel().select(1);

        transportComboBox.setItems(FXCollections.observableArrayList("TRAIN", "BUS", "TRAM", "METRO"));
        transportComboBox.getSelectionModel().selectFirst();

        travelClassComboBox.setItems(FXCollections.observableArrayList("SECOND", "FIRST"));
        travelClassComboBox.getSelectionModel().selectFirst();
        travelClassComboBox.setOnAction(e -> {
            if (card != null) {
                card.setTravelClass(TravelClass.valueOf(travelClassComboBox.getValue()));
                showMessage("Travel class set to " + travelClassComboBox.getValue());
            }
        });

        subscriptionComboBox.setItems(FXCollections.observableArrayList(
                "NONE", "DAL_VOORDEEL", "WEEKEND_VRIJ", "ALTIJD_VOORDEEL"));
        subscriptionComboBox.getSelectionModel().selectFirst();
        subscriptionComboBox.setOnAction(e -> {
            if (card != null) {
                card.setSubscription(Subscription.valueOf(subscriptionComboBox.getValue()));
                showMessage("Subscription set to " + subscriptionComboBox.getValue());
            }
        });

        filterComboBox.setItems(FXCollections.observableArrayList(
                "ALL", "TRAVEL", "TOP_UP", "AUTO_TOP_UP", "TRANSFER",
                "REFUND", "PENALTY", "DAY_PASS", "OV_FIETS", "LOYALTY"));
        filterComboBox.getSelectionModel().selectFirst();
        filterComboBox.setOnAction(e -> refreshUI());

        cardSelectorComboBox.setOnAction(e -> {
            int index = cardSelectorComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0 && account != null) {
                selectedCardIndex = index;
                card = account.getCard(index);
                travelClassComboBox.setValue(card.getTravelClass().name());
                subscriptionComboBox.setValue(card.getSubscription().name());
                autoTopUpCheckBox.setSelected(card.isAutoTopUpEnabled());
                balanceProtectionCheckBox.setSelected(card.isBalanceProtection());
                updateTransferCardSelector();
                refreshUI();
            }
        });

        Circle clip = new Circle(17.5, 17.5, 17.5);
        profileImageView.setClip(clip);
        Circle clipLarge = new Circle(30, 30, 30);
        profileImageLarge.setClip(clipLarge);

        darkModeButton.setText(OVChipkaartApp.isDarkMode() ? "Light" : "Dark");
        langButton.setText(OVChipkaartApp.isDutch() ? "EN" : "NL");
    }

    public void setAccount(Account account) {
        this.account = account;
        this.selectedCardIndex = 0;
        this.card = account.getCard(0);
        welcomeLabel.setText("Welcome, " + account.getName());
        editNameField.setText(account.getName());
        updateCardSelector();
        updateTransferCardSelector();
        travelClassComboBox.setValue(card.getTravelClass().name());
        subscriptionComboBox.setValue(card.getSubscription().name());
        autoTopUpCheckBox.setSelected(card.isAutoTopUpEnabled());
        balanceProtectionCheckBox.setSelected(card.isBalanceProtection());
        loadProfilePicture();
        refreshLanguage();
        refreshUI();
        showMessage("Logged in. Balance: €" + String.format("%.2f", card.getBalance()));
    }

    private void loadProfilePicture() {
        String pfpPath = account.getProfilePicturePath();
        if (pfpPath != null) {
            File pfpFile = new File(pfpPath);
            if (pfpFile.exists()) {
                Image img = new Image(pfpFile.toURI().toString(), 35, 35, true, true);
                profileImageView.setImage(img);
                Image imgLarge = new Image(pfpFile.toURI().toString(), 60, 60, true, true);
                profileImageLarge.setImage(imgLarge);
            }
        }
    }

    private void updateCardSelector() {
        List<String> cardNames = new ArrayList<>();
        for (int i = 0; i < account.getCards().size(); i++) {
            OVCard c = account.getCard(i);
            String type = c.isPersonal() ? "Personal" : "Anonymous";
            cardNames.add(c.getCardNumber() + " (" + type + ")");
        }
        cardSelectorComboBox.setItems(FXCollections.observableArrayList(cardNames));
        cardSelectorComboBox.getSelectionModel().select(selectedCardIndex);
    }

    private void updateTransferCardSelector() {
        List<String> otherCards = new ArrayList<>();
        for (int i = 0; i < account.getCards().size(); i++) {
            if (i != selectedCardIndex) {
                OVCard c = account.getCard(i);
                String type = c.isPersonal() ? "Personal" : "Anonymous";
                otherCards.add(i + ": " + c.getCardNumber() + " (" + type + ")");
            }
        }
        transferCardComboBox.setItems(FXCollections.observableArrayList(otherCards));
        if (!otherCards.isEmpty()) {
            transferCardComboBox.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onTap() {
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
        if (stationIndex < 0) {
            showMessage("Select a station first.");
            return;
        }
        Station selected = stations.get(stationIndex);
        String transportName = transportComboBox.getValue();
        TransportType transport = TransportType.valueOf(transportName);

        if (card.isCheckedIn()) {
            double fare = card.checkOut(selected);
            if (fare < 0) {
                showMessage("Could not check out.");
            } else {
                showMessage("Checked out at " + selected.getName() + ". Fare: €" + String.format("%.2f", fare));
            }
        } else {
            if (!selected.hasTransportType(transport)) {
                showMessage(selected.getName() + " does not have " + transportName);
                return;
            }
            if (card.isBlocked()) {
                showMessage("Card is BLOCKED. Top up to unblock.");
                return;
            }
            if (card.checkIn(selected, transport)) {
                showMessage("Checked in at " + selected.getName() + " (" + transportName + ")");
            } else if (card.isExpired()) {
                showMessage("Card is expired!");
            } else if (card.getBalance() < 20 && !card.isDayPassValid()) {
                showMessage("Not enough balance. You need at least €20.");
            } else {
                showMessage("Could not check in.");
            }
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onMachineTopUp() {
        doTopUp("machine", 5);
    }

    @FXML
    private void onOnlineTopUp() {
        doTopUp("online", 10);
    }

    private void doTopUp(String source, double minimum) {
        String text = topUpAmountField.getText().trim();
        if (text.isEmpty()) {
            showMessage("Enter a top-up amount.");
            return;
        }
        try {
            double amount = Double.parseDouble(text);
            if (amount < minimum) {
                showMessage("Minimum " + source + " top-up is €" + String.format("%.2f", minimum));
                return;
            }
            if (card.topUp(amount, source)) {
                showMessage("Topped up €" + String.format("%.2f", amount) + " via " + source);
                topUpAmountField.clear();
            } else if (card.isExpired()) {
                showMessage("Card is expired!");
            } else if (card.isBlocked()) {
                showMessage("Card is blocked!");
            } else if (!card.isPersonal() && card.getBalance() + amount > 150) {
                showMessage("Anonymous card cannot exceed €150 balance.");
            } else {
                showMessage("Invalid amount (must be €" + String.format("%.2f", minimum) + " - €150).");
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid number.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onAutoTopUpApply() {
        if (autoTopUpCheckBox.isSelected()) {
            try {
                double threshold = Double.parseDouble(autoTopUpThresholdField.getText().trim());
                double amount = Double.parseDouble(autoTopUpAmountField.getText().trim());
                card.enableAutoTopUp(threshold, amount);
                showMessage("Auto top-up enabled.");
            } catch (NumberFormatException e) {
                showMessage("Invalid auto top-up values.");
            }
        } else {
            card.disableAutoTopUp();
            showMessage("Auto top-up disabled.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onBuyDayPass() {
        if (card.buyDayPass()) {
            showMessage("Day pass purchased! Free travel for today.");
        } else if (card.getBalance() < 22.50) {
            showMessage("Not enough balance for day pass (€22.50).");
        } else {
            showMessage("Could not buy day pass.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onMissedCheckout() {
        if (!card.isCheckedIn()) {
            showMessage("Not checked in, no penalty to apply.");
            return;
        }
        double penalty = card.applyMissedCheckoutPenalty();
        showMessage("Penalty of €" + String.format("%.2f", penalty) + " applied.");
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onRefund() {
        if (card.refundLastTrip()) {
            showMessage("Last trip refunded.");
        } else {
            showMessage("No trips to refund.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onEstimateFare() {
        int fromIndex = fromStationComboBox.getSelectionModel().getSelectedIndex();
        int toIndex = toStationComboBox.getSelectionModel().getSelectedIndex();
        if (fromIndex < 0 || toIndex < 0) {
            fareEstimateLabel.setText("Select both stations.");
            return;
        }
        if (fromIndex == toIndex) {
            fareEstimateLabel.setText("Same station selected.");
            return;
        }
        Station from = stations.get(fromIndex);
        Station to = stations.get(toIndex);
        TransportType transport = TransportType.valueOf(transportComboBox.getValue());
        double fare = card.estimateFare(from, to, transport);
        double distance = Math.abs(from.getKilometerMarker() - to.getKilometerMarker());
        fareEstimateLabel.setText("Estimated fare: €" + String.format("%.2f", fare)
                + " (" + String.format("%.1f", distance) + " km)");
    }

    @FXML
    private void onRentBike() {
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
        if (stationIndex < 0) {
            showMessage("Select a station first.");
            return;
        }
        String stationName = stations.get(stationIndex).getName();
        if (card.rentBike(stationName)) {
            showMessage("OV-fiets rented at " + stationName);
        } else if (card.isBikeRented()) {
            showMessage("Already renting a bike. Return it first.");
        } else if (card.getBalance() < 3.85) {
            showMessage("Not enough balance for OV-fiets (€3.85).");
        } else {
            showMessage("Could not rent bike.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onReturnBike() {
        if (card.returnBike()) {
            showMessage("OV-fiets returned.");
        } else {
            showMessage("No bike to return.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onTransfer() {
        String selected = transferCardComboBox.getValue();
        if (selected == null || selected.isEmpty()) {
            showMessage("No card to transfer to.");
            return;
        }
        String text = transferAmountField.getText().trim();
        if (text.isEmpty()) {
            showMessage("Enter a transfer amount.");
            return;
        }
        try {
            double amount = Double.parseDouble(text);
            int toIndex = Integer.parseInt(selected.split(":")[0]);
            if (account.transferBetweenCards(selectedCardIndex, toIndex, amount)) {
                showMessage("Transferred €" + String.format("%.2f", amount));
                transferAmountField.clear();
            } else {
                showMessage("Transfer failed. Check balance and card status.");
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid amount.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onRedeemPoints() {
        String text = redeemPointsField.getText().trim();
        if (text.isEmpty()) {
            showMessage("Enter points to redeem.");
            return;
        }
        try {
            int points = Integer.parseInt(text);
            if (card.redeemLoyaltyPoints(points)) {
                double credit = points / 100.0;
                showMessage("Redeemed " + points + " points for €" + String.format("%.2f", credit));
                redeemPointsField.clear();
            } else if (points > card.getLoyaltyPoints()) {
                showMessage("Not enough points. You have " + card.getLoyaltyPoints() + ".");
            } else {
                showMessage("Could not redeem points.");
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid number.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onExportCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Transactions");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("transactions.csv");
        File file = fileChooser.showSaveDialog(tabPane.getScene().getWindow());
        if (file != null) {
            card.exportToCSV(file.getAbsolutePath());
            showMessage("Exported to " + file.getName());
        }
    }

    @FXML
    private void onBlockCard() {
        card.block();
        showMessage("Card blocked.");
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onUnblockCard() {
        if (card.unblock()) {
            showMessage("Card unblocked.");
        } else {
            showMessage("Cannot unblock. Balance must be €0 or above.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onBalanceProtection() {
        card.setBalanceProtection(balanceProtectionCheckBox.isSelected());
        if (balanceProtectionCheckBox.isSelected()) {
            showMessage("Balance protection enabled. Missed checkout penalty reduced to €5.");
        } else {
            showMessage("Balance protection disabled.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onGroupDayPass() {
        int count = account.buyGroupDayPass();
        if (count > 0) {
            showMessage("Group day pass: " + count + " card(s) activated.");
        } else {
            showMessage("Could not buy group day pass. Check balances.");
        }
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onChangeName() {
        String newName = editNameField.getText().trim();
        if (newName.isEmpty()) {
            showMessage("Enter a new name.");
            return;
        }
        account.setName(newName);
        welcomeLabel.setText("Welcome, " + newName);
        saveAccount();
        showMessage("Name changed to " + newName);
    }

    @FXML
    private void onChangePassword() {
        String oldPw = oldPasswordField.getText();
        String newPw = newPasswordField.getText();
        if (oldPw.isEmpty() || newPw.isEmpty()) {
            showMessage("Fill in both password fields.");
            return;
        }
        if (account.changePassword(oldPw, newPw)) {
            oldPasswordField.clear();
            newPasswordField.clear();
            saveAccount();
            showMessage("Password changed.");
        } else {
            showMessage("Wrong old password.");
        }
    }

    @FXML
    private void onUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Profile Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(tabPane.getScene().getWindow());
        if (file != null) {
            try {
                String ext = getExtension(file.getName());
                String destPath = ACCOUNTS_FOLDER + "/" + account.getEmail() + "_pfp" + ext;
                Files.copy(file.toPath(), new File(destPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                account.setProfilePicture(destPath);
                loadProfilePicture();
                saveAccount();
                showMessage("Profile photo updated.");
            } catch (Exception e) {
                showMessage("Could not upload photo: " + e.getMessage());
            }
        }
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot >= 0) {
            return filename.substring(dot);
        }
        return ".png";
    }

    @FXML
    private void onToggleDarkMode() {
        OVChipkaartApp.setDarkMode(!OVChipkaartApp.isDarkMode());
        Scene scene = tabPane.getScene();
        scene.getStylesheets().clear();
        String css = OVChipkaartApp.isDarkMode() ? "style-dark.css" : "style.css";
        scene.getStylesheets().add(OVChipkaartApp.class.getResource(css).toExternalForm());
        darkModeButton.setText(OVChipkaartApp.isDarkMode() ? "Light" : "Dark");
    }

    @FXML
    private void onToggleLanguage() {
        OVChipkaartApp.setDutch(!OVChipkaartApp.isDutch());
        refreshLanguage();
        refreshUI();
    }

    private void refreshLanguage() {
        boolean nl = OVChipkaartApp.isDutch();
        langButton.setText(nl ? "EN" : "NL");

        travelTab.setText(nl ? "Reizen" : "Travel");
        topUpTab.setText(nl ? "Opwaarderen" : "Top Up");
        transactionsTab.setText(nl ? "Transacties" : "Transactions");
        accountTab.setText(nl ? "Account" : "Account");
        logoutButton.setText(nl ? "Uitloggen" : "Logout");

        stationLabel.setText(nl ? "Station:" : "Station:");
        transportLabel.setText(nl ? "Vervoer:" : "Transport:");
        classLabel.setText(nl ? "Klasse:" : "Class:");
        subscriptionLabel.setText(nl ? "Abonnement:" : "Subscription:");
        dayPassButton.setText(nl ? "Koop Dagkaart (€22,50)" : "Buy Day Pass (€22.50)");
        missedCheckoutButton.setText(nl ? "Gemiste Uitcheck" : "Missed Checkout Penalty");
        fareEstimatorTitle.setText(nl ? "Ritprijs Schatten" : "Fare Estimator");
        fromLabel.setText(nl ? "Van:" : "From:");
        toLabel.setText(nl ? "Naar:" : "To:");
        estimateButton.setText(nl ? "Bereken" : "Estimate");
        ovFietsTitle.setText(nl ? "OV-fiets" : "OV-fiets");
        rentBikeButton.setText(nl ? "Huur OV-fiets (€3,85)" : "Rent OV-fiets (€3.85)");
        returnBikeButton.setText(nl ? "Retourneer OV-fiets" : "Return OV-fiets");

        manualTopUpTitle.setText(nl ? "Handmatig Opwaarderen" : "Manual Top-Up");
        machineButton.setText(nl ? "Automaat (min €5)" : "Machine (min €5)");
        onlineButton.setText(nl ? "Online (min €10)" : "Online (min €10)");
        autoTopUpTitle.setText(nl ? "Automatisch Opwaarderen" : "Auto Top-Up");
        autoTopUpCheckBox.setText(nl ? "Automatisch opwaarderen aan" : "Enable auto top-up");
        applyButton.setText(nl ? "Toepassen" : "Apply");
        transferTitle.setText(nl ? "Overschrijven Tussen Kaarten" : "Transfer Between Cards");
        transferButton.setText(nl ? "Overschrijven" : "Transfer");
        loyaltyTitle.setText(nl ? "Spaarpunten" : "Loyalty Points");
        redeemButton.setText(nl ? "Inwisselen (100 ptn = €1)" : "Redeem (100 pts = €1)");

        refundButton.setText(nl ? "Laatste Reis Terugbetalen" : "Refund Last Trip");
        exportButton.setText(nl ? "Exporteer CSV" : "Export CSV");

        profileTitle.setText(nl ? "Profiel" : "Profile");
        changeNameButton.setText(nl ? "Naam Wijzigen" : "Change Name");
        changePasswordButton.setText(nl ? "Wachtwoord Wijzigen" : "Change Password");
        uploadPhotoButton.setText(nl ? "Foto Uploaden" : "Upload Photo");
        myCardsTitle.setText(nl ? "Mijn Kaarten" : "My Cards");
        addPersonalButton.setText(nl ? "Persoonlijke Kaart" : "Add Personal Card");
        addAnonButton.setText(nl ? "Anonieme Kaart" : "Add Anonymous Card");
        removeCardButton.setText(nl ? "Verwijder Kaart" : "Remove Selected Card");
        blockButton.setText(nl ? "Blokkeer Kaart" : "Block Card");
        unblockButton.setText(nl ? "Deblokkeer Kaart" : "Unblock Card");
        groupDayPassButton.setText(nl ? "Groeps Dagkaart" : "Group Day Pass");
        balanceProtectionCheckBox.setText(nl ? "Saldobescherming (verlaagde boete)" : "Balance Protection (reduced penalty)");
        statsTitle.setText(nl ? "Statistieken" : "Statistics");
        cardInfoTitle.setText(nl ? "Kaart Info" : "Card Info");

        if (account != null) {
            welcomeLabel.setText((nl ? "Welkom, " : "Welcome, ") + account.getName());
        }
    }

    @FXML
    private void onAddPersonalCard() {
        OVCard newCard = account.addCard(true);
        newCard.topUp(0.01);
        showMessage("New personal card added: " + newCard.getCardNumber());
        updateCardSelector();
        updateTransferCardSelector();
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onAddAnonCard() {
        OVCard newCard = account.addCard(false);
        newCard.topUp(0.01);
        showMessage("New anonymous card added: " + newCard.getCardNumber());
        updateCardSelector();
        updateTransferCardSelector();
        saveAccount();
        refreshUI();
    }

    @FXML
    private void onRemoveCard() {
        if (account.getCards().size() <= 1) {
            showMessage("Can't remove your only card.");
            return;
        }
        if (account.removeCard(selectedCardIndex)) {
            selectedCardIndex = 0;
            card = account.getCard(0);
            showMessage("Card removed.");
            updateCardSelector();
            updateTransferCardSelector();
            saveAccount();
            refreshUI();
        }
    }

    @FXML
    private void onLogout() {
        saveAccount();
        try {
            OVChipkaartApp.showLoginScreen();
        } catch (Exception e) {
            showMessage("Could not logout: " + e.getMessage());
        }
    }

    private void saveAccount() {
        account.saveToFile(ACCOUNTS_FOLDER);
    }

    private void refreshUI() {
        boolean nl = OVChipkaartApp.isDutch();

        balanceLabel.setText("€" + String.format("%.2f", card.getBalance()));
        cardTypeLabel.setText(card.isPersonal() ? (nl ? "Persoonlijk" : "Personal") : (nl ? "Anoniem" : "Anonymous"));

        String warning = card.getBalanceWarning();
        warningLabel.setText(warning);
        warningLabel.setVisible(!warning.isEmpty());

        if (card.isCheckedIn()) {
            statusLabel.setText((nl ? "Ingecheckt bij " : "Checked in at ") + card.getCheckedInAt().getName()
                    + " (" + card.getCheckedInTransport() + ")");
            tapButton.setText(nl ? "UITCHECKEN" : "CHECK OUT");
        } else {
            statusLabel.setText(nl ? "Niet ingecheckt" : "Not checked in");
            tapButton.setText(nl ? "INCHECKEN" : "CHECK IN");
        }

        if (card.isDayPassValid()) {
            dayPassLabel.setText(nl ? "Dagkaart ACTIEF" : "Day pass ACTIVE");
        } else {
            dayPassLabel.setText("");
        }

        if (card.isBikeRented()) {
            bikeStatusLabel.setText((nl ? "Fiets gehuurd bij " : "Bike rented at ") + card.getBikeRentStationName());
        } else {
            bikeStatusLabel.setText("");
        }

        LocalDate now = LocalDate.now();
        monthSummaryLabel.setText(card.getMonthSummary(now.getYear(), now.getMonthValue()));

        String filter = filterComboBox.getValue();
        List<Transaction> filtered = card.getFilteredTransactions(filter);
        List<String> txStrings = filtered.stream()
                .map(Transaction::toString)
                .toList();
        transactionList.setItems(FXCollections.observableArrayList(txStrings));

        loyaltyLabel.setText((nl ? "Spaarpunten: " : "Loyalty points: ") + card.getLoyaltyPoints()
                + " (= €" + String.format("%.2f", card.getLoyaltyPoints() / 100.0) + ")");

        statsLabel.setText(
                (nl ? "Totaal reizen: " : "Total trips: ") + card.getTotalTrips()
                + (nl ? "\nTotale afstand: " : "\nTotal distance: ") + String.format("%.1f", card.getTotalDistanceKm()) + " km"
                + (nl ? "\nTotaal uitgegeven: €" : "\nTotal spent: €") + String.format("%.2f", card.getTotalSpent())
                + (nl ? "\nSpaarpunten: " : "\nLoyalty points: ") + card.getLoyaltyPoints()
        );

        cardInfoLabel.setText(
                (nl ? "Kaartnummer: " : "Card number: ") + card.getCardNumber()
                + (nl ? "\nType: " : "\nType: ") + (card.isPersonal() ? (nl ? "Persoonlijk" : "Personal") : (nl ? "Anoniem" : "Anonymous"))
                + (nl ? "\nVervalt: " : "\nExpiry: ") + card.getExpiryDate()
                + (nl ? "\nReisklasse: " : "\nTravel class: ") + card.getTravelClass()
                + (nl ? "\nAbonnement: " : "\nSubscription: ") + card.getSubscription()
                + (nl ? "\nGeblokkeerd: " : "\nBlocked: ") + (card.isBlocked() ? (nl ? "JA" : "YES") : (nl ? "Nee" : "No"))
                + (nl ? "\nAutomatisch opwaarderen: " : "\nAuto top-up: ") + (card.isAutoTopUpEnabled() ? (nl ? "Aan" : "Enabled") : (nl ? "Uit" : "Disabled"))
                + (nl ? "\nSaldobescherming: " : "\nBalance protection: ") + (card.isBalanceProtection() ? (nl ? "Aan" : "Enabled") : (nl ? "Uit" : "Disabled"))
                + (nl ? "\nFiets gehuurd: " : "\nBike rented: ") + (card.isBikeRented() ? (nl ? "Ja (" : "Yes (") + card.getBikeRentStationName() + ")" : (nl ? "Nee" : "No"))
        );
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
    }
}
