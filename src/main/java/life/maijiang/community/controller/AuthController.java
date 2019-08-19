package life.maijiang.community.controller;

import life.maijiang.community.dto.AccessTokenDTO;
import life.maijiang.community.dto.GithubUser;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import life.maijiang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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
    @Autowired
    private UserMapper userMapper;
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
        GithubUser githubUser = gitHubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        if(githubUser!=null){//登陆成功写入session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";//重定向
        }else {
            return "redirect:/";//重定向
        }

    }

}
