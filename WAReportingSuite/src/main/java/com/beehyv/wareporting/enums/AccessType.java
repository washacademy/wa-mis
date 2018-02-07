package com.beehyv.wareporting.enums;

/**
 * Created by beehyv on 5/5/17.
 */
public enum AccessType {

    MASTER_ADMIN("MASTER ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private String accessType;

    public static boolean isType(String test){
        for (AccessType type: AccessType.values()) {
            if(type.name().equalsIgnoreCase(test)){
                return true;
            }
        }
        return false;
    }

    public static String getType(String test){
        for (AccessType type: AccessType.values()) {
            if(type.name().equalsIgnoreCase(test)){
                return type.name();
            }
        }
        return "not valid";
    }


    AccessType(String accessType){this.accessType = accessType;}

    public String getAccessType(){return accessType;}



}
