package lk.ijse.dep11;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.Arrays;

public class MainFormController {

    @FXML
    private ListView<String> lstView;

    @FXML
    private TreeView<String> treeView;

    public void initialize(){
        File[] roots = File.listRoots();
        File[] files = roots[0].listFiles();

        System.out.println(roots[0]);

        TreeItem<String> rootItem = new TreeItem<>(Arrays.toString(roots));
        treeView.setRoot(rootItem);
        listFiles(files[7],rootItem);

    }
    public static void listFiles(File root,TreeItem<String> rootItem){
        File[] listFiles = root.listFiles();
        for (File file : listFiles) {

            if(file.isDirectory()) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                rootItem.getChildren().add(item);
                System.out.println(file.getName());
                listFiles(file,item);
            }
        }

    }


}
