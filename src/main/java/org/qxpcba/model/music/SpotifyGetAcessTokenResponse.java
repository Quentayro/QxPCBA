package org.qxpcba.model.music;

public class SpotifyGetAcessTokenResponse {
    private String accessToken;
    private int expiresIn;
    private String tokenType;

    public String getAccessToken() {
        return this.accessToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setAccess_token(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpires_in(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setToken_type(String tokenType) {
        this.tokenType = tokenType;
    }
}
