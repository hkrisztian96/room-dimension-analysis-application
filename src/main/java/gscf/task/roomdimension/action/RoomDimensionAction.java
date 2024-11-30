package gscf.task.roomdimension.action;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import gscf.task.roomdimension.action.process.InputFileProcessor;
import gscf.task.roomdimension.converter.RoomConverter;
import gscf.task.roomdimension.dto.Result;
import gscf.task.roomdimension.exception.InvalidMethodParameterException;
import gscf.task.roomdimension.exception.SizeLimitExceededIOException;
import gscf.task.roomdimension.log.AppLogger;
import gscf.task.roomdimension.util.ParamValidatorUtil;
import gscf.task.roomdimension.util.ResultPrinterUtil;
import gscf.task.roomdimension.validation.FileSizeValidator;

/**
 * The {@code RoomDimensionAction} class is responsible for processing room dimension data from an input file. It validates the input arguments and
 * file size, and delegates the file processing to {@link InputFileProcessor}. After processing the input file, it prints relevant results using
 * {@link ResultPrinterUtil}.
 *
 * @author krisztian.hathazi
 */
public class RoomDimensionAction implements Action<String[]> {

    private static final Logger LOGGER = AppLogger.getLogger();

    private static final long INPUT_FILE_MAX_SIZE_BYTES = 5L * 1024 * 1024;

    private final FileSizeValidator fileSizeValidator;

    private final InputFileProcessor inputFileProcessor;

    /**
     * Constructs a new {@code RoomDimensionAction} instance, initializing the file size validator and input file processor. The
     * {@code InputFileProcessor} is initialized with a default {@link RoomConverter}.
     */
    public RoomDimensionAction() {
        this.fileSizeValidator = new FileSizeValidator();
        this.inputFileProcessor = new InputFileProcessor(new RoomConverter());
    }

    /**
     * Processes the input arguments, validates the file size, and processes the input file based on business requirements. After the file is
     * processed, it prints the total square footage of wallpaper needed, cubic shaped rooms, and duplicate rooms using {@link ResultPrinterUtil}.
     *
     * @param args
     *            An array of strings representing the command-line arguments. There should be only one provided argument which should be the file
     *            path of the input file containing room dimension data.
     *
     * @throws InvalidMethodParameterException
     *             If the input arguments are null or empty.
     */
    @Override
    public void process(String[] args) {
        // Public method, so parameter validation is a must
        ParamValidatorUtil.requireNonEmpty(args, "args");

        // Validate input arguments' length
        if (args.length != 1) {
            throw new InvalidMethodParameterException(
                    "Please provide exactly one argument for the application which contains the path of the desired sample.");
        }

        try {

            String filePath = args[0];

            // Validate input file size without reading the whole file into the memory
            fileSizeValidator.validate(filePath, INPUT_FILE_MAX_SIZE_BYTES);

            // Process the input file per business requirements
            Result result = inputFileProcessor.process(filePath);

            ResultPrinterUtil.printTotalSquareFeetOfWallpaperNeeded(result);
            ResultPrinterUtil.printCubicShapedRooms(result);
            ResultPrinterUtil.printDuplicatedRooms(result);
        } catch (SizeLimitExceededIOException e) {
            String message = MessageFormat.format(
                    "The processing of the sample has failed due to a providing a too large file. The maximum acceptable size is: [{0}] bytes.",
                    INPUT_FILE_MAX_SIZE_BYTES);
            LOGGER.severe(message);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The processing of the sample has failed due to an IOException.", e);
        }
    }

}
