package com.mercadolibre.itaccelerator.elasticapitest.models;

import com.mercadolibre.itaccelerator.elasticapitest.controllers.ItemException;
import com.mercadolibre.itaccelerator.elasticapitest.controllers.Items;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.util.Collection;

public interface elasticInterface {
    //public Collection<Items> getAll();
    public SearchResponse getAll(String index);
    public SearchResponse getById(String index, String id);
    public void addItem(Items item);
    public IndexResponse addItemById(Items item, String id);
    public UpdateResponse updateItemById(Items item, String id);
    public SearchResponse searchByTitle(String index, String title);
    public DeleteResponse deleteItem(String id);


    /*
    public Items getItem(String id);
    public Items editItem(Items item)
        throws ItemException;
    public boolean itemExists(String id);
    */
}
