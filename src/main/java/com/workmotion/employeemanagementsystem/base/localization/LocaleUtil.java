package com.workmotion.employeemanagementsystem.base.localization;

import com.workmotion.employeemanagementsystem.base.model.LanguageEnum;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Alber Rashad
 * @created 01/11/2022
 * @description
 */
public class LocaleUtil {
    public static LanguageEnum getLang() {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return LanguageEnum.valueOf(locale.getLanguage().toUpperCase());
        }catch (Exception ex){
            //no language found with this locale
        }
        //default lang to return
        return LanguageEnum.EN;

    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static LanguageEnum getLang(Locale locale) {
        return LanguageEnum.valueOf(locale.getLanguage().toUpperCase());
    }

    public static Locale getLocale(LanguageEnum lang) {
        return new Locale(lang.getValue().toLowerCase());
    }
}
