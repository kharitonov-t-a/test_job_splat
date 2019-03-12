package banner.model.enums;

public enum Crud {

    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4),
    SORT(5);

    private int action;

    Crud(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

}
