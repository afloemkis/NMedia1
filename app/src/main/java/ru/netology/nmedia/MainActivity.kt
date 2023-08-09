package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likes?.setImageResource(R.drawable.ic_likes_liked)
            }
            likesNumber?.text = showNumbers(post.likes)
            sharesNumber?.text = showNumbers(post.shares)
            viewsNumber?.text = showNumbers(post.views)

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            likes?.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_likes_liked else R.drawable.ic_likes
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesNumber?.text = showNumbers(post.likes)
            }

            share?.setOnClickListener {
                Log.d("stuff", "share")
                post.shares++
                sharesNumber?.text = showNumbers(post.shares)
            }

            views?.setOnClickListener {
                Log.d("stuff", "share")
                post.views++
                viewsNumber?.text = showNumbers(post.views)
            }

            //avatar?.setOnClickListener {
            //    println("avatar")
            //}

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