
package com.example.backend.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            CartItem existingItem = items.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
    public List<CartItem> listItem (){
        return new ArrayList<>(items.values());
    }
    public void updateItemQuantity(String productId, int quantity) {
        try {
            int id = Integer.parseInt(productId);
            CartItem item = items.get(id);
            if (item != null) {
                item.setQuantity(quantity);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid productId: " + productId);
        }
    }
}
