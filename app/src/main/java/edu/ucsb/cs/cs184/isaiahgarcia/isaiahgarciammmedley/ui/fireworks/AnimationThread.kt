package edu.ucsb.cs.cs184.isaiahgarcia.isaiahgarciammmedley.ui.fireworks

import android.os.Handler
import android.os.Looper

/*
 * Java version, Winter 2015, Marty Stepp. Kotlin Adaptation, Fall 2020 UCSB CS184
 * This class is a helper to wrap up some of the icky code needed to
 * initiate an animation thread that repaints a view at regular intervals.
 */ /**
 * This class is a helper to wrap up some of the icky code needed to
 * initiate an animation thread that repaints a view at regular intervals.
 */
class AnimationThread(updateFunction: Runnable, fps: Int) {
    private var m_updateFunction : Runnable
    private var m_fps : Int
    private var m_thread = Thread(MainRunner())
    private var m_isRunning = false
    private var m_handler : Handler

    /**
     * Returns true if the drawing thread is currently started and running.
     */
    fun isRunning(): Boolean {
        return m_isRunning
    }

    /**
     * Starts the thread running so that it will repaint the view repeatedly.
     */
    fun start() {
        if (!m_isRunning) {
            m_thread.start()
            m_isRunning = true
        }
    }

    /**
     * Stops the thread so that it will not repaint the view any more.
     */
    fun stop() {
        if (m_isRunning) {
            m_isRunning = false
            try {
                m_thread.join()
            } catch (ie: InterruptedException) {
                // empty
            }
        }
    }

    /*
     * Small runnable helper class that contains the thread's main loop
     * to repeatedly sleep-and-redraw the view.
     */
    private inner class MainRunner : Runnable {
        override fun run() {
            m_isRunning = true
            while (m_isRunning) {
                try {
                    Thread.sleep(1000 / m_fps.toLong())
                } catch (ie: InterruptedException) {
                    m_isRunning = false
                }

                m_handler.post(Updater())
            }
        }
    }

    /*
     * Small runnable helper class needed by Android to redraw a view.
     */
    private inner class Updater : Runnable {
        override fun run() {
            m_updateFunction.run()
        }
    }

    /**
     * Constructs a new AnimationThread to update the given function
     * the given number of times per second.
     * Does NOT start the thread running; call start() to do so.
     */
    init {
        require(fps > 0)
        this.m_updateFunction = updateFunction
        this.m_fps = fps
        m_handler = Handler(Looper.getMainLooper())
    }
}