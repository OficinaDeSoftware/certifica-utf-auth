package com.OficinaDeSoftware.Auth.dto.producer;

public record EmailPayloadDto(
        String nrUuidReceiver,
        String subject,
        String receiver,
        EmailTemplateDto template
) {}
