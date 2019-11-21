package fr.campus.car.client.web.controller;
import fr.campus.car.client.model.Car;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api (description ="API pour les opérations CRUD sur les voitures")
@RestController
public class CarController {



    //Récupérer la liste des voitures
    @RequestMapping(value="/car", method= RequestMethod.GET)
    public List<Car> listeVoitures() {

            String url = "http://localhost:8082/car/";
            RestTemplate restTemplate = new RestTemplate();
            Car car = restTemplate.getForObject(url, Car.class);
            return Car;
    }


    //Récupérer une voiture par son Id
    @ApiOperation(value = "Récupère une voiture grâce à son ID à condition que celle-ci soit en stock")
    @GetMapping(value="/Cars/{id}")
    public Car afficherUneVoiture(@PathVariable int id) {
        String url = "http://localhost:8082/car/" + id;
        RestTemplate restTemplate = new RestTemplate();
        Car car = restTemplate.getForObject(url, Car.class);
        return car;




    }





//    //ajouter un produit
//    @PostMapping(value = "/Voitures")
//    public void ajouterVoiture(@RequestBody Car car) {
//        carDao.save(car);
//    }
}
