package com.simgesengun.gameapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.simgesengun.gameapplication.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding : FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater,container,false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        gameSetup()

        binding.imageButtonRestart.setOnClickListener {
            val snackbar = Snackbar.make(it,"Oyun yenilensin mi?",Snackbar.LENGTH_INDEFINITE)
                .setAction("Evet"){
                    gameSetup()
                    Snackbar.make(it,"Oyun yenilendi",Snackbar.LENGTH_SHORT).show()
                }
                .setActionTextColor(Color.YELLOW)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.DKGRAY)
            snackbar.view.setOnClickListener {
                snackbar.dismiss()
            }
            snackbar.show()
        }

        return binding.root
    }

    private fun gameSetup(){
        val random = (0..2).random()
        Toast.makeText(context,random.toString(),Toast.LENGTH_LONG).show()
        when(random){
            0 -> {
                binding.button.setAlertDialog(true)
                binding.button2.setAlertDialog()
                binding.button3.setAlertDialog()
            }
            1 -> {
                binding.button.setAlertDialog()
                binding.button2.setAlertDialog(true)
                binding.button3.setAlertDialog()
            }
            2 -> {
                binding.button.setAlertDialog()
                binding.button2.setAlertDialog()
                binding.button3.setAlertDialog(true)
            }
        }
    }

    private fun Button.setAlertDialog(isWinning : Boolean = false){
        setOnClickListener {
            val design = layoutInflater.inflate(R.layout.custom_alert,null)
            val imageViewAlert = design.findViewById<ImageView>(R.id.imageViewAlert)

            val ad = AlertDialog.Builder(context)
            ad.setMessage("Tekrar denemek ister misiniz?")
            if(isWinning){
                ad.setTitle("Kazandınız!")
                imageViewAlert.setImageResource(R.drawable.ic_happy)
            }else{
                ad.setTitle("Kaybettiniz!")
                imageViewAlert.setImageResource(R.drawable.ic_sad)
            }
            ad.setView(design)
            ad.setPositiveButton("Tekrar Dene"){ _,_ ->
                gameSetup()
            }
            ad.setNegativeButton("Çıkış Yap"){ _,_ ->
                exit()
            }
            ad.create().show()
        }
    }
    private fun exit(){
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_exit -> {
                exit()
                true
            }
            else -> false
        }
    }

}