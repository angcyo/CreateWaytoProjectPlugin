package com.angcyo.wayto.dialog

import com.intellij.ide.actions.OpenProjectFileChooserDescriptor
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import javax.swing.JComponent
import javax.swing.JPanel

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object BrowseFileHelper {

    fun addBrowseButton(panel: JPanel, anchorComponent: JComponent? = null): TextFieldWithBrowseButton {
        val textFieldWithBrowseButton = TextFieldWithBrowseButton().apply {
            addBrowseFolderListener(
                TextBrowseFolderListener(
                    OpenProjectFileChooserDescriptor(
                        false
                    ).apply {
                        title = "选择本地目录路径"
                    }
                )
            )
        }
        panel.add(textFieldWithBrowseButton)
//        if (layout is FormLayout) {
//            //CellConstraints[x=1; y=14; w=1; h=1; hAlign=fill; vAlign=default]
//            L.warn(layout.rowCount.toString())
//            L.warn(layout.columnCount.toString())
//
//            val gridY = layout.getConstraints(anchorComponent).gridY + 1
//
////            panel.add(
////                textFieldWithBrowseButton,
////                CellConstraints(
////                    1, gridY,
////                    1, 1,
////                    CellConstraints.FILL, CellConstraints.FILL
////                )
////            )
//
//            panel.add(
//                JLabel().apply {
//                    text = "test"
//                    size = Dimension(100, 100)
//                    minimumSize = Dimension(100, 100)
//                    preferredSize = Dimension(100, 100)
//
//                }, CellConstraints(
//                    1, gridY,
//                    1, 1,
//                    CellConstraints.FILL, CellConstraints.DEFAULT
//                )
//            )
//
//        } else {
//            panel.add(textFieldWithBrowseButton)
//        }

        return textFieldWithBrowseButton
    }
}