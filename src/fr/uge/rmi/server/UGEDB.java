package fr.uge.rmi.server;

import fr.uge.rmi.comon.IUGEDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Optional;

public class UGEDB extends UnicastRemoteObject implements IUGEDB {


    record UGEUser(String user, String password){

        public boolean isUser(String name) {
            return name == user;
        }
        public boolean isPassword(String pwd) {
            return pwd == password;
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
            users.put((long) i, new UGEUser(name[i], "password"));
        }
    }


    @Override
    public Boolean login(String user, String password) throws RemoteException {
        for(Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUSer(name) && user.getValue().isPassword(password)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Long> getId(String name) {
        for(Entry<Long, UGEUser> user : users.entrySet()) {
            if(user.getValue().isUSer(name)) {
                return user.getKey();
            }
        }
        return null;
    }




}
