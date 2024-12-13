package com.OficinaDeSoftware.Auth.factory;

import com.OficinaDeSoftware.Auth.model.ProviderEnum;
import com.OficinaDeSoftware.Auth.service.auth.Provider.GoogleProviderTokenService;
import com.OficinaDeSoftware.Auth.service.auth.Provider.ProviderTokenService;

// TODO class não utilizada 
public class ProviderTokenFactory {
    
    public static ProviderTokenService create( final ProviderEnum provider ){

        switch (provider) {
            case GOOGLE:
                return new GoogleProviderTokenService();
            default:
                return null;
        }
    }
}
