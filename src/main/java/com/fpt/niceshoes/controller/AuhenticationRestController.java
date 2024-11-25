package com.fpt.niceshoes.controller;

import com.fpt.niceshoes.dto.request.logindto.ChangePassword;
import com.fpt.niceshoes.dto.request.logindto.ResetPassword;
import com.fpt.niceshoes.infrastructure.common.ResponseObject;
import com.fpt.niceshoes.infrastructure.exception.CustomListValidationException;
import com.fpt.niceshoes.infrastructure.sercurity.auth.JwtAuhenticationResponse;
import com.fpt.niceshoes.infrastructure.sercurity.auth.SignUpRequets;
import com.fpt.niceshoes.infrastructure.sercurity.auth.SigninRequest;
import com.fpt.niceshoes.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-v2")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuhenticationRestController {
//    ahihi đồ ngốc

    private final AuthenticationService authenticationService;

    @PostMapping("/singup")
    public String singup(@Valid @RequestBody SignUpRequets requets, BindingResult bindingResult) throws CustomListValidationException {
        if (bindingResult.hasErrors()) {
            throw new CustomListValidationException(404, bindingResult.getAllErrors());
        }
        return authenticationService.signUp(requets);
    }

        @PostMapping("/singin")
    public ResponseEntity<JwtAuhenticationResponse> singin(@RequestBody SigninRequest requets) {
        return ResponseEntity.ok(authenticationService.singIn(requets));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword (@RequestBody ResetPassword resetPassword){
        return ResponseEntity.ok(authenticationService.resetPassword(resetPassword));
    }

    @PostMapping("/change-password")
    public ResponseObject changePassword (@RequestBody ChangePassword changePassword){
        return new ResponseObject(authenticationService.changePassword(changePassword));
    }


}
