import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {

    private SimpleStringProperty title;
    private SimpleStringProperty director;
    private SimpleStringProperty cast;
    private SimpleIntegerProperty year;
    private SimpleStringProperty homepage;
    private SimpleDoubleProperty averageView;

    public Movie(String title, String director, String cast, int year, String homepage, double averageView){
        this.title = new SimpleStringProperty(title);
        this.director = new SimpleStringProperty(director);
        this.cast = new SimpleStringProperty(cast);
        this.year = new SimpleIntegerProperty(year);
        this.homepage = new SimpleStringProperty(homepage);
        this.averageView = new SimpleDoubleProperty(averageView);
    }

    public void setTitle(String value){
        title.set(value);
    }

    public String getTitle(){
        return title.get();
    }

    public void setDirector(String value){
        director.set(value);
    }

    public String getDirector(){
        return director.get();
    }

    public String getCast(){
        return cast.get();
    }

    public void setCast(String value){
        cast.set(value);
    }

    public int getYear(){
        return year.get();
    }

    public void setYear(int value){
        year.set(value);
    }

    public String getHomepage(){
        return homepage.get();
    }

    public void setHomepage(String value){
        homepage.set(value);
    }

    public double getAverageView(){
        return averageView.get();
    }

    public void setAverageView(double value){
        averageView.set(value);
    }
}