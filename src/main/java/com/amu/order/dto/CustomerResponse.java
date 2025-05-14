package com.amu.order.dto;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {

}
