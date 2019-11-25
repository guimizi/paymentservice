package com.guimizi.challenge.paymentservice.model.api.charge;

import java.time.LocalDate;

public class ChargeEventResponse {

    private Long eventId;
    private LocalDate processedAt;

    public ChargeEventResponse(Long eventId) {
        this.eventId = eventId;
        this.processedAt = LocalDate.now();
    }

    public LocalDate getProcessedAt() {
        return processedAt;
    }

    public Long getEventId() {
        return eventId;
    }
}
