package com.challenge.api.customer.management.domain;

import com.challenge.api.customer.management.service.models.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "S_CHALLENGE.T_PERSONS")
public class Person {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotBlank(message = "El género no puede estar vacío")
    @Size(max = 10, message = "El género no puede tener más de 10 caracteres")
    private GenderEnum gender;

    @Id
    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(max = 20, message = "La identificación no puede tener más de 20 caracteres")
    private String identification;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String address;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    private String phone;

}