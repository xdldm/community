package com.community.xdldm.community.Controller;

import com.community.xdldm.community.dto.AccessTokenDto;
import com.community.xdldm.community.dto.GithubUser;
import com.community.xdldm.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_secret("b034e619f2d821dc8b80d94726771c103b9aed06");
        accessTokenDto.setClient_id("b2cfe9daa2d6cdce6d7c");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getId());
        return "index";
    }
}
