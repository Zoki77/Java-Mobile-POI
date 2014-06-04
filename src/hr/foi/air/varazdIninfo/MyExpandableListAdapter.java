package hr.foi.air.varazdIninfo;

import hr.foi.air.types.CheckedElements;
import hr.foi.air.types.Helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

	/**
	 * Custom ExpandableListAdapter
	 *
	 */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    	
    	private LayoutInflater inflater;
    	private Context context;
    	private String[] groups;
    	private String[][] children;
    	private List<CheckedElements> lista;
    	
    	/**
    	 * Constructor for this class
    	 * 
    	 * @param context Context in which will this class be invoked
    	 * @param groups Array of Strings which will be used for group Title
    	 * @param children Array of Strings which will be used for children Title
    	 */
    	public MyExpandableListAdapter(Context context,String[] groups, String children[][]) {
			this.context = context;
			this.groups = groups;
			this.children = children;
			lista = new ArrayList<CheckedElements>();
			inflater = LayoutInflater.from( context );
		}
        
        private int[][] slikeDjeca = {
        		{R.drawable.hypo ,R.drawable.pbz,R.drawable.raiffeisen,R.drawable.splitska,R.drawable.zaba},
        		{R.drawable.hypo,R.drawable.pbz,R.drawable.raiffeisen,R.drawable.splitska,R.drawable.zaba},
        		{R.drawable.ina,R.drawable.omv,R.drawable.tifon,R.drawable.lukoil},
        		{R.drawable.hospital,R.drawable.domzdravlja,R.drawable.pharmacy},
        		{R.drawable.electronics,R.drawable.hrana,R.drawable.odjeca},
        		{R.drawable.hoteli,R.drawable.hostel},
        		{R.drawable.stanleybet,R.drawable.psk,R.drawable.favorit,R.drawable.kasino},
        		{R.drawable.fastfood,R.drawable.japan,R.drawable.hr,R.drawable.pizza},
        		{R.drawable.dvorana,R.drawable.bazen},
        		{R.drawable.bus,R.drawable.vlak,R.drawable.taxi}
                };
        
        private int[] slike = {R.drawable.banka,R.drawable.bankomat,R.drawable.gas
        		,R.drawable.bolnica,R.drawable.shop,R.drawable.hotel,R.drawable.kladionica
        		,R.drawable.restoran,R.drawable.sport,R.drawable.transport};
    	
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
        	Helper.setSlike(slikeDjeca);
        	View v = null;
            v = inflater.inflate(R.layout.child_row, parent, false); 
            final TextView textView = (TextView)v.findViewById(R.id.label);
            textView.setText(getChild(groupPosition, childPosition).toString());
            ImageView iv = (ImageView)v.findViewById(R.id.imageView1);
            iv.setImageDrawable(context.getResources().getDrawable(slikeDjeca[groupPosition][childPosition]));
            final CheckBox cb = (CheckBox)v.findViewById( R.id.cBox );
            if(Helper.getLista()!=null){
	            for(CheckedElements elem:Helper.getLista()){
					if(elem.getGroupPos() == groupPosition && elem.getChildPos() == childPosition){
						cb.setChecked(true);
					}
				}
            }
            
            cb.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if(cb.isChecked()){
						String name = textView.getText().toString();
						lista.add(new CheckedElements(groupPosition, childPosition, name));
						Helper.setLista(lista);
					}
					else{
						for(CheckedElements elem:lista){
							if(elem.getGroupPos() == groupPosition && elem.getChildPos() == childPosition){
								lista.remove(elem);
								break;
							}
						}
						Helper.setLista(lista);
					}
				}
			});
            return v;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
        	View v = null;
            if( convertView != null )
                v = convertView;
            else
                v = inflater.inflate(R.layout.main_row, parent, false); 
            TextView textView = (TextView)v.findViewById(R.id.label);
            textView.setText(getGroup(groupPosition).toString());
            ImageView iv = (ImageView)v.findViewById(R.id.imageView1);
            iv.setImageDrawable(context.getResources().getDrawable(slike[groupPosition]));
            return v;
        }
        
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }

