package nl.ovchipkaart.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private String name;
    private String email;
    private String password;
    private List<OVCard> cards;
    private String profilePicturePath;

    public Account(String name, String email, String password, boolean personalCard) {
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
        this.cards = new ArrayList<>();
        OVCard firstCard = new OVCard(generateCardNumber(), personalCard);
        cards.add(firstCard);
    }

    private Account() {
        this.cards = new ArrayList<>();
    }

    private static String generateCardNumber() {
        return "3528-" + String.format("%04d", (int)(Math.random() * 10000))
                + "-" + String.format("%04d", (int)(Math.random() * 10000))
                + "-" + String.format("%04d", (int)(Math.random() * 10000));
    }

    private static String hashPassword(String raw) {
        int hash = 0;
        for (char c : raw.toCharArray()) {
            hash = 31 * hash + c;
        }
        return String.format("%08x", hash);
    }

    public OVCard addCard(boolean personal) {
        OVCard newCard = new OVCard(generateCardNumber(), personal);
        cards.add(newCard);
        return newCard;
    }

    public boolean removeCard(int index) {
        if (cards.size() <= 1) {
            return false;
        }
        if (index < 0 || index >= cards.size()) {
            return false;
        }
        cards.remove(index);
        return true;
    }

    public boolean checkPassword(String password) {
        String hashed = hashPassword(password);
        return this.password.equals(hashed) || this.password.equals(password);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!checkPassword(oldPassword)) {
            return false;
        }
        this.password = hashPassword(newPassword);
        return true;
    }

    public void setProfilePicture(String path) {
        this.profilePicturePath = path;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public boolean transferBetweenCards(int fromIndex, int toIndex, double amount) {
        if (fromIndex == toIndex) {
            return false;
        }
        if (fromIndex < 0 || fromIndex >= cards.size()) {
            return false;
        }
        if (toIndex < 0 || toIndex >= cards.size()) {
            return false;
        }
        return cards.get(fromIndex).transferTo(cards.get(toIndex), amount);
    }

    public int buyGroupDayPass() {
        int count = 0;
        for (OVCard c : cards) {
            if (c.buyDayPass()) {
                count = count + 1;
            }
        }
        return count;
    }

    public void saveToFile(String folder) {
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = folder + "/" + email + ".txt";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println(name);
            writer.println(email);
            writer.println(password);
            writer.println(cards.size());
            writer.println(profilePicturePath != null ? profilePicturePath : "null");
            writer.close();

            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).saveToFile(folder + "/" + email + "_card" + i + ".txt");
            }
        } catch (IOException e) {
            System.out.println("Could not save account: " + e.getMessage());
        }
    }

    public static Account loadFromFile(String folder, String email) {
        String filename = folder + "/" + email + ".txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            Account account = new Account();
            account.name = reader.readLine();
            account.email = reader.readLine();
            account.password = reader.readLine();
            int cardCount = Integer.parseInt(reader.readLine());

            String pfpLine = reader.readLine();
            if (pfpLine != null && !pfpLine.equals("null")) {
                account.profilePicturePath = pfpLine;
            }

            reader.close();

            for (int i = 0; i < cardCount; i++) {
                OVCard card = OVCard.loadFromFile(folder + "/" + email + "_card" + i + ".txt");
                if (card != null) {
                    account.cards.add(card);
                }
            }

            if (account.cards.isEmpty()) {
                account.cards.add(new OVCard("3528-0000-0000-0000", true));
            }

            return account;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean exists(String folder, String email) {
        File file = new File(folder + "/" + email + ".txt");
        return file.exists();
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<OVCard> getCards() { return cards; }
    public OVCard getCard(int index) { return cards.get(index); }
}
