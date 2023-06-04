package ch.fhnw.oop2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The ApplicationUI class represents the user interface of the movie application.
 * It extends the BorderPane class and contains various controls and a TableView to display movie data.
 */
public class ApplicationUI extends BorderPane {
    private ComboBox<String> dropdown;
    private TextField textField;
    private Label label1;
    private Label label2;
    private Button button;
    private TableView<Movie> tableView;
    private ObservableList<Movie> data;
    private PresentationModel presentationModel;
    private StringProperty label2Text;

    /**
     * Constructs an instance of the ApplicationUI class.
     * Initializes the controls, sets up the layout, and creates a PresentationModel.
     */
    public ApplicationUI() {
        initializeControls();
        layoutControls();
        presentationModel = new PresentationModel(data, dropdown, textField, label2Text);
        presentationModel.setComboBox(dropdown);
        presentationModel.setTextField(textField);
        presentationModel.setLabel2(label2);
        presentationModel.setTableView(tableView);
        presentationModel.setLabel2Text(label2Text);

    }

    /**
     * Sets up the layout of the user interface by placing controls in the appropriate containers.
     */
    private void layoutControls() {
        HBox topContainer = new HBox(10);
        topContainer.setPadding(new Insets(10));
        topContainer.getChildren().addAll(dropdown, textField, label1, label2, button);
        this.setTop(topContainer);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setCenter(tableView);
    }

    /**
    * Initializes the controls by creating instances and setting up event handlers.
    */
    private void initializeControls() {
        dropdown = new ComboBox<>();
        dropdown.setPromptText("Title");
        dropdown.setItems(FXCollections.observableArrayList("Title", "Director", "Cast", "Year"));
        dropdown.setOnAction(event -> presentationModel.handleComboBoxSelection());

        textField = new TextField();
        textField.setOnKeyReleased(event -> presentationModel.handleSearch());

        button = new Button("Load");

        data = FXCollections.observableArrayList();
        tableView = new TableView<>(data);

        label1 = new Label("Angezeigte Datensätze: ");
        label2 = new Label();

        label2Text = new SimpleStringProperty();
        label2.textProperty().bind(label2Text);

        button.setOnAction(event -> {
            presentationModel.loadDataFromServer();
        });

        TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.getColumns().add(titleColumn);

        TableColumn<Movie, String> directorColumn = new TableColumn<>("Director");
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        tableView.getColumns().add(directorColumn);

        TableColumn<Movie, String> castColumn = new TableColumn<>("Cast");
        castColumn.setCellValueFactory(new PropertyValueFactory<>("cast"));
        tableView.getColumns().add(castColumn);

        TableColumn<Movie, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        tableView.getColumns().add(yearColumn);

        TableColumn<Movie, String> homepageColumn = new TableColumn<>("Homepage");
        homepageColumn.setCellValueFactory(new PropertyValueFactory<>("homepage"));
        tableView.getColumns().add(homepageColumn);

        TableColumn<Movie, Double> averageViewColumn = new TableColumn<>("Average view");
        averageViewColumn.setCellValueFactory(new PropertyValueFactory<>("averageView"));
        tableView.getColumns().add(averageViewColumn);

        tableView.setItems(data);
    }

}