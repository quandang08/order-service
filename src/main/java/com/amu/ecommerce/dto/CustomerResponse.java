package com.amu.ecommerce.dto;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {

}
