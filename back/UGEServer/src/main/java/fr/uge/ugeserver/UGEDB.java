package fr.uge.ugeserver;

import fr.uge.rmi.common.IUGEDB;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping("/uge")
public class UGEDB extends UnicastRemoteObject implements IUGEDB {

    Logger logger = Logger.getLogger(UGEDB.class.getName());

    record UGEUser(String user, String password) {
        public boolean isUser(String name) {
            return name.equals(user);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final HashMap<Long, UGEUser> users = new HashMap<>();
    private final HashMap<UGEUser, String> connectedUsers = new HashMap<>();


    public UGEDB() throws RemoteException {
        fillHashMap();
    }


    private void fillHashMap() {

        String[] name= {"Julien", "Nader", "Thomas", "Adrien", "Maël", "William", "Georges", "Estelle", "Althéa", "Irwin", "Gogo",
                "Orhan", "Loris", "Xavit", "Sami", "Bryan", "Jimmy", "Fredo", "Rym", "Coco"};

        for (int i = 0; i < 20; i++) {
            users.put((long) i, new UGEUser(name[i], "pwd"));
        }
        logger.info(users.toString());
    }

    @Override
    public boolean isTokenValid(String token) {
        for (String t : connectedUsers.values()) {
            logger.info("token is " + token);
            logger.info("t is " + t);
            if(t.equals(token)) {
                logger.info("success : token is valid");
                return true;
            }
        }
        logger.info("failure : token doesn't exist");
        return false;
    }

    // code get from https://stackoverflow.com/questions/13992972/how-to-create-a-authentication-token-using-java
    private String randomToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody UGEUser ugeUser) throws RemoteException {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(ugeUser.user()) && user.getValue().isPassword(ugeUser.password())) {
                var token = randomToken();
                connectedUsers.put(ugeUser, token);
                logger.info("login success token is " + token);
                return token;
            }
        }

        logger.info("login has failed");
        return null;
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestBody UGEUser ugeUser) throws RemoteException {
        var token = connectedUsers.remove(ugeUser);
        logger.info("logout previous token were " + token);
    }


}
