package it.espr.jsonldparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.espr.jsonldparser.deserialiser.GsonDeserialiser;
import it.espr.jsonldparser.deserialiser.JacksonDeserialiser;
import it.espr.jsonldparser.deserialiser.JsonDeserialiser;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest
{
  public static final class Data
  {
    public String gtin13;
  }

  @InjectMocks
  private Parser parser;

  @Mock
  private JsonDeserialiser deserialiser;

  @Test
  public void whenHtmlContainsLdJSonTheJsonParsingMethodIsCalled()
    throws Exception
  {
    String html = this.loadResourceFile("fishpond.html");
    this.parser = spy(this.parser);

    this.parser.parseHtml(html, Data.class);

    verify(this.parser).parseJson(this.loadResourceFile("json-ld-expected.json"), Data.class);
  }

  @Test
  public void whenParseJSonMethodIsCalledItCallsDeserialiser()
    throws Exception
  {
    this.parser.parseJson("json", Data.class);

    verify(this.deserialiser).deserialise("json", Data.class);
  }

  @Test
  public void realTestWithJackson()
    throws Exception
  {
    Parser realParser = new Parser(new JacksonDeserialiser());
    String html = this.loadResourceFile("fishpond.html");
    Data data = realParser.parseHtml(html, Data.class);

    assertThat(data.gtin13).isEqualTo("9780143772064");
  }

  @Test
  public void realTestWithGson()
    throws Exception
  {
    Parser realParser = new Parser(new GsonDeserialiser());
    String html = this.loadResourceFile("fishpond.html");
    Data data = realParser.parseHtml(html, Data.class);

    assertThat(data.gtin13).isEqualTo("9780143772064");
  }

  @SuppressWarnings("resource")
  private String loadResourceFile(String file)
    throws Exception
  {
    java.net.URL url = Data.class.getClassLoader().getResource(file);
    return new java.util.Scanner(new File(url.toURI()), "UTF8").useDelimiter("\\Z").next();
  }
}
