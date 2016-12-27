package comfranklicm.github.openmind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import comfranklicm.github.openmind.utils.User;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/5
 */
public class FileListViewAdapter extends BaseAdapter{
    private Context context;                        //运行上下文
    public List<Map<String, Object>> listItems;    //信息集合
    private LayoutInflater listContainer;           //视图容器
    final class FileListItems{
        public TextView tubiao;
        public TextView filename;
        public TextView filedownload;
        public TextView filedate;
    }
    public FileListViewAdapter(Context context,List<Map<String, Object>> listItems){
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    public FileListViewAdapter() {
        super();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileListItems fileListItems=null;
        if(convertView==null)
        {
            fileListItems=new FileListItems();
            convertView=listContainer.inflate(R.layout.file_list_view_item,null);
            fileListItems.tubiao=(TextView)convertView.findViewById(R.id.tubiao);
            fileListItems.filename=(TextView)convertView.findViewById(R.id.filename);
            //fileListItems.filedownload=(TextView)convertView.findViewById(R.id.filedownload);
            fileListItems.filedate=(TextView)convertView.findViewById(R.id.filedate);
            convertView.setTag(fileListItems);
        }else {
           fileListItems=(FileListItems)convertView.getTag();
        }
        if((Integer)listItems.get(position).get("filetype")==0) {
            fileListItems.tubiao.setText(R.string.fa_folder_o);
            fileListItems.tubiao.setTypeface(FontManager.getTypeface(User.getInstance().getMyActivity(), FontManager.FONTAWESOME));
            //fileListItems.filedownload.setTypeface(FontManager.getTypeface(User.getInstance().getMyActivity(), FontManager.FONTAWESOME));
            //fileListItems.filedownload.setVisibility(View.GONE);
            fileListItems.filedate.setVisibility(View.GONE);
        }else
        {
            fileListItems.tubiao.setText(R.string.fa_file_o);
            fileListItems.tubiao.setTypeface(FontManager.getTypeface(User.getInstance().getMyActivity(), FontManager.FONTAWESOME));
            //fileListItems.filedownload.setTypeface(FontManager.getTypeface(User.getInstance().getMyActivity(), FontManager.FONTAWESOME));
            //fileListItems.filedownload.setVisibility(View.VISIBLE);
            fileListItems.filedate.setVisibility(View.VISIBLE);
            fileListItems.filedate.setText((String) listItems.get(position).get("filedate"));
        }
        fileListItems.filename.setText((String) listItems.get(position).get("filename"));
        return convertView;
    }
}
