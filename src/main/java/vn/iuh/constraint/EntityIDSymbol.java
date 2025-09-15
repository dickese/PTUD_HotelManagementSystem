package vn.iuh.constraint;

public enum EntityIDSymbol {
    ROOM_PREFIX("RO", 8),
    ACCOUNT_PREFIX("AC", 3),
    AdditionalFee_PREFIX("AF", 2 );
//................... Other here


    public String prefix;
    public int numberLength;

    EntityIDSymbol(String prefix, int numberLength) {
        this.prefix = prefix;
        this.numberLength = numberLength;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLength() {
        return numberLength;
    }
}
