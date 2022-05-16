package uz.pdp.hrmanagementsystem.entities.enums;

public enum NotificationType {
    ACTIVATE_ACCOUNT("Account verification"),NEW_USER("New User Request");

    private final String info;

    NotificationType(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String toStringLower() {
        return this.toString().toLowerCase();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
