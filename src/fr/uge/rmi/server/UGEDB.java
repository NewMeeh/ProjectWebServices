package fr.uge.rmi.server;

import fr.uge.rmi.common.IUGEDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UGEDB extends UnicastRemoteObject implements IUGEDB {


    record UGEUser(String user, String password){

        public boolean isUser(String name) {
            return name.equals(user);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final HashMap<Long, UGEUser> users = new HashMap<>();



    protected UGEDB() throws RemoteException {
        // BLABLA remplir la hashmap
        fillHashMap();
    }


    private void fillHashMap() {

        String[] name= {"Julien", "Nader", "Thomas", "Adrien", "Maël", "William", "Georges", "Estelle", "Althéa", "Irwin", "Gogo",
                "Orhan", "Loris", "Xavit", "Sami", "Bryan", "Jimmy", "Fredo", "Rym", "Coco"};

        for (int i = 0; i < 20; i++) {
            users.put((long) i, new UGEUser(name[i], "pwd"));
        }
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

    public Optional<Long> getId(String name) {
        for(Map.Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUser(name)) {
                return Optional.ofNullable(user.getKey());
            }
        }
        return null;
    }




}
