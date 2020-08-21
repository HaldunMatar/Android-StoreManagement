package com.example.zaitoneh.receiptDetail
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zaitoneh.database.ReceiptDetail
import com.example.zaitoneh.databinding.OneReceiptDetailBinding
import com.example.zaitoneh.employeetracker.toDate
import java.text.SimpleDateFormat

class ReceiptDetailAdapter(val clickListener: ReceiptDetailListener) : ListAdapter <ReceiptDetail,ReceiptDetailAdapter.ViewHolder> (ReceiptDetailDiffCallback()) , Filterable {
    lateinit var mLstReceiptDetail: MutableList<ReceiptDetail>
    lateinit var mFilteredList: MutableList<ReceiptDetail>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receiptDetail = getItem(position)

        holder.bind(clickListener,receiptDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: OneReceiptDetailBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ReceiptDetailListener,receiptDetail: ReceiptDetail) {
            binding.receiptdetail = receiptDetail
            //binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OneReceiptDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    fun updateList(lstReceiptDetail: MutableList<ReceiptDetail>) {
        mLstReceiptDetail = lstReceiptDetail
        mFilteredList=lstReceiptDetail

    }

    /*********************************/

      override fun getFilter(): Filter {

          return object : Filter() {
              override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                  val charString = charSequence.toString()

                  if (charString.isEmpty()) {
                      mFilteredList = mLstReceiptDetail
                  } else {

                      val filteredList = mLstReceiptDetail.filter {  ( it.receiptId.toString()  )==(charString.toLowerCase()) }
                          .toMutableList()
                      mFilteredList = filteredList

                  }



                  val filterResults = Filter.FilterResults()
                  filterResults.values = mFilteredList
                  return filterResults
              }

              override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                  submitList(filterResults.values as MutableList<ReceiptDetail>)
                  notifyDataSetChanged()
              }
          }


      }
    /*********************************************/

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {

        val date = toDate(time)

        // val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val format = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss")
        return format.format(date)
    }
}

class ReceiptDetailDiffCallback : DiffUtil.ItemCallback<ReceiptDetail>() {
    override fun areItemsTheSame(oldItem: ReceiptDetail, newItem: ReceiptDetail): Boolean {
        return oldItem.receiptId == newItem.receiptId
    }
    override fun areContentsTheSame(oldItem: ReceiptDetail, newItem: ReceiptDetail): Boolean {
        return oldItem == newItem
    }
}
class ReceiptDetailListener(val clickListener: (receiptDetailId: Long) -> Unit) {

    fun onClick(receiptDetail: ReceiptDetail) = clickListener(receiptDetail.receiptId)

}
