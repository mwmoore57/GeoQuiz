package com.bignerdranch.android.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnsweredAlready;
    private boolean mUserAnswer;

    public Question(int textResId, boolean answerTrue, boolean answeredAlready, boolean userAnswer) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnsweredAlready = answeredAlready;
        mUserAnswer = userAnswer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnsweredAlready() {
        return mAnsweredAlready;
    }

    public void setAnsweredAlready(boolean answeredAlready) {
        mAnsweredAlready = answeredAlready;
    }

    public boolean isUserAnswer() {
        return mUserAnswer;
    }

    public void setUserAnswer(boolean userAnswer) {
        mUserAnswer = userAnswer;
    }
}
