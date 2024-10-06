package part02;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.ImageIcon;

import console.Console;

/**
 * The QUBImages program is an application to manage a collection of images
 * using
 * addImage, search and displayAll methods
 * 
 * @author Nathan Watkins
 */
public class QUBMediaImages {

    private static ImageManager manager = new ImageManager();
    private static Console con = new Console(true);
    private static Font defaultFont = new Font("OCR A Extended", Font.PLAIN, 20);
    private static Font subTitleFont = new Font("OCR A Extended", Font.ITALIC, 30);

    /**
     * The main method utilizes the addImage, search, and displayAll
     * methods in a menu-based format.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Create an array of menu options
        String[] options = { "Add Image", "Search", "Display All", "Exit" };

        // Create menu
        ConsoleMenu menu = new ConsoleMenu(options, con);

        // Load inital images
        initialiseImages();

        // Load console
        initialiseConsole();

        boolean finished = false;
        boolean error = false;
        int option = 0;
        do {
            try {
                printTitle();
                if (error) {
                    error("Invalid option.\n\n");
                    error = false;
                }
                option = menu.getUserChoice();

                switch (option) {
                    case 1:
                        addImage();
                        break;
                    case 2:
                        search();
                        break;
                    case 3:
                        displayAll();
                        break;
                    case 4:
                        finished = true;
                        con.dispose();
                        break;
                    default:
                        error = true;
                        break;
                }
            } catch (Exception e) {
                error = true;
                con.clear();
            }
        } while (!finished);
    }

    /**
     * Initializes the external console window, setting the size, title, font,
     * color, and visibility.
     */
    private static void initialiseConsole() {
        con.setSize(1600, 900);
        con.setTitle("QUB Media Images");
        con.setFont(defaultFont);
        con.setBgColour(Color.BLACK);
        con.setColour(Color.GREEN);
        con.setVisible(true);
    }

    /**
     * Initializes a range of different images to the ImageManager.
     */
    private static void initialiseImages() {
        ImageRecord image1 = new ImageRecord("Andromeda Galaxy", "Image of the Andromeda Galaxy.", ImageType.ASTRONOMY,
                "2024-01-21", "Andromeda.png");
        manager.addImage(image1);
        ImageRecord image2 = new ImageRecord("Lanyon QUB", "An image of the QUB Lanyon building",
                ImageType.ARCHITECTURE, "2024-01-30", "LanyonQUB.png");
        manager.addImage(image2);
        ImageRecord image3 = new ImageRecord("Kermit Plays Golf", "An image of Kermit the frog playing golf.",
                ImageType.SPORT, "2024-02-12", "KermitGolf.png");
        manager.addImage(image3);
        ImageRecord image4 = new ImageRecord("Mourne Mountains", "A panoramic view of the Mourne Mountains.",
                ImageType.LANDSCAPE, "2024-03-23", "Mournes.png");
        manager.addImage(image4);
        ImageRecord image5 = new ImageRecord("Homer Simpson", "A portrait of Homer Simpson.", ImageType.PORTRAIT,
                "2024-04-02", "Homer.png");
        manager.addImage(image5);
        ImageRecord image6 = new ImageRecord("Red Kite", "A Red Kite bird of prey in flight.", ImageType.NATURE,
                "2024-05-06", "RedKite.png");
        manager.addImage(image6);
        ImageRecord image7 = new ImageRecord("Central Park", "An overhead view of Central Park, New York, USA.",
                ImageType.AERIAL, "2024-07-25", "CentralPark.png");
        manager.addImage(image7);
        ImageRecord image8 = new ImageRecord("Apples", "A bunch of apples.", ImageType.FOOD, "2024-08-17",
                "Apples.png");
        manager.addImage(image8);
        ImageRecord image9 = new ImageRecord("Programming Meme", "A ChatGPT programming meme.", ImageType.OTHER,
                "2024-10-14", "ChatGPT.png");
        manager.addImage(image9);
    }

