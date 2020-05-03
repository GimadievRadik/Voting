package ru.gimadiew.voting.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.gimadiew.voting.model.Dish;

import java.io.IOException;

public class DishSerializer extends StdSerializer<Dish> {

    public DishSerializer() {
        this(null);
    }

    public DishSerializer(Class<Dish> t) {
        super(t);
    }

    @Override
    public void serialize(Dish dish, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", dish.getId());
        jgen.writeStringField("description", dish.getDescription());
        jgen.writeNumberField("price", dish.getPrice() / 100.0);
        jgen.writeEndObject();
    }
}
