package org.dataloader.security;

import lombok.Getter;

@Getter
public class IdPattern extends SanitizedString {

    private static final String ID_PATTERN  = "^[a-fA-F0-9]{24}$";
    public IdPattern(String id){
        super(id,ID_PATTERN);
    }
}
