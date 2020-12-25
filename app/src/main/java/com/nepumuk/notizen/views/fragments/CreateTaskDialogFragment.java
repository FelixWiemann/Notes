package com.nepumuk.notizen.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.objects.tasks.BaseTask;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;

public class CreateTaskDialogFragment extends DialogFragment {

    EditNoteViewModel<BaseTask> taskViewModel;
    EditText textTaskTitle;
    EditText textTaskMessage;

    private boolean isInitialSetup = true;
    private boolean isUpdate = false;
    private boolean isTyping = false;

    public CreateTaskDialogFragment(ViewModelStoreOwner owner){
        super();
        taskViewModel = new ViewModelProvider(owner).get(EditNoteViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_task_fragment,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textTaskTitle = view.findViewById(R.id.task_title);
        taskViewModel.observe(this, task -> {
            if (task ==null) return;
            if (isTyping) {
                isTyping = false;
                return;
            }
            isUpdate = true;
            textTaskMessage.setText(task.data.getText());
            textTaskTitle.setText(task.data.getTitle());
            isUpdate = false;
        });

        textTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isInitialSetup || isUpdate) {
                    isInitialSetup = false;
                    return;
                }
                isTyping = true;
                taskViewModel.getSaveState().data.setTitle(s.toString());
                taskViewModel.getSaveState().save=true;
                taskViewModel.update();
            }
        });
        Window window = getDialog().getWindow();
        if (window!=null) {
            window.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE);
        }
        textTaskMessage = view.findViewById(R.id.task_message);

        textTaskMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isInitialSetup || isUpdate) {
                    isInitialSetup = false;
                    return;
                }
                isTyping = true;
                taskViewModel.getSaveState().data.setText(s.toString());
                taskViewModel.getSaveState().save=true;
                taskViewModel.update();
            }
        });
    }
}
