package com.example.quizletter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private TextView letterTextView;
    private Button skyButton, rootButton, grassButton;

    private String[] letters = {"A", "B", "C", "D", "E"};
    private int currentLetterIndex = 0;

    private DatabaseHelper databaseHelper;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmentquiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        letterTextView = view.findViewById(R.id.text_question);
        skyButton = view.findViewById(R.id.sky_button);
        rootButton = view.findViewById(R.id.root_button);
        grassButton = view.findViewById(R.id.grass_button);

        skyButton.setOnClickListener(this);
        rootButton.setOnClickListener(this);
        grassButton.setOnClickListener(this);

        displayNextLetter();

        databaseHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public void onClick(View v) {
        String answer = "";
        int id = v.getId();
        if (id == R.id.sky_button) {
            answer = "Sky";
        } else if (id == R.id.root_button) {
            answer = "Root";
        } else if (id == R.id.grass_button) {
            answer = "Grass";
        }



        databaseHelper.saveAnswer(letters[currentLetterIndex], answer);

        if (currentLetterIndex < letters.length - 1) {
            currentLetterIndex++;
            displayNextLetter();
        } else {
            Toast.makeText(getActivity(), "Quiz completed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNextLetter() {
        letterTextView.setText(letters[currentLetterIndex]);
    }
}
