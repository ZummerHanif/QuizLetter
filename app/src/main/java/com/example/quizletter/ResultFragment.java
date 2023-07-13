package com.example.quizletter;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class ResultFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private TextView resultTextView;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        resultTextView = view.findViewById(R.id.result_text_view);
        displayResults();
        return view;
    }

    private void displayResults() {
        Cursor cursor = databaseHelper.getResult();

        Map<Integer, String> resultsMap = new HashMap<>();

        while (cursor.moveToNext()) {
            int questionNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUESTION_NUMBER));
            String result = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RESULT));
            resultsMap.put(questionNumber, result);
        }

        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 1; i <= resultsMap.size(); i++) {
            String result = resultsMap.get(i);
            resultBuilder.append("Question ").append(i).append(": ").append(result).append("\n");
        }

        resultTextView.setText(resultBuilder.toString());
        cursor.close();
    }
}
