package migrator.TargetBlogConfigFactories;

import auth.AuthClient;
import auth.TistoryClient;
import convert.converters.Converter;
import convert.converters.tistory.TistoryConverter;
import migrator.TargetBlogConfigFactory;
import utils.Utils;

public class TistoryConfigFactory implements TargetBlogConfigFactory {
    @Override
    public AuthClient createAuthClient() {
        try{
            return new TistoryClient();
        }
        catch(Exception e){
            Utils.printMessage(e.getMessage());
        }
        return null;
    }

    @Override
    public Converter createConverter(AuthClient authClient) {
        try{
            return new TistoryConverter((TistoryClient) authClient);
        }
        catch(Exception e){
            Utils.printMessage(e.getMessage());
        }
        return null;
    }
}
