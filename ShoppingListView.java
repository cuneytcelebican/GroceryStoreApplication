/**
 * Created by cuneytcelebican on 2017-02-23.
 */
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ShoppingListView extends Pane {
    private Carryable[]         model;
    private ListView<String>    productList, shoppingCartList, contentList;
    private Button              buyButton, returnButton, checkoutButton;
    private TextField           totalPriceField;


    public Button getBuyButton() {return buyButton;}
    public ListView<String> getProductList() {return productList;}
    public ListView<String> getShoppingCartList() {return shoppingCartList;}
    public ListView<String> getContentList() {return contentList;}
    public Button getReturnButton() {return returnButton;}
    public Button getCheckoutButton() {return checkoutButton;}
    public TextField getTotalPriceField() {return totalPriceField;}

    public Carryable[] getModel() {
        return model;
    }

    public ShoppingListView(Carryable[] m){
        model = m;
        Label productsLabel =  new Label("Products");
        productsLabel.relocate(10,10);
        productsLabel.setPrefSize(200,35);

        Label shoppingCartLabel =  new Label("Shopping Cart");
        shoppingCartLabel.relocate(220,10);
        shoppingCartLabel.setPrefSize(200,35);

        Label contentsLabel =  new Label("Contents");
        contentsLabel.relocate(430,10);
        contentsLabel.setPrefSize(120,35);

        Label totalPriceLabel =  new Label("Total Price:");
        totalPriceLabel.relocate(565,355);
        totalPriceLabel.setPrefSize(65,25);
        totalPriceLabel.setStyle("-fx-font: 12 arial;");

        totalPriceField = new TextField();
        totalPriceField.relocate(630, 355);
        totalPriceField.setPrefSize(100,25);
        totalPriceField.setPromptText("$0.00");


        productList = new ListView<String>();
        productList.relocate(10, 45);
        productList.setPrefSize(200, 300);

        shoppingCartList = new ListView<String>();
        shoppingCartList.relocate(220, 45);
        shoppingCartList.setPrefSize(200, 300);

        contentList = new ListView<String>();
        contentList.relocate(430, 45);
        contentList.setPrefSize(300, 300);

        buyButton = new Button("Buy");
        buyButton.relocate(10,355);
        buyButton.setPrefSize(200,25);
        buyButton.setStyle("-fx-font: 12 arial;");

        returnButton = new Button("Return");
        returnButton.relocate(220,355);
        returnButton.setPrefSize(200,25);
        returnButton.setStyle("-fx-font: 12 arial;");

        checkoutButton = new Button("Checkout");
        checkoutButton.relocate(430,355);
        checkoutButton.setPrefSize(120,25);
        checkoutButton.setStyle("-fx-font: 12 arial;");

        getChildren().addAll(productsLabel, shoppingCartLabel, contentsLabel, productList,
                shoppingCartList, contentList, buyButton, returnButton,
                checkoutButton, totalPriceLabel, totalPriceField);

        update();
    }

    public void update() {
        String[] exactList = new String[model.length];
        for (int i=0; i < model.length; i++)
            exactList[i] = model[i].toString();
        int selectedIndex = productList.getSelectionModel().getSelectedIndex();
        productList.setItems(FXCollections.observableArrayList(exactList));
        productList.getSelectionModel().select(selectedIndex);

        buyButton.setDisable(productList.getSelectionModel().getSelectedIndex() <0);
        returnButton.setDisable(shoppingCartList.getSelectionModel().getSelectedIndex() < 0);
        checkoutButton.setDisable(shoppingCartList.getItems().isEmpty());

        if(productList.isDisable()){
            buyButton.setDisable(true);
            returnButton.setDisable(true);
        }

    }

}

