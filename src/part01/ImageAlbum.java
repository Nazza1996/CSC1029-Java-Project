package part01;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ImageAlbum class represents a collection of ImageRecord objects.
 * It provides methods to navigate through the images, sort them by date,
 * and generate a string representation of the album.
 */
public class ImageAlbum {

    private int currentImage = -1; // Set current image to -1 to see if an image has already been accessed
    protected ArrayList<ImageRecord> images;

    /**
     * Constructs an ImageAlbum object with the specified list of images.
     * 
     * @param images The list of ImageRecord objects to be included in the album.
     */
    public ImageAlbum(ArrayList<ImageRecord> images) {
        this.images = images;
    }

    /**
     * Retrieves the first image in the album.
     * 
     * @return The first ImageRecord object in the album, or null if the album is
     *         empty.
     */
    public ImageRecord getFirst() {
        currentImage = 0;
        if (images.size() > 0) {
            return images.get(currentImage);
        } else {
            return null;
        }
    }

    /**
     * Retrieves the next image in the album.
     *
     * @return The next ImageRecord object in the album, or null if there are no
     *         more images or the current image is invalid.
     */
    public ImageRecord getNext() {
        if (images.size() == 0 || currentImage >= (images.size() - 1) || images.get(currentImage + 1) == null) {
            return null;
        } else {
            currentImage++;
            return images.get(currentImage);
        }
    }

    /**
     * Retrieves the previous image in the album.
     *
     * @return The previous ImageRecord object in the album, or null if there are no
     *         more images or the current image is invalid.
     */
    public ImageRecord getPrevious() {
        if (images.size() == 0  || currentImage <= 0 || images.get(currentImage - 1) == null) {
            return null;
        } else {
            currentImage--;
            return images.get(currentImage);
        }
    }

    /**
     * Generates a string representation of the album, sorted by date.
     *
     * @return A string containing details of each image in the album, sorted by
     *         date.
     */
    public String toString() {
        ImageAlbum sortedAlbum = dateSort();
        String str = "\n";
        for (ImageRecord image : sortedAlbum.images) {
            str += "ID:           " + image.getId() + "\n" +
                    "Title:        " + image.getTitle() + "\n" +
                    "Description:  " + image.getDescription() + "\n" +
                    "Genre:        " + image.getGenre() + "\n" +
                    "Date Taken:   " + image.getDateTaken() + "\n" +
                    "Thumbnail:    " + image.getThumbnail() + "\n\n";
        }
        return str;
    }

    /**
     * Sorts the images in the album by date taken.
     *
     * @return An ImageAlbum object containing the sorted images.
     */
    public ImageAlbum dateSort() {
        ArrayList<ImageRecord> result = new ArrayList<ImageRecord>();

        for (ImageRecord image : images) {
            int i;
            for (i = 0; i < result.size(); i++) {
                LocalDate date1 = image.getDateTaken();
                LocalDate date2 = result.get(i).getDateTaken();
                if (date1.isBefore(date2)) {
                    break;
                }
            }
            result.add(i, image);
        }
        ImageAlbum albumResult = new ImageAlbum(result);
        return albumResult;
    }
}
