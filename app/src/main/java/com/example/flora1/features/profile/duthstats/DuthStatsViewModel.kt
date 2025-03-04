package com.example.flora1.features.profile.duthstats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flora1.navigationroot.main.databaseRef
import com.example.flora1.navigationroot.main.incrementClick
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class DuthStatsViewModel : ViewModel() {

    var stats: List<Pair<String, Long>> by mutableStateOf(emptyList())
        private set

    init {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                stats = snapshot
                    .getValue<Map<String, Long>>()
                    ?.toList()
                    ?.sortedByDescending { it.second }
                    ?: emptyList()
            }

            override fun onCancelled(error: DatabaseError) {
                databaseRef.incrementClick(error.toString())
            }
        })
    }
}
