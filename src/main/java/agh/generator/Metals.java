package agh.generator;

public enum Metals {
    Aluminium(1),
    Krzem(2),
    Magnez(3),
    Miedz(4),
    Cynk(5),
    Cyna(6),
    Nikiel(7),
    Zelazo(8),
    Olow(9);

    private final int value;

    Metals(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}
