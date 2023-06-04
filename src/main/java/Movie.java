import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a movie with various properties.
 */
public class Movie {

    // Property fields using JavaFX property types
    private SimpleStringProperty title;
    private SimpleStringProperty director;
    private SimpleStringProperty cast;
    private SimpleIntegerProperty year;
    private SimpleStringProperty homepage;
    private SimpleDoubleProperty averageView;

    /**
     * Constructs a Movie object with the specified properties.
     *
     * @param title       The title of the movie.
     * @param director    The director(s) of the movie.
     * @param cast        The cast members of the movie.
     * @param year        The release year of the movie.
     * @param homepage    The homepage URL of the movie.
     * @param averageView The average view rating of the movie.
     */
    public Movie(String title, String director, String cast, int year, String homepage, double averageView) {
        this.title = new SimpleStringProperty(title);
        this.director = new SimpleStringProperty(director);
        this.cast = new SimpleStringProperty(cast);
        this.year = new SimpleIntegerProperty(year);
        this.homepage = new SimpleStringProperty(homepage);
        this.averageView = new SimpleDoubleProperty(averageView);
    }

    /**
     * Sets the title of the movie.
     *
     * @param value The title of the movie.
     */
    public void setTitle(String value) {
        title.set(value);
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the director(s) of the movie.
     *
     * @param value The director(s) of the movie.
     */
    public void setDirector(String value) {
        director.set(value);
    }

    /**
     * Gets the director(s) of the movie.
     *
     * @return The director(s) of the movie.
     */
    public String getDirector() {
        return director.get();
    }

    /**
     * Gets the cast members of the movie.
     *
     * @return The cast members of the movie.
     */
    public String getCast() {
        return cast.get();
    }

    /**
     * Sets the cast members of the movie.
     *
     * @param value The cast members of the movie.
     */
    public void setCast(String value) {
        cast.set(value);
    }

    /**
     * Gets the release year of the movie.
     *
     * @return The release year of the movie.
     */
    public int getYear() {
        return year.get();
    }

    /**
     * Sets the release year of the movie.
     *
     * @param value The release year of the movie.
     */
    public void setYear(int value) {
        year.set(value);
    }

    /**
     * Gets the homepage URL of the movie.
     *
     * @return The homepage URL of the movie.
     */
    public String getHomepage() {
        return homepage.get();
    }

    /**
     * Sets the homepage URL of the movie.
     *
     * @param value The homepage URL of the movie.
     */
    public void setHomepage(String value) {
        homepage.set(value);
    }

    /**
     * Gets the average view rating of the movie.
     *
     * @return The average view rating of the movie.
     */
    public double getAverageView() {
        return averageView.get();
    }

    /**
     * Sets the average view rating of the movie.
     *
     * @param value The average view rating of the movie.
     */
    public void setAverageView(double value) {
        averageView.set(value);
    }
}