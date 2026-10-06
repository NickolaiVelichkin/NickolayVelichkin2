public interface WithdrawalOperations {

    Double withdraw(Double currentBalance, Double amount, BankType bankType);

    default Double applyCommission(Double amount, BankType bankType) {if (amount == null || bankType == null) {
        return 0.00;
    }

        double rawCommission = amount * bankType.getCommissionRate();
        return Math.round(rawCommission * 100.0) / 100.0;
    }
}