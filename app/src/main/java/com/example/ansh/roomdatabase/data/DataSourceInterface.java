package com.example.ansh.roomdatabase.data;

import java.util.List;

public interface DataSourceInterface {

    List<ListItem> getListOfData();

    ListItem createNewListItem();

    void deleteListItem(ListItem listItem);

    void insertListItem(ListItem temporaryListItem);

}
