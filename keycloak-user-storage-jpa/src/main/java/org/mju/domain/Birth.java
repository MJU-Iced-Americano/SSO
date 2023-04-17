package org.mju.domain;

import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public class Birth {
    private LocalDate localDate;

    protected Birth() {
        localDate = LocalDate.now();
    }

    private Birth(String value) {
        this.localDate = LocalDate.parse(value);
    }

    public Birth(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return localDate.toString();
    }
}
