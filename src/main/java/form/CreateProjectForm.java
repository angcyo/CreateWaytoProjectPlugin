package form;

import com.angcyo.wayto.Config;
import com.angcyo.wayto.ConfigKt;
import com.angcyo.wayto.dialog.BrowseFileHelper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;

import javax.swing.*;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CreateProjectForm {
    private JPanel rootFormPanel;
    private JTextField projectNameEdit;
    private JTextField packageNameEdit;
    private JTextField moduleNameEdit;
    private JTextField schemaEdit;
    private JLabel selectorLocalPathLabel;
    private JLabel selectorCorePathLabel;
    private JLabel selectorPluginPathLabel;
    private JPanel selectorLocalPathPanel;
    private JPanel selectorCorePathPanel;
    private JPanel selectorPluginPathPanel;

    private TextFieldWithBrowseButton selectorLocalPathButton;
    private TextFieldWithBrowseButton selectorCorePathButton;
    private TextFieldWithBrowseButton selectorPluginPathButton;

    public JPanel getRootFormPanel() {
        return rootFormPanel;
    }

    public void init() {
        addBrowseButton();

        Config config = ConfigKt.defaultConfig();

        projectNameEdit.setText(config.getProjectName());
        moduleNameEdit.setText(config.getModuleName());
        packageNameEdit.setText(config.getPackageName());
        schemaEdit.setText(config.getSchema());

        selectorLocalPathButton.setText(config.getSavePath());
        selectorCorePathButton.setText(config.getCorePath());
        selectorPluginPathButton.setText(config.getPluginPath());
    }

    public void addBrowseButton() {
        //KtKt.getL().warn(((FormLayout) rootFormPanel.getLayout()).getConstraints(selectorLocalPathLabel).toString());
        selectorLocalPathButton = BrowseFileHelper.INSTANCE.addBrowseButton(selectorLocalPathPanel, selectorLocalPathLabel);
        selectorCorePathButton = BrowseFileHelper.INSTANCE.addBrowseButton(selectorCorePathPanel, selectorCorePathLabel);
        selectorPluginPathButton = BrowseFileHelper.INSTANCE.addBrowseButton(selectorPluginPathPanel, selectorPluginPathLabel);
    }

    public Config readConfig() {
        Config config = new Config();

        config.setProjectName(projectNameEdit.getText());
        config.setModuleName(moduleNameEdit.getText());
        config.setPackageName(packageNameEdit.getText());
        config.setSchema(schemaEdit.getText());

        config.setSavePath(selectorLocalPathButton.getText());
        config.setCorePath(selectorCorePathButton.getText());
        config.setPluginPath(selectorPluginPathButton.getText());

        return config;
    }

    public ValidationInfo validation() {
        Config config = readConfig();

        if (config.getProjectName().isEmpty()) {
            return new ValidationInfo("请输入工程名", projectNameEdit);
        }
        if (config.getModuleName().isEmpty()) {
            return new ValidationInfo("请输入模块名", moduleNameEdit);
        }
        if (config.getPackageName().isEmpty()) {
            return new ValidationInfo("请输入包名", packageNameEdit);
        }
        if (config.getSchema().isEmpty()) {
            return new ValidationInfo("请输入Schema", schemaEdit);
        }
        if (config.getSavePath().isEmpty()) {
            return new ValidationInfo("请选择保存路径", selectorLocalPathButton);
        } else {
//            File savePathFile = new File(config.getSavePath());
//            if (savePathFile.exists()) {
//            } else {
//                savePathFile.mkdirs();
//                //return new ValidationInfo("保存路径不存在", selectorLocalPathButton);
//            }
        }
        return null;
    }
}
