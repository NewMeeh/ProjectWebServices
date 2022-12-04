package fr.uge.gustavebike;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gbs")
public class GustaveBikeDBService {
    /*
     * Class representing a user of Gustave Bike Service
     */
    record GBUser(Long id, String username, String password, String firstName, String lastName, String mail) {
        public boolean isUser(String user) {
            return user.equals(username);
        }
        public boolean isPassword(String pwd) {
            return pwd.equals(password);
        }
    }

    private final Logger logger = Logger.getLogger(GustaveBikeDBService.class.getName());
    private final IBikeDB bikeService;
    private final HashMap<Long, GBUser> users = new HashMap<>();
    private final HashMap<String, GBUser> connectedUsers = new HashMap<>();

    /* Pour chaque userId une liste est associé(le cart du user) */
    private final HashMap<Long, ArrayList<Bike>> allCarts = new HashMap<>();

    public GustaveBikeDBService() throws RemoteException, MalformedURLException, NotBoundException {
        fillHashMapWithExempleData();
        bikeService = (IBikeDB) Naming.lookup("rmi://localhost:1089/bikeService");
    }

    private void fillHashMapWithExempleData() {
        String[] name= {"Ju", "Nad", "Toto", "Adri", "ElMaël", "Wiwi", "Georgio", "Es", "AltF4", "Irwouin", "Jughurta",
                "OrhanUP", "Lolo", "Xaxa", "Sam", "Brian", "Jim", "Frederic", "Ryry", "Corentin", "Yann PICKER"};

        for (int i = 0; i < name.length; i++) {
            users.put((long) i, new GBUser((long) i, name[i], "pwd", name[i], "SNOW", name[i]+".snow@example.fr"));
        }
        logger.info("HashMap filled with example datas : " + users);
    }

    private String randomToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody GBUser gbUser) {
        for(Map.Entry<Long, GBUser> user : users.entrySet()) {
            if(user.getValue().isUser(gbUser.username()) && user.getValue().isPassword(gbUser.password())) {
                var token = randomToken();
                connectedUsers.put(token, user.getValue());
                /*si le user n'a pas son id dans allCarts:
                * cree un cart (new arraylist<Bike>)
                * ajouter dans allCarts le user id et son cart
                */
                logger.info("login success token is " + token);
                return token;
            }
        }

        logger.info("login has failed");
        return null;
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestHeader("gtoken") String gtoken) {
        connectedUsers.remove(gtoken);
        logger.info("logout previous token were " + gtoken);
    }

    @PostMapping(value = "/signIn")
    public void signIn() {
        /*
        * permet une inscription
        */
    }

    /* Voir le type de requete avec toto */
    @PostMapping("/myCart")
    public void buy(String gtoken) {
        /* RMI sur EBC
        * pour tous les bike du cart:
        * lance bank(allbikeincart.price)
        * si ok:
        * lance EBC.remove(getbikebyid(bike_id)) sur allbikeincart
        * lance notif d'achat
        * vide le cart
        * sinon:
        * lance notif refus bank
        * exit buy
        */
    }
    @PostMapping("/myCart/{bikeId}")
    public void addToCart(String gtoken) {
        /*
         * ajoute selectedbike ds cart
         */
    }
    @DeleteMapping("/myCart/{bikeId}")
    public void removeFromCart(String gtoken) {
        /*
         * retire selectedbike ds cart
         */
    }
    @DeleteMapping("/myCart")
    public void emptyCart(String gtoken) {
        /*
         * retire allbikes du cart
         */
    }
    @GetMapping("/myCart")
    public void getCart(String gtoken) {
        /*
         * renvoie cart<bike>
         */
    }

    @GetMapping("")
    public Collection<Bike> getBikes(@RequestHeader("gtoken") String gtoken) {
        /* RMI sur EBC
        * renvoie tous les bikes dispo à la vente.
        */
        return null;
    }
    @GetMapping("/{id}")
    public Bike getBikeById(@RequestHeader("gtoken") String gtoken, @PathVariable long id) {
        /* RMI sur EBC
         * renvoie tous le bikes si dispo à la vente
         * sinon exit la fonction.
         */
        return null;
    }
}
