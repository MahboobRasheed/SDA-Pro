package com.sda.shared.contracts;

public enum Severity {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int level;

    Severity(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static Severity fromLevel(int level) {
        for (Severity s : values()) {
            if (s.level == level) return s;
        }
        return LOW;
    }
}