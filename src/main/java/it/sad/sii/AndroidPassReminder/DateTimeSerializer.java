package it.sad.sii.AndroidPassReminder;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by lconcli on 30/09/15.
 */
public class DateTimeSerializer implements JsonDeserializer<DateTime>, JsonSerializer<DateTime>
    {
        static final org.joda.time.format.DateTimeFormatter DATE_TIME_FORMATTER =
                DateTimeFormat.forPattern("yyyyMMdd");

        @Override
        public DateTime deserialize(final JsonElement je, final Type type,
                                    final JsonDeserializationContext jdc) throws JsonParseException
        {
            String s = je.getAsString();
            if(s.length() == 0)
                return null;
            return DATE_TIME_FORMATTER.parseDateTime(s);
        }

        @Override
        public JsonElement serialize(final DateTime src, final Type typeOfSrc,
                                     final JsonSerializationContext context)
        {
            if(src == null)
                return null;
            return new JsonPrimitive(DATE_TIME_FORMATTER.print(src));
        }
    }

