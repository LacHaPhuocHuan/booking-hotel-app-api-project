package com.laptrinhweb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class AuthenticationRequest {
    @NonNull
    private String password;
    @NonNull
    private String email;

}
