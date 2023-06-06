package com.maayan.integrative_20.Adatpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> notes;

    public MyAdapter(Context context, ArrayList<String> notes) {
        this.context = context;
        this.notes = notes;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //when we give a look for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //assigned values for each row based on its position

        holder.checkBox.setText(notes.get(position));

        holder.checkBox.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notes.remove(position);
                notifyDataSetChanged();
                return true;
            }

        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox != null) {


                    String text = holder.checkBox.getText().toString();
                    SpannableString spannableString = new SpannableString(text);
                    if (holder.checkBox.isChecked()) {
                        spannableString.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    } else {
                        StrikethroughSpan[] spans = spannableString.getSpans(0, text.length(), StrikethroughSpan.class);
                        for (StrikethroughSpan span : spans) {
                            spannableString.removeSpan(span);
                        }
                    }
                    holder.checkBox.setText(spannableString);


                }
            }
        });


    }

    @Override
    public int getItemCount() {
        //how many items we have in total
        return notes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        //all the thing one row is composed of

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}