    /**
     * Prints an error statement to the console in red text.
     * 
     * @param str The error message to be displayed.
     */
    private static void error(String str) {
        con.setColour(Color.RED);
        con.print(str);
        con.setColour(Color.GREEN);
    }

    /**
     * Prints a string to the console in a larger, bold font as a title
     */
    private static void printTitle() {
        con.setFont(new Font("OCR A Extended", Font.BOLD, 40));
        con.println("QUB Media Images");
        con.setFont(defaultFont);
        con.println();
    }

    /**
     * Prints a string to the console in a medium-sized font with an underline.
     * 
     * @param subTitle The subtitle text to be displayed.
     */
    private static void printSubTitle(String subTitle) {
        con.setFont(subTitleFont);
        con.print(subTitle + "\n");
        for (int i = 0; i < subTitle.length(); i++) {
            con.print("~");
        }
        con.setFont(defaultFont);
        con.println("\n");
    }

    /**
     * Adds an ImageRecord to the ImageManager using user-inputted data
     */
    private static void addImage() {
        printTitle();
        printSubTitle("Add new image");

        con.print("Title: ");
        String title = con.readLn();
        title = title.trim();

        con.print("Description: ");
        String description = con.readLn();
        description = description.trim();

        con.print("Date Taken (YYYY-MM-DD): ");
        String dateTaken = con.readLn();
        dateTaken = dateTaken.trim();

        con.print("Thumbnail: ");
        String thumbnail = con.readLn();
        thumbnail = thumbnail.trim();

        ImageType genre = genrePicker();
        con.println("\n");

        ImageRecord record = new ImageRecord(title, description, genre, dateTaken, thumbnail);
        String errors = record.getErrors();

        if (errors != null || getImage(record) == null) {
            if (errors != null) {
                error(errors);
            }
            if (!(record.getThumbnail().equals("Unknown")) && getImage(record) == null) {
                error("Thumbnail " + record.getThumbnail() + " does not exist.\n");
            }
            con.println("\nImage has not been added.");
        } else {
            manager.addImage(record);
            con.println("\nImage added successfully.");
        }
        con.println();
        pressEnterToContinue();
        con.clear();
    }

    /**
     * Provides the user with options to search the ImageManager and displays the
     * results. If no results are found then a suitable message is displayed.
     */
    private static void search() {
        String[] searchOptions = { "Id", "Title", "Description", "Genre", "Date", "Cancel" };
        ConsoleMenu searchMenu = new ConsoleMenu(searchOptions, con);

        boolean finished = false;
        boolean error = false;
        int option = 0;

        do {
            try {
                printTitle();
                if (error) {
                    error("Invalid option.\n\n");
                    error = false;
                }
                error = false;
                option = searchMenu.getUserChoice();
                printTitle();

                switch (option) {
                    case 1:
                        con.print("Please enter an id: ");
                        String input = con.readLn();
                        int id = 0;

                        try {
                            id = Integer.parseInt(input);
                        } catch (Exception e) {

                        }

                        if (manager.searchId(id) == null) {
                            con.println("Search returned no results.\n");
                            pressEnterToContinue();
                        } else {
                            ImageRecord imageRecord = manager.searchId(id);
                            con.println();
                            printImage(imageRecord);
                            con.println(imageRecord.printImageRecord());
                        }
                        pressEnterToContinue();
                        break;

                    case 2:
                        con.print("Please enter a title: ");
                        String title = con.readLn();

                        if (manager.searchTitle(title) == null) {
                            con.println("\nSearch returned no results.\n");
                            pressEnterToContinue();
                        } else {
                            viewAlbum(manager.searchTitle(title));
                        }
                        break;

                    case 3:
                        con.print("Please enter a description: ");
                        String desc = con.readLn();

                        if (manager.searchDescription(desc) == null) {
                            con.println("\nSearch returned no results.\n");
                            pressEnterToContinue();
                        } else {
                            viewAlbum(manager.searchDescription(desc));
                        }
                        break;

                    case 4:
                        ImageType genre = genrePicker();

                        if (manager.searchGenre(genre) == null) {
                            con.println("\nSearch returned no results.\n");
                            pressEnterToContinue();
                        } else {
                            viewAlbum(manager.searchGenre(genre));
                        }
                        break;

                    case 5:
                        con.print("Please enter a start date: ");
                        String date1 = con.readLn();
                        date1 = checkDate(date1);

                        con.print("\nPlease enter an end date: ");
                        String date2 = con.readLn();
                        date2 = checkDate(date2);

                        LocalDate start = stringToLocalDate(date1);
                        LocalDate end = stringToLocalDate(date2);

                        if (manager.searchDates(start, end) == null) {
                            con.println("\nSearch returned no results.\n");
                            pressEnterToContinue();
                        } else {
                            manager.searchDates(start, end);
                        }
                        break;

                    case 6:
                        finished = true;
                        break;
                    default:
                        error = true;
                        break;
                }
            } catch (Exception e) {
                error = true;
                con.clear();
            }
            con.clear();
        } while (!finished);

        // con.print("Press ENTER to continue...");
        // con.readLn();
        con.clear();
    }

