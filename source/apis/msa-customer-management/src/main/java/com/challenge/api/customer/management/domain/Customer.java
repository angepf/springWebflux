package com.challenge.api.customer.management.domain;

import com.challenge.api.customer.management.service.models.StatusEnum;
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
@Table(name = "S_CHALLENGE.T_CUSTOMERS")
public class Customer {

    @Id
    private String identification;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 100, message = "La contraseña no puede tener más de 100 caracteres")
    private String password;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede tener más de 20 caracteres")
    private StatusEnum status;

}