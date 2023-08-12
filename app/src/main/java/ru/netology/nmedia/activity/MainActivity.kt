package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter ({
            viewModel.likeById(it.id)
        },
            {    viewModel.shareById(it.id)
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

//   with(binding) {
//   likes?.setOnClickListener {
//       viewModel.like()
//   }

//   share?.setOnClickListener {
//       viewModel.share()
//       sharesNumber?.text = viewModel.data.value?.let { it1 -> showNumbers(it1.shares) }
//  }

//   views?.setOnClickListener {
//       viewModel.view()
//       viewsNumber?.text = viewModel.data.value?.let { it1 -> showNumbers(it1.views) }
    }
}


private fun showNumbers(x: Int): String {
    return when (x) {
        in 0..999 -> x.toString()
        in 1000..10000 -> (x / 1000).toString() + "." + (x % 1000 / 100).toString() + "K"
        in 10000..999999 -> (x / 1000).toString() + "K"
        else -> (x / 1000000).toString() + "." + (x % 1000000 / 100000).toString() + "M"
    }

}

