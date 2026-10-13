public class CashMachine implements WithdrawalOperations, DepositOperations {

    @Override
    public Double deposit(Double currentBalance, Double amount) {
        if (currentBalance == null) {
            currentBalance = 0.00;
        }

        if (amount == null || amount <= 0.0) {
            return Math.round(currentBalance * 100.0) / 100.0;
        }

        double updatedBalance = currentBalance + amount;
        return Math.round(updatedBalance * 100.0) / 100.0;
    }

    @Override
    public Double withdraw(Double currentBalance, Double amount, BankType bankType) {
        if (currentBalance == null) {
            currentBalance = 0.00;
        }

        if (amount == null || amount <= 0.0) {
            return Math.round(currentBalance * 100.0) / 100.0;
        }

        Double commission = applyCommission(amount, bankType);
        double totalDebit = amount + commission;

        if (totalDebit > currentBalance) {
            System.out.println("Недостаточно средств для совершения операции.");
            return Math.round(currentBalance * 100.0) / 100.0;
        }

        double remainingBalance = currentBalance - totalDebit;
        return Math.round(remainingBalance * 100.0) / 100.0;
    }
}