package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class MainFormController {


    public Button btnRename;
    @FXML
    private ListView<String> lstView;

    @FXML
    private TreeView<String> treeView;
    private  String selectedItem;



    public void initialize(){


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
//                System.out.println(parents);
                addListView("/home"+parents.substring(3),lstView);
            }
        });
        lstView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {

            selectedItem=t1;
        });




    }



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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename");
        dialog.setHeaderText("Enter the new name:");
        Optional<String> result = dialog.showAndWait();

// Check if the user clicked OK and get the new name
        String newName="";
        if (result.isPresent()) {
            newName = result.get();

        }
        String selectedItem1 = lstView.getSelectionModel().getSelectedItem();
        TreeItem<String> newValue = treeView.getSelectionModel().getSelectedItem();
        if (newValue != null) {
//            lstView.getItems().clear();
            // Get the selected tree item
            TreeItem<String> selectedItem = newValue;

            // Traverse the tree to get parent nodes
            String parents = "";
            while (selectedItem != null) {
                parents = selectedItem.getValue() + "/" + parents;
                selectedItem = selectedItem.getParent();
            }
            String path = "/home" + parents.substring(3) + selectedItem1;
            System.out.println(path);
            File oldFile = new File(path);
            File newFile = new File("/home" + parents.substring(3) +newName);
            oldFile.renameTo(newFile);
            lstView.refresh();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }
}
