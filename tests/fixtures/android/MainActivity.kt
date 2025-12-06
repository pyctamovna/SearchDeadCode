// Test fixture: Android Activity with dead code
package com.example.fixtures.android

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.content.Intent

class MainActivity : Activity() {

    // Used - entry point lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        // Setup code
    }

    // DC001: Unused private method in Activity
    private fun unusedHelper() {
        // Never called
    }

    // DC009: Redundant override (only calls super)
    override fun onDestroy() {
        super.onDestroy()
    }

    // Intent extra - put but never retrieved
    fun startOtherActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("USER_ID", 123)
        intent.putExtra("LEGACY_FLAG", true)  // Never retrieved
        startActivity(intent)
    }

    // Unused parameter
    fun onClick(view: View, unusedParam: String) {
        // unusedParam never used
        view.visibility = View.GONE
    }
}

class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("USER_ID", -1)
        // Note: LEGACY_FLAG is never retrieved!
        println("User: $userId")
    }
}

// Unused BroadcastReceiver
class UnusedReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: android.content.Context?, intent: Intent?) {
        // Never registered
    }
}
