package ru.einster.moneytransfer;

public class GenericResponse<T> {

    private boolean isSuccess = true;
    private String errorMsg;
    private T payload;

    public GenericResponse(T payload) {
        this.payload = payload;
    }

    public GenericResponse(String errorMsg) {
        this.errorMsg = errorMsg;
        this.isSuccess = false;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
