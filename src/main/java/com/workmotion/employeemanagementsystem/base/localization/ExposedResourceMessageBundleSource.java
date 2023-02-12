package com.workmotion.employeemanagementsystem.base.localization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@Slf4j
public class ExposedResourceMessageBundleSource extends ReloadableResourceBundleMessageSource {

    @Override
    protected Properties loadProperties(Resource resource, String fileName) throws IOException {
        logger.info("Load " + fileName);
        return super.loadProperties(resource, fileName);
    }

    /**
     * Gets all messages for presented Locale.
     * @param locale user request's locale
     * @return all messages
     */
    public Properties getMessages(Locale locale){
        return getMergedProperties(locale).getProperties();
    }
}