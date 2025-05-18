package com.demoApp.otp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    // Manual builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String token;
        public Builder token(String token) { this.token = token; return this; }
        public AuthenticationResponse build() {
            AuthenticationResponse response = new AuthenticationResponse();
            response.token = this.token;
            return response;
        }
    }
} 