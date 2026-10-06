import java.util.Locale;

public class Account {

    public int cardNumber;
    public int pinCode;
    public Double balance;
    public BankType bankType;

    public Account(int cardNumber, int pinCode, Double balance, BankType bankType) {
        if (cardNumber >= 10000 && cardNumber <= 99999) {
            this.cardNumber = cardNumber;
        } else {
            this.cardNumber = 10000;
        }

        if (pinCode >= 100 && pinCode <= 999) {
            this.pinCode = pinCode;
        } else {
            this.pinCode = 100;
        }

        if (balance == null || balance < 0.0) {
            this.balance = 0.00;
        } else {
            this.balance = Math.round(balance * 100.0) / 100.0;
        }

        if (bankType == null) {
            this.bankType = BankType.NEO;
        } else {
            this.bankType = bankType;
        }
    }

    public int getCardNumber() {
        return this.cardNumber;
    }

    public int getPinCode() {
        return this.pinCode;
    }

    public Double getBalance() {
        return this.balance;
    }

    public BankType getBankType() {
        return this.bankType;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s Карта: %d, Баланс: %.2f руб.", this.bankType.getTitle(), this.cardNumber, this.balance);
    }
}