package com.example.ansh.roomdatabase.data;

import com.example.ansh.roomdatabase.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeDataSource implements DataSourceInterface {

    private static final int sizeOfCollection = 12;
    private Random random;

    private final String[] datesAndTimes = {
            "6:30AM 06/01/2017",
            "9:26PM 04/22/2013",
            "2:01PM 12/02/2015",
            "2:43AM 09/7/2018",
    };

    private final String[] messages = {
            "Check out content like Fragmented Podcast to expose yourself to the knowledge, ideas, " +
                    "and opinions of experts in your field",
            "Look at Open Source Projects like Android Architecture Blueprints to see how experts" +
                    " design and build Apps",
            "Write lots of Code and Example Apps. Writing good Quality Code in an efficient manner "
                    + "is a Skill to be practiced like any other.",
            "If at first something doesn't make any sense, find another explanation. We all " +
                    "learn/teach different from each other. Find an explanation that speaks to you."
    };

    private final int[] drawables = {
            R.drawable.green_drawable,
            R.drawable.red_drawable,
            R.drawable.blue_drawable,
            R.drawable.yellow_drawable
    };


    public FakeDataSource() {
        random = new Random();
    }

    /**
     * Creates a list of ListItems.
     *
     * @return A list of 12 semi-random ListItems for testing purposes
     */
    @Override
    public List<ListItem> getListOfData() {
        ArrayList<ListItem> listOfData = new ArrayList<>();
        Random random = new Random();
        //make 12 semi-random items
        for (int i = 0; i < 12; i++) {
            listOfData.add(
                    createNewListItem()
            );
        }

        return listOfData;
    }

    @Override
    public ListItem createNewListItem() {

        //these will be 0, 1, 2, or 3
        int randOne = random.nextInt(4);
        int randTwo = random.nextInt(4);
        int randThree = random.nextInt(4);

        //creates a semi-random ListItem
        ListItem listItem = new ListItem(
                datesAndTimes[randOne],
                messages[randTwo],
                drawables[randThree]
        );

        return listItem;
    }

    @Override
    public void deleteListItem(ListItem listItem) {
    }

    @Override
    public void insertListItem(ListItem temporaryListItem) {

    }

}
