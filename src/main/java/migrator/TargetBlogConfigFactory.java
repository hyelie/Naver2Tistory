package migrator;

import auth.AuthClient;
import convert.converters.Converter;

public interface TargetBlogConfigFactory {
    public AuthClient createAuthClient();
    public Converter createConverter(AuthClient authClient);
}
