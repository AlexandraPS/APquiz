package sk.plajdickova.apquiz.ui.main;

import sk.plajdickova.apquiz.ui.main.QuizGui;

import javax.swing.*;
import java.awt.*;

public  class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<QuestionListItem>{


        public Component getListCellRendererComponent(JList list, QuestionListItem item, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(item.isSelected);
            setEnabled(list.isEnabled());

            setText(item.q.text);
            return this;
        }




}
