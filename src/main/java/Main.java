import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Account referenceAccount = new Account(12345, 999, 10000.00, BankType.AUM);

        System.out.println("Приветствие");

        System.out.print("Введите номер карты: ");
        int inputCardNumber;
        if (scanner.hasNextInt()) {
            inputCardNumber = scanner.nextInt();
        } else {
            System.out.println("Некорректный формат номера карты.");
            scanner.close();
            return;
        }

        System.out.print("Введите ПИН-код: ");
        int inputPinCode;
        if (scanner.hasNextInt()) {
            inputPinCode = scanner.nextInt();
        } else {
            System.out.println("Некорректный формат PIN-кода.");
            scanner.close();
            return;
        }

        if (inputCardNumber != referenceAccount.getCardNumber() || inputPinCode != referenceAccount.getPinCode()) {
            System.out.println("Неверный номер карты или PIN-код.");
            scanner.close();
            return;
        }

        System.out.println("Авторизация успешна!");
        System.out.println("Данные счета: " + referenceAccount.toString());

        CashMachine cashMachine = new CashMachine();

        System.out.print("\nВведите сумму для внесения (например, 500.00): ");
        Double depositAmount = readSafeDouble(scanner);
        if (depositAmount == null || depositAmount <= 0.0) {
            System.out.println("Введена некорректная сумма для внесения.");
        }

        Double newBalanceAfterDeposit = cashMachine.deposit(referenceAccount.getBalance(), depositAmount);
        referenceAccount.balance = newBalanceAfterDeposit;
        System.out.printf(Locale.US, "Баланс после внесения: %.2f руб.%n", referenceAccount.getBalance());

        System.out.print("\nВведите сумму для снятия наличных (например, 3000.00): ");
        Double withdrawAmount = readSafeDouble(scanner);
        if (withdrawAmount == null || withdrawAmount <= 0.0) {
            System.out.println("Введена некорректная сумма для снятия.");
        }

        Double newBalanceAfterWithdraw = cashMachine.withdraw(
                referenceAccount.getBalance(),
                withdrawAmount,
                referenceAccount.getBankType()
        );
        referenceAccount.balance = newBalanceAfterWithdraw;
        System.out.printf(Locale.US, "Баланс после снятия: %.2f руб.%n", referenceAccount.getBalance());

        System.out.println("\nИтоговое состояние аккаунта:");
        System.out.println(referenceAccount.toString());

        scanner.close();
    }

    private static Double readSafeDouble(Scanner scanner) {
        if (!scanner.hasNext()) {
            return null;
        }
        String token = scanner.next().trim().replace(',', '.');
        return Double.valueOf(token);
    }
}