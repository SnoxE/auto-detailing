package allegro.agh.login_service.common.resource;

import java.nio.charset.StandardCharsets;
import com.google.common.io.Resources;

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
