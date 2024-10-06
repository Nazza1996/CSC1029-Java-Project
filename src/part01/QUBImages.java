package part01;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The QUBImages program is an application to manage a collection of images
 * using
 * addImage, search and displayAll methods
 * 
 * @author Nathan Watkins
 */
public class QUBImages {

    private static Scanner scanner = new Scanner(System.in);
    private static ImageManager manager = new ImageManager();

    /**
     * This is the main method which uses the addImage, search and displayAll
     * methods in a menu based format
     * 
     * @param args Unused.
     */
    public static void main(String[] args) {
        printQUBImages();

        // Create an array of menu options
        String[] options = { "Add Image", "Search", "Display All", "Exit" };

        // Load inital images
        initialiseImages();

        // Create a new menu instance
        Menu menu = new Menu("Image Menu", options);

        boolean finished = false;
        do {
            try {
                int option = menu.getUserChoice();

                switch (option) {
                    case 1:
                        menu.clear();
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
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid option\n");
                menu.clear();
            }
        } while (!finished);
        scanner.close();
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
     * Adds an ImageRecord to the ImageManager using user inputted data
     */
    public static void addImage() {
        // Ask user for information on the new image
        optionMessage("Add new image");

        System.out.print("Title: ");
        String title = scanner.nextLine();
        title = title.trim();

        System.out.print("Description: ");
        String description = scanner.nextLine();
        description = description.trim();

        System.out.print("Date Taken (YYYY-MM-DD): ");
        String dateTaken = scanner.nextLine();
        dateTaken = dateTaken.trim();

        System.out.print("Thumbnail: ");
        String thumbnail = scanner.nextLine();
        thumbnail = thumbnail.trim();

        ImageType genre = genrePicker();
        System.out.println();

        ImageRecord record = new ImageRecord(title, description, genre, dateTaken, thumbnail);

        manager.addImage(record);

    }

    /**
     * Provides the user with options to search the ImageManager and displays the
     * results. If no results are found then a suitable message is displayed
     */
    public static void search() {
        String[] searchOptions = { "Id", "Title", "Description", "Genre", "Date", "Cancel" };
        Menu searchMenu = new Menu("Search Menu", searchOptions);

        System.out.println();

        boolean retry = false;

        do {
            try {
                retry = false;
                int option = searchMenu.getUserChoice();

                switch (option) {
                    case 1:
                        System.out.print("\nPlease enter an id: ");
                        int id = 0;
                        try {
                            id = scanner.nextInt();
                        } catch (Exception e) {

                        }
                        scanner.nextLine();

                        if (manager.searchId(id) == null) {
                            System.out.println("\nSearch returned no results.\n");
                        } else {
                            ImageRecord image = manager.searchId(id);
                            System.out.println("\n" +
                                    "ID:           " + image.getId() + "\n" +
                                    "Title:        " + image.getTitle() + "\n" +
                                    "Description:  " + image.getDescription() + "\n" +
                                    "Genre:        " + image.getGenre() + "\n" +
                                    "Date Taken:   " + image.getDateTaken() + "\n" +
                                    "Thumbnail:    " + image.getThumbnail() + "\n");
                        }
                        break;

                    case 2:
                        System.out.print("\nPlease enter a title: ");
                        String title = scanner.nextLine();
                        if (manager.searchTitle(title) == null) {
                            System.out.println("\nSearch returned no results.\n");
                        } else {
                            viewAlbum(manager.searchTitle(title));
                        }
                        break;

                    case 3:
                        System.out.print("\nPlease enter a description: ");
                        String desc = scanner.nextLine();
                        if (manager.searchDescription(desc) == null) {
                            System.out.println("\nSearch returned no results.\n");
                        } else {
                            viewAlbum(manager.searchTitle(desc));
                        }
                        break;

                    case 4:
                        ImageType genre = genrePicker();

                        if (manager.searchGenre(genre) == null) {
                            System.out.println("\nSearch returned no results.\n");
                        } else {
                            viewAlbum(manager.searchGenre(genre));
                        }

                        break;

                    case 5:
                        System.out.print("\nPlease enter a start date: ");
                        String date1 = scanner.nextLine();
                        date1 = checkDate(date1);

                        System.out.print("\nPlease enter an end date: ");
                        String date2 = scanner.nextLine();
                        date2 = checkDate(date2);

                        LocalDate start = stringToLocalDate(date1);
                        LocalDate end = stringToLocalDate(date2);

                        if (manager.searchDates(start, end) == null) {
                            System.out.println("\nSearch returned no results.\n");
                        } else {
                            viewAlbum(manager.searchDates(start, end));
                        }
                        break;

                    case 6:
                        break;
                    default:
                        System.out.println("Invalid option, please try again.\n");
                        retry = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid option, please try again.\n");
                retry = true;
                searchMenu.clear();
            }
        } while (retry);

    }

    /**
     * Displays images from the provided image album and allows navigation through
     * them.
     *
     * @param album The image album to view.
     */
    public static void viewAlbum(ImageAlbum album) {
        boolean finished = false;

        // Display the first image from the album
        ImageRecord firstImage = album.getFirst();
        System.out.print("\n" + firstImage.printImageRecord());
        int choice = -1;

        // If there are more than one images in the album, allow navigation
        if (album.images.size() != 1) {
            while (!finished) {
                try {
                    // Prompt for navigation choice
                    System.out.print("<-- 1 for previous image      0 to exit      2 for next image --> : ");
                    choice = scanner.nextInt();
                    System.out.println();
                    switch (choice) {
                        case 1:
                            // Display previous image if available
                            ImageRecord previousImage = album.getPrevious();
                            if (previousImage != null) {
                                System.out.print(previousImage.printImageRecord());
                            } else {
                                System.out.println("This is the first image.\n");
                            }
                            break;
                        case 2:
                            // Display next image if available
                            ImageRecord nextImage = album.getNext();
                            if (nextImage != null) {
                                System.out.print(nextImage.printImageRecord());
                            } else {
                                System.out.println("This is the last image.\n");
                            }
                            break;
                        case 0:
                            // Exit the loop
                            finished = true;
                            break;
                        default:
                            System.out.println("Invalid choice: " + choice);
                            break;
                    }
                } catch (InputMismatchException e) {
                    // Handle invalid input
                    System.out.println("\nInvalid option, please try again.\n");
                    scanner.nextLine();
                }
            }
            scanner.nextLine(); // Consume newline character
        }
    }

    /**
     * Prints to the console all images contained within the ImageManager
     */
    public static void displayAll() {
        optionMessage("Displaying all images");
        ImageAlbum album = manager.getAllImages();
        System.out.println(album);
    }

    /**
     * Asks the user to pick a genre from a pre-defined list of options and
     * returns the corresponding ImageType object
     * 
     * @return The chosen ImageType
     */
    public static ImageType genrePicker() {
        genreOptions(true);
        System.out.print("Choose a genre: ");
        int choice = 0;

        boolean repeat = false;
        do {
            try {
                repeat = false;
                choice = scanner.nextInt();
                scanner.nextLine();
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
                        System.out.print("Please enter a valid option: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        repeat = true;
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Please enter a valid option: ");
                repeat = true;
            }
        } while (repeat);

        return ImageType.OTHER;
    }

    /**
     * Prints to the console all of the available genres to choose from
     * 
     * @param numbered Determines wether list outputted list should be numbered or
     *                 not
     */
    public static void genreOptions(boolean numbered) {
        if (numbered) {
            System.out.println("\n   1. Astronomy\n" +
                    "   2. Architecture\n" +
                    "   3. Sport\n" +
                    "   4. Landscape\n" +
                    "   5. Portrait\n" +
                    "   6. Nature\n" +
                    "   7. Aerial\n" +
                    "   8. Food\n" +
                    "   9. Other");
        } else {
            System.out.println("\n   Astronomy\n" +
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
     * Prints a formatted section header to the console
     * 
     * @param str The title of the section header
     */
    public static void optionMessage(String str) {
        System.out.println("\n" + str);
        for (int i = 0; i < str.length(); i++) {
            System.out.print("~");
        }
        System.out.println();
    }

    /**
     * Checks the user inputted date against pre-defined parameters
     * to ensure it has been entered correctly
     * 
     * @param date The date that is being checked
     * @return The string in date format
     */
    public static String checkDate(String date) {
        String validDate = date;
        if (!(date != null && date.length() == 10 && date.charAt(4) == '-' && date.charAt(7) == '-')) {
            validDate = null;
            System.out.print("Please enter a valid date (YYYY-MM-DD): ");
            String input = scanner.nextLine();
            validDate = checkDate(input);
        }
        return validDate;
    }

    /**
     * Converts the given String to a LocalDate object with validation
     * 
     * @param str String which represents a date
     * @return The string paramater as a LocalDate
     */
    public static LocalDate stringToLocalDate(String str) {
        LocalDate date = LocalDate.now();

        try {
            date = LocalDate.parse(str);
        } catch (Exception e) {
            System.out.print("\n" + str + " is an invalid date.\nPlease enter a valid date (YYYY-MM-DD): ");
            String input = scanner.nextLine();
            date = stringToLocalDate(input);
        }
        return date;
    }

    /**
     * Prints QUB Images in ASCII art
     */
    public static void printQUBImages() {
        System.out.println("   ____  _    _ ____    _____                                 \n" +
                "  / __ \\| |  | |  _ \\  |_   _|                                \n" +
                " | |  | | |  | | |_) |   | |  _ __ ___   __ _  __ _  ___  ___ \n" +
                " | |  | | |  | |  _ <    | | | '_ ` _ \\ / _` |/ _` |/ _ \\/ __|\n" +
                " | |__| | |__| | |_) |  _| |_| | | | | | (_| | (_| |  __/\\__ \\\n" +
                "  \\___\\_\\\\____/|____/  |_____|_| |_| |_|\\__,_|\\__, |\\___||___/\n" +
                "                                               __/ |          \n" +
                "                                              |___/         ");
    }

}