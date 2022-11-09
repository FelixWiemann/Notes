package com.nepumuk.notizen.tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.nepumuk.notizen.core.utils.SimpleTextWatcher;
import com.nepumuk.notizen.core.views.fragments.EditNoteViewModel;
import com.nepumuk.notizen.tasks.objects.BaseTask;

/**
 * <p>
 *     Dialog fragment handling the content of a single task.<br></br>
 *     From here the user can change the content of the tasks title and message
 * </p> <p>
 *     provide a {@link BaseTask} in the view model of type {@link EditNoteViewModel} <br></br>with the {@link androidx.lifecycle.ViewModelStoreOwner} at {@link NavController#getCurrentBackStackEntry()} before starting this fragment via Navigation
 *  e.g.
 *  <pre>{@code  // get nav controller
 *  NavController controller = Navigation.findNavController(...);
 *  // get view model with current back stack entry as store owner
 *  EditNoteViewModel<BaseTask> model = new ViewModelProvider(controller.getCurrentBackStackEntry()).get(EditNoteViewModel.class);
 *  // set the task to edit
 *  model.setNote(<BaseTask>);
 *  // navigate to create task dialog fragment
 *  controller.navigate({id of CreateTaskDialogFragment});
 *  }</pre>
 *  </p>
 */
public class CreateTaskDialogFragment extends DialogFragment {

    EditNoteViewModel<BaseTask> taskViewModel;
    EditText textTaskTitle;
    EditText textTaskMessage;

    private boolean isInitialSetup = true;
    private boolean isUpdate = false;
    private boolean isTyping = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.create_task_fragment,null);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.action_save, (dialog, which) -> {
                    taskViewModel.getSaveState().save=true;
                    taskViewModel.update();
                })
                .setNegativeButton(R.string.action_cancel,(dialog, which) -> {
                    taskViewModel.getSaveState().save=false;
                    taskViewModel.update();
                })
                .create();
        setCancelable(false);
        textTaskTitle = view.findViewById(R.id.task_title);
        textTaskTitle.addTextChangedListener(taskTitleWatcher);
        textTaskMessage = view.findViewById(R.id.task_message);
        textTaskMessage.addTextChangedListener(taskTextWatcher);
        if (taskViewModel==null){
            taskViewModel = new ViewModelProvider(requireActivity()).get(EditBaseTaskViewModel.class);
            taskViewModel.observeForever(task -> {
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
        }
        return alertDialog;
    }

    private final TextWatcher taskTitleWatcher = new SimpleTextWatcher(s -> {
            if (isInitialSetup || isUpdate) {
                isInitialSetup = false;
                return;
            }
            isTyping = true;
            taskViewModel.getSaveState().data.setTitle(s.toString());
            taskViewModel.update();
        }
    );

    private final TextWatcher taskTextWatcher = new SimpleTextWatcher(s -> {
            if (isInitialSetup || isUpdate) {
                isInitialSetup = false;
                return;
            }
            isTyping = true;
            taskViewModel.getSaveState().data.setText(s.toString());
            taskViewModel.update();
        }
    );
}
