package fr.uge.ebcserver;

import fr.uge.rmi.common.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/bikes")
public class BikeDB {

    @Autowired
    private BikeDBService bikeDBService;


    record BikeForm(String name, float locationPrice, float resalePrice, String description){
    }
    @PutMapping
    public void addBike(@RequestHeader("token") String token, @RequestBody BikeForm bikeForm) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(bikeForm);
        bikeDBService.addBike(token, bikeForm);
    }

    @DeleteMapping(value = "/{id}")
    public void remove(@RequestHeader("token") String token, @PathVariable long id) {
        Objects.requireNonNull(token);
        bikeDBService.remove(token, id);
    }

    @GetMapping("")
    public Collection<Bike> getBikes(@RequestHeader("token") String token) {
        Objects.requireNonNull(token);
        return bikeDBService.getBikes(token);
    }

    @GetMapping("/{id}")
    public Bike getBikeById(@RequestHeader("token") String token, @PathVariable long id) {
        Objects.requireNonNull(token);
        return bikeDBService.getBikeById(token, id);
    }

    @PostMapping(value = "/rent")
    public int rent(@RequestHeader("token") String token, @RequestBody long bikeId) {
        Objects.requireNonNull(token);
        return bikeDBService.rent(token, bikeId);
    }

    record RankForm(long bikeId, String comment, int grade){}
    @PostMapping(value = "/rank")
    public void rank(@RequestHeader("token") String token, @RequestBody RankForm rankForm) {
        Objects.requireNonNull(token);
        bikeDBService.rank(token, rankForm);
    }

    @PostMapping(value = "/turnIn")
    public void turnIn(@RequestHeader("token") String token, @RequestBody long bikeId) {
        Objects.requireNonNull(token);
        bikeDBService.turnIn(token, bikeId);
    }

    @GetMapping("/me")
    public HashMap<String, String> getUserInfo(@RequestHeader("token") String token) {return bikeDBService.getUserInfo(token);}

    @GetMapping("/me/share")
    public List<Bike> getMyShareBikes(@RequestHeader("token") String token) {
        return bikeDBService.getMyShareBikes(token);
    }

    @GetMapping("/me/rent")
    public List<Bike> getMyRentBikes(@RequestHeader("token") String token) {
        return bikeDBService.getMyRentBikes(token);
    }

    @GetMapping("/me/sell")
    public List<Bike> getMySellBikes(@RequestHeader("token") String token) {
        return bikeDBService.getMySellBikes(token);
    }

}
