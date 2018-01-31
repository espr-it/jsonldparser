package it.espr.jsonldparser.deserialiser;

public interface JsonDeserialiser
{
  public <Type> Type deserialise(String json, Class<Type> type)
    throws JsonDeserialisingException;
}
