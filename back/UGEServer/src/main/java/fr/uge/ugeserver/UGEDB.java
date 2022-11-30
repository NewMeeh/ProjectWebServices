package fr.uge.ugeserver;

import fr.uge.rmi.common.IUGEDB;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


@RestController
@RequestMapping("/uge")
public class UGEDB extends UnicastRemoteObject implements IUGEDB {

    Logger logger = Logger.getLogger(UGEDB.class.getName());

    record UGEUser(Long id, String user, String password) {
        public boolean isUser(String name) {
            return name.equals(user);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final HashMap<Long, UGEUser> users = new HashMap<>();
    private final HashMap<String, UGEUser> connectedUsers = new HashMap<>();


    public UGEDB() throws RemoteException {
        fillHashMap();
    }


    private void fillHashMap() {

        String[] name= {"Julien", "Nader", "Thomas", "Adrien", "Maël", "William", "Georges", "Estelle", "Althéa", "Irwin", "Gogo",
                "Orhan", "Loris", "Xavit", "Sami", "Bryan", "Jimmy", "Fredo", "Rym", "Coco"};

        for (long i = 0; i < 20; i++) {
            users.put(i, new UGEUser(i, name[(int) i], "pwd"));
        }
        logger.info(users.toString());
    }

    @Override
    /*
      Check if the given token is valid returns the id of the user if it is valid
      return -1 if the token not valid
     */
    public long isTokenValid(String token) throws RemoteException {
        logger.info("token is " + token);
        for(var e : connectedUsers.entrySet()) {
            if (e.getKey().equals(token)) {
                logger.info("success : token is valid");
                return e.getValue().id();
            }
        }
        logger.info("failure : token doesn't exist");
        return -1;
    }

    private String randomToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody UGEUser ugeUser) throws RemoteException {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(ugeUser.user()) && user.getValue().isPassword(ugeUser.password())) {
                var token = randomToken();
                connectedUsers.put(token, ugeUser);
                logger.info("login success token is " + token);
                return token;
            }
        }

        logger.info("login has failed");
        return null;
    }

    @PostMapping(value = "/logout")

    public void logout(@RequestHeader("token") String token) throws RemoteException {
        connectedUsers.remove(token);
        logger.info("logout previous token were " + token);
    }


}
