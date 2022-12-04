package fr.uge.ugeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/uge")
@CrossOrigin(origins = "http://localhost", maxAge = 3600,allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
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