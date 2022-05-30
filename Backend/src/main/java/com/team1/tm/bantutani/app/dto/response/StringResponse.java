package com.team1.tm.bantutani.app.dto.response;

public class StringResponse {
    private String status;
    private String message;

    public StringResponse() {
    }

    public StringResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder {
        private String status;
        private String message;
        public Builder status(String status) {
            this.status = status;
            return this;
        }
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public StringResponse build() {
            return new StringResponse(this);
        }
    }
}
