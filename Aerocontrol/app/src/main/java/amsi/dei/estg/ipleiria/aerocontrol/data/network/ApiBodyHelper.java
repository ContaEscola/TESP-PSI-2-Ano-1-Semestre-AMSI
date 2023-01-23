package amsi.dei.estg.ipleiria.aerocontrol.data.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ApiBodyHelper {

    public static String addAttributeToJsonBody (String json, String key, Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        map.put(key, value);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }

    public static Map<String, Object> convertJsonStringToMap(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Map.class);
    }
}
