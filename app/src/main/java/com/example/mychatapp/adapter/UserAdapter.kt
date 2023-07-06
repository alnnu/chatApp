package com.example.mychatapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mychatapp.R
import com.example.mychatapp.models.User
import com.example.mychatapp.views.Chat
import java.awt.font.TextAttribute


class UserAdapter(val context: Context, val usersList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = usersList[position]
        holder.nome.text = curr.nome

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Chat::class.java)

            val args = Bundle()

            args.putString("nome", curr.nome)
            args.putString("uid", curr.uid)

            intent.putExtras(args)
            context.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.txt_nome)
    }

}