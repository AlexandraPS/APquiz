package sk.plajdickova.apquiz.ui.main;

import sk.plajdickova.apquiz.data.Question;

public class QuestionListItem {

    public final Question q;
    public boolean isSelected = false;

    public QuestionListItem(Question q) {
        this.q = q;
    }
}

