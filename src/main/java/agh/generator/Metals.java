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

    public static double getRatio(Metals metal) {
        switch (metal) {
            case Aluminium:
                return 2;
            case Miedz:
                return 1.5;
            case Zelazo:
                return 1;
            case Cynk:
                return 2.3;
            case Olow:
                return 2.5;
            case Cyna:
                return 2.8;
            case Magnez:
                return 2;
            case Krzem:
                return 1;
            case Nikiel:
                return 1;
            default:
                return -1;

        }
    }
}
