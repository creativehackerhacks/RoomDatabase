package com.example.ansh.roomdatabase.logic;

import android.view.View;

import com.example.ansh.roomdatabase.data.DataSourceInterface;
import com.example.ansh.roomdatabase.data.ListItem;
import com.example.ansh.roomdatabase.list.ViewInterface;

public class Controller {

    private ListItem mTemporaryListItem;
    private int mTemporaryListItemPosition;

    private ViewInterface mViewInterface;
    private DataSourceInterface mDataSourceInterface;

    public Controller(ViewInterface viewInterface, DataSourceInterface dataSourceInterface) {
        mViewInterface = viewInterface;
        mDataSourceInterface = dataSourceInterface;
    }

    public void getListFromDataSource() {
        mViewInterface.setUpAdapterAndView(mDataSourceInterface.getListOfData());
    }

    public void onListItemClick(ListItem listItem, View viewRoot) {
        mViewInterface.startDetailActivity(
                listItem.getDateAndTime(),
                listItem.getMessage(),
                listItem.getColorResource(),
                viewRoot
        );
    }

    public void createNewListItem() {
        ListItem newItem = mDataSourceInterface.createNewListItem();
        mViewInterface.addNewListItem(newItem);
    }

    public void onListItemSwiped(int position, ListItem listItem) {
        /**
         * Here we're just mocking the actual working.
         * Where we're deleting item from the database as well as updating the interface.
         */
        //ensure that the view & data layers have consistent state
        mDataSourceInterface.deleteListItem(listItem);
        //tell the view to delete it too
        mViewInterface.deleteListItemAt(position);

        mTemporaryListItemPosition = position;
        mTemporaryListItem = listItem;

        mViewInterface.showUndoSnapBar();

    }

    public void onUndoConfirmed() {
        if(mTemporaryListItem != null) {
            //ensure View/Data Consistency
            mDataSourceInterface.insertListItem(mTemporaryListItem);
            mViewInterface.insertListItemAt(mTemporaryListItemPosition, mTemporaryListItem);

            mTemporaryListItem = null;
            mTemporaryListItemPosition = 0;
        }
    }

    public void onSnackBarTimeOut() {
        mTemporaryListItem = null;
        mTemporaryListItemPosition = 0;
    }
}
