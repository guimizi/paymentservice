package com.guimizi.challenge.paymentservice.model.entity.charge;

public enum ChargeEventType {
    CLASIFICADO(ChargeCategoryType.MARKETPLACE)
    , VENTA(ChargeCategoryType.MARKETPLACE)
    , PUBLICIDAD(ChargeCategoryType.SERVICIOS)
    , ENVÍO(ChargeCategoryType.MARKETPLACE)
    , CREDITO(ChargeCategoryType.SERVICIOS)
    , MERCADOPAGO(ChargeCategoryType.EXTERNOS)
    , MERCADOSHOP(ChargeCategoryType.EXTERNOS)
    , FIDELIDAD(ChargeCategoryType.SERVICIOS);

    private ChargeCategoryType category;

    ChargeEventType(ChargeCategoryType categoryType ) {
        this.category = categoryType;
    }

    public ChargeCategoryType getCategory() {
        return category;
    }
}
