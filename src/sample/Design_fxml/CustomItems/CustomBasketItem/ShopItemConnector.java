package sample.Design_fxml.CustomItems.CustomBasketItem;

import javafx.scene.layout.Pane;


public interface ShopItemConnector {
    void onDispose();
    void setPane(Pane p);
}
