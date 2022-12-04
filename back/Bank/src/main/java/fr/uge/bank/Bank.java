package fr.uge.bank;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Bank {


    static class Card {
        private final String cardNumber;
        private final Date expirationDate;
        private final String cvv;

        public String getCardNumber() {
            return cardNumber;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public String getCvv() {
            return cvv;
        }

        public Card(String cardNumber, Date expirationDate, String cvv) {
            Objects.requireNonNull(cardNumber);
            Objects.requireNonNull(expirationDate);
            Objects.requireNonNull(cvv);

            if (cardNumber.length() != 16) throw new IllegalArgumentException("Wrong card number");
            if (expirationDate.before(Date.from(Instant.now()))) throw new IllegalArgumentException("Expiration date must be further");

            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.cvv = cvv;

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Card card = (Card) o;
            return cardNumber.equals(card.cardNumber) && expirationDate.equals(card.expirationDate) && cvv.equals(card.cvv);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cardNumber, expirationDate, cvv);
        }
    }

    HashMap<Card, Float> accounts = new HashMap<>();

    public Bank() throws ParseException {
        accounts.put(new Card("0000111122223333", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("1111222233334444", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("2222333344445555", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("3333444455556666", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("4444555566667777", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("5555666677778888", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("6666777788889999", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("7777888899990000", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
        accounts.put(new Card("8888999900001111", (new SimpleDateFormat("MM/yy")).parse("12/24"), "123" ), 390.89f);
    }

    public boolean pay(String cardNumber, String expirationDate, String cvv, float amount) {
        Objects.requireNonNull(cardNumber); 
        Objects.requireNonNull(expirationDate);
        Objects.requireNonNull(cvv);
        
                
        Card card;
        try {
        	card = new Card(cardNumber, new SimpleDateFormat("MM/yy").parse(expirationDate), cvv);
        } catch(Exception e) {
        	throw new IllegalArgumentException("Wrong date format");
        }
        boolean paid = false;

        for (var e: accounts.entrySet()) {
            if (e.getKey().equals(card)) {
                if (e.getValue()>amount) {
                    paid = true;
                    accounts.put(card, e.getValue() - amount);
                }
                return paid;
            }
        }
        return false;
    }

}