    /**
     * Displays images from the provided image album and allows navigation through
     * them.
     *
     * @param album The image album to view.
     */
    public static void viewAlbum(ImageAlbum album) {
        con.clear();
        printTitle();

        boolean finished = false;
        boolean retry = false;
        ImageRecord currentImage = null;

        // Display the first image from the album
        ImageRecord firstImage = album.getFirst();
        printImage(firstImage);
        con.print(firstImage.printImageRecord() + "\n");
        String choice = "";
        currentImage = firstImage;

        // If there are more than one images in the album, allow navigation
        if (album.images.size() != 1) {
            while (!finished) {
                do {
                    retry = false;
                    // Prompt for navigation choice
                    con.print("\n<-- 1 for previous image      0 to exit      2 for next image --> : ");
                    choice = con.readLn();
                    switch (choice) {
                        case "1":
                            // Display previous image if available
                            ImageRecord previousImage = album.getPrevious();
                            if (previousImage != null) {
                                con.clear();
                                printTitle();
                                printImage(previousImage);
                                con.print(previousImage.printImageRecord());
                                currentImage = previousImage;
                            } else {
                                errorPrintCurrentImage(currentImage, "This is the first image.\n");
                                retry = true;
                            }
                            break;
                        case "2":
                            // Display next image if available
                            ImageRecord nextImage = album.getNext();
                            if (nextImage != null) {
                                con.clear();
                                printTitle();
                                printImage(nextImage);
                                con.print(nextImage.printImageRecord());
                                currentImage = nextImage;
                            } else {
                                errorPrintCurrentImage(currentImage, "This is the last image.\n");
                                retry = true;
                            }
                            break;
                        case "0":
                            // Exit the loop
                            finished = true;
                            break;
                        default:
                            errorPrintCurrentImage(currentImage, "Invalid choice: " + choice + "\n");
                            retry = true;
                            break;
                    }
                } while (retry);
            }
        } else {
            pressEnterToContinue();
        }
    }

    /**
     * Displays an error message and the current image
     * 
     * @param currentImage
     * @param str
     */
    private static void errorPrintCurrentImage(ImageRecord currentImage, String str) {
        con.clear();
        printTitle();
        error(str + "\n");
        printImage(currentImage);
        con.print(currentImage.printImageRecord());
    }

    /**
     * Displays all the images in the imageManager
     */
    private static void displayAll() {
        printTitle();
        con.println();

        ImageAlbum album = manager.getAllImages().dateSort();
        viewAlbum(album);

        con.clear();
    }

