package com.spectra.asr.enums;

public enum MigrationEnum {
    CLINIC_MIGRATE (1);

    private final int resulttype;

    MigrationEnum(int resulttype) {
        this.resulttype = resulttype;
    }

    public int getResulttype() {
        return resulttype;
    }
}
