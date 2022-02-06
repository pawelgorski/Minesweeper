import controller.MinesweeperController;
import model.MinesweeperModel;
import view.MinesweeperGUI;

public class Main {

    public static void main(String[] args) {


        MinesweeperController controller = new MinesweeperController();
        MinesweeperModel model = new MinesweeperModel(9,9,10);
        controller.setModel(model);

        model.setController(controller);
        MinesweeperGUI gui = new MinesweeperGUI(controller);
        controller.setGUI(gui);

    }




}
