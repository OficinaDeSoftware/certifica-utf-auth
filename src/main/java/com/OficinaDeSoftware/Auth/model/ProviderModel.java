package com.OficinaDeSoftware.Auth.model;

import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderModel {

    @NotNull
    private final String nrUuid;

    @NotNull
    private final String email;

    private final String name;
}
