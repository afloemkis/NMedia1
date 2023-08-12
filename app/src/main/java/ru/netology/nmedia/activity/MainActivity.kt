package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes?.setImageResource(if (post.likedByMe) R.drawable.ic_likes_liked else R.drawable.ic_likes)
                likesNumber?.text = showNumbers(post.likes)
                sharesNumber?.text = showNumbers(post.shares)
                viewsNumber?.text = showNumbers(post.views)
            }
        }

            with(binding) {
            likes?.setOnClickListener {
                viewModel.like()
            }

            share?.setOnClickListener {
                viewModel.share()
                sharesNumber?.text = viewModel.data.value?.let { it1 -> showNumbers(it1.shares) }
            }

            views?.setOnClickListener {
                viewModel.view()
                viewsNumber?.text = viewModel.data.value?.let { it1 -> showNumbers(it1.views) }
            }

        }

    }

    private fun showNumbers(x: Int): String {
        return when (x) {
            in 0..999 -> x.toString()
            in 1000 .. 10000 -> (x/1000).toString() + "." + (x % 1000 / 100).toString() + "K"
            in 10000 .. 999999 -> (x/1000).toString() + "K"
            else -> (x/1000000).toString() + "." + (x % 1000000 / 100000).toString() + "M"
        }

    }

}