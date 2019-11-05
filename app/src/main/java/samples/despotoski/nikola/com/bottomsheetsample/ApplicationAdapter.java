package samples.despotoski.nikola.com.bottomsheetsample;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nikola D. on 2/25/2016.
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private PackageManager mPackageManager;
    private Context mContext;

    public ApplicationAdapter(Context context, List<ApplicationInfo> mApplications) {
        this.mApplications = mApplications;
        mContext = context;
        mPackageManager = mContext.getPackageManager();
    }

    private List<ApplicationInfo> mApplications;

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplicationViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        ApplicationInfo applicationInfo = mApplications.get(position);
        holder.appName.setText(applicationInfo.loadLabel(mPackageManager));
        holder.appIcon.setImageDrawable(applicationInfo.loadIcon(mPackageManager));

    }

    @Override
    public int getItemCount() {
        return mApplications == null ? 0 : mApplications.size();
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        ImageView appIcon;
        TextView appName;

        public ApplicationViewHolder(Context context) {
            this(LayoutInflater.from(context).inflate(R.layout.application_item, null));
        }

        private ApplicationViewHolder(View itemView) {
            super(itemView);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            appName = (TextView) itemView.findViewById(R.id.app_name);
        }
    }
}
