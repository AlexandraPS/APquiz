package sk.plajdickova.apquiz.view.result;

import javax.swing.*;

public class ResultView extends JPanel {

    private ResultController controller;

    public ResultView() {
        String data[][] = {
                {"16.06.2021", "47s", "9"},
                {"16.06.2021", "47s", "9"},
                {"16.06.2021", "47s", "9"},
                {"16.06.2021", "47s", "9"}
        };
        String header[] = {"Dátum", "čas", "body"};
        JTable table = new JTable(data,header);
        add(table);
    }

    public void setController(ResultController controller) {
        this.controller = controller;
    }
}
