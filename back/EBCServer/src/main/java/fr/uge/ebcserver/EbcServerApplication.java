package fr.uge.ebcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@SpringBootApplication
public class EbcServerApplication {

    @Bean
    BikeDBService bikeDBServer() throws RemoteException, MalformedURLException, NotBoundException {
        LocateRegistry.createRegistry(1089);
        BikeDBService bikeDBServer = new BikeDBService();
        Naming.rebind("rmi://localhost:1089/bikeService", bikeDBServer);

        System.out.println("EEEHHHHHHH JE SUIS PASS2 PAR ICI");

        return bikeDBServer;
    }


    public static void main(String[] args) {
        SpringApplication.run(EbcServerApplication.class, args);
    }

}
