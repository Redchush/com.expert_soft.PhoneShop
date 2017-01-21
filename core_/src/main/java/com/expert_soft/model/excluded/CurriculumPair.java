package com.expert_soft.model.excluded;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class CurriculumPair {

    @NotNull(message = "{curriculumPair.phoneId.notNull}")
    @Min(value = 1, message = "{curriculumPair.phoneId.min}")
    private Long phoneId;

    @NotNull(message = "{curriculumPair.quantity.notNull}")
    @Min(value = 1, message = "{curriculumPair.quantity.min}")
    @Max(value = 10, message = "{curriculumPair.quantity.max}")
    private Long quantity;

    public CurriculumPair() {}

    public CurriculumPair(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public final Long getPhoneId() {
        return phoneId;
    }

    public final Long getQuantity() {
        return quantity;
    }
}
