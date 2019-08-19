package life.maijiang.community.controller;

import life.maijiang.community.dto.AccessTokenDTO;
import life.maijiang.community.dto.GithubUser;
import life.maijiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("a1af92fd7ad72e44c29f");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret("86e1bfb117fc03aa2c9aac864b630feb07048b1d");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}
