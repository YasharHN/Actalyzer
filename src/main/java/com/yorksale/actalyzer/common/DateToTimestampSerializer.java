package com.yorksale.actalyzer.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Created by admin on 2015-08-09.
 */
public class DateToTimestampSerializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String strDate = jsonParser.readValueAs(String.class);
        if(StringUtils.isNotEmpty(strDate)){
            return new DateTime(strDate).getMillis();
        } else {
            return null;
        }
    }
}
