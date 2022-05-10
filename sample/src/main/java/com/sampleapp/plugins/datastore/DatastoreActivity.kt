package com.sampleapp.plugins.datastore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.pluto.plugins.datastore.pref.PlutoDataStoreWatcher
import com.sampleapp.databinding.ActivityDatastorePreferencesBinding
import kotlinx.coroutines.launch

class DatastoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatastorePreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            finish()
        }
        binding.button.setOnClickListener {
            initDataForDataStoreSample()
        }
        initDataForDataStoreSample()
    }

    private fun initDataForDataStoreSample() {
        PlutoDataStoreWatcher.watch("preference_name", dataStore)
        PlutoDataStoreWatcher.watch("user_info", dataStore2)
        lifecycleScope.launch {
            dataStore2.edit {
                it[booleanPreferencesKey("isLoggedIn")] = true
                it[stringPreferencesKey("auth_token")] = "asljknva38uv972gv"
                it[stringPreferencesKey("refresh_token")] = "iuch21d2c1acbkufh2918hcb1837bc1a"
            }
            dataStore.edit {
                it[booleanPreferencesKey("random_boolean")] = false
                it[stringPreferencesKey("random_string")] = "random string value"
                it[longPreferencesKey("random_long")] = RANDOM_LONG
                it[floatPreferencesKey("random_float")] = PI_VALUE
            }
        }
    }

    companion object {
        const val RANDOM_LONG = 13_101_993L
        const val PI_VALUE = 3.141592653589793238462643383279502884197f
    }
}

private val Context.dataStore by preferencesDataStore(
    name = "preference name"
)
private val Context.dataStore2 by preferencesDataStore(
    name = "user_info"
)