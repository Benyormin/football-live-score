package com.beny.livescore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MatchAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;

    public MatchAdapter(Context context2) {
        this.context = context2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.match_row, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.homeTeamName.setText(MatchDetails.matchDetails_List.get(position).getHomeTeamName());
        holder.awayTeamName.setText(MatchDetails.matchDetails_List.get(position).getAwayTeamName());
        String status = MatchDetails.matchDetails_List.get(position).getStatus();
        if (status.equalsIgnoreCase("FINISHED") || status.equalsIgnoreCase("IN_PLAY") || status.equalsIgnoreCase("PAUSED")) {
            holder.homeTeamScore.setText(Integer.toString(MatchDetails.matchDetails_List.get(position).getHome_team_score()));
            holder.awayTeamScore.setText(Integer.toString(MatchDetails.matchDetails_List.get(position).getAway_team_score()));
            holder.vsTxt.setText("-");
        } else {
            holder.homeTeamScore.setText("");
            holder.awayTeamScore.setText("");
            holder.vsTxt.setText("VS");
        }
        if (status.equalsIgnoreCase("IN_PLAY")) {
            holder.stateImage.setColorFilter(this.context.getResources().getColor(R.color.green));
        }
        if (status.equalsIgnoreCase("FINISHED")) {
            holder.stateImage.setColorFilter(this.context.getResources().getColor(R.color.black));
        }
        if (status.equalsIgnoreCase("PAUSED")) {
            holder.stateImage.setColorFilter(this.context.getResources().getColor(R.color.Yellow));
        }
        if (status.equalsIgnoreCase("SCHEDULED")) {
            holder.stateImage.setColorFilter(this.context.getResources().getColor(R.color.blue));
        }
    }

    public int getItemCount() {
        return MatchDetails.matchDetails_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView awayTeamName;
        TextView awayTeamScore;
        TextView homeTeamName;
        TextView homeTeamScore;
        CircleImageView stateImage;
        TextView vsTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.homeTeamName = (TextView) itemView.findViewById(R.id.homeTeamtxt);
            this.awayTeamName = (TextView) itemView.findViewById(R.id.awayTeamtxt);
            this.homeTeamScore = (TextView) itemView.findViewById(R.id.homeTeamScoretxt);
            this.awayTeamScore = (TextView) itemView.findViewById(R.id.awayTeamScoretxt);
            this.vsTxt = (TextView) itemView.findViewById(R.id.vs_txt);
            this.stateImage = (CircleImageView) itemView.findViewById(R.id.stateImage);
        }
    }
}
