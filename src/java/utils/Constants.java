package utils;

public class Constants {

    public static final String GOOGLE_CLIENT_ID = "381681113764-hsaf7orbp826di9c40annfk7fidqmgiq.apps.googleusercontent.com";

    public static final String GOOGLE_CLIENT_SECRET = "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";

    public static final String GOOGLE_REDIRECT_URI = "http://localhost:9999/ManageLodgingHouse/LoginByGoogle";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static final String FACEBOOK_CLIENT_ID = "388338590876128";
    
    public static final String FACEBOOK_CLIENT_SECRET = "fc62e4cd6057cca37f5a5f7c09e50932";
    
    public static final String FACEBOOK_REDIRECT_URI = "http://localhost:9999/ManageLodgingHouse/LoginByFacebook";
    
    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";
    
    public static final String FACEBOOK_LINK_GET_USER_INFO = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";

}
