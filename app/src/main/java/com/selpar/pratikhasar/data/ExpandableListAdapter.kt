package com.selpar.navigotionview

import android.R
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView


class ExpandableListAdapter(
    context: Context,
    listDataHeader: List<ExpandedMenuModel>,
    listChildData: HashMap<ExpandedMenuModel, List<String>>,
    mView: ExpandableListView
) :
    BaseExpandableListAdapter() {

    private val mContext: Context
    private val mListDataHeader // header titles
            : List<ExpandedMenuModel>

    // child data in format of header title, child title
    private val mListDataChild: HashMap<ExpandedMenuModel, List<String>>
    var expandList: ExpandableListView

    init {
        mContext = context
        mListDataHeader = listDataHeader
        mListDataChild = listChildData
        expandList = mView
    }

    override fun getGroupCount(): Int {
        val i = mListDataHeader.size
        return mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0
        if (groupPosition != 9) {
            childCount = mListDataChild[mListDataHeader[groupPosition]]!!
                .size
        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return mListDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        return mListDataChild[mListDataHeader[groupPosition]]!!
            .get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView: View? = convertView
        val headerTitle: ExpandedMenuModel = getGroup(groupPosition) as ExpandedMenuModel
        if (convertView == null) {
            val infalInflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(com.selpar.pratikhasar.R.layout.listheader, null)
        }
        val lblListHeader = convertView
            ?.findViewById(com.selpar.pratikhasar.R.id.submenu) as TextView
        val headerIcon: ImageView = convertView?.findViewById(com.selpar.pratikhasar.R.id.iconimage) as ImageView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.setText(headerTitle.getIconName())
        headerIcon.setImageResource(headerTitle.getIconImg())
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView: View? = convertView
        val childText = getChild(groupPosition, childPosition) as String
        if (convertView == null) {
            val infalInflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(com.selpar.pratikhasar.R.layout.list_submenu, null)
        }
        val txtListChild = convertView
            ?.findViewById(com.selpar.pratikhasar.R.id.submenu) as TextView
        txtListChild.text = childText
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}