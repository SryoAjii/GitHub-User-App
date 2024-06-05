package com.example.fundafirstsub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundafirstsub.data.response.ItemsItem
import com.example.fundafirstsub.databinding.FragmentFollowBinding
import com.example.fundafirstsub.viewmodel.FollowerViewModel
import com.example.fundafirstsub.viewmodel.FollowingViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var index: Int = 0
    private var username: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            index = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(context)
        binding.followList.layoutManager = layoutManager

        val followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)

        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        followerViewModel.followerLoading.observe(viewLifecycleOwner) {
            followLoading(it)
        }

        followingViewModel.followingLoading.observe(viewLifecycleOwner) {
            followLoading(it)
        }

        if (index == 1) {
            followerViewModel.follower.observe(viewLifecycleOwner) { follower ->
                setFollower(follower)
            }
            username?.let { followerViewModel.getFollower(it) }
        } else {
            followingViewModel.following.observe(viewLifecycleOwner) { following ->
                setFollowing(following)
            }
            username?.let { followingViewModel.getFollowing(it) }
        }
    }

    private fun setFollower(follower: List<ItemsItem>) {
        val followerAdapter = FollowAdapter()
        followerAdapter.submitList(follower)
        binding.followList.adapter = followerAdapter
    }

    private fun setFollowing(following: List<ItemsItem>) {
        val followingAdapter = FollowAdapter()
        followingAdapter.submitList(following)
        binding.followList.adapter = followingAdapter
    }

    private fun followLoading(state: Boolean) {
        binding.followProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_USERNAME = "ARG_USERNAME"
    }

}