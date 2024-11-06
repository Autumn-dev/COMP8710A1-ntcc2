public enum Coin {
    TWO_POUND(200), ONE_POUND(100), FIFTY_PENCE(50),
    TWENTY_PENCE(20), TEN_PENCE(10), FIVE_PENCE(5),
    PENNY(1);

    private int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}