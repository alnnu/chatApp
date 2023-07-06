package com.example.mychatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.example.mychatapp.models.Message
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatapp.R
import java.util.logging.Logger


class MessageAdapter(val context: Context, val messageList: ArrayList<Message>, val userUid: String?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 2){
            val view: View = LayoutInflater.from(context).inflate(R.layout.reciver_message, parent, false)
            return MessageAdapter.ReciverViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sender_message, parent, false)
            return MessageAdapter.SenderViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val curr = messageList[position]
//        val Log = Logger.getLogger(MessageAdapter::class.java.name)
//        Log.warning(userUid)
//        Log.warning(curr.idUser)
//
//        if(curr.idUser == userUid){
//            holder.sendermensage.visibility = INVISIBLE
//            holder.myMensage.text = curr.text
//        }else {
//            holder.myMensage.visibility = INVISIBLE
//            holder.sendermensage.text = curr.text
//        }

        val curr = messageList[position]

        if(holder.javaClass == SenderViewHolder::class.java) {
            val viewHolder = holder as SenderViewHolder
            holder.myMensage.text = curr.text
        }else {
            val viewHolder = holder as ReciverViewHolder
            holder.sendermensage.text = curr.text
        }
    }


    override fun getItemViewType(position: Int): Int {
        val curr = messageList[position]

        if(curr.idUser == userUid) {
            return 1
        }else {
            return 2
        }
    }

    class SenderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val myMensage = itemView.findViewById<TextView>(R.id.my_message)
    }

    class ReciverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sendermensage = itemView.findViewById<TextView>(R.id.sender_message)
    }
}