package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Finish_Adapter extends RecyclerView.Adapter<Finish_Adapter.Finish_ViewHolder> {
    public List<Value> valueList;

    public Finish_Adapter(List<Value> reviseList) {
        valueList = reviseList;
    }

    @NonNull
    @Override
    public Finish_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finish, parent, false);
        return new Finish_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Finish_ViewHolder holder, int position) {
        Value value = valueList.get(position);
        holder.tienganh.setText(value.getTienganh());
        holder.tiengviet.setText(value.getTiengviet());
        holder.ngayontap.setText(value.getCheckday()+" ngày");
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    public static class Finish_ViewHolder extends RecyclerView.ViewHolder {
        TextView tienganh, tiengviet, ngayontap;

        public Finish_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tienganh = itemView.findViewById(R.id.tienganh_item);
            tiengviet = itemView.findViewById(R.id.tiengviet_item);
            ngayontap = itemView.findViewById(R.id.ngayontap);
        }
    }
}
