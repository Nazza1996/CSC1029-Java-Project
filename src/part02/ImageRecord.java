package part02;

import java.time.LocalDate;

/**
 * The ImageRecord class represents a single image record with various
 * attributes.
 */
public class ImageRecord {

    private int id;
    private String title;
    private String description;
    private ImageType genre;
    private LocalDate dateTaken;
    private String thumbnail;
    private static int nextId = 1;
    private String errors = "";

    /**
     * Constructs an ImageRecord object with the specified attributes.
     * Automatically generates an ID for the image record.
     * Validates input attributes and sets default values if invalid.
     *
     * @param title       The title of the image.
     * @param description The description of the image.
     * @param genre       The genre of the image.
     * @param dateTaken   The date the image was taken (in the format "yyyy-MM-dd").
     * @param thumbnail   The path to the thumbnail image.
     */
    public ImageRecord(String title, String description, ImageType genre, String dateTaken, String thumbnail) {
        this.id = nextId++;

        if (setTitle(title) != true) {
            errors += "Invalid Title: " + title + "\n";
        }

        if (setDescription(description) != true) {
            errors += "Invalid Description: " + description + "\n";
        }

        this.genre = genre;

        if (setDateTaken(dateTaken) != true) {
            errors += "Invalid Date-Taken: " + dateTaken + "\n";
        }

        if (setThumbnail(thumbnail) != true) {
            errors += "Invalid Thumbnail: " + thumbnail + "\n";
        }
    }

    /**
     * Returns a string representation of all errors.
     * 
     * @return A string representation of all errors.
     */
    public String getErrors() {
        if (errors.length() > 0) {
            return errors;
        }
        return null;
    }

    /**
     * Returns a string representation of the ImageRecord object.
     *
     * @return A string representation of the ImageRecord.
     */
    public String toString() {
        String str = "Image Titled: '" + title + "' with ID: " + id + ", Description: '" + description
                + "' and Genre: '" + genre + "' Taken On: " + dateTaken + " With Thumbnail: '" + thumbnail + "'";
        return str;
    }

    /**
     * Returns a formatted string representation of the ImageRecord object.
     * 
     * @return A formatted string representation of the ImageRecord object.
     */
    public String printImageRecord() {
        String str = "\n";
        str += "ID:           " + id + "\n" +
                "Title:        " + title + "\n" +
                "Description:  " + description + "\n" +
                "Genre:        " + genre + "\n" +
                "Date Taken:   " + dateTaken + "\n";
        return str;
    }

    /**
     * 
     * @param title
     * @return True if title is valid else return False
     */
    private boolean setTitle(String title) {
        if (title != null && title.length() > 0) {
            this.title = title;
            return true;
        } else {
            this.title = "Unknown";
            return false;
        }
    }

    /**
     * 
     * @param description
     * @return True if description is valid else return False
     */
    private boolean setDescription(String description) {
        if (description != null && description.length() > 0) {
            this.description = description;
            return true;
        } else {
            this.description = "Unknown";
            return false;
        }
    }

    /**
     * 
     * @param dateTaken
     * @return True if date-taken is valid else return False
     */
    private boolean setDateTaken(String dateTaken) {
        if (dateTaken != null && dateTaken.length() == 10 && dateTaken.charAt(4) == '-' && dateTaken.charAt(7) == '-') {
            this.dateTaken = LocalDate.parse(dateTaken);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param thumbnail
     * @return True if thumbnail is valid else return False
     */
    private boolean setThumbnail(String thumbnail) {
        if (thumbnail != null && thumbnail.length() > 0 && thumbnail.contains(".")) {
            this.thumbnail = thumbnail;
            return true;
        } else {
            this.thumbnail = "Unknown";
            return false;
        }
    }

    /**
     *
     * @return The ID of the image record.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return The title of the image.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return The description of the image.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return The genre of the image.
     */
    public ImageType getGenre() {
        return genre;
    }

    /**
     *
     * @return The date the image was taken.
     */
    public LocalDate getDateTaken() {
        return dateTaken;
    }

    /**
     *
     * @return The path to the thumbnail image.
     */
    public String getThumbnail() {
        return thumbnail;
    }

}
