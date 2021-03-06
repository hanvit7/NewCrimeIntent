package com.bignerdranch.android.newcrimeintent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leehyuntae on 8/21/16.
 */
public class DragNDropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_n_drop);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Setup D&D feature and RecyclerView
        RecyclerViewDragDropManager dragMgr = new RecyclerViewDragDropManager();

        dragMgr.setInitiateOnMove(false);
        dragMgr.setInitiateOnLongPress(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(dragMgr.createWrappedAdapter(myAdapter));

        dragMgr.attachRecyclerView(recyclerView);

        Button addImageButton = (Button) findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyItem addImageItem = new MyItem(myAdapter.getItemCount(), null, MyItem.TYPE_IMAGE);
                myAdapter.mItems.add(addImageItem);
                MyItem addEditTextItem= new MyItem(myAdapter.getItemCount(), null, MyItem.TYPE_TEXT);
                myAdapter.mItems.add(addEditTextItem);
            }
        });
    }

    static class MyViewHolder extends AbstractDraggableItemViewHolder {
        EditText editText;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.list_item_edit_text);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_image_view);
        }
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements DraggableItemAdapter<MyViewHolder> {
        List<MyItem> mItems;

        public MyAdapter() {
            setHasStableIds(true); // this is required for D&D feature.

            mItems = new ArrayList<>();

            mItems.add(new MyItem(1, null, MyItem.TYPE_TEXT));
//            mItems.add(new MyItem(2, "Item " + 2, MyItem.TYPE_IMAGE));
//            mItems.add(new MyItem(3, "Item " + 3, MyItem.TYPE_TEXT));
//            mItems.add(new MyItem(4, "Item " + 4, MyItem.TYPE_IMAGE));

        }

        @Override
        public long getItemId(int position) {
            return mItems.get(position).id; // need to return stable (= not change even after reordered) value
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_drag_n_drop, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            MyItem item = mItems.get(position);

            if (item.type == MyItem.TYPE_TEXT) {
//                holder.textView.setText(item.text);
                holder.editText.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
            } else {
                holder.editText.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onMoveItem(int fromPosition, int toPosition) {
            MyItem movedItem = mItems.remove(fromPosition);
            mItems.add(toPosition, movedItem);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public boolean onCheckCanStartDrag(MyViewHolder holder, int position, int x, int y) {
            return true;
        }

        @Override
        public ItemDraggableRange onGetItemDraggableRange(MyViewHolder holder, int position) {
            return null;
        }

        @Override
        public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
            return true;
        }
    }
}

