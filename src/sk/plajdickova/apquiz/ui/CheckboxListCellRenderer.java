package sk.plajdickova.apquiz.ui;

import sk.plajdickova.apquiz.data.Question;

import javax.swing.*;
import java.awt.*;

public abstract class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<QuizGui.QuestionListItem>{

        public CheckboxListCellRenderer() {

        }

        public Component getListCellRendererComponent(JList list, QuizGui.QuestionListItem item, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(isSelected);
            setEnabled(list.isEnabled());

            setText(item.q.text);
            onQuestionSelected(item);
            return this;
        }
        public abstract void onQuestionSelected(QuizGui.QuestionListItem item);



}
