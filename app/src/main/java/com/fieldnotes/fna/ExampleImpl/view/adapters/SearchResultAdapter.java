package com.fieldnotes.fna.ExampleImpl.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fieldnotes.fna.ExampleImpl.model.FieldNote;
import com.fieldnotes.fna.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This adapter is used to display the results of FNSearch in a ListView
 */
public class SearchResultAdapter extends ArrayAdapter<FieldNote> {

    public SearchResultAdapter(Context context, ArrayList<FieldNote> searchResults) {
        super(context, 0, searchResults);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            // inflate view
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_search_list_item, parent, false);

            // cache view fields into the holder
            holder = new ViewHolder();
            holder.mTicketNumberTv = view.findViewById(R.id.resultTicket);
            holder.mCreatorTv = view.findViewById(R.id.resultUser);
            holder.mProjectTv = view.findViewById(R.id.resultProject);
            holder.mDescriptionTv = view.findViewById(R.id.resultDescription);
            holder.mWellNameTv = view.findViewById(R.id.resultWell);
            holder.mBillingTv = view.findViewById(R.id.resultBilling);
            holder.mDateStartTv = view.findViewById(R.id.resultDateStart);
            holder.mDateEndTv = view.findViewById(R.id.resultDateEnd);
            holder.mTimeStartTv = view.findViewById(R.id.resultTimeStart);
            holder.mTimeEndTv = view.findViewById(R.id.resultTimeEnd);
            holder.mLocationTv = view.findViewById(R.id.resultLocation);
            holder.mMileageStartTv = view.findViewById(R.id.resultMileStart);
            holder.mMileageEndTv = view.findViewById(R.id.resultMileEnd);
            holder.mGpsTv = view.findViewById(R.id.resultGps);

            // associate the holder with the view for later lookup
            view.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) view.getTag();
        }

        // get data item for position
        FieldNote fieldNote = getItem(position);

        // populate views
        if (fieldNote != null) {
            holder.mTicketNumberTv.setText(fieldNote.getTicketNumber());
            holder.mCreatorTv.setText(fieldNote.getUsername());
            holder.mProjectTv.setText(fieldNote.getProject());
            holder.mDescriptionTv.setText(fieldNote.getDescription());
            holder.mWellNameTv.setText(fieldNote.getWellname());
            holder.mBillingTv.setText(fieldNote.getBilling());
            holder.mDateStartTv.setText(fieldNote.getStartDateTime().format(dateFormatter));
            holder.mDateEndTv.setText(fieldNote.getEndDateTime().format(dateFormatter));
            holder.mTimeStartTv.setText(fieldNote.getStartDateTime().format(timeFormatter));
            holder.mTimeEndTv.setText(fieldNote.getEndDateTime().format(timeFormatter));
            holder.mLocationTv.setText(fieldNote.getLocation());
            holder.mMileageStartTv.setText(String.valueOf(fieldNote.getMileageStart()));
            holder.mMileageEndTv.setText(String.valueOf(fieldNote.getMileageEnd()));
            holder.mGpsTv.setText(fieldNote.getGps());
        }

        return view;
    }

     class ViewHolder {
        TextView mTicketNumberTv;
        TextView mCreatorTv;
        TextView mProjectTv;
        TextView mDescriptionTv;
        TextView mWellNameTv;
        TextView mBillingTv;
        TextView mDateStartTv;
        TextView mDateEndTv;
        TextView mTimeStartTv;
        TextView mTimeEndTv;
        TextView mLocationTv;
        TextView mMileageStartTv;
        TextView mMileageEndTv;
        TextView mGpsTv;
    }
}
