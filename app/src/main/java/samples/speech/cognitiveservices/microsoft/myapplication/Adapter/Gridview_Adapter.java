package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Gridview_Adapter extends BaseAdapter {
    private final Context context;
    private List<Vocabulary> vocabularies;
    private List<Vocabulary> uncheck = new ArrayList<>();
    private ItemClick itemClick;
    public interface ItemClick{
        void vocabularyUncheck(List<Vocabulary> vocabularies);
    }
    public Gridview_Adapter(Context context,ItemClick itemClick ) {
        this.context = context;
        this.itemClick = itemClick;
    }
    public void setData(List<Vocabulary> vocabularies){
        this.vocabularies = vocabularies;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return vocabularies.size();
    }

    @Override
    public Object getItem(int i) {
        return vocabularies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        View view;
        TextView english, vietnamese;
        boolean check = true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.gridview_item, viewGroup, false);
            viewHolder.view = view;
            viewHolder.english = view.findViewById(R.id.english_gridview);
            viewHolder.vietnamese = view.findViewById(R.id.vietnamese_gridview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.english.setText(vocabularies.get(i).getTienganh());
        viewHolder.vietnamese.setText(vocabularies.get(i).getTiengviet());
        viewCLick(viewHolder,vocabularies.get(i));
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int desiredWidth = (screenWidth / 3) - dpToPx(4);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = desiredWidth;
        params.height = desiredWidth;
        view.setLayoutParams(params);
        return view;
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    private void viewCLick(ViewHolder viewHolder,Vocabulary vocabulary){
        viewHolder.view.setOnClickListener(view1 -> {
            if(viewHolder.check) {
                viewHolder.view.setBackground(ContextCompat.getDrawable(context, R.drawable.button_disable));
                uncheck.add(vocabulary);
            }else {
                viewHolder.view.setBackground(ContextCompat.getDrawable(context,R.drawable.button_true));
                uncheck.remove(vocabulary);
            }
            itemClick.vocabularyUncheck(uncheck);
            viewHolder.check = !viewHolder.check;
        });
    }
}
