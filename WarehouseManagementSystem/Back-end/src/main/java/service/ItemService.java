// ItemService.java
package com.yourcompany.service;

import com.yourcompany.model.Item;
import com.yourcompany.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        if (itemRepository.existsById(id)) {
            updatedItem.setId(id);
            return itemRepository.save(updatedItem);
        } else {
            return null;
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
