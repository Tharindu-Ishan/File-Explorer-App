package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Arrays;

public class MainFormController {

    public Menu btnNew;
    public Menu btnCut;
    public Menu btnCopy;
    public Menu btnRename;
    public Menu btnDelete;
    public MenuBar mnBar;
    @FXML
    private ListView<String> lstView;

    @FXML
    private TreeView<String> treeView;
    private  String selectedItem;



    public void initialize(){
        mnBar.setDisable(true);

        File[] roots = File.listRoots();
        File[] files = roots[0].listFiles();

        System.out.println(roots[0]);

        TreeItem<String> rootItem = new TreeItem<>(Arrays.toString(roots));
        treeView.setRoot(rootItem);
        listFiles(files[7],rootItem);

        treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, stringTreeItem, newValue) -> {
            lstView.getSelectionModel().clearSelection();
            if (newValue != null) {
                lstView.getItems().clear();
                // Get the selected tree item
                TreeItem<String> selectedItem = newValue;

                // Traverse the tree to get parent nodes
                String parents = "";
                while (selectedItem != null) {
                    parents = selectedItem.getValue() + "/" + parents;
                    selectedItem = selectedItem.getParent();
                }

                // Display the parent nodes
                System.out.println(parents);
                addListView("/home"+parents.substring(3),lstView);
            }
        });
        lstView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            mnBar.setDisable(t1==null);
            selectedItem=t1;
        });




    }


    @FXML
    public  static  void addListView(String parent,ListView<String> lstView){
        File file = new File(parent);
        for (File filex : file.listFiles()) {
            lstView.getItems().add(filex.getName());
        }


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


    public void btnNewOnAction(ActionEvent actionEvent) {

    }

    public void btnCutOnAction(ActionEvent actionEvent) {
    }

    public void btnCopyOnAction(ActionEvent actionEvent) {
    }

    public void btnRenameOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
