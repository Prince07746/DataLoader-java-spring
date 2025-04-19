package org.dataloader.security;

import lombok.Getter;

@Getter
public class IdPattern {

    private final String id;
    private static final String ID_PATTERN  = "^[a-fA-F0-9]{24}$";

    public IdPattern(String id){
        if (isValid(id)) {
            this.id = id;
        }else {
            throw new IllegalArgumentException("Invalid ID format");
        }
    }

    public static boolean isValid(String id){
        return id != null && id.matches(ID_PATTERN);
    }

}
