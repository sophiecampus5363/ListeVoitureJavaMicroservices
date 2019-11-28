package fr.campus.car.client.model;



public class Car{



    private int id;
    private String carMaker;
    private String carModel;

    public Car() {

    }

    public Car(int id, String carMaker, String carModel)
        {
            this.id = id;
            this.carMaker = carMaker;
            this.carModel = carModel;
        }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public void setCarMaker(String carMaker) {
        this.carMaker = carMaker;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Override
    public String toString(){
        return "Car{"+
                "id=" + id +
                ", carMaker='"+ carMaker + '\'' +
                ", carModel=" + carModel + '}';
    }
}
