package com.spectra.asr.enums;

public enum ProcessRunEnum {
    LOAD(1),
    READ(2),
    LEGACY_DATA(3),
    CLINIC_MIGRATE(4),
    TRANSFER (5),
    LOAD_REFS (6),
    CLINIC_PRIMARY (7),
    PARSE_CSV (8),
    KORUS_CSV (9),
    STS_SYNC (41);


    private final int value;

    ProcessRunEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
