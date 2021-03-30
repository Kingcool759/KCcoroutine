package com.example.kccoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        runBlocking()
        launch()
    }

    /**
     * 开启协程
     * 1、runBlocking
     * 2、launch
     */

    /**
     * 【runBlocking详解】：
     * runBlocking启动的协程任务会阻断当前线程，直到该协程执行结束。
     * runBlocking中调用launch()会在当前线程中执行协程，
     * 也就是说在runBlocking中不管开启多少个子协程它们都是使用runBlocking所使用的那一条线程来完成任务的，
     * 所以就会出现多个子协程霸占线程的情况。
     */

    //第一种方式
    fun runBlocking() = kotlinx.coroutines.runBlocking{
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
        //查看任务是否在同一个线程中
        repeat(5) {
            Log.i("minfo", "协程执行$it,当前线程id:${Thread.currentThread().id}")
            //这里发现重复执行的任务，都处于同一线程中
            delay(1000)
        }
    }

    /**
     * 【GlobalScope.launch{}详解】：
     *  可以切换协程到子线程中执行，然后在切回主线程更新UI
     *  不会出现线程被霸占的情况
     */

    //第二种方式
    fun launch(){
        GlobalScope.launch(Dispatchers.IO) {
            println("launch3")
            delay(1000)
            println("launch3 finished")
            //查看任务是否在同一个线程中
            repeat(5) {
                Log.i("minfo", "协程执行$it,当前线程id:${Thread.currentThread().id}")
                //这里发现重复执行的任务，都处于不同的线程中
                delay(1000)
            }
        }

//        GlobalScope.launch(Dispatchers.Main){
//            //launch里边被包着的代码块就叫做协程，也就是下面两行
//            val data = getData()
//            priceTv.text = data.price
//        }
//        /**
//         * suspend意为挂起
//         * 使用withContext函数的时候需要将方法声明为可挂起状态
//         * 挂起：
//         * 既能切线程又能自动切回来的
//         */
//        suspend fun getData():Data{
//            return withContext(Dispatchers.IO){
//                ApiService.getData()
//            }
//        }

//        GlobalScope.launch(Dispatchers.main){
//            val data = withContext(Dispatchers.IO){
//                apiService.getData()
//            }
//            priceTv.text = data.price
//        }
    }
}