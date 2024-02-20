package mochegov.xmlrates.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rates.RateGroup;

@Getter
@Setter
@NoArgsConstructor
public class RateGroupDto {
    private RateGroup rateGroup;

    public RateGroupDto(RateGroup rateGroup) {
        this.rateGroup = rateGroup;
    }
}
