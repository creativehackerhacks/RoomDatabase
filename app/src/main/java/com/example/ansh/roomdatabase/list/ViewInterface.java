package com.example.ansh.roomdatabase.list;

import android.view.View;

import com.example.ansh.roomdatabase.data.ListItem;

import java.util.List;

public interface ViewInterface {

    void startDetailActivity(String dateTime, String message, int colorResource, View viewRoot);
    void setUpAdapterAndView(List<ListItem> listOfData);
    void addNewListItem(ListItem newItem);
    void deleteListItemAt(int position);

    void showUndoSnapBar();

    void insertListItemAt(int temporaryListItemPosition, ListItem temporaryListItem);
}
