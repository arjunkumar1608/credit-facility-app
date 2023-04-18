package com.credit.facility.loan.security.enums;

public enum JwtEnums {
    BEARER("Bearer ")
    ;

    private final String constant;
    JwtEnums(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return constant;
    }
}
