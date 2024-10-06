package part01;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The ImageManager class manages a collection of ImageRecord objects.
 * It provides methods for adding images, searching for images by various
 * criteria,
 * and retrieving all images in the collection.
 */
public class ImageManager {
    private ArrayList<ImageRecord> collection = new ArrayList<ImageRecord>();

    /**
     * Constructs an ImageManager object with an empty collection of images.
     */
    public ImageManager() {

    }

    /**
     * Adds an ImageRecord to the collection.
     *
     * @param record The ImageRecord to be added to the collection.
     */
    public void addImage(ImageRecord record) {
        collection.add(record);
    }

    /**
     * Searches for an image by its ID.
     *
     * @param id The ID of the image to search for.
     * @return The ImageRecord with the specified ID, or null if not found.
     */
    public ImageRecord searchId(int id) {
        int start = 0;
        int end = collection.size() - 1;
        int pivot;

        do {
            pivot = start + (end - start) / 2;
            if (id == collection.get(pivot).getId()) {
                return collection.get(pivot);
            } else if (collection.get(pivot).getId() > id) {
                end = pivot - 1;
            } else {
                start = pivot + 1;
            }
        } while (start <= end);
        return null;
    }

    /**
     * Searches for images by title.
     *
     * @param str The title to search for.
     * @return An ImageAlbum containing images with titles containing the specified
     *         string,
     *         or null if no matching images are found.
     */
    public ImageAlbum searchTitle(String str) {
        ArrayList<ImageRecord> album = new ArrayList<ImageRecord>();
        for (ImageRecord imageRecord : collection) {
            if (imageRecord.getTitle().toLowerCase().contains(str.toLowerCase())) {
                album.add(imageRecord);
            }
        }

        if (album.size() > 0) {
            ImageAlbum albumSorted = new ImageAlbum(album).dateSort();
            return albumSorted;
        } else {
            return null;
        }
    }

    /**
     * Searches for images by description.
     *
     * @param str The description to search for.
     * @return An ImageAlbum containing images with descriptions containing the
     *         specified string,
     *         or null if no matching images are found.
     */
    public ImageAlbum searchDescription(String str) {
        ArrayList<ImageRecord> album = new ArrayList<ImageRecord>();
        for (ImageRecord imageRecord : collection) {
            if (imageRecord.getDescription().toLowerCase().contains(str.toLowerCase())) {
                album.add(imageRecord);
            }
        }

        if (album.size() > 0) {
            ImageAlbum albumSorted = new ImageAlbum(album).dateSort();
            return albumSorted;
        } else {
            return null;
        }
    }

    /**
     * Searches for images by genre.
     *
     * @param type The genre to search for.
     * @return An ImageAlbum containing images with the specified genre,
     *         sorted alphabetically by title,
     *         or null if no matching images are found.
     */
    public ImageAlbum searchGenre(ImageType type) {
        ArrayList<ImageRecord> album = new ArrayList<ImageRecord>();
        for (ImageRecord imageRecord : collection) {
            if (imageRecord.getGenre().equals(type)) {
                album.add(imageRecord);
            }
        }

        if (album.size() > 0) {
            ImageAlbum albumSorted = new ImageAlbum(album).dateSort();
            return albumSorted;
        } else {
            return null;
        }
    }

    /**
     * Searches for images within a specified date range.
     *
     * @param start The start date of the range (inclusive).
     * @param end   The end date of the range (inclusive).
     * @return An ImageAlbum containing images taken within the specified date
     *         range,
     *         sorted chronologically by date taken,
     *         or null if no matching images are found.
     */
    public ImageAlbum searchDates(LocalDate start, LocalDate end) {
        ArrayList<ImageRecord> album = new ArrayList<ImageRecord>();
        for (ImageRecord imageRecord : collection) {
            if (imageRecord.getDateTaken().isAfter(start.minusDays(1))
                    && imageRecord.getDateTaken().isBefore(end.plusDays(1))) {
                album.add(imageRecord);
            }
        }

        if (album.size() > 0) {
            ImageAlbum albumSorted = new ImageAlbum(album).dateSort();
            return albumSorted;
        } else {
            return null;
        }
    }

    /**
     * Retrieves all images in the collection.
     *
     * @return An ImageAlbum containing all images in the collection.
     */
    public ImageAlbum getAllImages() {
        return new ImageAlbum(collection);
    }
}
