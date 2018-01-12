/**
 * Created by cuneytcelebican on 2017-02-23.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ShoppingListApp extends Application {
    private ShoppingListView    view;
    private Shopper             shopper = new Shopper();


    public void start(Stage primaryStage) {
        GroceryItem[] products = {
                new FreezerItem("Smart-Ones Frozen Entrees", 1.99f, 0.311f),
                new GroceryItem("SnackPack Pudding", 0.99f, 0.396f),
                new FreezerItem("Breyers Chocolate Icecream",2.99f,2.27f),
                new GroceryItem("Nabob Coffee", 3.99f, 0.326f),
                new GroceryItem("Gold Seal Salmon", 1.99f, 0.213f),
                new GroceryItem("Ocean Spray Cranberry Cocktail",2.99f,2.26f),
                new GroceryItem("Heinz Beans Original", 0.79f, 0.477f),
                new RefrigeratorItem("Lean Ground Beef", 4.94f, 0.75f),
                new FreezerItem("5-Alive Frozen Juice",0.75f,0.426f),
                new GroceryItem("Coca-Cola 12-pack", 3.49f, 5.112f),
                new GroceryItem("Toilet Paper - 48 pack", 40.96f, 10.89f),
                new RefrigeratorItem("2L Sealtest Milk",2.99f,2.06f),
                new RefrigeratorItem("Extra-Large Eggs",1.79f,0.77f),
                new RefrigeratorItem("Yoplait Yogurt 6-pack",4.74f,1.02f),
                new FreezerItem("Mega-Sized Chocolate Icecream",67.93f,15.03f)
        };

        view = new ShoppingListView(products);
        primaryStage.setTitle("View Test");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(view, 740,390));
        primaryStage.show();

        view.getProductList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleListSelection();
            }
        });

        view.getShoppingCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleShoppingListSelection();
            }
        });



        view.getBuyButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleBuyButtonPress();
            }
        });

        view.getReturnButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleReturnButtonPress();
            }
        });

        view.getCheckoutButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCheckoutButtonPress();
                view.getCheckoutButton().setOnAction(event1 -> {
                    int numItems = shopper.getNumItems();
                    for (int i = 0; i < numItems; i++){
                        shopper.removeItem(shopper.getCart()[0]);
                    }
                    start(primaryStage);
                });
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }

    // The ListView selection event handler
    private void handleListSelection() {
        view.update();
    }

    private void handleShoppingListSelection() {
        if(view.getProductList().isDisable()){
            int index = view.getShoppingCartList().getSelectionModel().getSelectedIndex();
            view.getContentList().setItems(FXCollections.observableArrayList(shopper.getCart()[index].getContents()));
        }
        view.update();
    }

    float totalPrice = 0;
    private void handleBuyButtonPress() {
        //System.out.println(view.getModel()[view.getProductList().getSelectionModel().getSelectedIndex()]);
        int index = view.getProductList().getSelectionModel().getSelectedIndex();
        view.getTotalPriceField().setText(String.format("$%,1.2f", totalPrice));
        shopper.addItem(view.getModel()[index]);

        String[] itemsInTheCart = new String[shopper.getNumItems()];
        for (int i = 0; i < shopper.getNumItems(); i++)
            itemsInTheCart[i] = shopper.getCart()[i].getDescription();

        totalPrice = shopper.computeTotalCost();
        view.getTotalPriceField().setText(String.format("$%,1.2f", totalPrice));
        view.getShoppingCartList().setItems(FXCollections.observableArrayList(itemsInTheCart));

        view.update();
    }

    private void handleReturnButtonPress() {
        String item = view.getShoppingCartList().getSelectionModel().getSelectedItem();

        for (int i = 0; i < shopper.getNumItems(); i++) {
            if (item == shopper.getCart()[i].getDescription())
                shopper.removeItem(shopper.getCart()[i]);
        }

        String[] itemsInTheCart = new String[shopper.getNumItems()];
        for (int i = 0; i < shopper.getNumItems(); i++)
            itemsInTheCart[i] = shopper.getCart()[i].getDescription();

        totalPrice = shopper.computeTotalCost();
        view.getTotalPriceField().setText(String.format("$%1.2f", totalPrice));
        view.getShoppingCartList().setItems(FXCollections.observableArrayList(itemsInTheCart));

        view.update();
    }

    private void handleCheckoutButtonPress() {
        for (int i=0; i<shopper.getNumItems(); i++) {
            System.out.println(String.format("%-30s%6s%5.2f", shopper.getCart()[i].getDescription(),
                    "$",
                    shopper.getCart()[i].getPrice()));
        }
        System.out.println("------------------------------------------");
        System.out.println(String.format("%-30s%6s%5.2f", "Total Price", "$", totalPrice) + "\n");

        shopper.packBags();
        String[] packedInTheCart = new String[shopper.getNumItems()];
        for (int i=0; i<shopper.getNumItems(); i++) {
            packedInTheCart[i] =  shopper.getCart()[i].getDescription();
        }
        view.getProductList().setDisable(true);
        view.getShoppingCartList().setItems(FXCollections.observableArrayList(packedInTheCart));
        view.getCheckoutButton().setText("Restart Shopping");

        view.update();
    }

}
