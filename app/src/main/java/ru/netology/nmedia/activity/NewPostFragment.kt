package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
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

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val content = binding.edit.text.toString()
            if (content.isNotBlank()) {
                viewModel.changeContent(content)
                viewModel.save()
            }

            findNavController().navigateUp()
        }


        binding.cancelButton.setOnClickListener {
            viewModel.undoEdit()
            findNavController().navigateUp()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle(getString(R.string.headlineNewPostSystemBack))
                        setMessage(getString(R.string.messageNewPostSystemBack))
                        setPositiveButton(getString(R.string.yesButton)) { _, _ ->
                            viewModel.undoEdit()
                            findNavController().navigateUp()
                        }
                        setNegativeButton(getString(R.string.NoButton)) { _, _ -> }
                        setCancelable(true)
                    }.create().show()
                }
            }
        )

        return binding.newPostFragment
    }

    companion object {
        var Bundle.textArg by StringProperty
    }

}