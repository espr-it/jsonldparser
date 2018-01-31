package it.espr.jsonldparser.deserialiser;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeserialiser
    implements JsonDeserialiser
{
  private ObjectMapper mapper;

  public JacksonDeserialiser()
  {
    this(new ObjectMapper());
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public JacksonDeserialiser(ObjectMapper mapper)
  {
    super();
    this.mapper = mapper;
  }

  @Override
  public <Type> Type deserialise(String json, Class<Type> type)
    throws JsonDeserialisingException
  {
    try
    {
      return mapper.readValue(json, type);
    }
    catch (IOException e)
    {
      throw new JsonDeserialisingException(e);
    }
  }
}
