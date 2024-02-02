package mochegov.xmlrates.utils;

import static mochegov.xmlrates.utils.DateTimeUtils.buildXMLGregorianCalendar;
import static mochegov.xmlrates.utils.DateTimeUtils.localDateTimeToXmlGregorianCalendar;
import static ru.raiffeisen.gl.common.utils.MoscowDateTimeUtils.getCurrentMoscowTime;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.NonNull;
import mochegov.xmlrates.dto.CurrencyRateDto;
import mochegov.xmlrates.enums.Currency;
import rates.Rate;
import rates.RateGroup;
import rates.RateList;
import rates.RateValue;
import rates.RateVolume;

public class RateUtils {
    public static RateGroup currencyRateDtoToRateGroup(@NonNull List<CurrencyRateDto> dtoList) {
        LocalDate date = dtoList.get(0).getDate();

        RateVolume rateVolume = new RateVolume();
        rateVolume.setCode("ANY");
        rateVolume.setCurrency(Currency.EUR.toString());
        rateVolume.setFrom(BigDecimal.ZERO);

        RateList rateList = new RateList();
        rateList.getRate().addAll(dtoList.stream().map(dto -> {
            RateValue rateValue = new RateValue();
            rateValue.setLast(dto.getRateValue());
            rateValue.setMultiplier(BigDecimal.ONE);

            Rate rate = new Rate();
            rate.setCode("CBR_%s_%s".formatted(dto.getCurrency(), Currency.RUB));
            rate.setCurrency(dto.getCurrency().toString());
            rate.setCounterCurrency(Currency.RUB.toString());
            rate.setVolume(rateVolume);
            rate.setValue(rateValue);

            return rate;
        }).toList());

        RateGroup rateGroup = new RateGroup();
        rateGroup.setCode("CBR");
        rateGroup.setAuthor("CBR");
        rateGroup.setEntryDateTime(localDateTimeToXmlGregorianCalendar(getCurrentMoscowTime()));
        rateGroup.setValueDateTime(buildXMLGregorianCalendar(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()));
        rateGroup.setRateList(rateList);

        return rateGroup;
    }
}
