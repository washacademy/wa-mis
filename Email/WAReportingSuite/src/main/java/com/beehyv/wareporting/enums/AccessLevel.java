package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 5/5/17.
 */
public enum AccessLevel {
    NATIONAL("NATIONAL"),
    STATE("STATE"),
    DISTRICT("DISTRICT"),
    BLOCK("BLOCK");

    private String accessLevel;


    AccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public static boolean isLevel(String test) {
        for (AccessLevel e : AccessLevel.class.getEnumConstants()) {
            if (e.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public static AccessLevel getLevel(String test) {
        for (AccessLevel level : AccessLevel.values()) {
            if (level.name().equalsIgnoreCase(test)) {
                return level;
            }
            ;
        }
        return AccessLevel.BLOCK;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
}
