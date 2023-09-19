package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class MainFormController {


    public Button btnRename;
    public Button btnDelete;
    public Button btnNew;
    public Button btnCopy;
    public Button btnPaste;
    @FXML
    private ListView<String> lstView;

    @FXML
    private TreeView<String> treeView;
    private  String selectedItem;
    boolean isCut=false;


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
                addListView("/home"+parents.substring(3));
            }
        });
        lstView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            selectedItem=t1;
        });
        rootItem.setExpanded(true);


    }

    public void addListView(String parent){
        File file = new File(parent);
        for (File filex : file.listFiles()) {
            lstView.getItems().add(filex.getName());

        }

    }
    public void listFiles(File root,TreeItem<String> rootItem){
        File[] listFiles = root.listFiles();
        for (File file : listFiles) {

            if(file.isDirectory()) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                if(file.getName().equals("tharindu")){
                    item.setExpanded(true);
                }
                rootItem.getChildren().add(item);
                System.out.println(file.getName());
                listFiles(file,item);
            }
        }
    }
    public void btnNewOnAction(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Folder");
        dialog.setHeaderText("Enter name:");
        Optional<String> result = dialog.showAndWait();

// Check if the user clicked OK and get the new name
        String folderName="";
        if (result.isPresent()) {
            folderName = result.get();

        }
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

            File newFile = new File("/home" + parents.substring(3) +folderName);
            newFile.mkdir();
            lstView.refresh();
        }
        initialize();
    }
    public void btnCutOnAction(ActionEvent actionEvent) {
        copyFileOrFolderToClipboard();
        isCut=true;

    }

    public void btnCopyOnAction(ActionEvent actionEvent) {
        copyFileOrFolderToClipboard();
    }
    File selectedFileOrFolder;
    private void copyFileOrFolderToClipboard() {

        // Use a FileChooser to let the user select a file or folder
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

            selectedFileOrFolder = new File(path);
        }
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
            oldFile.delete();
            lstView.refresh();
        }
        initialize();
    }

    public void btnPasteOnAction(ActionEvent actionEvent) throws IOException {
        if (selectedFileOrFolder!=null&&selectedFileOrFolder.isFile()) {
            FileInputStream fis = new FileInputStream(selectedFileOrFolder);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] bytes = bis.readAllBytes();
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
                String path = "/home" + parents.substring(3) + selectedFileOrFolder;
                System.out.println(path);
                File oldFile = new File(path);
                FileOutputStream fos = new FileOutputStream(oldFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(bytes);
            }
            System.out.println("File copied to clipboard: " + selectedFileOrFolder.getAbsolutePath());
        } else if (selectedFileOrFolder!=null&&selectedFileOrFolder.isDirectory()) {
            System.out.println("Folder copied to clipboard: " + selectedFileOrFolder.getAbsolutePath());
        }
        if(isCut){
            selectedFileOrFolder.delete();
        }


    }
}
