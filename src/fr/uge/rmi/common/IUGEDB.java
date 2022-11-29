package fr.uge.rmi.common;

import java.rmi.Remote;

public interface IUGEDB extends Remote {
    boolean isTokenValid(String token);
}
