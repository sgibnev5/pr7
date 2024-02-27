package com.example.praktika

import adapter.OnInteractionListener
import adapter.PostsAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.praktika.databinding.ActivityMainBinding
import dto.Post
import util.AndroidUtils
import viewmodel.PostViewModel
import viewmodel.viewModels


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.textView)
            }
        }
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            }
        //val edit = findViewById<EditText>(R.id.edit)
        //val cancelButton = findViewById<Button>(R.id.cancelButton)
        //cancelButton.setOnClickListener {

          //  edit.setText("")
           // cancelButton.visibility = View.GONE
        //}

        //edit.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
          //  if (keyCode == KeyEvent.KEYCODE_BACK) {

            //    edit.setText("")
              //  cancelButton.visibility = View.GONE
               // return@OnKeyListener true
           // }
           // false
       // })

       // edit.setOnTouchListener { v, event ->

         //   cancelButton.visibility = View.VISIBLE
          //  false
       // }

    }
    }



