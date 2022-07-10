package com.community.xdldm.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.xdldm.community.dto.AccessTokenDto;
import com.community.xdldm.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();

         RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto), mediaType);
         Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                System.out.println(token);
                return token;
        } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url("https://api.github.com/user")
                    .header("Authorization","token "+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                System.out.println(string);
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (IOException e) {

            }
        return null;
    }
}

