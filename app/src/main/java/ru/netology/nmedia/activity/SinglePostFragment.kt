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
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class SinglePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSinglePostBinding.inflate(layoutInflater, container, false)
        val text = List(100) {it}.joinToString (separator = "\n")
        binding.singlePost.content.text = text
        val viewModel: PostViewModel by viewModels(ownerProducer =  ::requireParentFragment)
        val holder = PostViewHolder(binding.singlePost, object: OnInteractionListener{
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
                findNavController().navigate(R.id.action_feedFragments_to_singlePostFragment)
                Bundle().apply {
                    textArg  = post.content
                }
            }
        })
        holder.bind(Post(0,"Author", text, "30.08", 11,false))
        return binding.root
    }


}