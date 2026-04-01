package com.finance.finance_dashboard.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}