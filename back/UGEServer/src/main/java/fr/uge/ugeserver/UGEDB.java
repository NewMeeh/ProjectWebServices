package fr.uge.ugeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.uge.ugeserver.UGEService.UGEUser;

import java.util.HashMap;


@RestController
@RequestMapping("/uge")
public class UGEDB {

    @Autowired
    private UGEService ugeService;


    @PostMapping(value = "/login")
    public String login(@RequestBody UGEService.UGEUser ugeUser) {
        return ugeService.login(ugeUser);
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestHeader("token") String token) {
        ugeService.logout(token);
    }


}