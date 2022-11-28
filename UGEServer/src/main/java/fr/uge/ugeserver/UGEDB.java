package fr.uge.ugeserver;

import fr.uge.rmi.common.IUGEDB;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/uge")
public class UGEDB extends UnicastRemoteObject implements IUGEDB {


    record UGEUser(String user, String password) {
        public boolean isUser(String name) {
            return name.equals(user);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final HashMap<Long, UGEUser> users = new HashMap<>();


    public UGEDB() throws RemoteException {
        fillHashMap();
    }


    private void fillHashMap() {

        String[] name= {"Julien", "Nader", "Thomas", "Adrien", "Maël", "William", "Georges", "Estelle", "Althéa", "Irwin", "Gogo",
                "Orhan", "Loris", "Xavit", "Sami", "Bryan", "Jimmy", "Fredo", "Rym", "Coco"};

        for (int i = 0; i < 20; i++) {
            users.put((long) i, new UGEUser(name[i], "pwd"));
        }
    }


    @PostMapping(value = "/login")
    public boolean login(@RequestBody UGEUser ugeUser) throws RemoteException {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(ugeUser.user()) && user.getValue().isPassword(ugeUser.password())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean login(String name, String password) throws RemoteException {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(name) && user.getValue().isPassword(password)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/user/{name}")
    @Override
    public long getId(@PathVariable String name) throws RemoteException {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(name)) {
                return user.getKey();
            }
        }
        return -1L;
    }
}
