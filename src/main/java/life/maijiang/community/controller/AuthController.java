package life.maijiang.community.controller;

import life.maijiang.community.dto.AccessTokenDTO;
import life.maijiang.community.dto.GithubUser;
import life.maijiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")//项目启动时自动从application文件中读取key为github.client.id的值
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.client.Redirect_uri}")
    private String Redirect_uri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());
        if(user!=null){//登陆成功写入session
            request.getSession().setAttribute("user",user);
            return "redirect:/";//重定向
        }else {
            return "redirect:/";//重定向
        }

    }

}
