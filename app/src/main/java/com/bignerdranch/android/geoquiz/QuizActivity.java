package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;



    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true, false, false),
            new Question(R.string.question_mideast, false, false, false),
            new Question(R.string.question_africa, false, false, false),
            new Question(R.string.question_america, true, false, false),
            new Question(R.string.question_asia, true, false, false),
    };

    private int mCurrentIndex =0;
    private boolean mIsCheater;

    private void updateQuestion() {
        int question =0;

        try {
            question = mQuestionBank[mCurrentIndex].getTextResId();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e(TAG, "Index was out of bounds", ex);
        }
        mQuestionTextView.setText(question);
        // TODO if Already answered set Question text to red for incorrect -- green for correct
        /*
        if (mQuestionBank[mCurrentIndex].isAnsweredAlready()) {
            if (mQuestionBank[mCurrentIndex].isUserAnswer() == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                // set text color to GREEN
                mQuestionTextView.setTextColor(-16711936);
            } else {
                //set text color to RED -65536
                mQuestionTextView.setTextColor(-65536);
            }
        }
        */

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResID =0;

        if (mQuestionBank[mCurrentIndex].isAnsweredAlready()) {
            messageResID = R.string.answered_toast;
        } else {
            if (mIsCheater) {
                messageResID = R.string.judgment_toast;
            } else {
                if (userPressedTrue == answerIsTrue) {
                    messageResID = R.string.correct_toast;
                } else {
                    messageResID = R.string.incorrect_toast;
                }
            }
            mQuestionBank[mCurrentIndex].setUserAnswer(userPressedTrue);
            mQuestionBank[mCurrentIndex].setAnsweredAlready(true);
        }

        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestionTextView = (TextView)  findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkAnswer(true);
             }
         });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkAnswer(false);
             }
         });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pad mCurrentIndex by the length of the question array to avoid negative integer
                mCurrentIndex = (mQuestionBank.length + mCurrentIndex - 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });



        updateQuestion();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart(Bundle) called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume(Bundle) called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause(Bundle) called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        // save answer array
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(Bundle) called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy(Bundle) called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
