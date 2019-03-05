package banner.model.enums;

public enum Local {

    RU(1),
    US(2),
    DE(3);

    private int language;

    Local(int language) {
        this.language = language;
    }

    public int getLanguage() {
        return language;
    }
}
