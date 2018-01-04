package com.beehyv.wareporting.entity;

/**
 * Created by beehyv on 30/11/17.
 */
public class BasicValidationResult {
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
}
