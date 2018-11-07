import javax.swing.*;

public class HelloWorld {
    public static void main(String[] args) {
        //printNames();

        Market market = new Market();
        System.out.println(market.getStockPrice("IBM"));


        Frame frame = new Frame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

    }

    static void printNames() {
        System.out.println("Justas Stromilas was here.");
        System.out.println("Finlay Georgiou-Young was here.");
        System.out.println("Sean Mcleod was here.");
        System.out.println("Riaz Philippe was here.");
        System.out.println("Cameron Cunningham was here.");//Server Test Pull & Push Complete
    }
}

// Build frame in this class
class Frame extends JFrame {
    private int size = 400;
    public Frame(){
        setTitle("Default Frame");
        setSize(size, size);
    }
}