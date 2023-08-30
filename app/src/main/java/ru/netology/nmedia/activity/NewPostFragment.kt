package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringProperty
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        arguments?.textArg?.let {
            binding.edit.setText(it)
        }
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val postText = activity?.intent?.getStringExtra("test")
        binding.edit.setText(postText)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            val content = binding.edit.text.toString()
            if (content.isNotBlank()) {
                viewModel.changeContent(content)
                viewModel.save()
            }

            findNavController().navigateUp()
        }


        binding.cancelButton.setOnClickListener {
            val intent = Intent()
            activity?.setResult(Activity.RESULT_CANCELED, intent)
            activity?.finish()
        }

        return binding.newPostFragment
    }

    companion object {
        var Bundle.textArg by StringProperty
    }

}