package com.yourname.btmicfix

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class VoIPAnswerService : AccessibilityService() {

    companion object {
        var instance: VoIPAnswerService? = null
            private set
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {}

    fun answerIncomingCall() {
        val rootNode = rootInActiveWindow ?: return
        val targetKeywords = listOf("Accept", "Answer", "قبول", "رد")
        var buttonNode: AccessibilityNodeInfo? = null

        for (keyword in targetKeywords) {
            val nodes = rootNode.findAccessibilityNodeInfosByText(keyword)
            if (!nodes.isNullOrEmpty()) {
                for (node in nodes) {
                    if (node.isClickable) {
                        buttonNode = node
                        break
                    }
                }
            }
            if (buttonNode != null) break
        }

        buttonNode?.let {
            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            Log.d("VoIPAnswer", "Successfully clicked the answer button!")
        }
    }

    override fun onInterrupt() {}
    
    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
