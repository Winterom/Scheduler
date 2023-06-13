package alexey.grizly.com.users.messages.request;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RequestMessagePayloadDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        long begin = jp.getCurrentLocation().getCharOffset();
        jp.skipChildren();
        long end = jp.getCurrentLocation().getCharOffset();
        String json = jp.getCurrentLocation().getSourceRef().toString();
        return json.substring((int) begin - 1, (int) end);
    }
}
