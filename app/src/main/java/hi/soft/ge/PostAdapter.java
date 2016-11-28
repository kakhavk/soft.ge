package hi.soft.ge;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by kakha on 11/16/16.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<SoftgePost> softge;

    public PostAdapter(ArrayList<SoftgePost> softge){
        this.softge=softge;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int i) {
        String link="<a href=http://soft.ge/?p="+softge.get(i).getId()+">"+softge.get(i).getTitle()+"</a>";

        holder.title.setMovementMethod(LinkMovementMethod.getInstance());

        if(Build.VERSION.SDK_INT<=23){
            holder.title.setText(Html.fromHtml(link));
        }else{
            holder.title.setText(Html.fromHtml(link, 0));
        }

        Spannable s = (Spannable) holder.title.getText();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanline_none(span.getURL());
            s.setSpan(span, start, end, 0);
        }

        holder.title.setText(s);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://soft.ge/?p="+softge.get(i).getId()))
                .setContentTitle(holder.title.getText().toString())
                .setImageUrl(Uri.parse(softge.get(i).getImage()))
                .build();
        holder.date.setText(softge.get(i).getDate());
        holder.fbshare.setShareContent(content);

        Ion.with(holder.thumbnail)
            .placeholder(R.drawable.softge)
            .load(softge.get(i).getImage());
    }

    @SuppressLint("ParcelCreator")
    private class URLSpanline_none extends URLSpan {
        public URLSpanline_none(String url) {
            super(url);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }


    @Override
    public int getItemCount() {
        return softge.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, date;
        private ImageView thumbnail;
        private ShareButton fbshare;

        public ViewHolder(View view) {
            super(view);
            title=(TextView)view.findViewById(R.id.title);
            date=(TextView)view.findViewById(R.id.date);
            thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
            fbshare=(ShareButton) view.findViewById(R.id.fbshare);

        }
    }




}
