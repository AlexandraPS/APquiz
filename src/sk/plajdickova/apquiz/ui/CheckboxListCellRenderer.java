package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.*;

public  class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<QuizGui.QuestionListItem>{


        public Component getListCellRendererComponent(JList list, QuizGui.QuestionListItem item, int index,
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
