package fr.campus.car.client.web.controller;
import fr.campus.car.client.form.CarForm;
import fr.campus.car.client.model.Car;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * Ceci est un commentaire Javadoc
 */


@Controller
public class CarController {

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;


    /**
     * Affiche la fiche détaillée d'une voiture
     * @param id
     * @param model
     * @return le template de la fiche d'une voiture
     */

    @GetMapping(value = "/car/{id}")
    public String carDetail(@PathVariable Integer id, Model model) {
        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8082/car/" + id;
        Car car = rt.getForObject(url, Car.class);
        model.addAttribute("car", car);
        return "detailCar";
    }

    /**
     * Affiche la liste des voitures
     * @param model
     * @return template contenant le tableau de l'ensemble des voitures
     */

    @GetMapping(value = "/carList")
    public String CarList(Model model) {
        RestTemplate rt = new RestTemplate();
        String url = "http://localhost:8082/car/" ;
        List<Car> cars = rt.getForObject(url, List.class);
        model.addAttribute("cars", cars);
        return "carList";
    }

    /**
     * Permet de créer une nouvelle voiture qui sera ajoutée à la liste
     * @param model
     * @return un formulaire pour ajouter la marque et le modèle d'une nouvelle voiture
     */

    @GetMapping(value = "/addCar")
    public String showAddCarPage(Model model) {
        CarForm CarForm = new CarForm();
        model.addAttribute("carForm", CarForm);
        return "addCar";
    }

    /**
     * ajoute une voiture dans le tableau de la liste des voitures
     * @param model
     * @param carForm
     * @return le tableau des voitures mis à jour
     */


    @PostMapping(value = "/addCar")
    public String saveCar(Model model, @ModelAttribute("carForm") CarForm carForm) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        if (carForm != null) {
            Car newCar = new Car();
            headers.setContentType(MediaType.APPLICATION_JSON);
            newCar.setCarMaker(carForm.getCarMaker());
            newCar.setCarModel(carForm.getCarModel());
            HttpEntity<Car> request = new HttpEntity<Car>(newCar, headers);
            String url = "http://localhost:8082/car/";
            newCar = rt.postForObject(url, request, Car.class);
            model.addAttribute("car", newCar);
            return "redirect:/carList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addCar";
    }

    /**
     * Permet de mettre à jour les informations d'une voiture
     * @param id
     * @param model
     * @return un formulaire pour modifier la marque et/ou le modèle d'une voiture
     */

    @RequestMapping(value = { "/updateCar/{id}" }, method = RequestMethod.GET)
    public String carEdit(@PathVariable int id, Model model) {
        String url = "http://localhost:8082/car/"+id;
        RestTemplate restTemplate = new RestTemplate();
        CarForm carForm = restTemplate.getForObject(url, CarForm.class);
        model.addAttribute("carForm", carForm);

        return "updateCar";
    }

    /**
     * permet d'enregistrer les informations modifiées de la voiture
     * @param model
     * @param carForm
     * @param id
     * @return la liste des voitures mise à jour
     */

    @PostMapping(value = { "/updateCar/{id}" })
    public String editCar(Model model, //
                         @ModelAttribute("carForm") CarForm carForm, @PathVariable int id) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Car car = new Car();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (carForm != null) {
            car.setId(id);
            car.setCarMaker(carForm.getCarMaker());
            car.setCarModel(carForm.getCarModel());

            HttpEntity<Car> request = new HttpEntity<Car>(car, headers);
            String url = "http://localhost:8082/car/" + id;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(url, car);
            return "redirect:/carList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addCar";
    }

    /**
     * Permet d'effacer une voiture de la liste des voitures
     * @param id
     * @return la liste des voitures mise à jour
     */

    @DeleteMapping(value = "/car/{id}")
    public String remove(@PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/car/" + id;
        restTemplate.delete(url);

        return "carList";
    }








}