    /**
     * Asks the user to pick a genre from a pre-defined list of options and
     * returns the corresponding ImageType object.
     * 
     * @return The chosen ImageType.
     */
    private static ImageType genrePicker() {
        genreOptions(true);
        con.print("Choose a genre: ");
        String input;

        boolean repeat = false;
        do {
            try {
                repeat = false;
                input = con.readLn();
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        return ImageType.ASTRONOMY;
                    case 2:
                        return ImageType.ARCHITECTURE;
                    case 3:
                        return ImageType.SPORT;
                    case 4:
                        return ImageType.LANDSCAPE;
                    case 5:
                        return ImageType.PORTRAIT;
                    case 6:
                        return ImageType.NATURE;
                    case 7:
                        return ImageType.AERIAL;
                    case 8:
                        return ImageType.FOOD;
                    case 9:
                        return ImageType.OTHER;
                    default:
                        con.print("Please enter a valid option: ");
                        input = con.readLn();
                        choice = Integer.parseInt(input);
                        repeat = true;
                }
            } catch (Exception e) {
                con.print("Please enter a valid option: ");
                repeat = true;
            }
        } while (repeat);

        return ImageType.OTHER;
    }

    /**
     * Prints to the console all of the available genres to choose from.
     * 
     * @param numbered Determines whether the list should be numbered or not.
     */
    private static void genreOptions(boolean numbered) {
        if (numbered) {
            con.println("\n   1. Astronomy\n" +
                    "   2. Architecture\n" +
                    "   3. Sport\n" +
                    "   4. Landscape\n" +
                    "   5. Portrait\n" +
                    "   6. Nature\n" +
                    "   7. Aerial\n" +
                    "   8. Food\n" +
                    "   9. Other");
        } else {
            con.println("\n   Astronomy\n" +
                    "   Architecture\n" +
                    "   Sport\n" +
                    "   Landscape\n" +
                    "   Portrait\n" +
                    "   Nature\n" +
                    "   Aerial\n" +
                    "   Food\n" +
                    "   Other");
        }
    }

    /**
     * Checks the validity of the date format and prompts the user for a valid date
     * input if necessary.
     * 
     * @param date The date string to be validated.
     * @return A valid date string in the format "YYYY-MM-DD".
     */
    private static String checkDate(String date) {
        String validDate = date;
        if (!(date != null && date.length() == 10 && date.charAt(4) == '-' && date.charAt(7) == '-')) {
            validDate = null;
            con.print("Please enter a valid date (YYYY-MM-DD): ");
            String input = con.readLn();
            validDate = checkDate(input);
        }
        return validDate;
    }

    /**
     * Converts a string representing a date to a LocalDate object. Prompts the
     * user for a valid date input if necessary.
     * 
     * @param str The string representing the date in "YYYY-MM-DD" format.
     * @return A LocalDate object representing the parsed date.
     */
    private static LocalDate stringToLocalDate(String str) {
        LocalDate date = LocalDate.now();

        try {
            date = LocalDate.parse(str);
        } catch (Exception e) {
            con.print("\n" + str + " is an invalid date.\nPlease enter a valid date (YYYY-MM-DD): ");
            String input = con.readLn();
            date = stringToLocalDate(input);
        }
        return date;
    }

    /**
     * Prints an image record to the console.
     * 
     * @param record The ImageRecord object to be printed.
     */
    private static void printImage(ImageRecord record) {
        ImageIcon img = getImage(record);
        con.print(img);
    }

    /**
     * Retrieves an an ImageIcon object from an ImageRecord
     * 
     * @param record The ImageRecord object in which to retrieve the ImageIcon from
     * @return The ImageIcon found ot null if the ImageIcon does not exist
     */
    private static ImageIcon getImage(ImageRecord record) {
        String userdir = System.getProperty("user.dir");
        String imageFolder = userdir + "/Images/";
        String path = imageFolder + record.getThumbnail();

        ImageIcon img = new ImageIcon(path);

        if (img.getIconHeight() == -1) {
            return null;
        } else {
            return img;
        }
    }

    /**
     * Displays a prompt for the user to press the ENTER key to continue.
     */
    private static void pressEnterToContinue() {
        con.print("----> Press ENTER to continue");
        con.readLn();
    }

}
