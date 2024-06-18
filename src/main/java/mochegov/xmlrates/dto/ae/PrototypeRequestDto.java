package mochegov.xmlrates.dto.ae;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PrototypeRequestDto extends GlobeAeRequestDto {
    private PrototypeBusProc businessProcess;
}
