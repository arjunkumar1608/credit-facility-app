package com.credit.facility.loan.util.enums;


public enum CustomerErrorMessage implements BaseErrorMessage {
    CUSTOMER_NOT_FOUND("Customer Not Found!","Please check the id of the customer."),
    FIELD_CANNOT_BE_NULL("Field Cannot Be Null!","Please be sure that all fields are entered."),
    IDENTITY_NO_MUST_BE_UNIQUE("Identity No Must Be Unique!","Please check the identity no of the customer.");

    private  final String message;
    private  final String detailMessage;

    CustomerErrorMessage(String message, String detailMessage){
        this.message = message;
        this.detailMessage = detailMessage;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }

}
