package com.example.nftastops.utilclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
//import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.nftastops.model.Dropdowns;
import com.example.nftastops.ui.stops.StopFragment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MultiSpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener
{

    //String[] _items = null;
    Dropdowns[] _items = null;
    boolean[] mSelection = null;

    ArrayAdapter<String> tough_adapter;
    ArrayAdapter<String> simple_adapter;

    public MultiSpinner(Context context)
    {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);

        super.setAdapter(simple_adapter);
    }

    public MultiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;

            simple_adapter.clear();
            List<Dropdowns> dp = new ArrayList<>();
            dp = buildSelectedItem();
            for(Dropdowns item:dp){
                simple_adapter.add(item.toString());}
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }


    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        int len = _items.length;

        String[] _itemsShow = new String [len];
        for (int i = 0; i < len; i++){
            _itemsShow[i] = _items[i].getDisplay_name();
        }

        builder.setMultiChoiceItems(_itemsShow, mSelection, this);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                simple_adapter.notifyDataSetChanged();
            }
        });


        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
//        throw new RuntimeException(
//                "setAdapter is not supported by MultiSelectSpinner.");

        super.setAdapter(simple_adapter);
    }

//    public void setItems(String[] items) {
//        _items = items;
//        mSelection = new boolean[_items.length];
//        simple_adapter.clear();
//        simple_adapter.add(_items[0]);
//        Arrays.fill(mSelection, false);
//    }


    public void setItems(List<Dropdowns> items) {
        _items = items.toArray(new Dropdowns[items.size()]);
        mSelection = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0].toString());
        Arrays.fill(mSelection, false);
    }


    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                }
            }
        }
    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }


    public void setSelection(List<Dropdowns> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (Dropdowns sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                }
            }
        }
        simple_adapter.clear();

        List<Dropdowns> dp = new ArrayList<>();
        dp = buildSelectedItem();
        for(Dropdowns item:dp){
            simple_adapter.add(item.toString());}
    }


    public void setSelection(int[] selectedIndicies) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (int index : selectedIndicies) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        //    public void setSelection(int index) {
//        for (int i = 0; i < mSelection.length; i++) {
//            mSelection[i] = false;
//        }
//        if (index >= 0 && index < mSelection.length) {
//            mSelection[index] = true;
//        } else {
//            throw new IllegalArgumentException("Index " + index
//                    + " is out of bounds.");
//        }
//        simple_adapter.clear();
//        simple_adapter.add(buildSelectedItemString());
//    }
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        List<Dropdowns> dp = new ArrayList<>();
        dp = buildSelectedItem();
        for(Dropdowns item:dp){
            simple_adapter.add(item.toString());}
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i].getDisplay_name());
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i].getDisplay_name());
            }
        }
        return sb.toString();
    }

    private List<Dropdowns> buildSelectedItem() {
        boolean foundOne = false;
        List<Dropdowns> dp = new ArrayList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    //sb.append(", ");
                }
                foundOne = true;

                dp.add(_items[i]);
            }
        }
        return dp;
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i].getDisplay_name());
            }
        }
        return sb.toString();
    }

    public List<Dropdowns> getSelectedItems() {
        boolean foundOne = false;
        List<Dropdowns> dp = new ArrayList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    //sb.append(", ");
                }
                foundOne = true;
                dp.add(_items[i]);
            }
        }
        return dp;
    }
}