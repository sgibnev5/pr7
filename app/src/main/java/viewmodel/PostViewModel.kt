package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dto.Post
import repository.PostRepository
import repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    textView = "",
    textView2 = "",
    likedByMe = false,
    textView4 = ""
)
class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.textView == text) {
            return
        }
        edited.value = edited.value?.copy(textView = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
}