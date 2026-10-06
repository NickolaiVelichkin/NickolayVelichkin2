public enum BankType {
    NEO("НеоКредит Банк", 0.01),
    AUM("Арум Финтех", 0.02),
    VTA("Вектор Альянс Банк", 0.00);

    public String title;
    public double commissionRate;

    BankType(String title, double commissionRate) {
        this.title = title;
        this.commissionRate = commissionRate;
    }

    public String getTitle() {
        return this.title;
    }

    public double getCommissionRate() {
        return this.commissionRate;
    }
}