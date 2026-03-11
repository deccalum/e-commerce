package se.lexicon.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

    @NotBlank
    @Size(max = 50)
    String name
) {}

