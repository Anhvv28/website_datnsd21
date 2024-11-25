package com.fpt.niceshoes.service;

import com.fpt.niceshoes.dto.request.logindto.ChangePassword;
import com.fpt.niceshoes.dto.request.logindto.ResetPassword;
import com.fpt.niceshoes.infrastructure.sercurity.auth.JwtAuhenticationResponse;
import com.fpt.niceshoes.infrastructure.sercurity.auth.SignUpRequets;
import com.fpt.niceshoes.infrastructure.sercurity.auth.SigninRequest;

public interface AuthenticationService {

    String signUp(SignUpRequets signUpRequets);

    JwtAuhenticationResponse singIn(SigninRequest request);

    String resetPassword(ResetPassword resetPassword);

    String changePassword (ChangePassword changePassword);


}
