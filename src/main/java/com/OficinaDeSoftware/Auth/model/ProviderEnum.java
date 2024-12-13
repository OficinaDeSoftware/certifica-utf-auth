package com.OficinaDeSoftware.Auth.model;

public enum ProviderEnum {
    GOOGLE,
    UTFPR;

    public static ProviderEnum parse( String name ) {
        return valueOf( name.toUpperCase() );
    }
}