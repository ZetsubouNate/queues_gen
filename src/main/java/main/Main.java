package main;

import businessLogic.SimulationManager;
import controller.Controller;
import view.SimView;
import view.View;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        SimulationManager simulation = new SimulationManager("src\\main\\resources\\sim_in1.txt", "src\\main\\resources\\sim_out1.txt");
//        Thread t = new Thread(simulation);
//        t.start();

        View view = new View();
        SimView simView = new SimView();
        Controller controller = new Controller(simView, view, "src\\main\\resources\\sim_out.txt");
        view.setVisible(true);

    }
}
