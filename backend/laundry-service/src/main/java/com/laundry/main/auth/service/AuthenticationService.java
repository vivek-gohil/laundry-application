package com.laundry.main.auth.service;

import com.laundry.main.auth.dto.LoginRequest;
import com.laundry.main.auth.dto.LoginResponse;

public interface AuthenticationService {

  LoginResponse login(LoginRequest request);
}
