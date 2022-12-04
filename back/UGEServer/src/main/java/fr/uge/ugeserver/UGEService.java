package fr.uge.ugeserver;

import fr.uge.rmi.common.IUGEDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class UGEService extends UnicastRemoteObject implements IUGEDB {

    /*
     * Class representing a student or worker of UGE
     */
    record UGEUser(Long id, String username, String password, String firstName, String lastName, String mail) {
        public boolean isUser(String user) {
            return user.equals(username);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final Logger logger = Logger.getLogger(UGEDB.class.getName());
    private final HashMap<Long, UGEUser> users = new HashMap<>();
    private final HashMap<String, UGEUser> connectedUsers = new HashMap<>();

    protected UGEService() throws RemoteException {
        fillHashMapWithExempleData();
    }

    private void fillHashMapWithExempleData() {
        String[] name= {"Julien", "Nader", "Thomas", "Adrien", "Maël", "William", "Georges", "Estelle", "Althéa", "Irwin", "Gogo",
                "Orhan", "Loris", "Xavit", "Sami", "Bryan", "Jimmy", "Fredo", "Rym", "Coco", "ypicker"};

        for (int i = 0; i < name.length; i++) {
            users.put((long) i, new UGEUser((long) i, name[i], "pwd", name[i], "SNOW", name[i]+".snow@example.fr"));
        }
        logger.info("HashMap filled with example datas : " + users);
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
                logger.info("success : token is valid" + e.getValue());
                return e.getValue().id();
            }
        }
        logger.info("failure : token doesn't exist");
        return -1;
    }

    @Override
    public String getNameById(long id) throws RemoteException {
        return users.get(id).firstName();
    }

    private String randomToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String login(UGEUser ugeUser) {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(ugeUser.username()) && user.getValue().isPassword(ugeUser.password())) {
                var token = randomToken();
                connectedUsers.put(token, user.getValue());
                logger.info("login success token is " + token);
                return token;
            }
        }

        logger.info("login has failed");
        return null;
    }

    public void logout(String token) {
        connectedUsers.remove(token);
        logger.info("logout previous token were " + token);
    }

    @Override
    public HashMap<String, String> getUserInfo(String token) {
        var userInfos = connectedUsers.get(token);
        var ret = new HashMap<String, String>();
        ret.put("username", userInfos.username());
        ret.put("fname", userInfos.firstName());
        ret.put("lname", userInfos.lastName());
        ret.put("mail", userInfos.mail());
        return ret;
    }

}
