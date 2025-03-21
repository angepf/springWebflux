package com.challenge.api.account.management.domain;

import com.challenge.api.account.management.service.models.MovementTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "S_CHALLENGE.T_MOVEMENTS")
public class Movement {

    @Id
    private Long id;

    private Long accountNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotBlank(message = "El tipo no puede estar vacío")
    private MovementTypeEnum type;

    @Min(value = 0, message = "El valor no puede ser negativo")
    private BigDecimal value;

    private BigDecimal balance;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 20, message = "El estado no puede tener más de 20 caracteres")
    private Boolean status;
}