package com.example.myapplication.ui.prompts_feed

import com.example.myapplication.ui.authorization.UserModel


//class HomeViewModel : ViewModel() {
//
//    private var _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    var text: LiveData<String> = _text
//
////    private val _count = MutableLiveData<Int>().apply {
////        value = 0
////    }
////    val count: LiveData<Int> = _count
//
//    //    private val count: MutableLiveData<Int> by lazy{
////        MutableLiveData<Int>()
////    }
////    fun initCount(){
////        count.value = 0
////    }
////    fun increase() {
////        _count.value = _count.value!!.plus(1)
////    }
//
//    fun setText(text: MutableLiveData<String>) {
//        _text = text
//    }
////    fun jemali(): MutableLiveData<Int>{
////        return text
////    }
//}
class PromptModel {
    var text: String = ""
    var author: UserModel? = null
}