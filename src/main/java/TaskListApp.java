// This class will be replaced by the project's main file.
// Temporary class for use case 1 testing purposes

import data_access.InMemoryDataAccessObject;
import interface_adapters.ViewModel;
import interface_adapters.use_case1_manage_tasks.TaskController;
import interface_adapters.use_case1_manage_tasks.TaskListState;
import interface_adapters.use_case1_manage_tasks.TaskPresenter;
import use_case.TaskDataAccessInterface;
import use_case.use_case1_manage_tasks.ManageTasksInteractor;
import view.TaskListView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TaskListApp {
    public static void main(String[] args) {
        // Swing must be built on its own thread (the Event Dispatch Thread).
        // invokeLater runs the code below on that thread.
        SwingUtilities.invokeLater(() -> {

            // 1. Storage (outermost layer)
            TaskDataAccessInterface storage = new InMemoryDataAccessObject();

            // 2. View model: holds the screen's state, starting empty
            ViewModel<TaskListState> viewModel = new ViewModel<>(TaskListState.empty());

            // 3. Presenter -> interactor -> controller, inside out:
            //    each one needs the previous one in its constructor.
            TaskPresenter presenter = new TaskPresenter(viewModel);
            ManageTasksInteractor interactor = new ManageTasksInteractor(presenter, storage);
            TaskController controller = new TaskController(interactor);

            // 4. The view listens to the view model and sends clicks to the controller.
            TaskListView view = new TaskListView(viewModel, controller);

            // 5. Put the view in a window and show it.
            JFrame frame = new JFrame("To-do list");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // closing the window ends the program
            frame.add(view);
            frame.setSize(900, 450);
            frame.setLocationRelativeTo(null);   // centre on the screen
            frame.setVisible(true);
        });
    }
}
