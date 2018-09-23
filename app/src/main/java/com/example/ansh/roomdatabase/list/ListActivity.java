package com.example.ansh.roomdatabase.list;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.ansh.roomdatabase.CustomAdapter;
import com.example.ansh.roomdatabase.data.FakeDataSource;
import com.example.ansh.roomdatabase.data.ListItem;
import com.example.ansh.roomdatabase.R;
import com.example.ansh.roomdatabase.SimpleRecyclerViewOnClick;
import com.example.ansh.roomdatabase.logic.Controller;

import java.util.List;

public class ListActivity extends AppCompatActivity implements ViewInterface, View.OnClickListener {

    private static final String EXTRA_DATE_AND_TIME = "EXTRA_DATE_AND_TIME";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_COLOR = "EXTRA_COLOR";

    public static final String TAG = ListActivity.class.getSimpleName();
    private Context mContext = ListActivity.this;

    private List<ListItem> mListOfData;

    private LayoutInflater mLayoutInflater;
    private RecyclerView mRecyclerView;
    private CustomAdapter mCustomAdapter;

    private Controller mController;

    private SimpleRecyclerViewOnClick mSimpleRecyclerViewOnClick;

    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Important
        mSimpleRecyclerViewOnClick = new SimpleRecyclerViewOnClick() {
            @Override
            public void onClick(View v, int pos) {
                ListItem listItem = mListOfData.get(pos);
                mController.onListItemClick(listItem, v);
            }
        };

        mToolbar = findViewById(R.id.tlb_list_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mFloatingActionButton = findViewById(R.id.fab_create_new_item);
        mFloatingActionButton.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.rec_list_activity);
        mLayoutInflater = getLayoutInflater();

        mController = new Controller(this, new FakeDataSource());


        mController.getListFromDataSource();
    }

    @Override
    public void startDetailActivity(String dateTime, String message, int colorResource, View viewRoot) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(EXTRA_DATE_AND_TIME, dateTime);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_COLOR, colorResource);

        // There's another way to do this, which I used in "SpaceFeed" app.
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setExitTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    ListActivity.this,
                    new Pair<View, String>(viewRoot.findViewById(R.id.imv_list_item_circle),
                            getString(R.string.transition_drawable)),
                    new Pair<View, String>(viewRoot.findViewById(R.id.lbl_message),
                            getString(R.string.transition_message)),
                    new Pair<View, String>(viewRoot.findViewById(R.id.lbl_date_and_time),
                            getString(R.string.transition_time_and_date))
                    );
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void setUpAdapterAndView(List<ListItem> listOfData) {
        this.mListOfData = listOfData;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mCustomAdapter = new CustomAdapter(listOfData, mContext, mSimpleRecyclerViewOnClick);
        mRecyclerView.setAdapter(mCustomAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), layoutManager.getOrientation()
        );
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_white));

        mRecyclerView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void addNewListItem(ListItem newItem) {
        mListOfData.add(newItem);

        int endOfList = mListOfData.size()-1;
        mCustomAdapter.notifyItemInserted(endOfList);
        mRecyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void deleteListItemAt(int position) {
        mListOfData.remove(position);
        mCustomAdapter.notifyItemRemoved(position);
    }

    @Override
    public void showUndoSnapBar() {
        Snackbar.make(
                findViewById(R.id.root_list_activity),
                R.string.action_delete_item,
                Snackbar.LENGTH_SHORT
        )
                .setAction(R.string.action_undo, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mController.onUndoConfirmed();
                    }
                })
                /**
                 * By this we're telling controller that SnackBar is dismissed
                 * and there's no way user can undo the action.
                 */
                .addCallback(new BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);

                        mController.onSnackBarTimeOut();
                    }
                })
                .show();
    }

    @Override
    public void insertListItemAt(int temporaryListItemPosition, ListItem temporaryListItem) {
        mListOfData.add(temporaryListItemPosition, temporaryListItem);
        mCustomAdapter.notifyItemInserted(temporaryListItemPosition);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.fab_create_new_item) {
            // User want to create a new RecyclerView Item.
            mController.createNewListItem();
        }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleCallback = new SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                mController.onListItemSwiped(position, mListOfData.get(position));
            }
        };
        return simpleCallback;
    }


}
