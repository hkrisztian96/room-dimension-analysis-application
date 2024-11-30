package gscf.task.roomdimension;

import gscf.task.roomdimension.action.RoomDimensionAction;

/**
 * The main entry point for the application.
 *
 * @author krisztian.hathazi
 */
public class App {

    /**
     * The main method that starts the application.
     *
     * @param args
     *            The command-line arguments, should contain only one argument which is the file path to be processed.
     */
    public static void main(String[] args) {
        // Instantiate the action that handles the task
        RoomDimensionAction roomDimensionAction = new RoomDimensionAction();
        // Start processing input data
        roomDimensionAction.process(args);
    }
}
