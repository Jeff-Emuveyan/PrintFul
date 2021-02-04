package com.seamfix.printful.ui.users

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import com.seamfix.printful.R
import kotlinx.android.synthetic.main.user_item.view.*

class UserItem(v: View): RecyclerView.ViewHolder(v) {
    var tvName: TextView = v.findViewById(R.id.tvName)
    var tvLName: TextView = v.findViewById(R.id.tvLastName)
    var tvEmail: TextView =  v.findViewById(R.id.tvEmail)
    var imageView: ImageView = v.findViewById(R.id.profile_image)
    var shimmerView: ShimmerFrameLayout = v.findViewById(R.id.shimmerView)
    var parentLayout: ConstraintLayout = v.findViewById(R.id.parent_layout)
}