package com.example.kalkulator

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHistoryFragment : Fragment() {

    abstract val title: String

    private lateinit var tvTitle: TextView
    private lateinit var rvHistory: RecyclerView
    private lateinit var fragmentRoot: LinearLayout
    private val adapter = HistoryAdapter()
    private val historyList = mutableListOf<HistoryItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        tvTitle = view.findViewById(R.id.tvTitle)
        rvHistory = view.findViewById(R.id.rvHistory)
        fragmentRoot = view.findViewById(R.id.fragmentRoot)

        tvTitle.text = title
        rvHistory.layoutManager = LinearLayoutManager(requireContext())
        rvHistory.adapter = adapter

        savedInstanceState?.let { bundle ->
            @Suppress("UNCHECKED_CAST")
            val restored = bundle.getSerializable(KEY_HISTORY, ArrayList::class.java) as? ArrayList<HistoryItem>
            if (restored != null) {
                historyList.clear()
                historyList.addAll(restored)
                adapter.setItems(historyList)
                if (historyList.isNotEmpty() && historyList.last().isNegative) {
                    fragmentRoot.setBackgroundColor(Color.parseColor("#FFCDD2"))
                }
            }
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_HISTORY, ArrayList(historyList))
    }

    fun addHistory(expression: String, result: Double) {
        val isNegative = result < 0
        val item = HistoryItem(expression, isNegative)
        historyList.add(item)
        adapter.addItem(item)
        if (::rvHistory.isInitialized && historyList.isNotEmpty()) {
            rvHistory.scrollToPosition(historyList.size - 1)
        }

        // Apabila nilai yang dihasilkan kurang dari 0, ubah background menjadi berwarna merah
        if (::fragmentRoot.isInitialized) {
            if (isNegative) {
                fragmentRoot.setBackgroundColor(Color.parseColor("#FFCDD2"))
            } else {
                fragmentRoot.setBackgroundColor(Color.WHITE)
            }
        }
    }

    companion object {
        private const val KEY_HISTORY = "key_history_list"
    }
}
