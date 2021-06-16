package sk.plajdickova.apquiz.view.main;

import sk.plajdickova.apquiz.data.entity.Question;

public class QuestionListItem {

    public final Question q;
    public boolean isSelected = false;

    public QuestionListItem(Question q) {
        this.q = q;
    }
}

