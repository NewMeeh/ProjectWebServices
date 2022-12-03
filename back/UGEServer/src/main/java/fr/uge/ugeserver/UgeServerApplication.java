package fr.uge.ugeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

@SpringBootApplication
public class UgeServerApplication {

    @Bean
    UGEService ugeService() throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1099);
        UGEService ugeService = new UGEService();
        Naming.rebind("rmi://localhost:1099/ugeService", ugeService);

        System.out.println("EEEHHHHHHH JE SUIS PASS2 PAR ICI");

        return ugeService;
    }

    public static void main(String[] args) {

        try {
/*
            LocateRegistry.createRegistry(1099);
            UGEDB ugedb = new UGEDB();
            Naming.rebind("UGEDB", ugedb);
*/

            SpringApplication.run(UgeServerApplication.class, args);

        } catch(Exception e){
            System.out.println("Exception:" + e);
        }



    }

}
