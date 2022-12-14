package fr.uge.gustavebike;

import fr.uge.bank.Bank;
import fr.uge.bank.BankServiceLocator;
import fr.uge.ebcserver.exception.ForbiddenException;
import fr.uge.exchange.ExchangeService;
import fr.uge.exchange.ExchangeServiceLocator;
import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import org.springframework.web.bind.annotation.*;

import javax.xml.rpc.ServiceException;
import java.util.logging.Level;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


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
    private final ExchangeService exchangeService = new ExchangeServiceLocator();
    private final Bank bankService = new BankServiceLocator().getBank();

    /* Pour chaque userId une liste est associé(le cart du user) */
    private final HashMap<Long, ArrayList<Bike>> allCarts = new HashMap<>();

    public GustaveBikeDBService() throws RemoteException, MalformedURLException, NotBoundException, ServiceException {
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
                logger.info(connectedUsers.toString());
                if (!allCarts.containsKey(user.getKey())) {
                    var cart = new ArrayList<Bike>();
                    allCarts.put(user.getKey(), cart);
                }
                logger.info("login success token is " + token);
                return token;
            }
        }

        logger.info("login has failed");
        return null;
    }

    public long isTokenValid(String token) {
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

    private long checkValidAndGetId(String token) {
        long id = isTokenValid(token);
        if (id < 0) throw new ForbiddenException(new IllegalArgumentException("Token is not valid"));
        return id;

    }

    record ExchangeForm(String originCurrency, String targetCurrency, float amount){}
    @PostMapping(value="/exchange")
    public float exchange(@RequestBody ExchangeForm exchangeForm) throws RemoteException, ServiceException {
        return this.exchangeService.getExchange().exchange(exchangeForm.originCurrency, exchangeForm.targetCurrency, exchangeForm.amount);
    }

    @PostMapping(value = "/logout")
    public void logout(@RequestHeader("gtoken") String gtoken) {
        connectedUsers.remove(gtoken);
        logger.info("logout previous token were " + gtoken);
    }

    record RegisterForm(String userName, String pwd, String fname, String lname, String mail){}
    @PostMapping(value = "/signIn")
    public int signIn(@RequestBody RegisterForm registerForm) {
        /*
        * permet une inscription
        */
        if(users.values().stream().noneMatch(user -> Objects.equals(user.username, registerForm.userName()) || Objects.equals(user.mail, registerForm.mail()))) {
            users.put((long) users.size(), new GBUser((long) users.size(), registerForm.userName(), registerForm.pwd(), registerForm.fname(), registerForm.lname(), registerForm.mail()));
            return 1;
        }
        return 0;
    }


    record CardForm(String cardNumber, String expirationDate, String cvv){}
    /* Voir le type de requete avec toto */
    @PostMapping("/myCart/buy")
    public void buy(@RequestHeader("gtoken") String gtoken, @RequestBody CardForm cardForm) throws RemoteException {
        var userId = checkValidAndGetId(gtoken);
        var userCart = allCarts.get(userId);
        var totalPrice = (float) userCart.stream().mapToDouble(b -> b.getResalePrice()).sum();
        var hasMoney = bankService.pay(cardForm.cardNumber, cardForm.expirationDate, cardForm.cvv, totalPrice);
        if(hasMoney) {
            for (Bike bike : userCart) {
                try {
                    bikeService.remove(bike.getBikeId());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
            /*affiche notif : bravo pour votre achat*/
            emptyCart(gtoken);
        } else {
            /*affiche notif : t'es qu'une sale merde de pauvre*/
        }
    }

    @PostMapping("/myCart/total")
    public float total(@RequestHeader("gtoken") String gtoken) {
        var userId = checkValidAndGetId(gtoken);
        var userCart = allCarts.get(userId);
        return (float) userCart.stream().mapToDouble(Bike::getResalePrice).sum();
    }

    private boolean isInAllCarts(Bike bike) {
        var nbBikePresent = allCarts.values().stream().filter(cart -> cart.contains(bike)).count();
        if(nbBikePresent == 0) {
            return false;
        }
        if(nbBikePresent == 1) {
            return true;
        }
        throw new IllegalStateException("A same bike can't be in more than one cart");
    }

    @PostMapping("/myCart/{bikeId}")
    public void addToCart(@RequestHeader("gtoken") String gtoken, @PathVariable long bikeId) {
        var userId = checkValidAndGetId(gtoken);
        logger.info(allCarts.toString());
        var userCart = allCarts.get(userId);
        try {
            var bike = bikeService.getSellBikeById(bikeId);
            if(bike != null && !isInAllCarts(bike)) {
                userCart.add(bike);
                bikeService.unRent(bikeId);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    @DeleteMapping("/myCart/{bikeId}")
    public void removeFromCart(@RequestHeader("gtoken") String gtoken, @PathVariable long bikeId) {
        var userId = checkValidAndGetId(gtoken);
        var userCart = allCarts.get(userId);
        var bike = userCart.stream().filter(b -> b.getBikeId() == bikeId).collect(Collectors.toList());
        userCart.remove(bike.get(0));
        try {
            bikeService.reRent(bike.get(0).getBikeId());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    @DeleteMapping("/myCart")
    public void emptyCart(@RequestHeader("gtoken") String gtoken) {
        var userId = checkValidAndGetId(gtoken);
        var userCart = allCarts.get(userId);
        var bikes = new ArrayList<Bike>();
        for (Bike b: userCart) {
            bikes.add(b);
        }
        userCart.removeAll(bikes);
        try {
            for (Bike bike: bikes) {
                bikeService.reRent(bike.getBikeId());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    @GetMapping("/myCart")
    public ArrayList<Bike> getCart(@RequestHeader("gtoken") String gtoken) {
        var userId = checkValidAndGetId(gtoken);
        return allCarts.get(userId);
    }

    @GetMapping("/bikes")
    public Collection<Bike> getBikes(/*@RequestHeader("gtoken") String gtoken*/) {
        try {
            return bikeService.getSellBikes();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{id}")
    public Bike getBikeById(/*@RequestHeader("gtoken") String gtoken,*/ @PathVariable long id) {
        try {
            return bikeService.getSellBikeById(id);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/me")
    public HashMap<String, String> getUserInfo(@RequestHeader("gtoken") String gtoken) throws RemoteException {
        var userInfos = connectedUsers.get(gtoken);
        logger.info(userInfos.firstName);
        var ret = new HashMap<String, String>();
        ret.put("username", userInfos.username());
        ret.put("fname", userInfos.firstName());
        ret.put("lname", userInfos.lastName());
        ret.put("mail", userInfos.mail());
        return ret;
    }
}
