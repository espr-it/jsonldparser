package it.espr.jsonldparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.espr.jsonldparser.deserialiser.JsonDeserialiser;
import it.espr.jsonldparser.deserialiser.JsonDeserialisingException;

public class Parser
{
  private JsonDeserialiser deserialiser;

  public Parser(JsonDeserialiser deserialiser)
  {
    super();
    this.deserialiser = deserialiser;
  }

  /**
   * Parse json string into provided Type.
   * 
   * @param json json string
   * @param type data type class
   * @return instance of data type
   */
  public <Type> Type parseJson(String json, Class<Type> type)
  {
    try
    {
      return deserialiser.deserialise(json, type);
    }
    catch (JsonDeserialisingException e)
    {
      return null;
    }
  }

  /**
   * Parse HTML with default ld-json javascript matcher.
   * 
   * @param html html code
   * @param type output data type
   * @return instance of data type
   */
  public <Type> Type parseHtml(String html, Class<Type> type)
  {
    // find json-ld text
    return this.parseHtml(Pattern.compile("<script type=\"application/ld\\+json\">(.*?)</script>", Pattern.DOTALL), html, type);
  }

  /**
   * PArse HTML with custom pattern for json-ld script.
   * 
   * @param pattern custom regex pattern
   * @param html html code
   * @param type output data type
   * @return instance of data type
   */
  public <Type> Type parseHtml(Pattern pattern, String html, Class<Type> type)
  {
    // find json-ld text
    Matcher matcher = pattern.matcher(html);
    if (matcher.find())
    {
      String ldJson = matcher.group(1).trim();
      return this.parseJson(ldJson, type);
    }
    return null;
  }

}
