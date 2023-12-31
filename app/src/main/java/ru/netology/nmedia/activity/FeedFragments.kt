package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragments : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer =  ::requireParentFragment)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragments_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

                viewModel.shareById(post.id)
            }

            override fun onVideo(post: Post) {
                if (!post.video.isNullOrEmpty()) {
                    val videoUri = Uri.parse(post.video)
                    val intent = Intent(Intent.ACTION_VIEW, videoUri)
                    startActivity(intent)
                }
            }

            override fun onView(post: Post) {
                viewModel.viewById(post.id)
            }

            override fun onSinglePost(post: Post) {
                val bundle = Bundle().apply {
                    putLong("postId", post.id) // Устанавливаем postId в Bundle
                    putString("textArg", post.content) // Устанавливаем textArg в Bundle
                }

                findNavController().navigate(
                    R.id.action_feedFragments_to_singlePostFragment,
                    bundle // Передаем Bundle вместе с навигацией
                )

            //                findNavController().navigate(R.id.action_feedFragments_to_singlePostFragment)
//                Bundle().apply {
//                    postId = post.id
//                }
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragments_to_newPostFragment)

        }
        return binding.feedFragments
    }

}





