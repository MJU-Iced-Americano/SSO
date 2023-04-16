package org.mju.domain;

import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public class Birth {
    private LocalDate localDate;

    protected Birth() {
        localDate = LocalDate.now();
    }

    public Birth(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return localDate.toString();
    }
}
