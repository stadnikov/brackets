package com.stadnikov.brackets.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BooleanResponse {

    private Boolean isCorrect;

    public BooleanResponse(Boolean bool) {
        this.isCorrect = bool;
    }

    @JsonProperty(value="isCorrect")
    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
