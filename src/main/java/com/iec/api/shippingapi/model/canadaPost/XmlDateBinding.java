package com.iec.api.shippingapi.model.canadaPost;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/** Preserves XML date values without converting them to timestamps or changing their timezone. */
public final class XmlDateBinding {
    private XmlDateBinding() {
    }

    public static final class Serializer extends ValueSerializer<XMLGregorianCalendar> {
        @Override
        public void serialize(XMLGregorianCalendar value, JsonGenerator generator, SerializationContext context) {
            generator.writeString(value.toXMLFormat());
        }
    }

    public static final class Deserializer extends ValueDeserializer<XMLGregorianCalendar> {
        @Override
        public XMLGregorianCalendar deserialize(JsonParser parser, DeserializationContext context) {
            String value = parser.getString().trim();
            try {
                return DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(value);
            } catch (IllegalArgumentException exception) {
                return (XMLGregorianCalendar) context.handleWeirdStringValue(
                        XMLGregorianCalendar.class, value, "Expected an XML date value");
            }
        }
    }
}
