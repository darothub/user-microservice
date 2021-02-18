package com.darothub.usermicroservice.utils;



public class ConstantUtils {
    public static final String GENDER_PATTERN = "[Mm]ale|[Ff]emale";
    public static final String CATEGORY_PATTERN = "[Tt]ailor|[Ff]ashionista";
    public static final String CHAR_PATTERN = "[a-zA-z\\s]+";
    public static final String NUMBER_PATTERN = "\\d{13}";
    public static final String PASSWORD = "^ (?=.*\\\\d) (?=\\\\S+$) (?=.* [@#$%^&+=]) (?=.* [a-z]) (?=.* [A-Z]). {8,10}$";
    public static final String IMAGE_PATTERN = "image\\/(png|jpg|jpeg)";
}
