package com.dante.curso.resouces.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    public ValidationError(Integer error, String message, Long timeStamp) {
        super(error, message, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldMessage> errors) {
        this.errors = errors;
    }

    public void addError(final String fieldName, final String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }

}
