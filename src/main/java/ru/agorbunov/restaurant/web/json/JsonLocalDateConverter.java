package ru.agorbunov.restaurant.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.agorbunov.restaurant.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Class for customize serialize-deserialize LocalDateTime
 */
class JsonLocalDateConverter {
    static class UserSettingSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate ldt, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(DateTimeUtil.toString(ldt));
        }

        @Override
        public Class<LocalDate> handledType() {
            return LocalDate.class;
        }
    }

    static class UserSettingDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
            return DateTimeUtil.parseLocalDate(jp.getText());
        }

        @Override
        public Class<LocalDate> handledType() {
            return LocalDate.class;
        }
    }
}
