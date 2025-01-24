package allegro.agh.login_service.common.resource;

import com.google.common.io.Resources;
import java.nio.charset.StandardCharsets;

public class ResourceManager {

  public static String readSqlQuery(String resource) {
    try {
      return Resources.toString(Resources.getResource(resource), StandardCharsets.UTF_8)
          .replaceAll("\\s+", " ");
    } catch (Exception e) {
      throw new ResourceException("Unable to read static resource '" + resource + "'", e);
    }
  }
}
