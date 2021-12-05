package com.leencecodes.nifixiegari.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kanyideveloper.kenyan_counties.Kenya.Companion.counties
import com.leencecodes.nifixiegari.R
import com.leencecodes.nifixiegari.databinding.ActivitySkillsBinding
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap


class SkillsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySkillsBinding
    private var garage: String? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mFirebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        mFirebaseAuth = FirebaseAuth.getInstance()


        // Create an empty list
        val skills = mutableListOf<String>()

        // Loop through the chips
        for (index in 0 until binding.chipGroup.childCount) {
            val chip:Chip = binding.chipGroup.getChildAt(index) as Chip

            // Set the chip checked change listener
            chip.setOnCheckedChangeListener{view, isChecked ->
                if (isChecked){
                    skills.add(view.text.toString())
                }else{
                    skills.remove(view.text.toString())
                }
            }
        }

        binding.buttonSaveSkills.setOnClickListener {
            if (skills.isNotEmpty()){
                // SHow the selection
                Toast.makeText(applicationContext, skills.toString(), Toast.LENGTH_SHORT).show()

                val separator = ","

                val string = skills.joinToString(separator)

                saveSkills(string)
                updateGarage("Joel Kanyi")


                val intent = Intent(this@SkillsActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(applicationContext, "No Skills Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSkills(skillsList: String){
        databaseReference.child("mechanics").child(mFirebaseAuth.currentUser!!.uid).child("skills").setValue(skillsList)
    }

    private fun updateGarage(name: String){
        var key: String? = null
        databaseReference.child("featured_garages").orderByChild("garageName").equalTo(binding.fgarage.selectedItem.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    key = ds.key
                    val hashMap:HashMap<String,String> = HashMap<String,String>()
                    hashMap["name"] = ""+intent.getStringExtra("NAME")
                    databaseReference.child("featured_garages").child(key.toString()).child("mechs").child("testing2").setValue(hashMap)
                    return
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}