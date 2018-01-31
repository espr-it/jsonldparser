package it.espr.jsonldparser.deserialiser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonDeserialiser
    implements JsonDeserialiser
{
  private Gson gson;

  public GsonDeserialiser()
  {
    this(new Gson());
  }

  public GsonDeserialiser(Gson gson)
  {
    super();
    this.gson = gson;
  }

  @Override
  public <Type> Type deserialise(String json, Class<Type> type)
    throws JsonDeserialisingException
  {
    try
    {
      return gson.fromJson(json, type);
    }
    catch (JsonSyntaxException e)
    {
      throw new JsonDeserialisingException(e);
    }
  }
}
