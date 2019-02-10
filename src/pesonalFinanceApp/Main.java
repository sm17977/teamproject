package pesonalFinanceApp;

import java.io.IOException;

import static pesonalFinanceApp.GUI.saveClientList;

public class Main {
    public static void main(String[] args) {

        GUI frame = new GUI();
        frame.updateClientList();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

    }
}