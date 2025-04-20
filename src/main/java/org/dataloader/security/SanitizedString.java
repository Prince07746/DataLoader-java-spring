package org.dataloader.security;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SanitizedString {

    String id;
    public SanitizedString(String id, String ID_PATTERN){
        if (!isValid(id,ID_PATTERN) || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid ID format");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static boolean isValid(String id, String ID_PATTERN){
        Pattern pattern1 = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern1.matcher(id);
        return matcher.matches();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanitizedString)) return false;
        SanitizedString that = (SanitizedString) o;
        return Objects.equals(this.id, that.id); // Compare values
    }


}
