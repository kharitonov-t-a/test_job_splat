package banner.model.enums;

public enum Crud {

    CREATE(1),
    UPDATE(2),
    SWITCH_ACTIVITY(3),
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
