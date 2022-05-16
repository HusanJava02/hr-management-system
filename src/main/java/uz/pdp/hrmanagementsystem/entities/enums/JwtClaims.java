package uz.pdp.hrmanagementsystem.entities.enums;

public enum JwtClaims {
    USER_ID, EMAIL;


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
